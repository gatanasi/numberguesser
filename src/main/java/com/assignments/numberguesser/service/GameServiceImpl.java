package com.assignments.numberguesser.service;

import com.assignments.numberguesser.data.models.Game;
import com.assignments.numberguesser.data.models.Status;
import com.assignments.numberguesser.data.payloads.request.GuessNumberRequest;
import com.assignments.numberguesser.data.payloads.response.GuessNumberResponse;
import com.assignments.numberguesser.data.payloads.response.NewGameResponse;
import com.assignments.numberguesser.data.repository.GameRepository;
import com.assignments.numberguesser.exception.GameNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
public class GameServiceImpl implements GameService {

    public static final Integer MAX_RANDOM_NUMBER = 10000;
    public static final Integer MAX_ATTEMPTS = 20000;

    @Autowired
    GameRepository gameRepository;

    @Override
    public NewGameResponse startNewGame() {
        UUID randomUUID = UUID.randomUUID();

        // Generate a random number between 1 and 10000
        int randomInt = ThreadLocalRandom.current().nextInt(1, MAX_RANDOM_NUMBER + 1);

        log.info("Generated randomInt {} for UUID {}", randomInt, randomUUID);

        return new NewGameResponse(randomUUID.toString());
    }

    @Override
    @Transactional
    public GuessNumberResponse guessNumber(GuessNumberRequest guessNumberRequest) {
        GuessNumberResponse guessNumberResponse = null;

        Integer lastGuess = guessNumberRequest.getNumberGuess();

        Optional<Game> currentGame = gameRepository.findById(guessNumberRequest.getGameId());
        if (currentGame.isEmpty()) {
            throw new GameNotFoundException("Game not found with ID= " + guessNumberRequest.getGameId());
        } else {
            Integer winnerNumber = currentGame.get().getWinnerNumber();
            Integer numberOfAttempts = currentGame.get().getNumberOfAttempts();

            while (!lastGuess.equals(winnerNumber) && numberOfAttempts < MAX_ATTEMPTS - 1 && lastGuess != 0) {
                if (lastGuess.equals(winnerNumber)) {
                    log.info("Right!");
                    guessNumberResponse = new GuessNumberResponse("EQUAL");
                    currentGame.get().setStatus(Status.FINISHED);
                } else if (lastGuess < winnerNumber) {
                    log.info("Your guess was too LOW.");
                    guessNumberResponse = new GuessNumberResponse("SMALLER");
                    numberOfAttempts++;
                } else {
                    log.info("Your guess was too HIGH.");
                    guessNumberResponse = new GuessNumberResponse("LARGER");
                    numberOfAttempts++;
                }
            }

            currentGame.get().setNumberOfAttempts(numberOfAttempts);
            gameRepository.saveAndFlush(currentGame.get());
        }

        return guessNumberResponse;
    }

}
