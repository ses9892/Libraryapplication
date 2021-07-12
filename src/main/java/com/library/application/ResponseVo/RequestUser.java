package com.library.application.ResponseVo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestUser {

    private String userId;

    private String password;
    private String pwd;
    private String name;
    private String phone;
    private String email;
    private String gender;
    private Boolean autoReturn;

}
