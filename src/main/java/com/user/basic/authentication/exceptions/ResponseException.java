package com.user.basic.authentication.exceptions;

import com.user.basic.authentication.dtos.ErrorDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ResponseException {
    private ErrorDTO errors;
}
