package com.example.nuj;

import java.util.Date;

public class Goal {

    private int id;
    private String description;
    private int difficulty;
    private Date start;
    private Date end;
    private boolean completed;

    // Constructor method
    public Goal(int id, String description, int difficulty, Date start, Date end, boolean completed) {
        this.id = id;
        this.description = description;
        this.difficulty = difficulty;
        this.start = start;
        this.end = end;
        this.completed = completed;
    }

    // Getter and setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public boolean getCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }


}
