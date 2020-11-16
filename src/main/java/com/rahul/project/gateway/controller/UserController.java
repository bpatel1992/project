package com.rahul.project.gateway.controller;

import com.rahul.project.gateway.configuration.annotations.RESTController;
import com.rahul.project.gateway.dto.*;
import com.rahul.project.gateway.dto.services.OauthResponse;
import com.rahul.project.gateway.service.SignUpService;
import com.rahul.project.gateway.service.UserService;
import com.rahul.project.gateway.utility.CommonUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;

/**
 * @author Rahul Malhotra
 * @since 1.0
 * Date 2019-05-21
 */
@RESTController
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    UserService userService;

    @Autowired
    CommonUtility commonUtility;

    @Autowired
    SignUpService signUpService;

    @PostMapping(value = "/oauth2/api/register-device", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO registerDevice(@Valid @RequestBody UserDeviceDTO userDeviceDTO) throws Exception {
        logger.info("inside register device !!");
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setResponseMessage(userService.registerDevice(userDeviceDTO));
        return responseDTO;
    }

    @PostMapping(value = "/oauth2/api/save/doc", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public DocumentDTO userDocument(@Valid @RequestBody DocumentDTO documentDTO) throws Exception {
        return userService.processUserDocument(documentDTO);
    }

    @PostMapping(value = "/oauth2/api/update/doc", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public DocumentDTO userDocumentUpdate(@Valid @RequestBody DocumentDTO documentDTO) throws Exception {
        return userService.userDocumentUpdate(documentDTO);
    }

    @PostMapping(value = "/oauth2/api/upload/doc", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public DocumentDTO uploadDoc(@RequestParam("file") MultipartFile file, @RequestParam("id") Long docId) throws Exception {
        return userService.uploadDoc(file, docId);
    }

    @GetMapping(value = "/oauth2/api/get-user-id", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO getUserObject() {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setId(commonUtility.getLoggedInUser());
        return responseDTO;
    }

    @GetMapping(value = "/oauth2/api/user/getProfilePic", /*consumes = MediaType.APPLICATION_JSON_VALUE,*/
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO getProfilePic(@RequestParam(value = "randomKey", required = false) String randomKey) throws Exception {
        return new ResponseDTO(userService.downloadProfilePicture(randomKey));
    }

    @PostMapping(value = "/oauth2/api/user/uploadProfilePic", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO profilePicUpdate(@RequestParam("file") MultipartFile file, @RequestParam(value = "randomKey", required = false) String randomKey) throws Exception {
        return userService.updateProfilePicture(file, randomKey, new ResponseDTO());
    }

    @PostMapping(value = "/oauth2/api/pet/uploadProfilePic", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO profilePicUpdatePet(@RequestParam("file") MultipartFile file, @RequestParam("randomKey") String randomKey) throws Exception {
        return userService.updateProfilePicturePet(file, randomKey, new ResponseDTO());
    }

    @PostMapping(value = "/oauth2/api/user/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO saveUserAvatar(@RequestParam("file") MultipartFile file, @RequestParam(value = "randomKey", required = false) String randomKey) throws Exception {
        return userService.updateUserAvatar(file, randomKey, new ResponseDTO());
    }

    @PostMapping(value = "/oauth2/api/user/cover", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO saveUserCover(@RequestParam("file") MultipartFile file, @RequestParam(value = "randomKey", required = false) String randomKey) throws Exception {
        return userService.updateUserCover(file, randomKey, new ResponseDTO());
    }

    @PostMapping(value = "/oauth2/api/user/certificate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO saveUserCertificate(@RequestParam("files") MultipartFile[] files, @RequestParam(value = "randomKey", required = false) String randomKey) throws Exception {
        return userService.updateUserCertificate(files, randomKey, new ResponseDTO());
    }

    @PostMapping(value = "/oauth2/api/user/gallery", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO saveUserGallery(@RequestParam("files") MultipartFile[] files, @RequestParam(value = "randomKey", required = false) String randomKey) throws Exception {
        return userService.updateUserGallery(files, randomKey, new ResponseDTO());
    }

    @DeleteMapping(value = "/oauth2/api/user/removeProfilePic", /*consumes = MediaType.APPLICATION_JSON_VALUE,*/
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO removeProfilePic() throws Exception {
        return new ResponseDTO(userService.removeProfilePic());
    }

    @PostMapping(value = {"/oauth2/api/user/role/update", "/api/user/role/update"}, /*consumes = MediaType.APPLICATION_JSON_VALUE,*/
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO passwordUpdate(@RequestBody SavePasswordAdminDTO savePasswordAdminDTO) throws Exception {
        return userService.userPasswordUpdate(savePasswordAdminDTO, new ResponseDTO());
    }

    @PostMapping(value = {"/api/user/password/update", "oauth2/api/user/password/update"}, /*consumes = MediaType.APPLICATION_JSON_VALUE,*/
            produces = MediaType.APPLICATION_JSON_VALUE)
    public OauthResponse passwordUpdateSignUp(@RequestBody SavePasswordAdminDTO savePasswordAdminDTO) throws Exception {

        userService.userPasswordUpdate(savePasswordAdminDTO, new ResponseDTO());
        return signUpService.loginAfterSignUp("random", savePasswordAdminDTO.getRandomKey());
    }

    @PostMapping(value = {"oauth2/api/user/password/update"}, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO passwordUpdateNew(@RequestBody SavePasswordAdminDTO savePasswordAdminDTO) throws Exception {

        return userService.userPasswordUpdate(savePasswordAdminDTO, new ResponseDTO());
    }

    /*  @PostMapping(value = {"/api/login"}, *//*consumes = MediaType.APPLICATION_JSON_VALUE,*//*
            produces = MediaType.APPLICATION_JSON_VALUE)
    public OauthResponse apiLogin(@RequestBody SavePasswordAdminDTO savePasswordAdminDTO) throws Exception {
        return signUpService.loginAfterSignUp("random", savePasswordAdminDTO.getRandomKey());
    }*/

    @RequestMapping(path = {"/oauth2/api/admin/user/create"}, method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO createAdminUser(@Valid @RequestBody UserDTO userDTO) throws Exception {
        return userService.createAdminUser(userDTO);
    }

    @RequestMapping(path = {"/oauth2/api/admin/role/create/update", "/api/admin/role/create/update"}, method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public RoleDTO createRoleAdmin(@Valid @RequestBody RoleDTO roleDTO) throws Exception {
        return userService.createUpdateRoleAdmin(roleDTO);
    }


    @RequestMapping(path = {"/api/admin/add/client"}, method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public AddClientDto addClient(@Valid @RequestBody AddClientDto addClientDto) throws Exception {
        return userService.addClient(addClientDto);
    }

}
