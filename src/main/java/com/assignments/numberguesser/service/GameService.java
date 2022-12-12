package com.assignments.numberguesser.service;

import com.assignments.numberguesser.data.payloads.request.GuessNumberRequest;
import com.assignments.numberguesser.data.payloads.response.GuessNumberResponse;
import com.assignments.numberguesser.data.payloads.response.NewGameResponse;
import org.springframework.stereotype.Component;

@Component
public interface GameService {
    NewGameResponse startNewGame();

    GuessNumberResponse guessNumber(GuessNumberRequest guessNumberRequest);
}
