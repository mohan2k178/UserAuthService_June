package org.example.userauthservice.services;

import org.antlr.v4.runtime.misc.Pair;
import org.example.userauthservice.models.Token;
import org.example.userauthservice.models.User;

public interface IAuthService {
    User signup(String username, String password, String email, String phone);
    Token login(String email, String password);
    User validateToken(String token);
}
