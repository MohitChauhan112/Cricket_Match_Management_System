package com.crick.apis.runner;

import com.crick.apis.entities.Match;
import com.crick.apis.entities.MatchStatus;
import com.crick.apis.repositories.MatchRepo;
import com.crick.apis.services.CricketService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class MatchStartupRunner implements CommandLineRunner {

    private final MatchRepo matchRepo;
    private final CricketService cricketService;

    public MatchStartupRunner(MatchRepo matchRepo, CricketService cricketService) {
        this.matchRepo = matchRepo;
        this.cricketService = cricketService;
    }

    @Override
    public void run(String... args) {
        LocalDateTime now = LocalDateTime.now();

        // 1️⃣ Start missed UPCOMING matches (jinke startTime abhi aa chuka hai)
        List<Match> upcoming = matchRepo.findByStatus(MatchStatus.UPCOMING);
        for (Match match : upcoming) {
            if (match.getStartTime().isBefore(now) || match.getStartTime().isEqual(now)) {
                cricketService.startMatch(match.getMatchId());
                System.out.println("✅ Started UPCOMING match automatically: " + match.getTeamHeading());
            }
        }

        // 2️⃣ Resume already LIVE matches
        List<Match> liveMatches = matchRepo.findByStatus(MatchStatus.LIVE);
        for (Match match : liveMatches) {
            System.out.println("🔄 Resuming LIVE match: " + match.getTeamHeading());
            // Scheduler automatically continue updates
        }
    }
}
