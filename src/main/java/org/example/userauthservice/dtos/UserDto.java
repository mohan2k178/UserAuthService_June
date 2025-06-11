package org.example.userauthservice.dtos;

import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import org.example.userauthservice.models.Role;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class UserDto {
private Long id;
private String username;
private String email;
//private String tokenValue;
private List<Role> roles = new ArrayList<>();

}
