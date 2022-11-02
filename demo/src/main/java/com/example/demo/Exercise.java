package com.example.demo;

public class Exercise {
    private String exName;
    public Exercise(){}
    public Exercise(String name){
        setName(name);
    }

    public String getName(){
        return exName;
    }
    public void setName(String name){
        exName = name;
    }
}
