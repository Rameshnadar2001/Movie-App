package com.movie.favourite.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // Parse and validate the token and set the user id from claims in the request header as an attribute.
        String authHeader=request.getHeader("Authorization");
        if(authHeader==null||!authHeader.startsWith("Bearer"))
        {
            throw new ServletException();
        }
        else{
            String token=authHeader.substring(7);
            Claims claim=Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody();
            request.setAttribute("claims",claim);
            filterChain.doFilter(request, response);
        }

    }

}

