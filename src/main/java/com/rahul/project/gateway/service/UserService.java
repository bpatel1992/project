package com.rahul.project.gateway.service;

import com.rahul.project.gateway.configuration.BusinessException;
import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.crud.controller.CrudCtrlBase;
import com.rahul.project.gateway.crud.uiBeans.BNEUser;
import com.rahul.project.gateway.dao.AbstractDao;
import com.rahul.project.gateway.dao.UserDao;
import com.rahul.project.gateway.dto.*;
import com.rahul.project.gateway.model.Pet;
import com.rahul.project.gateway.model.RoleFeature;
import com.rahul.project.gateway.model.RoleFunctionality;
import com.rahul.project.gateway.model.*;
import com.rahul.project.gateway.repository.*;
import com.rahul.project.gateway.utility.AESPasswordUtil;
import com.rahul.project.gateway.utility.CommonUtility;
import com.rahul.project.gateway.utility.Translator;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.codec.binary.Base64;
import org.hibernate.HibernateException;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.NoResultException;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Rahul Malhotra
 * @since 1.0
 * Date 2019-05-21
 */
@Getter
@Setter
@TransactionalService
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    UserDao userDao;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    Translator translator;

    @Autowired
    AbstractDao abstractDao;

    @Autowired
    CommonUtility commonUtility;

    @Autowired
    AESPasswordUtil passwordUtil;

    @Autowired
    SendMailService sendMailService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PetRepository petRepository;

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    UserLoginTypeRepository userLoginTypeRepository;

    @Autowired
    UserLoginPasswordRepository userLoginPasswordRepository;

    @Autowired
    CertificateRepository certificateRepository;
    @Autowired
    GalleryRepository galleryRepository;
    @Autowired
    Environment environment;
    @Autowired
    UserDocumentRepository userDocumentRepository;
    @Autowired
    DocumentTypeRepository documentTypeRepository;
    @Autowired
    BNEUser bneUser;
    PropertyMap<AddClientDto, User> userMapping = new PropertyMap<AddClientDto, User>() {
        protected void configure() {
//            map().setId(source.getId());
            map().getCountry().setId(source.getCountryId());
            map().getGender().setId(source.getGenderId());
            map().getCreatedByPartner().setId(source.getCreatedByPartnerId());
            map().getTitle().setId(source.getTitleId());
            map().getCreatedByUserId().setId(source.getCreatedByUserId());
        }
    };
    PropertyMap<User, AddClientDto> userFieldMapping = new PropertyMap<User, AddClientDto>() {
        protected void configure() {
            map().setCountryId(source.getCountry().getId());
            map().setGenderId(source.getGender().getId());
            map().setCreatedByPartnerId(source.getCreatedByPartner().getId());
            map().setTitleId(source.getTitle().getId());
            map().setCreatedByUserId(source.getCreatedByUserId().getId());
        }
    };
    @Autowired
    private NewsLetterSubscriptionRepository newsLetterSubscriptionRepository;


    public UserService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        modelMapper.addMappings(userMapping);
        modelMapper.addMappings(userFieldMapping);
    }

    public ResponseDTO userNewsLetterSubscription(String email, ResponseDTO responseDTO) throws Exception {

        User user = getUserObject();
        if (user != null && user.getEmail() != null && !"".equalsIgnoreCase(user.getEmail()))
            email = user.getEmail();
        if (email == null)
            throw new BusinessException("email.required");
        NewsLetterSubscription newsLetterSubscription = newsLetterSubscriptionRepository.getByEmail(email);
        if (newsLetterSubscription != null) {
            if (newsLetterSubscription.isSubscribed()) {
                responseDTO.setResponseCode("7001");
                responseDTO.setResponseMessage(translator.toLocale("email.subscribed.already"));
                return responseDTO;
            }
        } else {
            newsLetterSubscription = new NewsLetterSubscription();
            newsLetterSubscription.setEmail(email);
        }

        newsLetterSubscription.setSubscribed(true);
        newsLetterSubscriptionRepository.save(newsLetterSubscription);
        responseDTO.setResponseMessage(translator.toLocale("email.subscribed.news.letter"));
        return responseDTO;
    }

    public CustomerProfileDTO getUserData() throws Exception {
        User user = getUserObject();
        if (user == null)
            throw new BusinessException("user.not.found.info");
        return bneUser.processObject(user);
    }

    public User getUserObject() {
        User user = null;
        Long userId = commonUtility.getLoggedInUser();
        if (userId != null) {
            user = abstractDao.getEntityById(User.class, userId);
        }
        return user;
    }

    public String downloadProfilePicture(String randomKey) throws Exception {
        Long user = commonUtility.getLoggedInUser();
        if (randomKey != null) {
            user = userRepository.getByRandomKey(randomKey).getId();
        }
        String filePath = commonUtility.getEnvironmentProperty("location.file.profile.pic") + user;
        String response;
        File file = new File(filePath);
        File[] listOfFiles = file.listFiles();
        if (listOfFiles != null && listOfFiles.length > 0) {
            String fileName = listOfFiles[0].getName();
            logger.info("file name== {}", fileName);
            fileName = filePath + "/" + fileName;
            Path path = Paths.get(fileName);
            byte[] data = Files.readAllBytes(path);
            response = new String(Base64.encodeBase64(data));
        } else {
            throw new BusinessException("user.image.not.found");
        }
        return response;
    }

    public ResponseDTO updateProfilePicture(MultipartFile files, String randomKey, ResponseDTO responseDTO) throws Exception {
        User user;

        if (randomKey != null) {
            user = userRepository.getByRandomKey(randomKey);
            if (user == null)
                throw new BusinessException("user.not.found");
        } else {
            user = userRepository.getOne(commonUtility.getLoggedInUser());
        }
        String filePath = commonUtility.getEnvironmentProperty("location.file.profile.pic") + user.getId();
        String imageName = processFile(filePath, user.getId(), files, true);
        user.setImageName(imageName);
        userRepository.save(user);

        responseDTO.setResponseMessage(translator.toLocale("user.image.update.success"));
        responseDTO.setImage(environment.getRequiredProperty("gateway.api.url") + "assets/user/profile?randomKey=" + user.getRandomKey());
        return responseDTO;
    }

    public ResponseDTO updateUserAvatar(MultipartFile files, String randomKey, ResponseDTO responseDTO) throws Exception {
        User user;

        if (randomKey != null) {
            user = userRepository.getByRandomKey(randomKey);
            if (user == null)
                throw new BusinessException("user.not.found");
        } else {
            user = userRepository.getOne(commonUtility.getLoggedInUser());
        }
        String filePath = commonUtility.getEnvironmentProperty("location.file.avatar") + user.getId();
        String imageName = processFile(filePath, user.getId(), files, true);
        user.setAvatarName(imageName);
        userRepository.save(user);

        responseDTO.setResponseMessage(translator.toLocale("user.image.update.success"));
        responseDTO.setImage(environment.getRequiredProperty("gateway.api.url") + "assets/user/avatar?randomKey=" + user.getRandomKey());
        return responseDTO;
    }

    public ResponseDTO updateUserCover(MultipartFile files, String randomKey, ResponseDTO responseDTO) throws Exception {
        User user;

        if (randomKey != null) {
            user = userRepository.getByRandomKey(randomKey);
            if (user == null)
                throw new BusinessException("user.not.found");
        } else {
            user = userRepository.getOne(commonUtility.getLoggedInUser());
        }
        String filePath = commonUtility.getEnvironmentProperty("location.file.cover") + user.getId();
        String imageName = processFile(filePath, user.getId(), files, true);
        user.setCoverName(imageName);
        userRepository.save(user);

        responseDTO.setResponseMessage(translator.toLocale("user.image.update.success"));
        responseDTO.setImage(environment.getRequiredProperty("gateway.api.url") + "assets/user/cover?randomKey=" + user.getRandomKey());
        return responseDTO;
    }

    public ResponseDTO updateUserCertificate(MultipartFile[] files, String randomKey, ResponseDTO responseDTO) throws Exception {
        User user;

        if (randomKey != null) {
            user = userRepository.getByRandomKey(randomKey);
            if (user == null)
                throw new BusinessException("user.not.found");
        } else {
            user = userRepository.getOne(commonUtility.getLoggedInUser());
        }
        String filePath = commonUtility.getEnvironmentProperty("location.file.certificates") + user.getId();
        Set<String> strings = processFile(filePath, user.getId(), files);
        Set<Certificate> certificates = user.getCertificates();
        if (certificates == null)
            certificates = new HashSet<>();
        if (strings != null && strings.size() > 0) {
            List<String> certificateURLs = new ArrayList<>();
            for (String s : strings) {
                Certificate certificate = new Certificate(s);
                certificateRepository.save(certificate);
                certificates.add(certificate);
                certificateURLs.add(environment.getRequiredProperty("gateway.api.url") + "assets/user/certificate?randomKey="
                        + user.getRandomKey() + "&fileName=" + certificate.getName());
            }
            user.setCertificates(certificates);
            userRepository.save(user);
            responseDTO.setResponseMessage(translator.toLocale("user.image.update.success"));
            responseDTO.setCertificateURLs(certificateURLs);
            return responseDTO;
        } else
            throw new BusinessException("no file found");

    }

    public ResponseDTO updateUserGallery(MultipartFile[] files, String randomKey, ResponseDTO responseDTO) throws Exception {
        User user;

        if (randomKey != null) {
            user = userRepository.getByRandomKey(randomKey);
            if (user == null)
                throw new BusinessException("user.not.found");
        } else {
            user = userRepository.getOne(commonUtility.getLoggedInUser());
        }
        String filePath = commonUtility.getEnvironmentProperty("location.file.gallery") + user.getId();
        Set<String> strings = processFile(filePath, user.getId(), files);
        Set<Gallery> galleries = user.getGalleries();
        if (galleries == null)
            galleries = new HashSet<>();
        if (strings != null && strings.size() > 0) {
            List<String> galleryURLs = new ArrayList<>();
            for (String s : strings) {
                Gallery gallery = new Gallery(s);
                galleryRepository.save(gallery);
                galleries.add(gallery);
                galleryURLs.add(environment.getRequiredProperty("gateway.api.url") + "assets/user/gallery?randomKey="
                        + user.getRandomKey() + "&fileName=" + gallery.getName());
            }
            user.setGalleries(galleries);
            userRepository.save(user);
            responseDTO.setResponseMessage(translator.toLocale("user.image.update.success"));
            responseDTO.setGalleryURLs(galleryURLs);
            return responseDTO;
        } else
            throw new BusinessException("no file found");

    }

    public DocumentDTO processUserDocument(DocumentDTO documentDTO) {
        DocumentType documentType = abstractDao.getEntityById(DocumentType.class, documentDTO.getDocTypeId());
        User user = abstractDao.getEntityById(User.class, commonUtility.getLoggedInUser());
        UserDocument userDocument = new UserDocument(user, documentType, documentDTO.getTitle(),
                documentDTO.getDisplayOrder());
        userDocument.setDocumentNumber(commonUtility.get20DigitRandomKey());
        userDocument.setVideoCode(documentDTO.getVideoCode());
        UserDocument document = abstractDao.saveOrUpdateEntity(userDocument);
        documentDTO.setId(document.getId());
        documentDTO.setDocumentNumber(userDocument.getDocumentNumber());
        return documentDTO;
    }

    public DocumentDTO userDocumentUpdate(DocumentDTO documentDTO) throws Exception {

        UserDocument userDocument = abstractDao.getEntityById(UserDocument.class, documentDTO.getId());
        if (userDocument == null)
            throw new BusinessException("user.not.found.info");
        else if (userDocument.getUser().getId().equals(commonUtility.getLoggedInUser())) {
            userDocument.setTitle(documentDTO.getTitle());
            userDocument.setVideoCode(documentDTO.getVideoCode());
            userDocument.setDisplayOrder(documentDTO.getDisplayOrder());
            userDocument.setDocumentNumber(commonUtility.get20DigitRandomKey());
            abstractDao.saveOrUpdateEntity(userDocument);
        } else
            throw new BusinessException("operation not performed");

        documentDTO.setDocumentNumber(userDocument.getDocumentNumber());
        documentDTO.setDocTypeId(userDocument.getDocumentType().getId());
        documentDTO.setDisplayOrder(userDocument.getDisplayOrder());
        documentDTO.setVideoCode(userDocument.getVideoCode());
        documentDTO.setTitle(userDocument.getTitle());
        return documentDTO;
    }

    public DocumentDTO uploadDoc(MultipartFile file, Long docId) throws Exception {
        UserDocument userDocument = abstractDao.getEntityById(UserDocument.class, docId);
        String base = environment.getRequiredProperty("location.file.upload");
        base = base + "user" + File.separator + userDocument.getUser().getId() + File.separator + "eCard" + File.separator
                + userDocument.getDocumentType().getDocumentType();
        String imageName = processFile(base, userDocument.getUser().getId(), file, false);
        userDocument.setFileName(imageName);
        abstractDao.saveOrUpdateEntity(userDocument);

        DocumentDTO documentDTO = modelMapper.map(userDocument, DocumentDTO.class);
        documentDTO.setImage(environment.getRequiredProperty("gateway.api.url") + "assets/user/e/card?key=" + userDocument.getDocumentNumber());
        return documentDTO;
    }

    public ResponseDTO updateProfilePicturePet(MultipartFile files, String randomKey, ResponseDTO responseDTO) throws Exception {
        Pet pet;

        pet = petRepository.getByRandomKey(randomKey);
        if (pet == null)
            throw new BusinessException("user.not.found");
        String filePath = commonUtility.getEnvironmentProperty("location.file.pet.profile.pic") + pet.getId();
        String imageName = processFile(filePath, pet.getId(), files, true);
        pet.setImageName(imageName);
        petRepository.save(pet);

        responseDTO.setResponseMessage(translator.toLocale("user.image.update.success"));
        responseDTO.setImage(environment.getRequiredProperty("gateway.api.url") + "assets/pet/profile?randomKey=" + pet.getRandomKey());
        return responseDTO;
    }

    public String processFile(String filePath, Long id, MultipartFile files, Boolean delete) throws IOException {
        String fileName = null;
        String fileExt = null;
        if (delete) {
            File folderFile = new File(filePath);
            if (folderFile.exists()) {
                File[] folderFiles = folderFile.listFiles();
                if (folderFiles != null) {
                    for (File f : folderFiles) {
                        f.delete();
                    }
                }
            }
        }
        if (files != null) {

            fileName = files.getOriginalFilename();
            fileExt = files.getOriginalFilename().split("\\.")[1];
            logger.info("fileName=======" + fileName);
            fileName = id + "_" + commonUtility.get16DigitRandomKey() + "." + fileExt;
            File outFile = new File(filePath + File.separator + fileName);
            outFile.getParentFile().mkdirs();
            outFile.createNewFile();
            byte[] bytes = files.getBytes();
            BufferedOutputStream buffStream =
                    new BufferedOutputStream(new FileOutputStream(outFile));
            buffStream.write(bytes);
            buffStream.close();
        }

        return fileName;
    }

    private Set<String> processFile(String filePath, Long id, MultipartFile[] files) throws IOException {
        Set<String> response = new HashSet<>();
        String fileName;
        String fileExt = null;
       /* int c = 0;
        File folderFile = new File(filePath);
        if (folderFile.exists()) {
            File[] folderFiles = folderFile.listFiles();
            if (folderFiles != null) {
                for (File f : folderFiles) {
                    c++;
                }
            }
        }*/
        if (files != null) {

            for (MultipartFile file : files) {
                fileName = file.getOriginalFilename();
                fileExt = file.getOriginalFilename().split("\\.")[1];
                logger.info("fileName=======" + fileName);
                fileName = id + "_" + commonUtility.get16DigitRandomKey() + "." + fileExt;
                File outFile = new File(filePath + File.separator + fileName);
                response.add(fileName);
                outFile.getParentFile().mkdirs();
                outFile.createNewFile();
                byte[] bytes = file.getBytes();
                BufferedOutputStream buffStream =
                        new BufferedOutputStream(new FileOutputStream(outFile));
                buffStream.write(bytes);
                buffStream.close();
            }
        }

        return response;
    }

    public String removeProfilePic() throws Exception {
        String filePath = commonUtility.getEnvironmentProperty("location.file.profile.pic") + commonUtility.getLoggedInUser();
        File folderFile = new File(filePath);
        if (folderFile.exists()) {
            File[] folderFiles = folderFile.listFiles();
            if (folderFiles != null) {
                for (File f : folderFiles) {
                    f.delete();
                }
            }
        }
        return translator.toLocale("user.image.update.success");
    }

    public ResponseDTO userPasswordUpdate(SavePasswordAdminDTO savePasswordAdminDTO, ResponseDTO responseDTO) throws BusinessException {
        User user;
        if (commonUtility.getLoggedInUser() != null) {
            user = abstractDao.getEntityById(User.class, commonUtility.getLoggedInUser());
        } else {
            user = userRepository.getByRandomKey(savePasswordAdminDTO.getRandomKey());
            if (user == null)
                throw new BusinessException("user.not.found");
        }

        // login type self
        String loginType = "self";
        /*if (savePasswordAdminDTO.getSignUpBy() != null) {
            if ("gmail".equalsIgnoreCase(savePasswordAdminDTO.getSignUpBy()))
                loginType = "gmail";
            else if ("facebook".equalsIgnoreCase(savePasswordAdminDTO.getSignUpBy()))
                loginType = "facebook";
            else
                loginType = savePasswordAdminDTO.getSignUpBy();
        }*/
        UserLoginType loginTypeEntity = userLoginTypeRepository.getByLoginType(loginType);
        UserLoginPassword userLoginPassword = userLoginPasswordRepository.getByUserAndLoginType(user, loginTypeEntity);
        if (userLoginPassword == null) {
            userLoginPassword = new UserLoginPassword();
            CompositeId compositeId = new CompositeId(loginTypeEntity.getId(), user.getId());
            userLoginPassword.setCompositeId(compositeId);
        }/*else {
            userLoginPassword.setUser(new User(user.getId()));
            userLoginPassword.setLoginType(new UserLoginType(loginType1.getId()));
        }*/
        if (savePasswordAdminDTO.getPassword() != null)
            userLoginPassword.setPassword(passwordUtil.convertToAES(savePasswordAdminDTO.getPassword().trim()));
        else
            userLoginPassword.setPassword(userLoginPasswordRepository.getByUserAndLoginType(user, loginTypeEntity).getPassword());
        user.setActivated(true);
        Set<Authority> authorities = user.getAuthorities();
        if (authorities == null)
            authorities = new HashSet<>();
        if (savePasswordAdminDTO.getUserType() != null) {
            //authority
            String authority = "ROLE_ADMIN";
            if ("customer".equalsIgnoreCase(savePasswordAdminDTO.getUserType()))
                authority = "ROLE_CUSTOMER";
            else if ("partner".equalsIgnoreCase(savePasswordAdminDTO.getUserType()))
                authority = "ROLE_PARTNER";
            String finalAuthority = authority;
            Authority exist = authorities.stream()
                    .filter(authority1 -> finalAuthority.equalsIgnoreCase(authority1.getName()))
                    .findAny()
                    .orElse(null);
            if (exist == null) {
                authorities.add(authorityRepository.getByName(authority));
                user.setAuthorities(authorities);
            }
            userRepository.save(user);
        }
        userLoginPasswordRepository.save(userLoginPassword);
        responseDTO.setRandomKey(user.getRandomKey());
        return responseDTO;
    }

    public String registerDevice(UserDeviceDTO userDeviceDTO) throws Exception {

        Long userId = commonUtility.getLoggedInUser();
        logger.info("User Id : " + userId);
        logger.info("Device Token : " + userDeviceDTO.getDeviceToken());
        try {
            UserDevice userDevice = userDao.getUserDevice(userDeviceDTO.getDeviceToken());
            logger.info("user device is present");
            throw new BusinessException(translator.toLocale("device.present"));
        } catch (NoResultException e) {
            UserDevice userDevice = modelMapper.map(userDeviceDTO, UserDevice.class);
            userDevice.setUser(abstractDao.getEntityById(User.class, userId));
            abstractDao.saveOrUpdateEntity(userDevice);
            logger.info("device registered");
            return translator.toLocale("device.registered");
        } catch (HibernateException e) {
            throw new BusinessException(translator.toLocale("record.multiple"));
        } catch (IllegalArgumentException e) {
            logger.error("Error:", e);
            throw new BusinessException(translator.toLocale("illegal.argument"));
        } catch (Exception e) {
            logger.error("Error:", e);
            throw new BusinessException(translator.toLocale("internal.error"));
        }
    }

    public RoleDTO createUpdateRoleAdmin(RoleDTO roleDTO) throws Exception {
        Role role = new Role();
        if (roleDTO.getId() != null) {
            role = abstractDao.getEntityById(Role.class, roleDTO.getId());
            for (RoleFunctionality roleFunctionality : role.getRoleFunctionality()) {
                for (RoleFeature roleFeature : roleFunctionality.getFeatures()) {
                    abstractDao.delete(roleFeature);
                }
                abstractDao.delete(roleFunctionality);
            }
        }
        Role entity = modelMapper.map(roleDTO, Role.class);
        CrudCtrlBase.copyNonNullProperties(entity, role);
        for (RoleFunctionality roleFunctionality : role.getRoleFunctionality()) {
            if (roleFunctionality.getFeatures() != null)
                for (RoleFeature roleFeature : roleFunctionality.getFeatures()) {
                    abstractDao.saveOrUpdateEntity(roleFeature);
                }
            abstractDao.saveOrUpdateEntity(roleFunctionality);
        }
        abstractDao.saveOrUpdateEntity(role);
        roleDTO = modelMapper.map(role, RoleDTO.class);
        return roleDTO;
    }

    public UserDTO createAdminUser(UserDTO userDTO) throws Exception {
        User user = new User();
        if (userDTO.getId() != null) {
            user = abstractDao.getEntityById(User.class, userDTO.getId());
        }
        User entity = modelMapper.map(userDTO, User.class);
        CrudCtrlBase.copyNonNullProperties(entity, user);
        user.setUserType("A");
        user.setEmail(userDTO.getEmail());
        String userName = user.getFirstName();
        sendMailService.sendStaffAdminVerificationMail(user, userName);
        abstractDao.saveOrUpdateEntity(user);

        return userDTO;

    }

    public AddClientDto addClient(AddClientDto addClientDto) throws Exception {

        String fullName = "";
        User user = modelMapper.map(addClientDto, User.class);
        user.setActivated(false);
        if (addClientDto.getFirstName() != null && addClientDto.getLastName() != null) {
            fullName = addClientDto.getFirstName() + " " + addClientDto.getLastName();
            user.setFullName(fullName);
        }
        Set<Authority> authorities = user.getAuthorities();
        if (authorities == null)
            authorities = new HashSet<>();

        String authority = "ROLE_CUSTOMER";
        authorities.add(authorityRepository.getByName(authority));
        user.setAuthorities(authorities);


        String userName = user.getFirstName() + user.getLastName();
        String email = user.getEmail();
        if (!email.isEmpty()) {
            sendMailService.sendClientConfirmation(user, userName);
        }
        user.setRandomKey(commonUtility.generateUserRandomKey());
        abstractDao.saveOrUpdateEntity(user);
        return modelMapper.map(user, AddClientDto.class);
    }


}
