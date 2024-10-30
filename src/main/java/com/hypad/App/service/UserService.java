package com.hypad.App.service;

import com.hypad.App.dto.UserDTO;
import com.hypad.App.model.UserEntity;
import com.hypad.App.repository.UserRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Types;
import java.util.List;

@Service
public class UserService {
    private final UserRepository repository;
    private final NamedParameterJdbcTemplate jdbcTemplate;
    public UserService(UserRepository repository, NamedParameterJdbcTemplate jdbcTemplate) {
        this.repository = repository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<UserEntity> getAllUsers(){
        return repository.findAll();
    }

    public Object findUserById(Long id){
        if (repository.findUserById(id) != null){
            return repository.findUserById(id);
        }
        return "No user with id " + id + " found";
    }

    public void deleteUser(Long id){
        if(findUserById(id)!=null){
            repository.delete((UserEntity) findUserById(id));
        }else {
            throw new RuntimeException("Id cant be null");
        }
    }

    public Object findUserByName(String username){
        if (repository.findByUsername(username).isPresent()){
            return repository.findByUsername(username).get();
        }
        return "No user with username " + username;
    }

    public void updateExistingUser(UserDTO userDTO){
        String sql = "UPDATE t_users SET username = :username, password = :password WHERE email = :email";
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("username",userDTO.getUsername())
                .addValue("password",new BCryptPasswordEncoder().encode(userDTO.getPassword()))
                .addValue("email", userDTO.getEmail());
        jdbcTemplate.update(sql, parameterSource);
    }
}
