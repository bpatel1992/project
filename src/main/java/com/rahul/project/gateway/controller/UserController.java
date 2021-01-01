package com.rahul.project.gateway.controller;

import com.rahul.project.gateway.configuration.annotations.RESTController;
import com.rahul.project.gateway.dto.*;
import com.rahul.project.gateway.dto.services.OauthResponse;
import com.rahul.project.gateway.service.SignUpService;
import com.rahul.project.gateway.service.UserService;
import com.rahul.project.gateway.utility.CommonUtility;
import io.swagger.annotations.*;
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
@Api(value = "API provide product basic functionalities",
        description = "This API provides below functionalities : " + "\n" +
                "1. Register user device, " + "\n" +
                "2. Save user document details, " + "\n" +
                "3. Update user document details, "+ "\n" +
                "4. Upload user document by document Id, "+ "\n" +
                "5. Fetch user details, "+ "\n" +
                "6. Fetch user profile pic by random key, "+ "\n" +
                "7. Upload user profile pic by random key or User Id, "+ "\n" +
                "8. Upload user pet profile pic by random key, "+ "\n" +
                "9. Upload user avatar by random key, "+ "\n" +
                "10. Upload user cover by random key or User Id, "+ "\n" +
                "11. Upload user certificate by random key or User Id, "+ "\n" +
                "12. Upload user document by document Id, "+ "\n" +
                "13. Remove user profile pic by User Id, "+ "\n" +
                "14. Update user password and authorities by random key or User Id, "+ "\n" +
                "15. Update user password after sign up, "+ "\n" +
                "16. Update user password, "+ "\n" +
                "17. Create admin user, "+ "\n" +
                "18. Update admin role details, "+ "\n" +
                "19. Create client, "+ "\n" +
                "20. Fetch client details",tags = { "User services" })
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    UserService userService;

    @Autowired
    CommonUtility commonUtility;

    @Autowired
    SignUpService signUpService;

    @ApiOperation(value = "Register user device", produces = MediaType.APPLICATION_JSON_VALUE)
    /*@ApiImplicitParams({
            @ApiImplicitParam(name = "bearer token", value = "Bearer token required to access this service"
                    , required = true, dataType = "String", paramType = "header")})*/
    @PostMapping(value = "/oauth2/api/register-device", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO registerDevice(@Valid @RequestBody UserDeviceDTO userDeviceDTO) throws Exception {
        logger.info("inside register device !!");
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setResponseMessage(userService.registerDevice(userDeviceDTO));
        return responseDTO;
    }

    @ApiOperation(value = "Save user document details", produces = MediaType.APPLICATION_JSON_VALUE)
    /*@ApiImplicitParams({
            @ApiImplicitParam(name = "bearer token", value = "Bearer token required to access this service"
                    , required = true, dataType = "String", paramType = "header")})*/
    @PostMapping(value = "/oauth2/api/save/doc", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public DocumentDTO userDocument(@Valid @RequestBody DocumentDTO documentDTO) throws Exception {
        return userService.processUserDocument(documentDTO);
    }

    @ApiOperation(value = "Update user document details", produces = MediaType.APPLICATION_JSON_VALUE)
    /*@ApiImplicitParams({
            @ApiImplicitParam(name = "bearer token", value = "Bearer token required to access this service"
                    , required = true, dataType = "String", paramType = "header")})*/
    @PostMapping(value = "/oauth2/api/update/doc", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public DocumentDTO userDocumentUpdate(@Valid @RequestBody DocumentDTO documentDTO) throws Exception {
        return userService.userDocumentUpdate(documentDTO);
    }

    @ApiOperation(value = "Upload user document by document Id", produces = MediaType.APPLICATION_JSON_VALUE)
    /*@ApiImplicitParams({
            @ApiImplicitParam(name = "bearer token", value = "Bearer token required to access this service"
                    , required = true, dataType = "String", paramType = "header")})*/
    @PostMapping(value = "/oauth2/api/upload/doc", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public DocumentDTO uploadDoc(@ApiParam(name = "file", required = true) MultipartFile file, @ApiParam(name = "id", required = true) Long docId) throws Exception {
        return userService.uploadDoc(file, docId);
    }

    @ApiOperation(value = "Fetch user details", produces = MediaType.APPLICATION_JSON_VALUE)
    /*@ApiImplicitParams({
            @ApiImplicitParam(name = "bearer token", value = "Bearer token required to access this service"
                    , required = true, dataType = "String", paramType = "header")})*/
    @GetMapping(value = "/oauth2/api/get-user-id", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO getUserObject() {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setId(commonUtility.getLoggedInUser());
        return responseDTO;
    }

    @ApiOperation(value = "Fetch user profile pic by random key", produces = MediaType.APPLICATION_JSON_VALUE)
    /*@ApiImplicitParams({
            @ApiImplicitParam(name = "bearer token", value = "Bearer token required to access this service"
                    , required = true, dataType = "String", paramType = "header")})*/
    @GetMapping(value = "/oauth2/api/user/getProfilePic", /*consumes = MediaType.APPLICATION_JSON_VALUE,*/
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO getProfilePic(@ApiParam(name = "randomKey", required = false) String randomKey) throws Exception {
        return new ResponseDTO(userService.downloadProfilePicture(randomKey));
    }

    @ApiOperation(value = "Upload user profile pic by random key or User Id", produces = MediaType.APPLICATION_JSON_VALUE)
    /*@ApiImplicitParams({
            @ApiImplicitParam(name = "bearer token", value = "Bearer token required to access this service"
                    , required = true, dataType = "String", paramType = "header")})*/
    @PostMapping(value = "/oauth2/api/user/uploadProfilePic", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO profilePicUpdate(@ApiParam(name = "file", required = true) MultipartFile file, @ApiParam(name = "randomKey", required = false) String randomKey) throws Exception {
        return userService.updateProfilePicture(file, randomKey, new ResponseDTO());
    }

    @ApiOperation(value = "Upload user pet profile pic by random key", produces = MediaType.APPLICATION_JSON_VALUE)
    /*@ApiImplicitParams({
            @ApiImplicitParam(name = "bearer token", value = "Bearer token required to access this service"
                    , required = true, dataType = "String", paramType = "header")})*/
    @PostMapping(value = "/oauth2/api/pet/uploadProfilePic", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO profilePicUpdatePet(@ApiParam(name = "file", required = true) MultipartFile file, @ApiParam(name = "randomKey", required = true) String randomKey) throws Exception {
        return userService.updateProfilePicturePet(file, randomKey, new ResponseDTO());
    }

    @ApiOperation(value = "Upload user avatar by random key", produces = MediaType.APPLICATION_JSON_VALUE)
    /*@ApiImplicitParams({
            @ApiImplicitParam(name = "bearer token", value = "Bearer token required to access this service"
                    , required = true, dataType = "String", paramType = "header")})*/
    @PostMapping(value = "/oauth2/api/user/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO saveUserAvatar(@ApiParam(name = "file", required = true) MultipartFile file, @ApiParam(name = "randomKey", required = false) String randomKey) throws Exception {
        return userService.updateUserAvatar(file, randomKey, new ResponseDTO());
    }

    @ApiOperation(value = "Upload user cover by random key or User Id", produces = MediaType.APPLICATION_JSON_VALUE)
    /*@ApiImplicitParams({
            @ApiImplicitParam(name = "bearer token", value = "Bearer token required to access this service"
                    , required = true, dataType = "String", paramType = "header")})*/
    @PostMapping(value = "/oauth2/api/user/cover", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO saveUserCover(@ApiParam(name = "file", required = true) MultipartFile file, @ApiParam(name = "randomKey", required = false) String randomKey) throws Exception {
        return userService.updateUserCover(file, randomKey, new ResponseDTO());
    }

    @ApiOperation(value = "Upload user certificate by random key or User Id", produces = MediaType.APPLICATION_JSON_VALUE)
    /*@ApiImplicitParams({
            @ApiImplicitParam(name = "bearer token", value = "Bearer token required to access this service"
                    , required = true, dataType = "String", paramType = "header")})*/
    @PostMapping(value = "/oauth2/api/user/certificate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO saveUserCertificate(@RequestParam("files") MultipartFile[] files, @RequestParam(value = "randomKey", required = false) String randomKey) throws Exception {
        return userService.updateUserCertificate(files, randomKey, new ResponseDTO());
    }

    @ApiOperation(value = "Upload user document by document Id", produces = MediaType.APPLICATION_JSON_VALUE)
    /*@ApiImplicitParams({
            @ApiImplicitParam(name = "bearer token", value = "Bearer token required to access this service"
                    , required = true, dataType = "String", paramType = "header")})*/
    @PostMapping(value = "/oauth2/api/user/gallery", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO saveUserGallery(@RequestParam("files") MultipartFile[] files, @RequestParam(value = "randomKey", required = false) String randomKey) throws Exception {
        return userService.updateUserGallery(files, randomKey, new ResponseDTO());
    }

    @ApiOperation(value = "Remove user profile pic by User Id", produces = MediaType.APPLICATION_JSON_VALUE)
    /*@ApiImplicitParams({
            @ApiImplicitParam(name = "bearer token", value = "Bearer token required to access this service"
                    , required = true, dataType = "String", paramType = "header")})*/
    @DeleteMapping(value = "/oauth2/api/user/removeProfilePic", /*consumes = MediaType.APPLICATION_JSON_VALUE,*/
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO removeProfilePic() throws Exception {
        return new ResponseDTO(userService.removeProfilePic());
    }

    @ApiOperation(value = "Update user password and authorities by random key or User Id", produces = MediaType.APPLICATION_JSON_VALUE)
    /*@ApiImplicitParams({
            @ApiImplicitParam(name = "bearer token", value = "Bearer token required to access this service"
                    , required = true, dataType = "String", paramType = "header")})*/
    @PostMapping(value = {"/oauth2/api/user/role/update", "/api/user/role/update"}, /*consumes = MediaType.APPLICATION_JSON_VALUE,*/
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO passwordUpdate(@RequestBody SavePasswordAdminDTO savePasswordAdminDTO) throws Exception {
        return userService.userPasswordUpdate(savePasswordAdminDTO, new ResponseDTO());
    }

    @ApiOperation(value = "Update user password after sign up", produces = MediaType.APPLICATION_JSON_VALUE)
    /*@ApiImplicitParams({
            @ApiImplicitParam(name = "bearer token", value = "Bearer token required to access this service"
                    , required = true, dataType = "String", paramType = "header")})*/
    @PostMapping(value = {"/api/user/password/update", "oauth2/api/user/password/update"}, /*consumes = MediaType.APPLICATION_JSON_VALUE,*/
            produces = MediaType.APPLICATION_JSON_VALUE)
    public OauthResponse passwordUpdateSignUp(@RequestBody SavePasswordAdminDTO savePasswordAdminDTO) throws Exception {

        userService.userPasswordUpdate(savePasswordAdminDTO, new ResponseDTO());
        return signUpService.loginAfterSignUp("random", savePasswordAdminDTO.getRandomKey());
    }

    @ApiOperation(value = "Update user password", produces = MediaType.APPLICATION_JSON_VALUE)
    /*@ApiImplicitParams({
            @ApiImplicitParam(name = "bearer token", value = "Bearer token required to access this service"
                    , required = true, dataType = "String", paramType = "header")})*/
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

    @ApiOperation(value = "Create admin user", produces = MediaType.APPLICATION_JSON_VALUE)
    /*@ApiImplicitParams({
            @ApiImplicitParam(name = "bearer token", value = "Bearer token required to access this service"
                    , required = true, dataType = "String", paramType = "header")})*/
    @RequestMapping(path = {"/oauth2/api/admin/user/create"}, method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO createAdminUser(@Valid @RequestBody UserDTO userDTO) throws Exception {
        return userService.createAdminUser(userDTO);
    }

    @ApiOperation(value = "Update admin role details", produces = MediaType.APPLICATION_JSON_VALUE)
    /*@ApiImplicitParams({
            @ApiImplicitParam(name = "bearer token", value = "Bearer token required to access this service"
                    , required = true, dataType = "String", paramType = "header")})*/
    @RequestMapping(path = {"/oauth2/api/admin/role/create/update", "/api/admin/role/create/update"}, method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public RoleDTO createRoleAdmin(@Valid @RequestBody RoleDTO roleDTO) throws Exception {
        return userService.createUpdateRoleAdmin(roleDTO);
    }

    @ApiOperation(value = "Create client", produces = MediaType.APPLICATION_JSON_VALUE)
    /*@ApiImplicitParams({
            @ApiImplicitParam(name = "bearer token", value = "Bearer token required to access this service"
                    , required = true, dataType = "String", paramType = "header")})*/
    @RequestMapping(path = {"/api/admin/add/client"}, method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public AddClientDto addClient(@Valid @RequestBody AddClientDto addClientDto) throws Exception {
        return userService.addClient(addClientDto);
    }

    @ApiOperation(value = "Fetch client details", produces = MediaType.APPLICATION_JSON_VALUE)
    /*@ApiImplicitParams({
            @ApiImplicitParam(name = "bearer token", value = "Bearer token required to access this service"
                    , required = true, dataType = "String", paramType = "header")})*/
    @RequestMapping(path = {"/api/user/fetchClientDetails"}, method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO fetchClientDetails(@Valid @RequestBody UserDTO userDTO) throws Exception {
        return userService.fetchClientDetails(userDTO);
    }

}
