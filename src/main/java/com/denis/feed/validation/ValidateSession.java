package com.denis.feed.validation;


import com.denis.feed.Utils.Session;
import com.denis.feed.authToken.domain.AuthToken;
import com.denis.feed.authToken.enums.TokenStatus;
import com.denis.feed.authToken.service.AuthTokenService;
import com.denis.feed.exceptions.AccessNotAllowedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import com.denis.feed.user.domain.User;
import com.denis.feed.user.service.UserService;

import java.time.Duration;
import java.time.Instant;

/**
 * Created by denis on 8/10/15
 */

@Component
public class ValidateSession implements HandlerMethodArgumentResolver {

    protected final Logger log = LogManager.getLogger();


    @Autowired
    private AuthTokenService tokenService;

    @Autowired
    private UserService userService;

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        String token = webRequest.getHeader("AUTH");
        //HACER COSAS
        log.info("[resolveArgument] token[" + token + "]");

        if ((token == null) || (token.trim().isEmpty())) {
            log.info("[resolveArgument] token not specified");
            throw new AccessNotAllowedException();
        }
        try {
            AuthToken t = this.tokenService.getByHash(token);

            if ((t == null) || (t.getStatus() == (TokenStatus.ACTIVE.getStatus()) == false)) {
                log.error("[resolveArgument] tokenHash[" + token
                        + "] is not enabled");
                throw new AccessNotAllowedException();
            }
            t.setExpirationDate(Instant.now().plus(Duration.ofMinutes(30)));
            t = this.tokenService.update(t);

            log.info("[resolveArgument] idUser[" + t.getIdUser()
                    + "] authenticated with idToken[" + t.getIdToken() + "]");

            User u = userService.getUser(t.getIdUser());
            Session s = new Session(t, u);
            return s;
        } catch (AccessNotAllowedException notAllowed) {
            log.error("[resolveArgument] unauthorized request");
            throw notAllowed;
        }
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(InjectSession.class);
    }
}