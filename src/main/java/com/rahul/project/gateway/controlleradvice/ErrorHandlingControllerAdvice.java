package com.rahul.project.gateway.controlleradvice;

import com.rahul.project.gateway.configuration.BusinessException;
import com.rahul.project.gateway.configuration.UserNotActivatedException;
import com.rahul.project.gateway.dto.ResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.lang.invoke.MethodHandles;
import java.util.Locale;

/**
 * @author Rahul Malhotra
 * @since 1.0
 * Date 2019-05-21
 */
@ControllerAdvice
class
ErrorHandlingControllerAdvice {

    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse onConstraintValidationException(ConstraintViolationException e, Locale locale) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        for (ConstraintViolation violation : e.getConstraintViolations()) {
            error.getViolations().add(new Violation(violation.getPropertyPath().toString(),
                    messageSource.getMessage(violation.getMessage(), null, locale)));
        }
        return error;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse onMethodArgumentNotValidException(MethodArgumentNotValidException e, Locale locale) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            error.getViolations().add(new Violation(fieldError.getField(),
                    messageSource.getMessage(fieldError.getDefaultMessage(), null, locale)));
        }
        return error;
    }

    @ExceptionHandler({BusinessException.class, UserNotActivatedException.class})
    @ResponseStatus(HttpStatus.FAILED_DEPENDENCY)
    @ResponseBody
    public ResponseDTO handleBusinessExceptions(Exception ex, Locale locale) {
        String message = messageSource.getMessage(ex.getMessage(), null, locale);
        logger.error("business exception {}.", message);
        ResponseDTO responseDTO = new ResponseDTO();
        if (message.contains(":")) {
            responseDTO.setResponseCode(message.substring(0, message.lastIndexOf(":")));
            responseDTO.setResponseMessage(message.substring(message.lastIndexOf(":") + 1));
        } else {
            responseDTO.setResponseCode("XXXX");
            responseDTO.setResponseMessage(message);
        }
        return responseDTO;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseDTO handleExceptions(Exception ex, Locale locale) {
        logger.error("exception trace is ", ex);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setResponseMessage(messageSource.getMessage("internal.error", null, locale));
        return responseDTO;
    }
}
