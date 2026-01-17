package com.crick.apis.runner;

import com.crick.apis.entities.Match;
import com.crick.apis.entities.MatchStatus;
import com.crick.apis.repositories.MatchRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private final MatchRepo matchRepo;

    public DataInitializer(MatchRepo matchRepo) {
        this.matchRepo = matchRepo;
    }

    @Override
    public void run(String... args) {

        // 🔒 Duplicate se bachav
        if (matchRepo.count() > 0) {
            return;
        }

        // ======================
        // UPCOMING MATCH
        // ======================
        Match upcoming = new Match();
        upcoming.setTeamA("India");
        upcoming.setTeamB("Australia");
        upcoming.setTeamHeading("India vs Australia");
        upcoming.setStatus(MatchStatus.UPCOMING);
        upcoming.setStartTime(LocalDateTime.now().plusMinutes(2));
        upcoming.setLastUpdated(LocalDateTime.now());

        // ScoreSummary me nothing yet
        upcoming.setScoreSummary("");  

        // ======================
        // LIVE MATCH (1st innings chal rahi hai)
        // ======================
        Match live = new Match();
        live.setTeamA("England");
        live.setTeamB("Pakistan");
        live.setTeamHeading("England vs Pakistan");
        live.setStatus(MatchStatus.LIVE);
        live.setStartTime(LocalDateTime.now().minusMinutes(15));
        live.setLastUpdated(LocalDateTime.now());

        // Batting/Bowling teams
        live.setBattingTeam("England");
        live.setBowlingTeam("Pakistan");

        // First innings progress stored in scoreSummary
        live.setScoreSummary("England 45/1 (6 ov)");

        // ======================
        // COMPLETED MATCH
        // ======================
        Match completed = new Match();
        completed.setTeamA("India");
        completed.setTeamB("New Zealand");
        completed.setTeamHeading("India vs New Zealand");
        completed.setStatus(MatchStatus.COMPLETED);
        completed.setStartTime(LocalDateTime.now().minusHours(1));
        completed.setLastUpdated(LocalDateTime.now());

        // First innings + second innings summary in scoreSummary
        completed.setScoreSummary(
                "India 150/6 (20 ov)\n" +
                "New Zealand 145/8 (20 ov)\n" +
                "| India won | Match Completed"
        );

        completed.setMatchResult("India won");

        // ======================
        // SAVE ALL
        // ======================
        matchRepo.save(upcoming);
        matchRepo.save(live);
        matchRepo.save(completed);

        System.out.println("✅ Initial matches inserted (UPCOMING, LIVE, COMPLETED)");
    }
}
