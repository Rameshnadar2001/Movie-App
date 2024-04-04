package com.bej.authentication.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Use the@ResponseStatus annotation to set the exception message and status
@ResponseStatus(code= HttpStatus.CONFLICT,reason="User Already Exists")
public class UserAlreadyExistsException extends  Exception{
}
