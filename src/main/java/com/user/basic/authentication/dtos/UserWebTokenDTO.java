package com.user.basic.authentication.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserWebTokenDTO {

    private String phone_number;
    private String auth_token;
}
