package org.example.userauthservice.controllers;

import org.example.userauthservice.dtos.LoginRequestDto;
import org.example.userauthservice.dtos.SignupRequestDto;
import org.example.userauthservice.dtos.UserDto;
import org.example.userauthservice.models.Token;
import org.example.userauthservice.models.User;
import org.example.userauthservice.services.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IAuthService authService;

    @PostMapping("/signup")
    public UserDto signup(@RequestBody SignupRequestDto signupRequestDto) {
      User user = authService.signup(signupRequestDto.getUsername(), signupRequestDto.getPassword(), signupRequestDto.getEmail(), signupRequestDto.getPhone());
      return from(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto) {
       Token token = authService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
//        UserDto userDto = new UserDto();
//        userDto.setEmail(token.getUser().getEmail());
//        userDto.setRoles(token.getUser().getRoles());
//        userDto.setTokenValue(token.getToken());

        return new ResponseEntity<>(token.getToken(), HttpStatus.OK);
    }

    @GetMapping("/validate/{tokenValue}")
    public void validateToken(@PathVariable String tokenValue) {
        // This method can be used to validate the token if needed
        // For now, it is left empty as per the original code
    }
    private UserDto from(User user){
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        return userDto;
    }

}
