package org.zero.dto;

public class GameDTO {
    private int id;
    private String name;
    private int team_id;
    private boolean captain;
    private String category;
    public GameDTO(int id, String name, int team_id, boolean caption, String category) {
        this.id = id;
        this.name = name;
        this.team_id = team_id;
        this.captain = caption;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public boolean isCaptain() {
        return captain;
    }

    public void setCaptain(boolean captain) {
        this.captain = captain;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
