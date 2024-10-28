package com.hypad.App.controlller;

import com.hypad.App.details.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
public class PublicEndController {

    @GetMapping("/login")
    @Operation(summary = "authenticates you as user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "UserNameNotFoundException")
    })
    public String login(){
        return "login";
    }

    @GetMapping("/signup")
    public String signUp(){
        return "signup";
    }

    @GetMapping("/sa")
    public String signUpAsAdmin(){
        return "signupAdmin";
    }

    @GetMapping("/public")
    @ResponseBody
    public String publicEndPoint(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated()){
            Object principal = authentication.getPrincipal();
            if(principal instanceof CustomUserDetails){
                return "Hello " + ((CustomUserDetails) principal).getUsername();
            }else {
                return "Principal is not an instanceof CustomUserDetails";
            }
        }else {
            return "Authentication null or user isnt authenticated";
        }
    }

}
