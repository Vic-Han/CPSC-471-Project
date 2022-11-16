package com.example.demo;

public class Metric {
    //dummy class for testing
    private String metName;
    private String metUnit;
    public Metric(String name, String unit){
        setName(name);
        setUnit(unit);
    }
    public String getName(){
        return metName;
    }
    public String getUnit(){
        return metUnit;
    }
    public void setName(String name){
        metName = name;
    }
    public void setUnit(String unit){
        metUnit = unit;
    }
}
