package com.user.basic.authentication.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.user.basic.authentication.entities.User;
import com.user.basic.authentication.enums.GenderEnum;
import lombok.Getter;
import lombok.Setter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
public class UserWebDTO {
    private Long id;

    private String first_name;

    private String last_name;

    private String country_code;

    private String phone_number;

    private String gender;

    private String birthdate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String status;

    public static UserWebDTO from(User user) {
        UserWebDTO userWebDTO = new UserWebDTO();
        userWebDTO.setId(user.getId());
        userWebDTO.setFirst_name(user.getFirstName());
        userWebDTO.setLast_name(user.getLastName());
        userWebDTO.setCountry_code(user.getCountryCode());
        userWebDTO.setPhone_number(user.getPhoneNumber());
        userWebDTO.setGender(user.getGender().name().toLowerCase());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String birthdate = simpleDateFormat.format(user.getBirthdate());
        userWebDTO.setBirthdate(birthdate);
        userWebDTO.setStatus(user.getStatus());
        return userWebDTO;
    }
}
