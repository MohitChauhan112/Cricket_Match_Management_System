package com.crick.apis.repositories;

import com.crick.apis.entities.Match;
import com.crick.apis.entities.MatchStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MatchRepo extends JpaRepository<Match, Long> {

    List<Match> findByStatus(MatchStatus status);
}
