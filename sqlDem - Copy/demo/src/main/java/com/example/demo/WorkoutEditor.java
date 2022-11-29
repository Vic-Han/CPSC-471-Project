package com.example.demo;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class WorkoutEditor extends VerticalLayout implements Editor<ExerciseSubmission>{
    
    private int userID;
    private Workout workout;
    private Editor<Workout> parent;
    public WorkoutEditor(Editor<Workout> parent)
    {

    }
    public WorkoutEditor(Editor<Workout> parent,Workout workout)
    {
        userID = parent.getUserID();
        this.parent = parent;
        this.workout = workout;
    }
    public int getWorkoutID()
    {
        return workout.getID();
    }
    @Override
    public void fetchData()
    {
       
    }
    @Override
    public void addObject(ExerciseSubmission sub)
    {
        
    }
    @Override
    public void deleteObject(ExerciseSubmission sub)
    {
       
        fetchData();
    }
    @Override
    public int getUserID()
    {
        return userID;
    }
}
