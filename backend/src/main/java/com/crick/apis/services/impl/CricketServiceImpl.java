package com.crick.apis.services.impl;

import com.crick.apis.entities.Match;
import com.crick.apis.entities.MatchStatus;
import com.crick.apis.repositories.MatchRepo;
import com.crick.apis.services.CricketService;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CricketServiceImpl implements CricketService {

    private final MatchRepo matchRepo;

    public CricketServiceImpl(MatchRepo matchRepo) {
        this.matchRepo = matchRepo;
    }

    @Override
    
    @Transactional
    public Match startMatch(Long matchId) {

        Match match = matchRepo.findById(matchId)
                .orElseThrow(() -> new RuntimeException("Match not found"));

        match.setStatus(MatchStatus.LIVE);
        match.setBattingTeam(match.getTeamA());
        match.setBowlingTeam(match.getTeamB());

        match.setScoreSummary(
                match.getTeamA() + " 0/0 (0 ov)"
        );

        match.setLastUpdated(LocalDateTime.now());
        return matchRepo.save(match);
    }
    @Override
    public Match saveMatch(Match match) {
        return matchRepo.save(match);
    }


    @Override
    public List<Match> getLiveMatches() {
        return matchRepo.findByStatus(MatchStatus.LIVE);
    }

    @Override
    public List<Match> getUpcomingMatches() {
        return matchRepo.findByStatus(MatchStatus.UPCOMING);
    }

    @Override
    public List<Match> getCompletedMatches() {
        return matchRepo.findByStatus(MatchStatus.COMPLETED);
    }
}
