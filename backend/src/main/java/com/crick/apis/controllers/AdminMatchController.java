package com.crick.apis.controllers;

import com.crick.apis.entities.Match;
import com.crick.apis.entities.MatchStatus;
import com.crick.apis.services.CricketService;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/admin/matches")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminMatchController {

    private final CricketService cricketService;

    public AdminMatchController(CricketService cricketService) {
        this.cricketService = cricketService;
    }

    // ✅ Start/play a match
    @PostMapping("/play/{matchId}")
    public Match playMatch(@PathVariable Long matchId) {
        return cricketService.startMatch(matchId); // use existing method
    }
    @PostMapping("/create")
    public Match createMatch(@RequestBody Match match) {
        // Agar status null ho → default UPCOMING
        if (match.getStatus() == null) {
            match.setStatus(MatchStatus.UPCOMING);
        }

        // Agar scoreSummary null ho → default initial score
        if (match.getScoreSummary() == null || match.getScoreSummary().isEmpty()) {
            match.setScoreSummary(match.getTeamA() + " 0/0 (0.0 ov)");
        }

        return cricketService.saveMatch(match);
    }
    
}
