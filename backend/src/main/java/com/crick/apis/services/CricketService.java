package com.crick.apis.services;

import com.crick.apis.entities.Match;
import java.util.List;

public interface CricketService {

    List<Match> getLiveMatches();

    List<Match> getUpcomingMatches();

    List<Match> getCompletedMatches();

    Match startMatch(Long matchId);   // admin use
    Match saveMatch(Match match);

}
