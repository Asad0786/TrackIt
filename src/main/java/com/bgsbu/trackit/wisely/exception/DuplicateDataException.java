package com.bgsbu.trackit.wisely.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@NoArgsConstructor
@ResponseStatus(value = HttpStatus.CONFLICT)
public class DuplicateDataException extends RuntimeException{

    private String message;

    public DuplicateDataException(String msg) {
        super(msg);
        this.message = msg;
    }
}
