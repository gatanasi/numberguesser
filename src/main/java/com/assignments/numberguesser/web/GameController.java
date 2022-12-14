package com.assignments.numberguesser.web;

import com.assignments.numberguesser.data.payloads.request.GuessNumberRequest;
import com.assignments.numberguesser.data.payloads.response.GuessNumberResponse;
import com.assignments.numberguesser.data.payloads.response.NewGameResponse;
import com.assignments.numberguesser.exception.GameAlreadyFinishedException;
import com.assignments.numberguesser.exception.GameNotFoundException;
import com.assignments.numberguesser.exception.MaxAttemptsExceededException;
import com.assignments.numberguesser.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@RestController
@RequestMapping("/game")
public class GameController {

    public static final String PLAYER_ROLE = "APPROLE_Player";

    @Autowired
    private GameService gameService;

    @GetMapping(path = "/start", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority(\"" + PLAYER_ROLE + "\")")
    public ResponseEntity<NewGameResponse> startGame() {
        NewGameResponse newGameResponse = gameService.startNewGame();
        return new ResponseEntity<>(newGameResponse, HttpStatus.OK);
    }

    @PostMapping(path = "/guess", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority(\"" + PLAYER_ROLE + "\")")
    public ResponseEntity<GuessNumberResponse> guessNumber(@RequestBody final GuessNumberRequest guessNumberRequest)
            throws MaxAttemptsExceededException, GameNotFoundException, GameAlreadyFinishedException {
        GuessNumberResponse guessNumberResponse = gameService.guessNumber(guessNumberRequest);
        return new ResponseEntity<>(guessNumberResponse, HttpStatus.OK);
    }

}
