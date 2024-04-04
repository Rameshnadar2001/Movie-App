package com.bej.authentication.controller;

import com.bej.authentication.exception.UserAlreadyExistsException;
import com.bej.authentication.exception.InvalidCredentialsException;
import com.bej.authentication.exception.UserNotFoundException;
import com.bej.authentication.security.SecurityTokenGenerator;
import com.bej.authentication.service.IUserService;
import com.bej.authentication.domain.User;
import com.bej.authentication.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v0")
public class UserController {
    //Autowire the dependencies for UserService and SecurityTokenGenerator

    private UserServiceImpl userService;
    private SecurityTokenGenerator securityTokenGenerator;
    private ResponseEntity responseEntity;
   @Autowired
   public UserController(UserServiceImpl userService, SecurityTokenGenerator securityTokenGenerator) {
        this.userService = userService;
        this.securityTokenGenerator = securityTokenGenerator;
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        // Write the logic to save a user,
        // return 201 status if user is saved else 500 status
        try{
            responseEntity=new ResponseEntity<>(userService.saveUser(user),HttpStatus.CREATED);

        } catch (UserAlreadyExistsException e) {
            responseEntity=new ResponseEntity<>(e,HttpStatus.CONFLICT);
        }

        return responseEntity;
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) throws InvalidCredentialsException {
        // Generate the token on login,
        // return 200 status if user is saved else 500 status
        User loggedInUser = null;
        try{
            loggedInUser = userService.getUserByEmailIdAndPassword(user.getEmailId(), user.getPassword());
        }
        catch (InvalidCredentialsException e){
            responseEntity = new ResponseEntity<>("Invalid credentials", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(loggedInUser!=null){
            if(loggedInUser.getEnabled()){
                String token = securityTokenGenerator.createToken(user);
                responseEntity = new ResponseEntity<>(token, HttpStatus.OK);
            }else {
                responseEntity = new ResponseEntity<>("Invalid User", HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }

        return responseEntity;
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestBody User user) throws UserNotFoundException{
       try {
           User updatedUser = userService.updateUserPassword(user);
           return responseEntity = new ResponseEntity<>(updatedUser, HttpStatus.OK);
       }
       catch (UserNotFoundException e){
          return responseEntity = new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
       }
    }

    @GetMapping("/verify")
    public ResponseEntity<?>verifyUser(@RequestParam("token") String token){
        Boolean isSuccess=userService.verifyToken(token);
        String htmlContent = "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Email Verification Success</title>\n" +
                "</head>\n" +
                "<body style=\"font-family: Arial, sans-serif; background-color: #f0f0f0; margin: 0; padding: 0;\">\n" +
                "    <div style=\"max-width: 400px; margin: 50px auto; background-color: #ffffff; border-radius: 10px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);\">\n" +
                "        <div style=\"padding: 20px;\">\n" +
                "            <div style=\"text-align: center;\">\n" +
                "                <img src=\"https://png.pngtree.com/png-vector/20191113/ourmid/pngtree-green-check-mark-icon-flat-style-png-image_1986021.jpg\" alt=\"Tick Icon\" style=\"width: 80px; height: 80px;\">\n" +
                "            </div>\n" +
                "            <h2 style=\"text-align: center; margin-top: 20px; margin-bottom: 10px;\">Email Verified Successfully!</h2>\n" +
                "            <p style=\"text-align: center; margin-bottom: 20px;\">Your email has been successfully verified. You can now access all features.</p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>\n";
        return new ResponseEntity<>(htmlContent, HttpStatus.OK);
    }
}
