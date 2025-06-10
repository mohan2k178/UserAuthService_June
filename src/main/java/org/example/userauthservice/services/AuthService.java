package org.example.userauthservice.services;

import org.antlr.v4.runtime.misc.Pair;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.userauthservice.exceptions.PasswordMismatchException;
import org.example.userauthservice.exceptions.UserAlreadyExistException;
import org.example.userauthservice.exceptions.UserNotSignedException;
import org.example.userauthservice.models.Token;
import org.example.userauthservice.models.User;
import org.example.userauthservice.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService implements IAuthService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User signup(String username,  String password, String email, String phone){
        Optional<User> userOptional = userRepo.findByEmailEquals(email);
            if(userOptional.isPresent()){
                throw new UserAlreadyExistException("Please try login directly");
            }

            User user = new User();
            user.setEmail(email);

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            user.setPassword(bCryptPasswordEncoder.encode(password)); //use Bcrypt here
            user.setPhone(phone);
            user.setUsername(username);
            return userRepo.save(user);

    }


    public Pair<User, String> login(String email, String password){
        Optional<User> userOptional = userRepo.findByEmailEquals(email);
        if(userOptional.isEmpty()){
            throw new UserNotSignedException("Please try signup first");
        }

     //   String storedPassword = userOptional.get().getPassword();
     //   if(!password.equals(storedPassword)){
     //       throw new PasswordMismatchException("Passwords do not match");
     //   }

          if(! bCryptPasswordEncoder.matches(password, userOptional.get().getPassword())){
              throw new PasswordMismatchException("Passwords do not match");

          }

        //Generate a token and store it in Tokens table
        Token token = new Token();
          token.setUser(userOptional.get());
          token.setToken(RandomStringUtils.randomAlphanumeric(128));
          token.setExpireAt();

        return new Pair<>(userOptional.get(), "token");

    }

}
