package com.assignments.numberguesser.data.payloads.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class GuessNumberRequest {
    @NotEmpty
    private String gameId;

    @NotNull
    @Min(1)
    private Integer numberGuess;

}
