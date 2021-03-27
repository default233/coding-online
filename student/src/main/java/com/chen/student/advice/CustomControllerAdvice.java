package com.chen.student.advice;

import com.chen.biz.exception.CustomException;
import com.chen.student.utils.ErrorDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @author danger
 * @date 2021/3/25
 */
@ControllerAdvice
@Slf4j
public class CustomControllerAdvice {

    @ExceptionHandler({CustomException.class, UsernameNotFoundException.class})
    public HttpEntity customExceptionHandler(Exception e, HttpServletRequest request) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setTimestamp(LocalDateTime.now());
        errorDetails.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorDetails.setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        errorDetails.setMessage(e.getLocalizedMessage());
        e.printStackTrace();
        errorDetails.setPath(request.getServletPath());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorDetails);
    }

    @ExceptionHandler({MailSendException.class})
    public HttpEntity mailExceptionHandler(Exception e, HttpServletRequest request) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setTimestamp(LocalDateTime.now());
        errorDetails.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        e.printStackTrace();
        errorDetails.setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        errorDetails.setMessage("邮箱地址无效，请使用正确的邮箱！");
        errorDetails.setPath(request.getServletPath());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorDetails);
    }

    @ExceptionHandler({BadCredentialsException.class})
    public HttpEntity exceptionHandler(Exception e, HttpServletRequest request) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setTimestamp(LocalDateTime.now());
        errorDetails.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        e.printStackTrace();
        errorDetails.setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        errorDetails.setMessage("用户名或密码不正确！");
        e.printStackTrace();
        errorDetails.setPath(request.getServletPath());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorDetails);
    }
}
