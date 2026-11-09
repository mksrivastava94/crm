package com.crm.erp.exception;

import com.crm.erp.dto.HttpResponse;
import com.crm.erp.exception.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

import static com.crm.erp.constant.ProfileConstant.ERROR_PATH;
import static com.crm.erp.constant.ProfileConstant.INTERNAL_SERVER_ERROR_MSG;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ExceptionHandling implements ErrorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandling.class);

    @ExceptionHandler(EmailExistException.class)
    public ResponseEntity<HttpResponse> emailExistException(EmailExistException exception) {
        LOGGER.error(exception.getMessage());
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }


    @ExceptionHandler(InvalidOrgNameOrEmpty.class)
    public ResponseEntity<HttpResponse> invalidOrgNameOrEmpty(InvalidOrgNameOrEmpty exception) {
        LOGGER.error(exception.getMessage());
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpResponse> internalServerErrorException(Exception exception) {
        LOGGER.error(exception.getMessage());
        if (exception.getMessage().contains("notFound404()"))
            return notFound404();
        else
            return createHttpResponse(INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MSG);
    }

    @ExceptionHandler(UserServiceDownException.class)
    public ResponseEntity<HttpResponse> userServiceDownException(UserServiceDownException exception) {
        LOGGER.error(exception.getMessage());
        return createHttpResponse(INTERNAL_SERVER_ERROR, exception.getMessage());
    }


    @ExceptionHandler(ProfileExistException.class)
    public ResponseEntity<HttpResponse> profileExistException(ProfileExistException exception) {
        LOGGER.error(exception.getMessage());
        exception.printStackTrace();
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(ProfileNotFoundException.class)
    public ResponseEntity<HttpResponse> profileNotFoundException(ProfileNotFoundException exception) {
        LOGGER.error(exception.getMessage());
        return createHttpResponse(NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(PhoneNumberNotFoundException.class)
    public ResponseEntity<HttpResponse> phoneNumberNotFoundException(PhoneNumberNotFoundException exception) {
        LOGGER.error(exception.getMessage());
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<HttpResponse> constraintViolationException(Exception exception) {
        LOGGER.error(exception.getMessage());
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }


    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message) {
        HttpResponse error = new HttpResponse(httpStatus.value(), httpStatus,
                httpStatus.getReasonPhrase().toUpperCase(), message.toUpperCase());
        return new ResponseEntity<>(error, httpStatus);
    }

    @RequestMapping(ERROR_PATH)
    public ResponseEntity<HttpResponse> notFound404() {
        return createHttpResponse(NOT_FOUND, "There is no mapping for this URL");
    }
}
