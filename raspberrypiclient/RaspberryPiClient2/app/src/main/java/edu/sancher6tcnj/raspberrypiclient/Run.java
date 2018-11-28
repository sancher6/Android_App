package edu.sancher6tcnj.raspberrypiclient;

import java.util.Dictionary;

public class Run {
    //fields
    private Dictionary runs;
    private String runID;
    private String runName;

    //constructors
    public Run(){}

    public Run (Dictionary runs){
        this.runs = runs;
    }
    //methods
    public void setRunID(String runID){
        this.runID = runID;
    }

    public String getRunID() {
        return this.runID;
    }
}
