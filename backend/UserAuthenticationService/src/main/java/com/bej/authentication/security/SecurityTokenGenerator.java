package com.bej.authentication.security;



import com.bej.authentication.domain.User;


public interface SecurityTokenGenerator {
    String createToken(User user);
}
