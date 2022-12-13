package com.assignments.numberguesser.service;

import com.assignments.numberguesser.data.models.Game;
import com.assignments.numberguesser.data.models.Status;
import com.assignments.numberguesser.data.payloads.request.GuessNumberRequest;
import com.assignments.numberguesser.data.payloads.response.GuessNumberResponse;
import com.assignments.numberguesser.data.payloads.response.NewGameResponse;
import com.assignments.numberguesser.data.repository.GameRepository;
import com.assignments.numberguesser.exception.GameAlreadyFinishedException;
import com.assignments.numberguesser.exception.GameNotFoundException;
import com.assignments.numberguesser.exception.MaxAttemptsExceededException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
public class GameServiceImpl implements GameService {

    public static final Integer MIN_RANDOM_NUMBER = 1;
    public static final Integer MAX_RANDOM_NUMBER = 10000;
    public static final Integer MAX_ATTEMPTS = 20000;

    @Autowired
    private GameRepository gameRepository;

    @Override
    @Transactional
    public NewGameResponse startNewGame() {
        UUID randomUUID = UUID.randomUUID();

        // Generate a random number between 1 and 10000
        int randomInt = ThreadLocalRandom.current().nextInt(MIN_RANDOM_NUMBER, MAX_RANDOM_NUMBER + 1);

        log.info("Generated randomInt {} for UUID {}", randomInt, randomUUID);

        Game newGame = new Game();
        newGame.setId(randomUUID.toString());
        newGame.setWinnerNumber(randomInt);

        gameRepository.save(newGame);

        return new NewGameResponse(randomUUID.toString());
    }

    @Override
    @Transactional
    public GuessNumberResponse guessNumber(final GuessNumberRequest guessNumberRequest)
            throws GameNotFoundException, MaxAttemptsExceededException, GameAlreadyFinishedException {

        Game currentGame = gameRepository.findById(guessNumberRequest.getGameId())
                .orElseThrow(() -> new GameNotFoundException("Game not found with ID= " + guessNumberRequest.getGameId()));

        if (currentGame.getStatus().equals(Status.FINISHED)) {
            throw new GameAlreadyFinishedException();
        }

        GuessNumberResponse guessNumberResponse;
        Integer lastGuess = guessNumberRequest.getNumberGuess();
        Integer winnerNumber = currentGame.getWinnerNumber();
        Integer numberOfAttempts = currentGame.getNumberOfAttempts();

        try {
            if (numberOfAttempts < MAX_ATTEMPTS) {
                currentGame.setStatus(Status.IN_PROGRESS);
                ++numberOfAttempts;
                if (lastGuess.equals(winnerNumber)) {
                    guessNumberResponse = new GuessNumberResponse("EQUAL");
                    currentGame.setStatus(Status.FINISHED);
                } else if (lastGuess < winnerNumber) {
                    guessNumberResponse = new GuessNumberResponse("SMALLER");
                } else {
                    guessNumberResponse = new GuessNumberResponse("LARGER");
                }
            } else {
                currentGame.setStatus(Status.FINISHED);
                throw new MaxAttemptsExceededException();
            }
        } finally {
            currentGame.setNumberOfAttempts(numberOfAttempts);
            gameRepository.save(currentGame);
        }

        return guessNumberResponse;
    }

}
