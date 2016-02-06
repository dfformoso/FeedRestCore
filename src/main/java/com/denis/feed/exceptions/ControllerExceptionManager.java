package com.denis.feed.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by denis on 7/10/15
 */
@ControllerAdvice
public class ControllerExceptionManager {

    protected final Logger log = LogManager.getLogger();

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not found")  // 404
    @ExceptionHandler(NotFoundException.class)
    public void notFound() {
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Internal error")  // 500
    @ExceptionHandler(RuntimeException.class)
    public void runtimeExc(RuntimeException exc) {
        log.error("Unexpected error", exc);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "bad request")  // 400
    @ExceptionHandler({DuplicateException.class, HttpMessageNotReadableException.class})
    public void duplicateExc(Exception exc) {
        log.error("Bad request", exc);
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "com.denis.feed.user auth error")  // 403
    @ExceptionHandler({AccessNotAllowedException.class})
    public void notAllowedExc(Exception exc) {
        log.error("com.denis.feed.user auth error");
    }
}
