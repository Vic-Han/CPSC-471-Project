package com.example.demo;

public class Exercise {
    private String exName;
    private int userID;
    public Exercise(){}
    public Exercise(String name, int ID){
        setName(name);
        userID = ID;
    }

    public String getName(){
        return exName;
    }
    public void setName(String name){
        exName = name;
    }
    public int getID()
    {
        return userID;
    }
}