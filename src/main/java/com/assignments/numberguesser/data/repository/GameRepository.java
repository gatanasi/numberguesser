package com.assignments.numberguesser.data.repository;

import com.assignments.numberguesser.data.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, String> {
}
