package com.denis.feed.exceptions;

/**
 * Created by denis on 13/10/15
 */
public class AccessNotAllowedException extends Exception {

    public AccessNotAllowedException() {
        super();
    }

    public AccessNotAllowedException(String message) {
        super(message);
    }
}
