package org.example.userauthservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Setter
@Getter
public class Token extends BaseModel {
    private String token;
    private Date expireAt;
    @ManyToOne
    private User user;

}
