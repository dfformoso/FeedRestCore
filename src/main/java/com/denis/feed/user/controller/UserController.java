package com.denis.feed.user.controller;

import com.denis.feed.authToken.domain.AuthToken;
import com.denis.feed.exceptions.DuplicateException;
import com.denis.feed.exceptions.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.denis.feed.user.domain.User;
import com.denis.feed.user.service.UserService;


/**
 * Created by denis on 29/09/15
 */
@RestController
@RequestMapping("/users")
public class UserController {
    protected final Logger log = LogManager.getLogger();

    @Autowired
    UserService userService;

//    @RequestMapping("/{id}")
//    public ResponseEntity<User> findById(@PathVariable Long id, @InjectSession Session session) {
//        User com.denis.feed.user = userService.getUser(id);
//        return new ResponseEntity<>(com.denis.feed.user, HttpStatus.OK);
//
//    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<User> create(@RequestBody User user) throws DuplicateException {
        User toRet = userService.createUser(user);
        return new ResponseEntity<>(toRet, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public
    @ResponseBody
    AuthToken login(@RequestBody(required = true) User user
    ) throws NotFoundException {
        AuthToken token = userService.loginUser(user.getUsername(), user.getPassword());
        log.info("Token" + token.getTokenHash());
        return token;
    }

}
