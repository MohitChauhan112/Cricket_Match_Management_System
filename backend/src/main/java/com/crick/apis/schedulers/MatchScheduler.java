package com.crick.apis.schedulers;

import com.crick.apis.entities.Match;
import com.crick.apis.entities.MatchStatus;
import com.crick.apis.repositories.MatchRepo;
import com.crick.apis.services.CricketService;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Component
public class MatchScheduler {

    private final MatchRepo matchRepo;
    private final CricketService cricketService;
    private final Random random = new Random();

    public MatchScheduler(MatchRepo matchRepo, CricketService cricketService) {
        this.matchRepo = matchRepo;
        this.cricketService = cricketService;
    }

    @Scheduled(fixedRate = 10000) // har 10 sec → 1 ball per update
    public void playLiveMatches() {
        List<Match> liveMatches = cricketService.getLiveMatches();

        for (Match match : liveMatches) {
            simulateMatch(match);
            match.setLastUpdated(LocalDateTime.now());
            matchRepo.save(match);
        }
    }

    private void simulateMatch(Match match) {
        int runs = random.nextInt(7);    // 0-6 runs per ball
        int wicket = random.nextInt(20) == 0 ? 1 : 0; // 5% chance wicket

        String[] lines = match.getScoreSummary().split("\n");
        boolean firstInningsComplete = lines.length > 1;
        boolean secondInningsComplete = lines.length > 2 && match.getStatus() == MatchStatus.COMPLETED;

        if (!firstInningsComplete) {
            // First innings
            String lastLine = lines.length > 0 ? lines[lines.length - 1] : match.getBattingTeam() + " 0/0 (0.0 ov)";
            lastLine = updateLine(lastLine, runs, wicket, match);

            if (lines.length > 0) lines[lines.length - 1] = lastLine;
            else lines = new String[]{lastLine};
            match.setScoreSummary(String.join("\n", lines));

            // Check if first innings finished
            if (getBallsFromLine(lastLine) >= 120) { // 20 overs = 120 balls
                // Swap teams
                String temp = match.getBattingTeam();
                match.setBattingTeam(match.getBowlingTeam());
                match.setBowlingTeam(temp);

                // Add second innings line
                match.setScoreSummary(match.getScoreSummary() + "\n" + match.getBattingTeam() + " 0/0 (0.0 ov)");
            }

        } else if (!secondInningsComplete) {
            // Second innings
            String lastLine = lines[lines.length - 1];
            lastLine = updateLine(lastLine, runs, wicket, match);

            lines[lines.length - 1] = lastLine;
            match.setScoreSummary(String.join("\n", lines));

            // Check if second innings finished
            if (getBallsFromLine(lastLine) >= 120) {
                completeMatch(match, lines);
            }
        }
    }

    private String updateLine(String line, int runs, int wicket, Match match) {
        Pattern pattern = Pattern.compile("(\\d+)/(\\d+) \\((\\d+\\.?\\d*) ov\\)");
        Matcher matcher = pattern.matcher(line);

        if (!matcher.find()) return line; // agar match na ho, return line as-is

        int runsSoFar = Integer.parseInt(matcher.group(1));
        int wicketsSoFar = Integer.parseInt(matcher.group(2));
        double overs = Double.parseDouble(matcher.group(3));

        // Convert overs to total balls
        int totalBalls = (int)(Math.floor(overs) * 6 + Math.round((overs - Math.floor(overs)) * 10));
        totalBalls += 1; // 1 ball per update

        int newOvers = totalBalls / 6;
        int newBalls = totalBalls % 6;
        double updatedOvers = newOvers + newBalls / 10.0;

        // Update runs & wickets
        runsSoFar += runs;
        wicketsSoFar += wicket;
        if (wicketsSoFar > 10) wicketsSoFar = 10;

        String team = line.substring(0, matcher.start()).trim();
        return team + " " + runsSoFar + "/" + wicketsSoFar + " (" + String.format("%.1f", updatedOvers) + " ov)";
    }

    // Total balls from line (0.0 → 0 balls, 0.1 → 1 ball ...)
    private int getBallsFromLine(String line) {
        try {
            Pattern pattern = Pattern.compile("\\((\\d+\\.?\\d*) ov\\)");
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                double overs = Double.parseDouble(matcher.group(1));
                int balls = (int)(Math.floor(overs) * 6 + Math.round((overs - Math.floor(overs)) * 10));
                return balls;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void completeMatch(Match match, String[] lines) {
        match.setStatus(MatchStatus.COMPLETED);

        // Parse runs for both innings safely
        int firstInningsRuns = parseRunsFromLine(lines[0]);
        int secondInningsRuns = parseRunsFromLine(lines[lines.length - 1]); // second innings last line

        String winner;
        if (secondInningsRuns > firstInningsRuns) {
            winner = match.getBattingTeam() + " won";  // second innings batting team
        } else if (firstInningsRuns > secondInningsRuns) {
            winner = match.getBowlingTeam() + " won";  // first innings batting team
        } else {
            winner = "Draw";
        }

        match.setMatchResult(winner);
        match.setScoreSummary(match.getScoreSummary() + "\n| " + winner + " | Match Completed");
    }

    // Helper method to safely extract runs from a score line
    private int parseRunsFromLine(String line) {
        try {
            // Regex to match "120/3" pattern
            Pattern pattern = Pattern.compile("(\\d+)/(\\d+)");
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                return Integer.parseInt(matcher.group(1)); // group(1) = runs
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0; // agar match na ho to 0 return
    }
}
