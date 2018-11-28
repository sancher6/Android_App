package edu.sancher6tcnj.raspberrypiclient;

import java.util.Dictionary;

public class Run {
    //fields
    private String runID;
    private String runName;
    private String time;
    private String[] Instr;

    //constructors
    public Run(){}

    public Run (String id, String name, String time, String[] instr){
        this.runID = id;
        this.runName = name;
        this.time = time;
        this.Instr = instr;
    }
    //methods
    public void setRunID(String runID){
        this.runID = runID;
    }

    public String getRunID() {
        return this.runID;
    }
}
