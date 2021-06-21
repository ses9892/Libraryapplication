package com.library.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String userId;
    private String password;
    private String encryptPassword;
    private String name;
    private String phone;
    private String email;
    private String gender;

    @Enumerated(EnumType.STRING)
    private Role role;


}
