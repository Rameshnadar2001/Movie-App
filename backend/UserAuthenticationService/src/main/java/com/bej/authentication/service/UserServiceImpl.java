package com.bej.authentication.service;

import com.bej.authentication.domain.Confirmations;
import com.bej.authentication.domain.User;
import com.bej.authentication.exception.UserAlreadyExistsException;
import com.bej.authentication.exception.InvalidCredentialsException;
import com.bej.authentication.exception.UserNotFoundException;
import com.bej.authentication.repository.ConfirmationsRepository;
import com.bej.authentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

// Autowire the UserRepository using constructor autowiring
    private UserRepository userRepository;
    private ConfirmationsRepository confirmationsRepository;
    private  EmailService emailService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ConfirmationsRepository confirmationsRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.confirmationsRepository=confirmationsRepository;
        this.emailService=emailService;
    }


    @Override
    public User saveUser(User user) throws UserAlreadyExistsException {
        if (userRepository.findById(user.getEmailId()).isPresent()) {
            throw new UserAlreadyExistsException();
        }
        user.setEnabled(false);
        User user1=userRepository.save(user);
        Confirmations confirmations=new Confirmations(user);
        Confirmations confirmations1= confirmationsRepository.save(confirmations);

        emailService.sendEmailWithAttachments(user.getUserName(),user.getEmailId(),confirmations.getToken());
        return user1;
    }

    @Override
    public User getUserByEmailIdAndPassword(String emailId, String password) throws InvalidCredentialsException {
        if(userRepository.findByEmailIdAndPassword(emailId, password)==null)
        {
            throw new InvalidCredentialsException();
        }
        return userRepository.findByEmailIdAndPassword(emailId, password);
    }

    @Override
    public User updateUserPassword(User user) throws UserNotFoundException {
        User existingUser = userRepository.findById(user.getEmailId()).get();
        if(existingUser==null){
            throw new UserNotFoundException();
        }
        if(user.getPassword()!=null){
            existingUser.setPassword(user.getPassword());
        }
        return userRepository.save(existingUser);
    }

    @Override
    public Boolean verifyToken(String token) {
        Confirmations confirmations=confirmationsRepository.findByToken(token);
        User user=userRepository.findByEmailId(confirmations.getUser().getEmailId());
        user.setEnabled(true);
        userRepository.flush();
        return Boolean.TRUE;

    }

}
