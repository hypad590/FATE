package com.hypad.App.controlller;

import com.hypad.App.dto.UserDTO;
import com.hypad.App.enums.RoleEnum;
import com.hypad.App.model.UserEntity;
import com.hypad.App.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Transactional(isolation = Isolation.READ_COMMITTED)
@RestController
public class RestSignController {

    private final UserRepository userRepository;

    public RestSignController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/signup")
    @Operation(summary = "sign up", description = "takes userdto as a body" +
            " and saves converted dto to user into db")
    public void signUp(@RequestBody UserDTO dto){
        UserEntity user = UserEntity
                .builder()
                .username(dto.getUsername())
                .password(new BCryptPasswordEncoder().encode(dto.getPassword()))
                .role(RoleEnum.USER_ROLE)
                .build();
        userRepository.save(user);
    }

    @PostMapping("/sa")
    @Operation(summary = "sign up as admin", description = "does same as sign up " +
            "but only admin can create another admin")
    public void signUpAsAdmin(@RequestBody UserDTO dto){
        UserEntity user = UserEntity
                .builder()
                .username(dto.getUsername())
                .password(new BCryptPasswordEncoder().encode(dto.getPassword()))
                .role(RoleEnum.ADMIN_ROLE).build();
        userRepository.save(user);
    }

}
