package com.movie.favourite.service;

import com.movie.favourite.domain.Movie;
import com.movie.favourite.domain.User;
import com.movie.favourite.exception.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface UserService {
   User registerUser(User user) throws UserAlreadyExistsExcepiton, ImageAlreadyExistException;
   User  saveFavoriteMovieToList(Movie movie,String emailId) throws MovieAlreadyExistsException,UserNotFoundException;
   User removeMovieFromList(String emailId,Long id)throws MovieNotFoundException, UserNotFoundException;
   List<Movie> getMoviesFromList(String emailId) throws Exception;
   User getUserDetails(String emailId) throws UserNotFoundException;
   User updateUserDetails(User user) throws UserNotFoundException;
}
