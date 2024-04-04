package com.movie.favourite.controller;

import com.movie.favourite.domain.Movie;
import com.movie.favourite.domain.User;
import com.movie.favourite.exception.*;
import com.movie.favourite.service.UserServiceImpl;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/v2")
public class UserController {
    private UserServiceImpl userServiceimpl;
    private ResponseEntity responseEntity;
    @Autowired
    public UserController(UserServiceImpl userServiceimpl) {
        this.userServiceimpl = userServiceimpl;
    }

    public static String uploadDirectory = "D:/NIIT/SE Capstone Project/SE CASTPONE PROJECT/SE_FRONTEND_CP/src/assets";

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@ModelAttribute User user, @RequestParam("image") MultipartFile file) throws Exception {
        // Register a new user and save to db, return 201 status if user is saved else 500 status
        try{
            String originalFileName = file.getOriginalFilename();
            Path fileNameAndPath = Paths.get(uploadDirectory, originalFileName);
            System.out.println(fileNameAndPath);
            Files.write(fileNameAndPath, file.getBytes());
            user.setProfileImage(originalFileName);
            User user1 = userServiceimpl.registerUser(user);
            responseEntity = new ResponseEntity<>(user1, HttpStatus.CREATED);
        }
        catch(UserAlreadyExistsExcepiton e)
        {
            responseEntity=new ResponseEntity<>("User already exist.",HttpStatus.CONFLICT);
        }
        catch(ImageAlreadyExistException e)
        {
            responseEntity=new ResponseEntity<>("Image already exist, try using different image.",HttpStatus.CONFLICT);
        }
        catch (IOException e) {
            responseEntity=new ResponseEntity<>("Runtime exception.",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @PostMapping("/user/movie")
    public ResponseEntity<?> saveMovie(@RequestBody Movie movie, HttpServletRequest request)throws MovieAlreadyExistsException {


        try{  Claims claimsRetrieved=(Claims) request.getAttribute("claims");
            String retrievedId= claimsRetrieved.getSubject();
            responseEntity=new ResponseEntity<>(userServiceimpl.saveFavoriteMovieToList(movie,retrievedId),HttpStatus.CREATED);
        }
        catch ( MovieAlreadyExistsException e){
            responseEntity=new ResponseEntity<>("Movie already exist in the favorites",HttpStatus.CONFLICT);
        }
        catch (UserNotFoundException e) {
            responseEntity=new ResponseEntity<>("User not found",HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    @GetMapping("/user/movies")
    public ResponseEntity<?> getAllMovies(HttpServletRequest request) throws MovieNotFoundException {
        // display all the tracks of a specific user, extract user id from claims,
        // return 200 status if user is saved else 500 status
        Claims claims=(Claims) request.getAttribute("claims");
        String retrievedId= claims.getSubject();
        try{
            responseEntity=new ResponseEntity<>(userServiceimpl.getMoviesFromList(retrievedId),HttpStatus.OK);
        } catch (UserNotFoundException | MovieNotFoundException e) {
            responseEntity=new ResponseEntity<>(e,HttpStatus.CONFLICT);
        } catch (Exception e) {
            return responseEntity= new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }
    @DeleteMapping("/user/movie/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable Long id, HttpServletRequest request) throws MovieNotFoundException{
        // delete a track based on user id and track id, extract user id from claims
        // return 200 status if user is saved else 500 status
        Claims claims=(Claims) request.getAttribute("claims");
        String retrievedId= claims.getSubject();
        try{
            responseEntity=new ResponseEntity<>(userServiceimpl.removeMovieFromList(retrievedId,id),HttpStatus.OK);
        } catch (UserNotFoundException|MovieNotFoundException e) {
            responseEntity=new ResponseEntity<>(e,HttpStatus.CONFLICT);
        }

        return responseEntity;
    }

    @GetMapping("/user/getUserDetails")
    public ResponseEntity<?> getUserData(HttpServletRequest request) throws UserNotFoundException{
        try {
            Claims claims = (Claims) request.getAttribute("claims");
            String email = claims.getSubject();
            User user = userServiceimpl.getUserDetails(email);
            responseEntity = new ResponseEntity<>(user, HttpStatus.OK);
        }
        catch (UserNotFoundException e){
            responseEntity = new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestBody User user) throws UserNotFoundException{
        try {
            User user1 = userServiceimpl.updateUserDetails(user);
        } catch (UserNotFoundException e) {
            responseEntity = new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            return responseEntity;
        }
        return responseEntity = new ResponseEntity<>(user, HttpStatus.OK);
    }


}
