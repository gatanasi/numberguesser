package com.assignments.numberguesser.service;

import com.assignments.numberguesser.data.payloads.request.GuessNumberRequest;
import com.assignments.numberguesser.data.payloads.response.GuessNumberResponse;
import com.assignments.numberguesser.data.payloads.response.NewGameResponse;
import com.assignments.numberguesser.exception.GameAlreadyFinishedException;
import com.assignments.numberguesser.exception.GameNotFoundException;
import com.assignments.numberguesser.exception.MaxAttemptsExceededException;
import org.springframework.stereotype.Component;

@Component
public interface GameService {
    NewGameResponse startNewGame();

    GuessNumberResponse guessNumber(GuessNumberRequest guessNumberRequest) throws GameNotFoundException, MaxAttemptsExceededException, GameAlreadyFinishedException;
}
