package com.movie.favourite.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Image already exist")
public class ImageAlreadyExistException extends Exception{
}
