package com.assignments.numberguesser.web;

import com.assignments.numberguesser.data.payloads.response.ErrorResponse;
import com.assignments.numberguesser.exception.GameAlreadyFinishedException;
import com.assignments.numberguesser.exception.GameNotFoundException;
import com.assignments.numberguesser.exception.MaxAttemptsExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.assignments.numberguesser.service.GameServiceImpl.MAX_ATTEMPTS;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(GameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse requestGameNotFound(GameNotFoundException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(MaxAttemptsExceededException.class)
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    @ResponseBody
    public ErrorResponse requestMaxAttemptsExceededException() {
        return new ErrorResponse("You exceeded the maximum number of attempts: " + MAX_ATTEMPTS);
    }

    @ExceptionHandler(GameAlreadyFinishedException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    public ErrorResponse requestGameAlreadyFinishedException() {
        return new ErrorResponse("This game is already finished");
    }

}