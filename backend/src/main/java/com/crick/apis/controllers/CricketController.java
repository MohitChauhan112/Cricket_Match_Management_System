package com.crick.apis.controllers;

import com.crick.apis.entities.Match;
import com.crick.apis.services.CricketService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matches")
@CrossOrigin(origins = "http://localhost:4200") // 👈 ADD THIS (IMPORTANT)
public class CricketController {

    private final CricketService cricketService;

    public CricketController(CricketService cricketService) {
        this.cricketService = cricketService;
    }

    @GetMapping("/live")
    public List<Match> getLiveMatches() {
        return cricketService.getLiveMatches();
    }

    @GetMapping("/upcoming")
    public List<Match> getUpcomingMatches() {
        return cricketService.getUpcomingMatches();
    }

    @GetMapping("/completed")
    public List<Match> getCompletedMatches() {
        return cricketService.getCompletedMatches();
    }
}
