package com.user.basic.authentication.exceptions;

import com.user.basic.authentication.dtos.ErrorDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorException extends RuntimeException{
    private HttpStatus status;
    private ErrorDTO errors;
}
