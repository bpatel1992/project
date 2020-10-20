
package com.rahul.project.gateway.dao;


import com.rahul.project.gateway.model.User;

/**
 * @author Rahul Malhotra
 * @since 1.0
 * Date 2019-05-21
 */
public interface IUserDao {

    User findByUserName(String userName) throws Exception;

    User findByUserName(String userName, String password, String loginType) throws Exception;

    User findByUserName(String userName, String password, String loginType, String userAuthority) throws Exception;

    User findByEmail(String email);

    User findByEmailAndActivationKey(String email, String activationKey);

    User findByEmailAndResetPasswordKey(String email, String resetPasswordKey);

}
