package edu.sancher6tcnj.raspberrypiclient;

import android.util.Pair;

import java.util.Dictionary;
import java.util.HashMap;

public class Run {
    //fields
    private String runID;
    private String runName;
    private int time;
    private HashMap<String,Integer> Instr = new HashMap<>();

    //constructors
    public Run(){}

    public Run (String id, String name, int time, HashMap Instr){
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

    public void setInstr(HashMap instr){this.Instr = instr;}

    public HashMap getInstr(){return this.Instr;}

    public void addInstr(String instr, int dist){
        this.Instr.put(instr, dist);
    }

    public void remInst(String instr){
        this.Instr.remove(instr);
    }

}
