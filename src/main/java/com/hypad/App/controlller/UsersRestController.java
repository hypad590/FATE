package com.hypad.App.controlller;

import com.hypad.App.model.UserEntity;
import com.hypad.App.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class UsersRestController {

    private final UserService service;

    public UsersRestController(UserService service) {
        this.service = service;
    }

    @GetMapping("/users")
    @ResponseBody
    public List<UserEntity> getAllUsers(){
        return service.getAllUsers();
    }

    @GetMapping("/users/{role}")
    @ResponseBody
    public List<UserEntity> getUsersByRole(@PathVariable String role){
        return service.getAllUsers().stream()
                .filter(u -> u.getRole().toString().equals(role))
                .collect(Collectors.toList());
    }
}
