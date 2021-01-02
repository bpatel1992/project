package com.rahul.project.gateway.dao;

import com.rahul.project.gateway.configuration.annotations.RepositoryDao;
import com.rahul.project.gateway.dto.PartnerRequestDTO;
import com.rahul.project.gateway.dto.PartnerResponseDTO;
import com.rahul.project.gateway.model.Partner;
import com.rahul.project.gateway.model.TimeRange;
import com.rahul.project.gateway.repository.UserAddressTimingRepository;
import com.rahul.project.gateway.utility.CommonUtility;
import com.rahul.project.gateway.utility.Translator;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

@RepositoryDao
public class PartnerDao {

    @Autowired
    AbstractDao abstractDao;

    @Autowired
    CommonUtility commonUtility;

    @Autowired
    Translator translator;

    @Autowired
    private UserAddressTimingRepository userAddressTimingRepository;


    public List<PartnerResponseDTO> getPartnerByLocationAndPartnerType(PartnerRequestDTO partnerRequestDTO) throws Exception {
        int startPage = partnerRequestDTO.getPageNumber() * partnerRequestDTO.getPageSize();
        int offset = startPage + partnerRequestDTO.getPageSize();
        Session session = abstractDao.getSession();
        int inKm = 6371;
        if (partnerRequestDTO.getDistanceUnit() != null && partnerRequestDTO.getDistanceUnit().equalsIgnoreCase("miles")) {
            inKm = 3959;
        }
        String queryMain = "Select *,(" + inKm + " * acos (cos ( radians(" + partnerRequestDTO.getLattitude() + ") )* cos( radians( lattitude ) ) * cos( radians( longitude ) - radians(" + partnerRequestDTO.getLongitude() + "))" +
                "      + sin ( radians(" + partnerRequestDTO.getLattitude() + ") )* sin( radians( lattitude)))) AS distance FROM partner_address " +
                "HAVING distance < " + partnerRequestDTO.getDistance() + " ORDER BY distance LIMIT " + startPage + "," + offset;

        Query partnerQuery = session.createSQLQuery(queryMain);
        List partnerAddressList = partnerQuery.getResultList();
        List<PartnerResponseDTO> partnerResponseDTOList = new ArrayList<>();
        if (partnerAddressList != null && partnerAddressList.size() > 0) {
            for (int i = 0; i < partnerAddressList.size(); i++) {
                Object[] resultSet = (Object[]) partnerAddressList.get(i);
                String partnerHqlQuery = null;
                if (partnerRequestDTO.getPartnerTypeId() <= 0) {
                    partnerHqlQuery = "From PartnerM where id = " + Long.valueOf(resultSet[5].toString());
                } else {

                    partnerHqlQuery = "Select p From PartnerM p ,IN (p.partnerTypes) pt where p.id = " + Long.valueOf(resultSet[5].toString()) + " AND pt.id=" + partnerRequestDTO.getPartnerTypeId();
                }

                Query query = session.createQuery(partnerHqlQuery);
                List<Partner> partnerList = (List<Partner>) query.getResultList();
                if (partnerList == null || partnerList.isEmpty()) {
                    continue;
                }
                Calendar time = Calendar.getInstance();
                time.setTime(commonUtility.getOnlyDate(partnerRequestDTO.getDate()));
                LocalDateTime localDateTime = LocalDateTime.ofInstant(time.toInstant(), time.getTimeZone().toZoneId());
                int day = localDateTime.getDayOfWeek().getValue();
                Partner partner = partnerList.get(0);
                PartnerResponseDTO partnerResponseDTO = new PartnerResponseDTO();
                partnerResponseDTO.setAddressId(Long.valueOf(resultSet[0].toString()));
                partnerResponseDTO.setAddress(resultSet[1].toString());
                partnerResponseDTO.setLattitude(Double.valueOf(resultSet[2].toString()));
                partnerResponseDTO.setLongitude(Double.valueOf(resultSet[3].toString()));
                partnerResponseDTO.setId(Long.valueOf(resultSet[5].toString()));
                partnerResponseDTO.setDistance(Double.valueOf(resultSet[6].toString()));
                partnerResponseDTO.setPartnerName(partner.getName());
                partnerResponseDTO.setPartnerDesc(partner.getPartnerDesc());
                partnerResponseDTO.setPartnerExperience(partner.getPartnerExperience());
                partnerResponseDTO.setEmail(partner.getEmail());
                partnerResponseDTO.setMobile(partner.getMobile());
                partnerResponseDTO.setTimeRange(fetchTimeRangeAndCheckIfClinicIsOpen(localDateTime, day, partner, partnerResponseDTO));
                partnerResponseDTOList.add(partnerResponseDTO);

            }
        }
        return partnerResponseDTOList;
    }

    private List<String> fetchTimeRangeAndCheckIfClinicIsOpen(LocalDateTime localDateTime, int day, Partner partner, PartnerResponseDTO partnerResponseDTO) {
        Set<TimeRange> timeRanges = userAddressTimingRepository.getTimeRange(partner.getId(), partnerResponseDTO.getAddressId(), String.valueOf(day));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        LocalTime currentTime = localDateTime.toLocalTime();
        List<String> timeRangeList = new ArrayList<>();
        for (TimeRange timeRange : timeRanges) {
            timeRangeList.add(timeRange.getTimeRange());
            String[] timeRangeArr = timeRange.getTimeRange().split("\\-");
            LocalTime fromTime = LocalTime.parse(timeRangeArr[0].trim().
                    replace("p", "PM").replace("a", "AM"), formatter);
            LocalTime toTime = LocalTime.parse(timeRangeArr[1].trim().
                    replace("p", "PM").replace("a", "AM"), formatter);
            if((currentTime.compareTo(fromTime) >= 0) &&
                    (currentTime.compareTo(toTime) <= 0)){
                partnerResponseDTO.setClinicOpen(Boolean.TRUE);
            }
        }
        return timeRangeList;
    }

}
