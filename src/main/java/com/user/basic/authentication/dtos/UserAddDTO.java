package com.user.basic.authentication.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.user.basic.authentication.enums.GenderEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserAddDTO {

    //@NotNull(message = "First name Cannot be empty")
    private String first_name;

    //@NotNull(message = "Last name Cannot be empty")
    private String last_name;

    //@NotNull(message = "Country Code Cannot be empty")
    private String country_code;

    //@NotNull(message = "Phone Number Cannot be empty")
    private String phone_number;

    //@NotNull(message = "Gender Cannot be empty")
    private GenderEnum gender;

    //@NotNull(message = "Birth Date Cannot be empty")
    private String birthdate;

    //@NotNull(message = "Avatar Cannot be empty")
    private MultipartFile avatar;

    private String email;
}
