package com.denis.feed.user.service;

import com.denis.feed.authToken.domain.AuthToken;
import com.denis.feed.authToken.service.AuthTokenService;
import com.denis.feed.exceptions.DuplicateException;
import com.denis.feed.exceptions.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.denis.feed.user.dao.UserRepository;
import com.denis.feed.user.domain.User;

import java.util.Date;

/**
 * Created by denis on 24/09/15
 */
@Service(value = "userService")
public class UserService {

    protected final Logger log = LogManager.getLogger();


    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthTokenService authTokenService;

    @Transactional
    public User createUser(User user) throws DuplicateException {
        user.setCreationDate(new Date());
        User dbUser = userRepository.getByUsername(user.getUsername());
        if (dbUser != null) {
            throw new DuplicateException();
        }
        User toRet = userRepository.save(user);
        return toRet;
    }

    @Transactional
    public AuthToken loginUser(String username, String password) throws NotFoundException {
        User dbUser = userRepository.getByUsername(username);
        //TODO WITH NO ENCRYPTION
        log.info("username " + username);
        log.info("password " + password);
        log.info("dbUser " + dbUser);
        if (dbUser != null) {
            log.info("dbUser.getPassword() " + dbUser.getPassword());
        }
        if ((dbUser == null) || (dbUser.getPassword().equals(password) == false)) {
            throw new NotFoundException();
        }
        return authTokenService.create(dbUser.getIdUser());
    }

    public User getUser(Long idUser) {
        return userRepository.getByIdUser(idUser);
    }
}
