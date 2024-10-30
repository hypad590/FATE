package com.hypad.App.controlller;

import com.hypad.App.model.UserEntity;
import com.hypad.App.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@Transactional(isolation = Isolation.READ_COMMITTED)
public class UsersRestController {

    private final UserService service;

    public UsersRestController(UserService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserEntity>> getAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllUsers());
    }

    @GetMapping("/users/{role}")
    public ResponseEntity<List<UserEntity>> getUsersByRole(@PathVariable String role){
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllUsers().stream()
                .filter(u -> u.getRole().toString().equals(role))
                .collect(Collectors.toList()));
    }

    @GetMapping("/users/n")
    public ResponseEntity<Object> usersById(@RequestParam Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.findUserById(id));
    }
}
