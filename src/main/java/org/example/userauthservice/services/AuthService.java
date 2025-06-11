package org.example.userauthservice.services;

import org.antlr.v4.runtime.misc.Pair;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.userauthservice.exceptions.InvalidTokenException;
import org.example.userauthservice.exceptions.PasswordMismatchException;
import org.example.userauthservice.exceptions.UserAlreadyExistException;
import org.example.userauthservice.exceptions.UserNotSignedException;
import org.example.userauthservice.models.Token;
import org.example.userauthservice.models.User;
import org.example.userauthservice.repos.TokenRepository;
import org.example.userauthservice.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class AuthService implements IAuthService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private TokenRepository tokenRepository;

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


    public Token login(String email, String password){
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
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 30);
        Date datePlus30Days = calendar.getTime();

          token.setExpireAt(datePlus30Days);
          return tokenRepository.save(token);


    }

    @Override
    public User validateToken(String tokenValue) {
        Optional<Token> tokenOptional = tokenRepository.findByTokenAndExpireAtAfter(tokenValue, new Date());
         // Check if the token exists and is not expired
         // If not, throw an exception
        if (tokenOptional.isEmpty()) {
            return null;
        }

        return tokenOptional.get().getUser();

//        if (token.getExpireAt().before(new Date())) {
//            throw new UserNotSignedException("Token has expired");
//        }
//        return token.getUser();
    }

}
