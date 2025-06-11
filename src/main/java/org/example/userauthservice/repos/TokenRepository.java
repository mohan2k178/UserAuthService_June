package org.example.userauthservice.repos;

import org.example.userauthservice.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    @Override
    Token save(Token token);
    //select * from token where token = ? and expireAt > now()
    Optional<Token> findByTokenAndExpireAtAfter(String tokenValue, Date currentDate);
}
