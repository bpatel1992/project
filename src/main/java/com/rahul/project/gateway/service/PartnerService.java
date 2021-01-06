package com.rahul.project.gateway.service;

import com.rahul.project.gateway.configuration.BusinessException;
import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.crud.uiBeans.BusinessAddress;
import com.rahul.project.gateway.dao.AbstractDao;
import com.rahul.project.gateway.dao.PartnerDao;
import com.rahul.project.gateway.dto.*;
import com.rahul.project.gateway.model.Country;
import com.rahul.project.gateway.model.Partner;
import com.rahul.project.gateway.model.*;
import com.rahul.project.gateway.repository.*;
import com.rahul.project.gateway.utility.CommonUtility;
import com.rahul.project.gateway.utility.Translator;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.lang.invoke.MethodHandles;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

@TransactionalService
public class PartnerService {

    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private AbstractDao abstractDao;
    @Autowired
    private PartnerDao partnerDao;
    @Autowired
    private Translator translator;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CommonUtility commonUtility;
    @Autowired
    private Environment environment;
    @Autowired
    @Qualifier("PartnerRepository")
    private PartnerRepository partnerRepository;
    @Autowired
    private UserDocumentRepository userDocumentRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PartnerDocumentRepository partnerDocumentRepository;
    @Autowired
    private UserPartnerRelationMPRepository userPartnerRelationMPRepository;
    @Autowired
    private PartnerAddressTimingRepository partnerAddressTimingRepository;
    @Autowired
    private UserAddressTimingRepository userAddressTimingRepository;

    public void registerPartner(Partner partner) throws Exception {
        partner.getPartnerAddresses().stream().forEach(partnerAddress -> {
            // partnerAddress.setPartner(partner);
        });
        abstractDao.saveOrUpdateEntity(partner);
    }

    public ResponseDTO userUpdateCreate(ResponseDTO responseDTO) {
        responseDTO.setAllowed(true);
        return responseDTO;
    }

    public Set<PartnerAddressDTO> fetchPartnerAddress(PartnerAddressDTO partnerAddressDTO, ResponseDTO responseDTO) {
        Set<PartnerAddressDTO> partnerAddressDTOS = null;
        Set<PartnerAddress> partnerAddress = null;
        if (partnerAddressDTO.getIsPartner()) {
            partnerAddress = partnerAddressTimingRepository.getPartnerAddress(partnerAddressDTO.getPartnerId());
            partnerAddress.forEach(partnerAddress1 ->
            {
                Set<BusinessTiming> businessTimings =
                        partnerAddressTimingRepository.businessTimings
                                (partnerAddressDTO.getPartnerId(), partnerAddress1.getId());
                partnerAddress1.setBusinessTimings(businessTimings);
            });
        } else {
           /* if (partnerAddress.getId() != null) {
                PartnerAddress entity = abstractDao.getEntityById(PartnerAddress.class, partnerAddress.getId());
                CrudCtrlBase.copyNonNullProperties(entity, partnerAddress);
            }*/
            partnerAddress = userAddressTimingRepository.getPartnerAddress(commonUtility.getLoggedInUser());
            partnerAddress.forEach(partnerAddress1 ->
            {
                Set<BusinessTiming> businessTimings = userAddressTimingRepository.
                        businessTimingsByUserIdAndPartnerAddressId(commonUtility.getLoggedInUser(), partnerAddress1.getId());
                partnerAddress1.setBusinessTimings(businessTimings);
            });
        }
        partnerAddressDTOS = partnerAddress.stream()
                .map(address -> {
                    PartnerAddressDTO map = modelMapper.map(address, PartnerAddressDTO.class);
                    Set<BusinessTimingDTO> businessTimingDTOS = address.getBusinessTimings().stream()
                            .map(tim -> modelMapper.map(tim, BusinessTimingDTO.class))
                            .collect(Collectors.toSet());
                    map.setBusinessTimings(businessTimingDTOS);
                    return map;
                })
                .collect(Collectors.toSet());
        return partnerAddressDTOS;
    }

    public PartnerAddressDTO createUpdatePartnerAddress(PartnerAddressDTO partnerAddressDTO, ResponseDTO responseDTO) {
        Set<BusinessTiming> businessTimings = null;

        PartnerAddress partnerAddress = modelMapper.map(partnerAddressDTO, PartnerAddress.class);

        if (partnerAddressDTO.getBusinessTimings() != null && partnerAddressDTO.getBusinessTimings().size() > 0) {
            businessTimings = partnerAddressDTO.getBusinessTimings()
                    .stream().map(tim -> modelMapper.map(tim, BusinessTiming.class))
                    .collect(Collectors.toSet());
            businessTimings.forEach(businessTiming -> abstractDao.saveOrUpdateEntity(businessTiming.getTimeRange()));
            abstractDao.saveOrUpdateEntity(businessTimings);
        }


        if (partnerAddressDTO.getIsPartner()) {
            if (!partnerAddress.getPartnerContactNumbers().isEmpty()) {
                abstractDao.saveOrUpdateEntity(partnerAddress.getPartnerContactNumbers());
            }
            abstractDao.saveOrUpdateEntity(partnerAddress);
            PartnerAddressTiming partnerAddressTiming = new PartnerAddressTiming(abstractDao.getEntityById(Partner.class, partnerAddressDTO.getPartnerId())
                    , partnerAddress, businessTimings);
            abstractDao.saveOrUpdateEntity(partnerAddressTiming);
        } else {
           /* if (partnerAddress.getId() != null) {
                PartnerAddress entity = abstractDao.getEntityById(PartnerAddress.class, partnerAddress.getId());
                CrudCtrlBase.copyNonNullProperties(entity, partnerAddress);
            }*/
            abstractDao.saveOrUpdateEntity(partnerAddress);
            UserAddressTiming userAddressTiming = new UserAddressTiming(
                    abstractDao.getEntityById(User.class, commonUtility.getLoggedInUser()), partnerAddress, businessTimings);
            abstractDao.saveOrUpdateEntity(userAddressTiming);
        }

        partnerAddress.setBusinessTimings(businessTimings);
        return modelMapper.map(partnerAddress, PartnerAddressDTO.class);
    }

    public ResponseDTO deletePartnerAddress(PartnerAddressDTO partnerAddressDTO, ResponseDTO responseDTO) throws Exception {
//        Transaction transaction = abstractDao.getSession().beginTransaction();
        PartnerAddress partnerAddress = abstractDao.getEntityById(PartnerAddress.class, partnerAddressDTO.getId());
        if (partnerAddress == null)
            throw new BusinessException("user.not.found.info");
        if (partnerAddressDTO.getIsPartner()) {
            Partner partner = abstractDao.getEntityById(Partner.class, partnerAddressDTO.getPartnerId());
            PartnerAddressTiming partnerAddressTiming =
                    partnerAddressTimingRepository.getByPartnerAndPartnerAddress
                            (partner, partnerAddress);
           if(Objects.nonNull(partnerAddressTiming))
               abstractDao.delete(partnerAddressTiming);
        } else {

            UserAddressTiming userAddressTiming =
                    userAddressTimingRepository.getUserAddressTiming(commonUtility.getLoggedInUser(), partnerAddress.getId());
//            EntityManager em = abstractDao.getSession().getEntityManagerFactory().createEntityManager();
//            em.remove(em.contains(userAddressTiming) ? userAddressTiming : em.merge(userAddressTiming));
            if (userAddressTiming.getBusinessTimings() != null && userAddressTiming.getBusinessTimings().size() > 0) {
                userAddressTiming.getBusinessTimings().forEach(businessTiming -> {
                    abstractDao.delete(businessTiming.getTimeRange());
                    abstractDao.delete(businessTiming);
                });
            }
            if(Objects.nonNull(userAddressTiming))
                abstractDao.delete(userAddressTiming);
        }
        /*if (partnerAddress.getBusinessTimings() != null && partnerAddress.getBusinessTimings().size() > 0) {
            partnerAddress.getBusinessTimings().forEach(businessTiming -> abstractDao.delete(businessTiming.getTimeRange()));
        }*/
        if (!partnerAddress.getPartnerContactNumbers().isEmpty()) {
            abstractDao.delete(partnerAddress.getPartnerContactNumbers());
        }
        abstractDao.delete(partnerAddress);
//        transaction.commit();
        return new ResponseDTO();
    }


    /*public Set<PartnerAddress> fetchUserPartnerAddress(PartnerAddressDTO partnerAddressDTO) {

        if (partnerAddressDTO.getIsPartner()) {
            PartnerAddressTiming partnerAddressTiming = new PartnerAddressTiming(abstractDao.getEntityById(Partner.class, partnerAddressDTO.getPartnerId())
                    , partnerAddress, businessTimings);
            abstractDao.saveOrUpdateEntity(partnerAddressTiming);
        } else {
            UserAddressTiming userAddressTiming = new UserAddressTiming(
                    abstractDao.getEntityById(User.class, commonUtility.getLoggedInUser()), partnerAddress, businessTimings);
            abstractDao.saveOrUpdateEntity(userAddressTiming);
        }

        partnerAddress.setBusinessTimings(businessTimings);
        return partnerAddress;
    }*/

    public List<PartnerResponseDTO> fetchPartnerLocationWise(PartnerRequestDTO partnerRequestDTO) throws Exception {

        List<PartnerResponseDTO> partnerAddressList = partnerDao.getPartnerByLocationAndPartnerType(partnerRequestDTO);
        if (partnerAddressList == null) {
            throw new BusinessException(translator.toLocale("partners.not.found"));
        }
        return partnerAddressList;
    }

    public ECardDTO getUserDetails(String userName) throws Exception {
        User user = userService.getUserRepository().getByUserName(userName);
        if (user == null)
            throw new BusinessException(translator.toLocale("user.not.found", new Object[]{userName}));
        return processPartnerAddress(userAddressTimingRepository.getPartnerAddress(user.getId()), user, new ECardDTO());
    }

    public ECardDTO processPartnerAddress(Set<PartnerAddress> partnerAddresses, User user, ECardDTO eCardDTO) {
        if (partnerAddresses != null) {
            List<BusinessAddress> businessAddresses = new ArrayList<>();
            for (PartnerAddress partnerAddress : partnerAddresses) {
                BusinessAddress businessAddress = new BusinessAddress();
                businessAddress.setId(partnerAddress.getId());
                businessAddress.setAddressTypeId(partnerAddress.getAddressType() != null ? partnerAddress.getAddressType().getId() : null);
                businessAddress.setBusinessAddress(partnerAddress.getAddress());
                businessAddress.setBusinessAddressLink("https://www.google.com/maps/search/" + partnerAddress.getAddress());
                businessAddress.setTitle(partnerAddress.getName());
                businessAddress.setDisplayOrder(partnerAddress.getDisplayOrder());
//                if (partnerAddress.getBusinessTimings() != null) {
//                    Set<BusinessTimingFlow> businessTimingFlows = new HashSet<>();
//                    for (BusinessTiming businessTiming : partnerAddress.getBusinessTimings()) {
//                        BusinessTimingFlow businessTimingFlow = new BusinessTimingFlow();
//                        businessTimingFlow.setDays(businessTiming.getDayRange());
//                        if (businessTiming.getTimeRange() != null) {
//                            List<TimeRangeFlow> timeRangeFlows = new ArrayList<>();
//                            for (TimeRange timeRange : businessTiming.getTimeRange()) {
//                                TimeRangeFlow timeRangeFlow = new TimeRangeFlow();
//                                timeRangeFlow.setTimeRange(timeRange.getTimeRange());
//                                timeRangeFlow.setDisplayOrder(timeRange.getDisplayOrder());
//                                timeRangeFlows.add(timeRangeFlow);
//                            }
//                            if (timeRangeFlows.size() > 1)
//                                timeRangeFlows.sort(Comparator.comparing(TimeRangeFlow::getDisplayOrder));
//                            List<String> strings = new ArrayList<>();
//                            timeRangeFlows.forEach(timeRangeFlow -> strings.add(timeRangeFlow.getTimeRange()));
//                            businessTimingFlow.setTime(strings.toArray(new String[0]));
//                        }
//                        businessTimingFlows.add(businessTimingFlow);
//                        businessAddress.setTimings(businessTimingFlows);
//                    }
//                }
                businessAddresses.add(businessAddress);
            }
            if (businessAddresses.size() > 1)
                businessAddresses.sort(Comparator.comparing(BusinessAddress::getDisplayOrder));
            eCardDTO.setBusinessAddress(businessAddresses);
        }

        eCardDTO.setName(commonUtility.getCompleteName(user));
        eCardDTO.setAvatarURL(commonUtility.getUserAvatarURL(user));
        eCardDTO.setImageURL(commonUtility.getUserImageURL(user));
        eCardDTO.setCoverURL(commonUtility.getUserCoverURL(user));
        eCardDTO.setProfession(commonUtility.getUserProfession(user));
        eCardDTO.setBio(user.getBio());
        eCardDTO.setUserCharges(user.getUserCharges());
        eCardDTO.setUserExperience(user.getUserExperience());
        return eCardDTO;
    }


    public ECardDTO getECardDetails(String userName, String partnerUserName) throws Exception {
        ECardDTO eCardDTO = new ECardDTO();
        Boolean partnerUserNamePresent = false;
        User user = userService.getUserRepository().getByUserName(userName);
        Partner partner;
        if (user == null)
            throw new BusinessException(translator.toLocale("user.not.found", new Object[]{userName}));
        if (partnerUserName != null) {
            partnerUserNamePresent = true;
            partner = partnerRepository.getByUserName(partnerUserName);
            if (partner == null)
                throw new BusinessException(translator.toLocale("user.not.found", new Object[]{partnerUserName}));
        } else {
            List<Authority> collect = user.getAuthorities().stream().filter(authority -> "ROLE_PARTNER".equalsIgnoreCase(authority.getName())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(collect)) {
                Set<Partner> partners = userPartnerRelationMPRepository.byUserAndRelation(user.getId(), "Owner");
                if (CollectionUtils.isEmpty(partners))
                    throw new BusinessException(translator.toLocale("user.not.found", new Object[]{userName}));
                else {
                    partner = partners.iterator().next();
                }
            } else {
                throw new BusinessException(translator.toLocale("user.not.found", new Object[]{userName}));
            }
        }

//        partner.setName(userName);
//        ECardFlow eCardFlow = new ECardFlow(partner);
//        eCardDTO = modelMapper.map(eCardFlow, ECardDTO.class);
        eCardDTO.setAddress(partner.getAddress());
        eCardDTO.setAddressLink("https://www.google.com/maps/search/" + partner.getAddress());
        eCardDTO.setEmail(partner.getEmail());
        eCardDTO.setEmailLink("mailto:" + partner.getEmail());
        eCardDTO.setYoutubeLink(partner.getYoutubeLink());
        eCardDTO.setTwitterLink(partner.getTwitterLink());
        eCardDTO.setInstagramLink(partner.getInstagramLink());
        eCardDTO.setFbLink(partner.getFbLink());
        eCardDTO.setWhatsShareLink("https://api.whatsapp.com/send?text=" +
                URLEncoder.encode(translator.toLocale("user.ecard.share",
                        new String[]{environment.getRequiredProperty("application.url") + "ecard/" + userName
                                + (partnerUserNamePresent ? ("/" + partnerUserName) : "")}), "UTF-8"));
        eCardDTO.setExperience(user.getUserExperience() + " " + translator.toLocale("user.experience.label"));
        eCardDTO.setConsultationFee("₹ " + commonUtility.currencyFormat(user.getUserCharges()) + " " + translator.toLocale("consultation.fees.label"));

        return processPartnerAddress(userAddressTimingRepository.getPartnerAddress(user.getId()), user, eCardDTO);
    }


    public Enquiry saveEnquiry(EnquiryDTO enquiryDTO) throws Exception {
        Enquiry enquiry = new Enquiry();//modelMapper.map(enquiryDTO, Enquiry.class);
        if (enquiryDTO.getCountryId() != null)
            enquiry.setCountry(new Country(enquiryDTO.getCountryId()));
        enquiry.setTitle(new Title(enquiryDTO.getTitleId()));
        enquiry.setEmail(enquiryDTO.getEmail());
        enquiry.setMessage(enquiryDTO.getMessage());
        enquiry.setMobile(enquiryDTO.getMobile());
        enquiry.setName(enquiryDTO.getName());
        Partner partner = null;
        if (enquiryDTO.getUserName() != null)
            partner = partnerRepository.getByUserName(enquiryDTO.getUserName());
//        if (partner == null)
//            throw new BusinessException(translator.toLocale("user.not.found", new String[]{enquiryDTO.getUserName()}));
        enquiry.setPartner(partner);
        return abstractDao.saveOrUpdateEntity(enquiry);
    }

    public FileSystemResource getECardAssets(String userName, String serviceName, String fileName) throws Exception {
        Partner partner = partnerRepository.getByUserName(userName);
        if (partner == null)
            throw new BusinessException(translator.toLocale("user.not.found", new String[]{userName}));
        String base = environment.getRequiredProperty("location.file.upload");
        base = base + "partner" + File.separator + partner.getId() + File.separator + "eCard" + File.separator;
        if ("avatar".equalsIgnoreCase(serviceName))
            base = base + "avatar" + File.separator + partner.getECardAvatar();
        else if ("cover".equalsIgnoreCase(serviceName))
            base = base + "cover" + File.separator + partner.getECardCoverImage();
        else
            base = base + "gallery" + File.separator + fileName;
        File file = new File(base);
        return new FileSystemResource(file.getAbsoluteFile());
    }

    public FileSystemResource getECardAssetsUser(String key) throws Exception {
        File file = getUserFile(key);
        return new FileSystemResource(file.getAbsoluteFile());
    }

    public FileSystemResource getECardAssetsPartner(String key) throws Exception {
        File file = getPartnerFile(key);
        return new FileSystemResource(file.getAbsoluteFile());
    }

    public DocumentDTO uploadDoc(MultipartFile file, Long docId, Long partnerId) throws Exception {
        PartnerDocument partnerDocument = abstractDao.getEntityById(PartnerDocument.class, docId);
        String base = environment.getRequiredProperty("location.file.upload");
        base = base + "partner" + File.separator + partnerDocument.getPartner().getId() + File.separator + "eCard" + File.separator
                + partnerDocument.getDocument().getDocumentType();
        String imageName = userService.processFile(base, partnerDocument.getPartner().getId(), file, false);
        partnerDocument.setFileName(imageName);
        abstractDao.saveOrUpdateEntity(partnerDocument);

        DocumentDTO documentDTO = modelMapper.map(partnerDocument, DocumentDTO.class);
        documentDTO.setImage(environment.getRequiredProperty("gateway.api.url") + "assets/user/e/card?key=" + partnerDocument.getDocumentNumber());
        return documentDTO;
    }

    public ResponseDTO deleteECardAssetsPartner(String key, ResponseDTO responseDTO) throws Exception {
        PartnerDocument partnerDocument = partnerDocumentRepository.getByDocumentNumber(key);
        if (partnerDocument == null)
            throw new BusinessException("user.not.found.info");
        File file = getPartnerFile(key);
        logger.info("is file deleted -->" + file.delete());
        return responseDTO;
    }

    public ResponseDTO deleteECardAssetsUser(String key, ResponseDTO responseDTO) throws Exception {
        UserDocument userDocument = userDocumentRepository.getByDocumentNumber(key);
        if (userDocument == null)
            throw new BusinessException("user.not.found.info");
        else if (userDocument.getUser().getId().equals(commonUtility.getLoggedInUser())) {
            File file = getUserFile(key);
            if (file.exists())
                logger.info("is file deleted -->" + file.delete());
            userDocumentRepository.delete(userDocument);
            return responseDTO;
        } else
            throw new BusinessException("operation not performed");
    }

    public File getUserFile(String key) throws Exception {
        UserDocument userDocument = userDocumentRepository.getByDocumentNumber(key);
        if (userDocument == null)
            throw new BusinessException("user.not.found.info");
        String base = environment.getRequiredProperty("location.file.upload");
        base = base + "user" + File.separator + userDocument.getUser().getId() + File.separator + "eCard" + File.separator
                + userDocument.getDocumentType().getDocumentType() + File.separator + userDocument.getFileName();
        return new File(base);
    }

    public File getPartnerFile(String key) throws Exception {
        PartnerDocument partnerDocument = partnerDocumentRepository.getByDocumentNumber(key);
        if (partnerDocument == null)
            throw new BusinessException("user.not.found.info");
        String base = environment.getRequiredProperty("location.file.upload");
        base = base + "user" + File.separator + partnerDocument.getPartner().getId() + File.separator + "eCard" + File.separator
                + partnerDocument.getDocument().getDocumentType() + File.separator + partnerDocument.getFileName();
        return new File(base);
    }
}
