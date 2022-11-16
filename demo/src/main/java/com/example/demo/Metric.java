package com.example.demo;

public class Metric {
    private String metName;
    private int UserID;
    public Metric(String name, int id){
        setName(name);
        UserID = id;
    }
    public String getName(){
        return metName;
    }
    public int getID(){
        return UserID;
    }
    public void setName(String name){
        metName = name;
    }
    //public String getUnit()
    //{
    //}
}
