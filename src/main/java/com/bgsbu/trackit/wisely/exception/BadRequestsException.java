package com.bgsbu.trackit.wisely.exception;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@NoArgsConstructor
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestsException extends RuntimeException{

    private String message;

    public BadRequestsException(String msg){

        super(msg);
        this.message = msg;
    }
}
