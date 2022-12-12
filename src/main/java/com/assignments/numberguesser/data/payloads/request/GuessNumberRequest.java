package com.assignments.numberguesser.data.payloads.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class GuessNumberRequest {
    @NotNull
    private String gameId;

    @NotNull
    private Integer numberGuess;

}
