package com.user.basic.authentication.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorEntry {

    private String error;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer count;

    public ErrorEntry(String error){
        this.error = error;
    }
}
