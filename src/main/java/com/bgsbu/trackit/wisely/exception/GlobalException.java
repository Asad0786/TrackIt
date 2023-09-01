package com.bgsbu.trackit.wisely.exception;

import com.bgsbu.trackit.wisely.payload.ExceptionalDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;

@ControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionalDto> handleResourceNotFound(
            ResourceNotFoundException resourceNotFoundException,
            WebRequest webRequest){
        ExceptionalDto exceptionalDto = new ExceptionalDto();
        exceptionalDto.setLocalDate(LocalDate.now());
        exceptionalDto.setError(Integer.toString(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        exceptionalDto.setMessage(resourceNotFoundException.getMessage());

        return new ResponseEntity<>(exceptionalDto, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(DuplicateDataException.class)
    public ResponseEntity<ExceptionalDto> handleDuplicate(
            DuplicateDataException duplicateDataException,
            WebRequest webRequest){
        ExceptionalDto exceptionalDto = new ExceptionalDto();
        exceptionalDto.setLocalDate(LocalDate.now());
        exceptionalDto.setError(Integer.toString(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        exceptionalDto.setMessage(duplicateDataException.getMessage());

        return new ResponseEntity<>(exceptionalDto, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(BadRequestsException.class)
    public ResponseEntity<ExceptionalDto> handleBadReq(
            BadRequestsException badRequestsException,
            WebRequest webRequest){
        ExceptionalDto exceptionalDto = new ExceptionalDto();
        exceptionalDto.setLocalDate(LocalDate.now());
        exceptionalDto.setError(Integer.toString(HttpStatus.BAD_REQUEST.value()));
        exceptionalDto.setMessage(badRequestsException.getMessage());

        return new ResponseEntity<>(exceptionalDto, HttpStatus.BAD_REQUEST);

    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ExceptionalDto> global(
//            Exception e,
//            WebRequest webRequest){
//        ExceptionalDto exceptionalDto = new ExceptionalDto();
//        exceptionalDto.setLocalDate(LocalDate.now());
//        exceptionalDto.setError(Integer.toString(HttpStatus.INTERNAL_SERVER_ERROR.value()));
//        exceptionalDto.setMessage("please contact service providers");
//
//        return new ResponseEntity<>(exceptionalDto, HttpStatus.INTERNAL_SERVER_ERROR);
//
//    }


}
