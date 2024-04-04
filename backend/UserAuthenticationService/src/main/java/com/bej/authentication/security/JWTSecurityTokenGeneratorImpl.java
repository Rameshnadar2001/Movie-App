package com.bej.authentication.security;


import com.bej.authentication.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTSecurityTokenGeneratorImpl implements SecurityTokenGenerator {
    public String createToken(User user){
        Map<String, Object> claims = new HashMap<>();
        claims.put("emailId",user.getEmailId());
      String token=Jwts.builder().setClaims(claims).setSubject(user.getEmailId()).setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256,"secretkey").compact();
      return token;
    }

}
