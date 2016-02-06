package com.denis.feed.Utils;


import com.denis.feed.authToken.domain.AuthToken;
import com.denis.feed.user.domain.User;

/**
 * Created by denis on 14/10/15
 */
public class Session {

    private final AuthToken token;

    private final User user;

    public Session(AuthToken token, User user) {
        this.token = token;
        this.user = user;
    }

    public AuthToken getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }
}
