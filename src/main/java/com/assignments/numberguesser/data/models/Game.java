package com.assignments.numberguesser.data.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Game {
    @Id
    private String id;

    @CreationTimestamp
    private LocalDateTime startDate;

    private Integer winnerNumber;

    private Integer numberOfAttempts = 0;

    @Enumerated(EnumType.STRING)
    private Status status = Status.NEW;

}
