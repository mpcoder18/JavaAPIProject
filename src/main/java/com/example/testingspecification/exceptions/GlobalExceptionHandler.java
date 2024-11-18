package com.example.testingspecification.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.io.IOException;


/**
 * Spring bean, used for global exception handling
 * Handles CSVFormatError, EntityNotFoundException and IOException.
 * The class is annotated with @ControllerAdvice, which tells Spring to
 * treat it as a global exception handler. This means that whenever an exception
 * is thrown in any part of the application (especially from controllers), this class
 * will be responsible for catching and handling it.
 */
@ControllerAdvice
public class GlobalExceptionHandler {


    /**
     * This method catches instances of EntityNotFoundException, typically thrown
     * when an entity requested by the client (like a database record) cannot be found.
     * It returns an ErrorResponse object with a 404 Not Found status, indicating the
     *  requested resource does not exist.
     * @param e exception parameter.
     * @return returns a errorrepsonse.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException e) {
        ErrorResponse errorObject = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }

    /**
     * This method handles CSVFormatError, a custom exception that occurs when there are issues with CSV file
     * formatting. It returns an error response with a 400 Bad Request status, indicating that the
     * client made a bad request due to malformed CSV data.
     * @param e excpetion.
     * @return return a error response.
     */
    @ExceptionHandler(CSVFormatError.class)
    public ResponseEntity<ErrorResponse> handleCSVFormatError(CSVFormatError e) {
        ErrorResponse errorObject = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
    }

    /**
     * This method catches IOException, which may be thrown when there are issues related
     * to input/output operations, such as reading or writing files. It returns an ErrorResponse
     * with a 500 Internal Server Error status, indicating a server-side problem.
     * @param e this is an excpetion.
     * @return return a response error.
     */
    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorResponse> handleIOException(IOException e) {
        ErrorResponse errorObject = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(errorObject, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
