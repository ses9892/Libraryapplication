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

    @Size(min = 8 , max = 15)
    private String userId;

    @Size(min = 8 , max = 15)
    private String password;
    @Size(min = 3 , max = 4)
    private String name;
    @Size(min = 11,max=13)
    private String phone;
    private String email;
    private String gender;

}
