package com.user.basic.authentication.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class ErrorDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ErrorEntry> first_name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ErrorEntry> last_name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ErrorEntry> country_code;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ErrorEntry> phone_number;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ErrorEntry> gender;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ErrorEntry> birthdate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ErrorEntry> avatar;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ErrorEntry> email;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ErrorEntry> token;


    public boolean hasEmptyLists() {
        return Objects.isNull(first_name) && Objects.isNull(last_name)
                && Objects.isNull(country_code) && Objects.isNull(phone_number)
                && Objects.isNull(gender) && Objects.isNull(birthdate)
                && Objects.isNull(avatar) && Objects.isNull(email) && Objects.isNull(token) ;
    }
}
