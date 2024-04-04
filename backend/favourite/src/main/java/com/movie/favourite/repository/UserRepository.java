package com.movie.favourite.repository;

import com.movie.favourite.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.multipart.MultipartFile;

public interface UserRepository extends MongoRepository<User,String> {
    User findByEmailId(String email);
    User findByProfileImage(String profileImage);
}
