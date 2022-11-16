package com.example.demo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Day {
    private LocalDate date;
    private List<Workout> workouts;
    private List<Meal> meals;
    public Day(LocalDate date){
        this.date = date;
        //dummy vars:
            workouts = new ArrayList<Workout>();
            workouts.add(new Workout(1));
            workouts.add(new Workout(2));
            meals = new ArrayList<Meal>();
            meals.add(new Meal(1,100,100,100,100));
            meals.add(new Meal(2,200,200,200,200));
        //end dummy vars
    }
    public LocalDate getDate(){
        return date;
    }
    public List<Workout> getWorkouts(){
        return workouts;
    }
    public List<Meal> getMeals(){
        return meals;
    }
}
