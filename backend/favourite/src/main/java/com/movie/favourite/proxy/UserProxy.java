package com.movie.favourite.proxy;


import com.movie.favourite.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="user-authentication-service",url="localhost:8083")
public interface UserProxy {
    @PostMapping("/api/v0/save")
    public ResponseEntity<?> saveUser(@RequestBody User user);
    @PutMapping("/api/v0/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestBody User user);
}
