package com.hypad.App.dto;

import com.hypad.App.enums.RoleEnum;
import lombok.Getter;

@Getter
public class UserDTO {
    private Long id;
    private String username;
    private String password;

    private RoleEnum role;
    private String email;

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", email='" + email + '\'' +
                '}';
    }
}
