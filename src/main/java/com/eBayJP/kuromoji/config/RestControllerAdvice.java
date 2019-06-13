package com.eBayJP.kuromoji.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.eBayJP.kuromoji.common.exception.CannotGenerateJwtKeyException;
import com.eBayJP.kuromoji.common.exception.JwtAuthorizationException;
import com.eBayJP.kuromoji.common.exception.UnAuthenticationException;

/**
 * <pre>
 * com.eBayJP.kuromoji.config_RestControllerAdvice.java
 * </pre>
 * @date : 2019. 6. 13
 * @author : hychoi
 */
@ControllerAdvice
public class RestControllerAdvice {
	private final Logger log = LoggerFactory.getLogger(RestControllerAdvice.class);
	
	@ExceptionHandler(UnAuthenticationException.class)
    public ResponseEntity<Void> unAuthentication(UnAuthenticationException e) {
        log.debug("UnAuthenticationException occur! : {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(JwtAuthorizationException.class)
    public ResponseEntity<Void> jwtAuthorization(JwtAuthorizationException e) {
        log.debug("JwtAuthorizationException occur! : {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @ExceptionHandler({CannotGenerateJwtKeyException.class})
    public ResponseEntity<Void> internalServer(CannotGenerateJwtKeyException e) {
        log.debug("500 Error occur! : {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
