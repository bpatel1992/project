
package com.rahul.project.gateway.dao;

import com.rahul.project.gateway.configuration.BusinessException;
import com.rahul.project.gateway.configuration.annotations.RepositoryDao;
import com.rahul.project.gateway.model.User;
import com.rahul.project.gateway.model.UserDevice;
import com.rahul.project.gateway.model.UserLoginPassword;
import com.rahul.project.gateway.utility.AESPasswordUtil;
import com.rahul.project.gateway.utility.PasswordUtil;
import com.rahul.project.gateway.utility.Translator;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author Rahul Malhotra
 * @since 1.0
 */
@SuppressWarnings("unchecked")
@RepositoryDao
public class UserDao implements IUserDao {

    @Autowired
    PasswordUtil passwordUtil;
    @Autowired
    Translator translator;
    @Autowired
    AESPasswordUtil aesPasswordUtil;
    @Autowired
    private AbstractDao abstractDao;

    @Override
    public User findByUserName(String userName) throws Exception {
        User user = null;
        DetachedCriteria criteria = DetachedCriteria.forClass(User.class)
                /* .add(Restrictions.and((Restrictions.or(
                         Restrictions.eq("userName", userName),
                         Restrictions.eq("email", userName),
                         Restrictions.eq("mobile", userName))), Restrictions.eq("userType", "")));*/
                .add((Restrictions.or(Restrictions.eq("userName", userName),
                        Restrictions.eq("email", userName),
                        Restrictions.eq("mobile", userName))));
        List<User> userList = (List<User>) abstractDao.getHibernateTemplate().findByCriteria(criteria);
        if (!CollectionUtils.isEmpty(userList)) {
            user = userList.get(0);
        }
        return user;
    }

    public User findByUserName(String userName, String userType) {
        User user = null;
        DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
        if (userType == null) {
            criteria.add((Restrictions.or(Restrictions.eq("userName", userName),
                    Restrictions.eq("email", userName),
                    Restrictions.eq("mobile", userName))));
        } else {
            criteria.createAlias("authorities", "a");
            criteria
                    .add(Restrictions.and((Restrictions.or(
                            Restrictions.eq("userName", userName),
                            Restrictions.eq("email", userName),
                            Restrictions.eq("mobile", userName))),
                            Restrictions.eq("a.name", userType)));
        }
        List<User> userList = (List<User>) abstractDao.getHibernateTemplate().findByCriteria(criteria);
        if (!CollectionUtils.isEmpty(userList)) {
            user = userList.get(0);
        }
        return user;
    }

    public User findByUserNameCountryId(String userName, String userType, Long countryId) {
        User user = null;
        DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
        if (userType == null) {
            if (countryId == null)
                criteria.add((Restrictions.or(Restrictions.eq("userName", userName),
                        Restrictions.eq("email", userName),
                        Restrictions.eq("mobile", userName))));
            else {
                criteria.createAlias("country", "c");
                criteria.add(Restrictions.and((Restrictions.or(
                        Restrictions.eq("userName", userName),
                        Restrictions.eq("email", userName),
                        Restrictions.eq("mobile", userName))),
                        Restrictions.eq("c.id", countryId)));
            }

        } else {
            if (countryId == null) {
                criteria.createAlias("authorities", "a");
                criteria.add(Restrictions.and((Restrictions.or(
                        Restrictions.eq("userName", userName),
                        Restrictions.eq("email", userName),
                        Restrictions.eq("mobile", userName))),
                        Restrictions.eq("a.name", userType)));
            } else {
                criteria.createAlias("country", "c");
                criteria.add(Restrictions.and((Restrictions.or(
                        Restrictions.eq("userName", userName),
                        Restrictions.eq("email", userName),
                        Restrictions.eq("mobile", userName))),
                        Restrictions.eq("a.name", userType), Restrictions.eq("c.id", countryId)));
            }
        }
        List<User> userList = (List<User>) abstractDao.getHibernateTemplate().findByCriteria(criteria);
        if (!CollectionUtils.isEmpty(userList)) {
            user = userList.get(0);
        }
        return user;
    }

    public User findByUserName(String userName, String password, String loginType, String userAuthority) throws Exception {
        User user;
        String query = " select ulp from UserLoginPassword as ulp,UserAuthority as ua,User u " +
                "where ua.userAuthorityId.user.id=ulp.user.id and  u.id=ulp.user.id and u.id=ua.userAuthorityId.user.id and " +
                "ulp.loginType.loginType=:loginType and (u.userName=:userName or u.email=:userName or " +
                "u.mobile=:userName) and ua.userAuthorityId.authority.name=:userAuthority";
        List<UserLoginPassword> userList = (List<UserLoginPassword>) abstractDao.getSession().createQuery(query).
                setParameter("loginType", loginType).setParameter("userName", userName)
                .setParameter("userAuthority", userAuthority).list();
        if (!CollectionUtils.isEmpty(userList)) {
            user = userList.get(0).getUser();
            if (!userList.get(0).getPassword().equalsIgnoreCase(aesPasswordUtil.convertToAES(password)))
                throw new BusinessException("password.incorrect");
            if (!user.getActivated())
                throw new BusinessException(translator.toLocale("user.not.activated", new String[]{userName}));
        } else
            throw new BusinessException(translator.toLocale("user.type.not.found", new String[]{userName, userAuthority}));
        return user;
    }

    public User findByUserName(String userName, String password, String loginType) throws Exception {
        User user;
        String query = " select ulp from UserLoginPassword as ulp,UserAuthority as ua,User u " +
                "where u.id=ulp.user.id and " +
                "ulp.loginType.loginType=:loginType and (u.userName=:userName or u.email=:userName or " +
                "u.mobile=:userName) ";
        List<UserLoginPassword> userList = (List<UserLoginPassword>) abstractDao.getSession().createQuery(query).
                setParameter("loginType", loginType).setParameter("userName", userName).list();
        if (!CollectionUtils.isEmpty(userList)) {
            user = userList.get(0).getUser();
            if (!userList.get(0).getPassword().equalsIgnoreCase(aesPasswordUtil.convertToAES(password)))
                throw new BusinessException("password.incorrect");
            if (!user.getActivated())
                throw new BusinessException(translator.toLocale("user.not.activated", new String[]{userName}));
        } else
            throw new BusinessException(translator.toLocale("user.not.found", new String[]{userName}));
        return user;
    }

    public UserDevice getUserDevice(String deviceToken) throws Exception {
        CriteriaBuilder builder = abstractDao.getSession().getCriteriaBuilder();
        CriteriaQuery<UserDevice> criteria = builder.createQuery(UserDevice.class);
        Root<UserDevice> root = criteria.from(UserDevice.class);
        criteria.where(builder.equal(root.get("token"), deviceToken));
        TypedQuery<UserDevice> deviceQuery = abstractDao.getSession().createQuery(criteria);
        return deviceQuery.getSingleResult();

    }

    @Override
    public User findByEmail(String email) {
        return null;
    }

    @Override
    public User findByEmailAndActivationKey(String email, String activationKey) {
        return null;
    }

    @Override
    public User findByEmailAndResetPasswordKey(String email, String resetPasswordKey) {
        return null;
    }

}
