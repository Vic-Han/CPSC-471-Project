package com.example.demo;

public class WorkoutEditor {
    
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
    public int workoutID()
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
