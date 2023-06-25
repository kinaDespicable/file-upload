package dev.dani.fileupload.exception;

import dev.dani.fileupload.exception.exceptions.FileUploadException;
import dev.dani.fileupload.exception.exceptions.InvalidPathSequenceException;
import dev.dani.fileupload.exception.exceptions.ResourceNotFoundException;
import dev.dani.fileupload.model.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<ErrorResponse> fileUploadExceptionHandler(FileUploadException exception) {
        var responseBody = constructResponse(exception, INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(responseBody);
    }

    @ExceptionHandler(InvalidPathSequenceException.class)
    public ResponseEntity<ErrorResponse> invalidPathSequenceExceptionHandler(InvalidPathSequenceException exception) {
        var responseBody = constructResponse(exception, BAD_REQUEST);
        return ResponseEntity.status(BAD_REQUEST).body(responseBody);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException exception) {
        var responseBody = constructResponse(exception, NOT_FOUND);
        return ResponseEntity.status(NOT_FOUND).body(responseBody);
    }

    private ErrorResponse constructResponse(RuntimeException exception, HttpStatus httpStatus) {
        return ErrorResponse.builder()
                .status(httpStatus)
                .statusCode(httpStatus.value())
                .timestamp(Instant.now())
                .message(exception.getMessage())
                .build();
    }

}
