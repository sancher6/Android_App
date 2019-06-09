package edu.sancher6tcnj.raspberrypiclient;

public class Run {
    //fields
    private String runID;
    private String runName;
    private String Instr;
    private int time;

    //constructors
    public Run(){}

    public Run (String id, String name, int time, String Instr){
        this.runID = id;
        this.runName = name;
        this.time = time;
        this.Instr = Instr;
    }
    //methods
    public void setRunID(String runID){
        this.runID = runID;
    }

    public String getRunID() {
        return this.runID;
    }

    public void setRunName(String runName){
        this.runName = runName;
    }

    public String getRunName(){
        return this.runName;
    }

    public void setTime(int time){this.time = time;}

    public int getTime(){return this.time;}

    public void setInstr(String instr){this.Instr = instr;}

    public String getInstr(){return this.Instr;}

}
