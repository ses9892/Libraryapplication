package com.library.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String idx;
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
    private Boolean autoReturn;
    private String auth;

    @Enumerated(EnumType.STRING)
    private Role role;

}
