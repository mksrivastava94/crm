package com.technu.erp.exception;

import com.technu.erp.exception.domain.*;
import com.technu.erp.model.response.HttpResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.technu.erp.constant.OrderConstant.ERROR_PATH;
import static com.technu.erp.constant.OrderConstant.INTERNAL_SERVER_ERROR_MSG;
import static org.springframework.http.HttpStatus.*;

@Log4j2
@RestControllerAdvice
public class ExceptionHandling implements ErrorController {

    @ExceptionHandler(EmailExistException.class)
    public ResponseEntity<HttpResponse> emailExistException(EmailExistException exception) {
        log.error(exception.getMessage());
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(InvalidEmailIdException.class)
    public ResponseEntity<HttpResponse> invalidEmailIdException(InvalidEmailIdException exception) {
        log.error(exception.getMessage());
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<HttpResponse> productNotFoundException(ProductNotFoundException exception) {
        log.error(exception.getMessage());
        log.error(exception.toString());
        return createHttpResponse(NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<HttpResponse> clientNotFoundException(ClientNotFoundException exception) {
        log.error(exception.getMessage());
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(InvalidOrgNameOrEmpty.class)
    public ResponseEntity<HttpResponse> invalidOrgNameOrEmpty(InvalidOrgNameOrEmpty exception) {
        log.error(exception.getMessage());
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<HttpResponse> orderNotFoundException(OrderNotFoundException exception) {
        log.error(exception.getMessage());
        return createHttpResponse(NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpResponse> internalServerErrorException(Exception exception) {
        log.error(exception.getMessage());
        if (exception.getMessage().contains("notFound404()"))
            return notFound404();
        else
            return createHttpResponse(INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MSG);
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
