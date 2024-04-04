package com.movie.favourite.service;

import com.movie.favourite.domain.Movie;
import com.movie.favourite.domain.User;
import com.movie.favourite.exception.*;
import com.movie.favourite.proxy.UserProxy;
import com.movie.favourite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sound.midi.Track;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private UserProxy userProxy;
@Autowired
    public UserServiceImpl(UserRepository userRepository, UserProxy userProxy) {
        this.userRepository = userRepository;
        this.userProxy = userProxy;
    }

    @Override
    public User registerUser(User user) throws UserAlreadyExistsExcepiton, ImageAlreadyExistException {
        if(userRepository.findById(user.getEmailId()).isPresent())
        {
            throw new UserAlreadyExistsExcepiton();
        }
        User existingUser = userRepository.findByProfileImage(user.getProfileImage());
        if(existingUser!=null){
            throw new ImageAlreadyExistException();
        }
        User savedUser=userRepository.save(user);
        if(!(savedUser.getEmailId().isEmpty()))
        {
            ResponseEntity r=userProxy.saveUser(user);  //authentication
            System.out.println(r.getBody());
        }
        return savedUser;
    }

    @Override
    public User saveFavoriteMovieToList(Movie movie, String emailId) throws MovieAlreadyExistsException,UserNotFoundException {
        Optional<User> optionalUser=userRepository.findById(emailId);

        if(optionalUser.isEmpty())
        {
            throw new UserNotFoundException();
        }
        else{
            List<Movie> movieList=optionalUser.get().getFavoriteMovieList();
            User user=optionalUser.get();
            if(movieList==null){
                user.setFavoriteMovieList( Arrays.asList(movie));
            }
            else{
                if(user.getFavoriteMovieList().stream().anyMatch(existingMovie->existingMovie.getId().equals(movie.getId())))
                {
                    throw new MovieAlreadyExistsException();
                }

                movieList.add(movie);
                user.setFavoriteMovieList(movieList);

            }

            return  userRepository.save(user);


        }
    }

    @Override
    public User removeMovieFromList(String emailId, Long id) throws MovieNotFoundException, UserNotFoundException {
        Optional<User> optionalUser=userRepository.findById(emailId);
        if(optionalUser.isEmpty())
        {
            throw  new UserNotFoundException();
        }
        else{
            List<Movie> movieList=optionalUser.get().getFavoriteMovieList();
            if(movieList==null)
            {
                throw new MovieNotFoundException();
            }
            else{
                Iterator<Movie> iter=movieList.iterator();
                boolean isMovieFound=false;
                while(iter.hasNext())
                {
                    Movie movie=iter.next();
                    if(movie.getId().equals(id))
                    {

                        isMovieFound=true;
                        iter.remove();
                    }
                }
                if(!isMovieFound)
                {
                    throw  new MovieNotFoundException();
                }
            }

            User user=optionalUser.get();
            user.setFavoriteMovieList(movieList);
            return userRepository.save(user);

        }
    }

    @Override
    public List<Movie> getMoviesFromList(String emailId) throws Exception {
        if(userRepository.findById(emailId).isEmpty())
        {
            throw new UserNotFoundException();
        }
        else{
            List<Movie> movieList=userRepository.findById(emailId).get().getFavoriteMovieList();
            return  movieList;
        }

    }

    @Override
    public User getUserDetails(String emailId) throws UserNotFoundException {
        User user = userRepository.findByEmailId(emailId);
        if(user==null){
            throw new UserNotFoundException();
        }
        return user;
    }

    @Override
    public User updateUserDetails(User user) throws UserNotFoundException {
        if( userRepository.findById(user.getEmailId()).isEmpty()){
            throw new UserNotFoundException();
        }
        User existingUser = userRepository.findById(user.getEmailId()).get();
        if(user.getPassword()!=null){
            existingUser.setPassword(user.getPassword());
            ResponseEntity r=userProxy.updatePassword(user);
        }

        return userRepository.save(existingUser);
    }


}
