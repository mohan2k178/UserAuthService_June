package org.example.userauthservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignupRequestDto {
    private  String username;
    private  String password;
    private  String email;
    private  String phone;
}
