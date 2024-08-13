package com.joaoeduardo.planner_backend.config;

import com.joaoeduardo.planner_backend.participant.exception.ParticipantNotFoundException;
import com.joaoeduardo.planner_backend.trip.exception.TripNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;

@ControllerAdvice
public class ExceptionEntityHandler {

    @ExceptionHandler(TripNotFoundException.class)
    public ResponseEntity<StandardError> handleTripNotFoundException(TripNotFoundException exception, HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError error = new StandardError(Instant.now(), status.value(), "TRIP not found!",
                exception.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(ParticipantNotFoundException.class)
    public ResponseEntity<StandardError> handleParticipantNotFoundException(ParticipantNotFoundException exception, HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError error = new StandardError(Instant.now(), status.value(), "PARTICIPANT not found!",
                exception.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<StandardError> handleValidationException(ValidationException exception, HttpServletRequest request){

        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError error = new StandardError(Instant.now(), status.value(), "Validation Error!",
                exception.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(error);

    }

//    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
//    public ResponseEntity<StandardError> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception,
//                                                                            HttpServletRequest request){
//
//        HttpStatus status = HttpStatus.NOT_FOUND;
//        StandardError error = new StandardError(Instant.now(), status.value(), "Resource Not Found with provided ID!",
//                exception.getMessage(), request.getRequestURI());
//        return ResponseEntity.status(status).body(error);
//    }

}
