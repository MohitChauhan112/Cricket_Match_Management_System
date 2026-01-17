

	package com.crick.apis.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cricket_match")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchId;

    // -------- BASIC --------
    private String teamA;
    private String teamB;
    private String teamHeading;   // India vs Pakistan

    @Enumerated(EnumType.STRING)
    private MatchStatus status;   // UPCOMING / LIVE / COMPLETED

    private LocalDateTime startTime;
    private LocalDateTime lastUpdated;

    // -------- CURRENT STATE --------
    private String battingTeam;
    private String bowlingTeam;

    // -------- SCORE (UI READY) --------
    @Column(length = 200)
    private String scoreSummary;

    // -------- RESULT --------
    private String matchResult;   // India won by 5 runs

    // -------- CONFIG --------
    private int maxOvers = 20;

    // ---------------- CONSTRUCTORS ----------------

    public Match() {
    }

    public Match(Long matchId, String teamA, String teamB, String teamHeading,
                 MatchStatus status, LocalDateTime startTime, LocalDateTime lastUpdated,
                 String battingTeam, String bowlingTeam, String scoreSummary,
                 String matchResult, int maxOvers) {

        this.matchId = matchId;
        this.teamA = teamA;
        this.teamB = teamB;
        this.teamHeading = teamHeading;
        this.status = status;
        this.startTime = startTime;
        this.lastUpdated = lastUpdated;
        this.battingTeam = battingTeam;
        this.bowlingTeam = bowlingTeam;
        this.scoreSummary = scoreSummary;
        this.matchResult = matchResult;
        this.maxOvers = maxOvers;
    }

    // ---------------- GETTERS & SETTERS ----------------

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    public String getTeamA() {
        return teamA;
    }

    public void setTeamA(String teamA) {
        this.teamA = teamA;
    }

    public String getTeamB() {
        return teamB;
    }

    public void setTeamB(String teamB) {
        this.teamB = teamB;
    }

    public String getTeamHeading() {
        return teamHeading;
    }

    public void setTeamHeading(String teamHeading) {
        this.teamHeading = teamHeading;
    }

    public MatchStatus getStatus() {
        return status;
    }

    public void setStatus(MatchStatus status) {
        this.status = status;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getBattingTeam() {
        return battingTeam;
    }

    public void setBattingTeam(String battingTeam) {
        this.battingTeam = battingTeam;
    }

    public String getBowlingTeam() {
        return bowlingTeam;
    }

    public void setBowlingTeam(String bowlingTeam) {
        this.bowlingTeam = bowlingTeam;
    }

    public String getScoreSummary() {
        return scoreSummary;
    }

    public void setScoreSummary(String scoreSummary) {
        this.scoreSummary = scoreSummary;
    }

    public String getMatchResult() {
        return matchResult;
    }

    public void setMatchResult(String matchResult) {
        this.matchResult = matchResult;
    }

    public int getMaxOvers() {
        return maxOvers;
    }

    public void setMaxOvers(int maxOvers) {
        this.maxOvers = maxOvers;
    }

    // ---------------- toString ----------------

    @Override
    public String toString() {
        return "Match{" +
                "matchId=" + matchId +
                ", teamA='" + teamA + '\'' +
                ", teamB='" + teamB + '\'' +
                ", teamHeading='" + teamHeading + '\'' +
                ", status=" + status +
                ", startTime=" + startTime +
                ", lastUpdated=" + lastUpdated +
                ", battingTeam='" + battingTeam + '\'' +
                ", bowlingTeam='" + bowlingTeam + '\'' +
                ", scoreSummary='" + scoreSummary + '\'' +
                ", matchResult='" + matchResult + '\'' +
                ", maxOvers=" + maxOvers +
                '}';
    }
}
