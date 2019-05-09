package com.pure.matches;

import java.util.LinkedList;
import java.util.List;

public class Match {

    private String ruleName = "";
    private String description = "";
    private Integer score = 0;
    private Integer matchesFound = 0;
    private List<String> matchedRows = null;
    private String currentJobFull = "";
    private String jobid = "";

    public Match(String jobPath, String jobId){
        matchedRows = new LinkedList<>();
        currentJobFull = jobPath;
        this.jobid = jobId;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getMatchesFound() {
        return matchesFound;
    }

    public void setMatchesFound(Integer matchesFound) {
        this.matchesFound = matchesFound;
    }

    public List<String> getMatchedRows() {
        return matchedRows;
    }

    public void setMatchedRows(List<String> matchedRows) {
        this.matchedRows = matchedRows;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCurrentJobFull() {
        return currentJobFull;
    }

    public void setCurrentJobFull(String currentJobFull) {
        this.currentJobFull = currentJobFull;
    }

    public String getJobid() {
        return jobid;
    }

    public void setJobid(String jobid) {
        this.jobid = jobid;
    }
}
