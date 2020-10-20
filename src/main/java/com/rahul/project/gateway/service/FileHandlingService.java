package com.rahul.project.gateway.service;

import com.rahul.project.gateway.configuration.BusinessException;
import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.model.Pet;
import com.rahul.project.gateway.model.User;
import com.rahul.project.gateway.repository.PetRepository;
import com.rahul.project.gateway.repository.UserRepository;
import com.rahul.project.gateway.utility.CommonUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.lang.invoke.MethodHandles;

/**
 * @author Rahul Malhotra
 * @since 1.0
 * Date 2019-05-21
 */
@TransactionalService
public class FileHandlingService {

    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    @Autowired
    CommonUtility commonUtility;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PetRepository petRepository;
    @Autowired
    private Environment environment;

//    public String uploadFile(String documentId, String userId, MultipartFile files) throws Exception {
//
//        String BASE_PATH = environment.getRequiredProperty("location.file.upload");
//        String fileName;
//        File outFile = null;
//
//        File folderFile = new File(BASE_PATH + File.separator + userId + File.separator + documentId);
//        if (folderFile.exists()) {
//            File[] folderFiles = folderFile.listFiles();
//            if (folderFiles != null) {
//                for (File f : folderFiles) {
////                    f.delete();
//                    logger.info("file name -->" + f.getName());
//                }
//            }
//        }
//        if (files != null) {
//
//            fileName = files.getOriginalFilename();
//            logger.info("fileName=======" + fileName);
//            outFile = new File(BASE_PATH + File.separator + userId + File.separator + documentId + File.separator + userId + "_" + documentId + "_" + fileName);
//            logger.info("folder created successfully -->" + outFile.getParentFile().mkdirs());
//            logger.info("file created successfully -->" + outFile.createNewFile());
//
//
//            byte[] bytes = files.getBytes();
//            BufferedOutputStream buffStream =
//                    new BufferedOutputStream(new FileOutputStream(outFile));
//            buffStream.write(bytes);
//            buffStream.close();
//        }
//
//        return outFile != null ? outFile.getAbsolutePath() : "";
//    }

    public String uploadBreedIdentificationFile(MultipartFile files) throws Exception {

        String BASE_PATH = environment.getRequiredProperty("location.file.upload.breed.identification");

        String path = BASE_PATH + File.separator + commonUtility.get16DigitRandomKey();
        return commonUtility.createFile(files, path);


    }

    public FileSystemResource getSymptomCheckerFile(String fileName) {
        String base = environment.getRequiredProperty("location.file.symptom.checker");
        File file = new File(base + fileName);
        return new FileSystemResource(file.getAbsoluteFile());
    }

    public FileSystemResource getAssets(String fileName) {
        String base = environment.getRequiredProperty("location.file.assets");
        File file = new File(base + fileName);
        return new FileSystemResource(file.getAbsoluteFile());
    }

    public FileSystemResource getAssetsUser(String randomKey) {
        User user = userRepository.getByRandomKey(randomKey);
        String base = environment.getRequiredProperty("location.file.profile.pic");
        File file = new File(base + user.getId() + File.separator + user.getImageName());
        return new FileSystemResource(file.getAbsoluteFile());
    }

    public FileSystemResource getAssetsUserAvatar(String randomKey) {
        User user = userRepository.getByRandomKey(randomKey);
        String base = environment.getRequiredProperty("location.file.avatar");
        File file = new File(base + user.getId() + File.separator + user.getAvatarName());
        return new FileSystemResource(file.getAbsoluteFile());
    }

    public FileSystemResource getAssetsUserCover(String randomKey) {
        User user = userRepository.getByRandomKey(randomKey);
        String base = environment.getRequiredProperty("location.file.cover");
        File file = new File(base + user.getId() + File.separator + user.getCoverName());
        return new FileSystemResource(file.getAbsoluteFile());
    }

    public FileSystemResource getAssetsUserCertificate(String randomKey, String fileName) throws Exception {
        return getFile(randomKey, fileName, environment.getRequiredProperty("location.file.certificates"));
    }

    public FileSystemResource getAssetsUserGallery(String randomKey, String fileName) throws Exception {
        return getFile(randomKey, fileName, environment.getRequiredProperty("location.file.gallery"));
    }

    private FileSystemResource getFile(String randomKey, String fileName, String base) throws Exception {
        User user = userRepository.getByRandomKey(randomKey);
        if (user == null)
            throw new BusinessException("user.not.found.info");
        File file = new File(base + user.getId() + File.separator + fileName);
        return new FileSystemResource(file.getAbsoluteFile());
    }

    public FileSystemResource getAssetsPet(String randomKey) {
        Pet pet = petRepository.getByRandomKey(randomKey);
        String base = environment.getRequiredProperty("location.file.pet.profile.pic");
        File file = new File(base + pet.getId() + File.separator + pet.getImageName());
        return new FileSystemResource(file.getAbsoluteFile());
    }
}

