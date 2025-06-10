package org.example.userauthservice.services;

import org.antlr.v4.runtime.misc.Pair;
import org.example.userauthservice.models.User;

public interface IAuthService {
    User signup(String username, String password, String email, String phone);
    Pair<User, String> login(String email, String password);
}
