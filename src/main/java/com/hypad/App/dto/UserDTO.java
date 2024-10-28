package com.hypad.App.dto;

import com.hypad.App.enums.RoleEnum;
import lombok.Getter;

@Getter
public class UserDTO {
    private String username;
    private String password;
    private RoleEnum role;
}
