package com.hypad.App.controlller;

import com.hypad.App.details.CustomUserDetails;
import com.hypad.App.enums.RoleEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @Operation(summary = "public endpoint")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Has admin role/user role"),
            @ApiResponse(responseCode = "418", description = "Principal is instanceof another object"),
            @ApiResponse(responseCode = "202", description = "No authorization")
    })
    public ResponseEntity<String> publicEndPoint(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated()){

            Object principal = authentication.getPrincipal();
            boolean hasAdminRole = authentication.getAuthorities()
                    .stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(RoleEnum.ADMIN_ROLE.name()));

            if(principal instanceof CustomUserDetails){
                if(hasAdminRole){
                    return ResponseEntity.status(HttpStatus.OK).body("You can choose your FATË admin " + ((CustomUserDetails) principal).getUsername());
                }
                return ResponseEntity.status(HttpStatus.OK).body("Hello " + ((CustomUserDetails) principal).getUsername());
            } else {
                return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body("Principal is not an instanceof CustomUserDetails/UserDetails");
            }
        }else {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Thats public view"); //return ModelAndView
        }
    }

}
