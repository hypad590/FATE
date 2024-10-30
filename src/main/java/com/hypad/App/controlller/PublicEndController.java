package com.hypad.App.controlller;

import com.hypad.App.details.CustomUserDetails;
import com.hypad.App.enums.RoleEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView publicEndPoint(){
        ModelAndView modelAndView = new ModelAndView("publicEndpoint");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated()){

            Object principal = authentication.getPrincipal();
            boolean hasAdminRole = authentication.getAuthorities()
                    .stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(RoleEnum.ADMIN_ROLE.name()));

            if(principal instanceof CustomUserDetails){
                if(hasAdminRole){
                    setUpModelAndView(modelAndView,
                            "You can choose your FATË admin " + ((CustomUserDetails) principal).getUsername(),
                            HttpStatus.OK);
                    return modelAndView;
                }
                setUpModelAndView(modelAndView,
                        "Hello " + ((CustomUserDetails) principal).getUsername(),
                        HttpStatus.OK);
            } else {
                setUpModelAndView(modelAndView,
                        "Principal is not an instanceof CustomUserDetails/UserDetails",
                        HttpStatus.I_AM_A_TEAPOT);
            }
        }else {
            setUpModelAndView(modelAndView,
                    "Thats public view",
                    HttpStatus.ACCEPTED);
        }
        assert !modelAndView.isEmpty();
        return modelAndView;
    }
    private void setUpModelAndView(ModelAndView modelAndView, String attrValue, HttpStatus httpStatus){
        modelAndView.addObject("msg",attrValue);
        modelAndView.setStatus(httpStatus);
    }
}
