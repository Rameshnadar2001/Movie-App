package com.bej.authentication.service;

import com.bej.authentication.domain.User;
import com.bej.authentication.exception.UserAlreadyExistsException;
import com.bej.authentication.exception.InvalidCredentialsException;
import com.bej.authentication.exception.UserNotFoundException;

public interface IUserService {
    User saveUser(User user) throws UserAlreadyExistsException;
    User getUserByEmailIdAndPassword(String emailId, String password) throws InvalidCredentialsException;
    User updateUserPassword(User user) throws UserNotFoundException;
    Boolean verifyToken(String token);

}
