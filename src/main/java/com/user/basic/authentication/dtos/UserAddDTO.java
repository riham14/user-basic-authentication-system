package com.user.basic.authentication.dtos;

import com.user.basic.authentication.enums.GenderEnum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserAddDTO implements Serializable{

    private String first_name;

    private String last_name;

    private String country_code;

    private String phone_number;

    private GenderEnum gender;

    private String birthdate;

    private String email;
}
