package org.zero.dto;

import java.sql.Time;

public class RankDTO {
    private int teamId;
    private String playerName;
    private Time playTime;

    public RankDTO(int teamId, String playerName, Time playTime) {
        this.teamId = teamId;
        this.playerName = playerName;
        this.playTime = playTime;
    }

    public RankDTO(int teamId, String playerName) {
        this.teamId = teamId;
        this.playerName = playerName;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Time getPlayTime() {
        return playTime;
    }

    public void setPlayTime(Time playTime) {
        this.playTime = playTime;
    }
}
