package com.video.stream.errors;

import com.video.stream.models.HttpResponses.CustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomizdErrorExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(BadRequest.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequest ex, WebRequest request) {
        CustomResponse errorResponse = new CustomResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setCode("RR");
//        errorResponse.setDetails(request.getDescription(false));

        // Logger logger = Logger.getLogger(getClass().getName());
        // GeneralUtils.logger(Level.SEVERE, errorResponse, getClass().getName());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}