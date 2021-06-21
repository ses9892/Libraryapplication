package com.library.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String userId;
    private String pwd;
    private String encryptPassword;
    private String name;
    private String phone;
    private String email;
    private String gender;
    private Date createdAt;
    private String borrowed_book;
    private String overdue_num;

    @Enumerated(EnumType.STRING)
    private Role role;


}
