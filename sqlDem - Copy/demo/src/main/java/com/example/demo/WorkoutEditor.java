package com.example.demo;

import java.sql.Date;
import java.time.LocalDate;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class WorkoutEditor extends VerticalLayout implements Editor<ExerciseSubmission>{
    
    private int userID;
    private Workout workout;
    private Editor<Workout> parent;
    private Dialog submissionDialog;
    public WorkoutEditor(Editor<Workout> parent, LocalDate date)
    {
        userID = parent.getUserID();
        this.parent = parent;
        this.workout = new Workout(userID, Date.valueOf(date));
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
     submissionDialog.close();  
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
