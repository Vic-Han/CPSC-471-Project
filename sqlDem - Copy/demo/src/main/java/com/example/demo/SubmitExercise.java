package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SubmitExercise implements Editor<Exercise>{
    private int userID;
    private Editor<ExerciseSubmission> parent;
    private ArrayList<MetricPair> metrics;
    private Exercise exercise;
    private ExerciseSubmission exSubmission;
    private ArrayList<Exercise> exercises;
    private Connection con;
    public void initConnection()
    {
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost/tracker", "athlete", "cpsc");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public SubmitExercise(Editor<ExerciseSubmission> parent){
        initConnection();
        this.parent = parent;
        metrics = new ArrayList<MetricPair>();
        exSubmission = new ExerciseSubmission();
        exercises = new ArrayList<Exercise>();
    }
    public SubmitExercise(Editor<ExerciseSubmission> parent, ExerciseSubmission exSubmission){

    }
    private void rewriteMetrics(){
        exSubmission.setMetricList(metrics);
    }
    private void clearMetrics(){
        //clear metric layout
    }
    @Override
    public void addObject(Exercise exercise) {
        exercises.add(exercise);
        
    }

    @Override
    public void deleteObject(Exercise exercise) {
        exercises.remove(exercise);
        if (this.exercise == exercise){
            clearMetrics();
        }
        try{
            PreparedStatement query1 = con.prepareStatement("DELETE FROM EXERCISE WHERE User_ID = ? AND NAME = ? ;");
            query1.setInt(1,userID);
            query1.setString(2,exercise.getName());
            query1.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        try
        {
            PreparedStatement query2 = con.prepareStatement("DELETE FROM METRIC_DESCRIBES_EXERCISE WHERE Metric_user_ID = ? AND Exercise_name = ?;");
            query2.setInt(1,userID);
            query2.setString(2,exercise.getName());
            query2.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        try
        {
            PreparedStatement query3 = con.prepareStatement("DELETE FROM EXERCISE_SUBMISSION User_ID = ? AND Exercise_name = ?;");
            query3.setInt(1,userID);
            query3.setString(2,exercise.getName());
            query3.executeUpdate();
            }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        
    }

    @Override
    public void fetchData() {
        exercises.clear();
        try
        {
            PreparedStatement query1 = con.prepareStatement("SELECT Name FROM  EXERCISE WHERE Owner_ID = ? ;");
            query1.setInt(1,userID);
            ResultSet rs = query1.executeQuery();
            while (rs.next()){
                Exercise tmp = new Exercise(rs.getString(1), userID);
                exercises.add(tmp);
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

    }
        

    @Override
    public int getUserID() {
        return userID;
    }

    
    
}
