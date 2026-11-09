package com.crm.erp.exception;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.crm.erp.dto.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.crm.erp.constant.SecurityConstant.ERROR_PATH;
import static com.crm.erp.constant.SecurityConstant.INTERNAL_SERVER_ERROR_MSG;
import static org.springframework.http.HttpStatus.*;

public class ExceptionHandling implements ErrorController {

        private final Logger LOGGER = LoggerFactory.getLogger(getClass());

        @ExceptionHandler(Exception.class)
        public ResponseEntity<HttpResponse> internalServerErrorException(Exception exception) {
            LOGGER.error(exception.getMessage());
            if (exception.getMessage().contains("notFound404()"))
                return notFound404();
            else
                return createHttpResponse(INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MSG);
        }

    @ExceptionHandler(JWTDecodeException.class)
    public ResponseEntity<HttpResponse> jWTDecodeException(JWTDecodeException exception) {
        LOGGER.error(exception.getMessage());
        return createHttpResponse(UNAUTHORIZED, exception.getMessage());
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
