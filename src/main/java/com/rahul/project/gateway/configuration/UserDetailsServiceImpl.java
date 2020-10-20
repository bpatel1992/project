
package com.rahul.project.gateway.configuration;

import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.dao.AbstractDao;
import com.rahul.project.gateway.model.Authority;
import com.rahul.project.gateway.model.User;
import com.rahul.project.gateway.utility.Translator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Rahul Malhotra
 * @since 1.0
 * Date 2019-05-21
 */
@TransactionalService
public class UserDetailsServiceImpl implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    @Autowired
    Translator translator;
    @Autowired
    private AbstractDao abstractDao;

    @Override
    public UserDetails loadUserByUsername(final String userName) {

        log.debug("Authenticating {}", userName);
        User userFromDatabase;
        userFromDatabase = getUserById(Long.parseLong(userName));

        if (userFromDatabase == null) {
            throw new UsernameNotFoundException(translator.toLocale("user.not.found", new String[]{userName}));
        } else if (!userFromDatabase.getActivated()) {
            throw new UserNotActivatedException(translator.toLocale("user.not.activated", new String[]{userName}));
        }

        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Authority authority : userFromDatabase.getAuthorities()) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getName());
            grantedAuthorities.add(grantedAuthority);
        }

        return new org.springframework.security.core.userdetails.User(String.valueOf(userFromDatabase.getId()),
                "", grantedAuthorities);

    }

    public User getUserById(Long userId) {
        return abstractDao.getEntityById(User.class, userId);
    }

}
