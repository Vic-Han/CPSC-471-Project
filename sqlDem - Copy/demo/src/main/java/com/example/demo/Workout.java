package com.example.demo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Workout {
    private int userID;
    private int workoutID;
    private Connection con;

    //ctors..
    public Workout(int workoutID, int userID){
        initConnection();
        this.workoutID = workoutID;
        this.userID = userID;
    }
    public Workout(int userID){
        initConnection();
        this.userID = userID;
        setID();
        insertWorkout();
        
    }
    //methods..
    public ArrayList<ExerciseSubmission> getSubmissionList() throws SQLException{
        try
        {
            ArrayList<ExerciseSubmission> subList = new ArrayList<ExerciseSubmission>();
            PreparedStatement query1 = con.prepareStatement("SELECT Submission_ID FROM EXERCISE_SUBMISSION WHERE Workout_ID = ?;");
            query1.setInt(1, workoutID);
            ResultSet rs = query1.executeQuery();
            while (rs.next()){
                ExerciseSubmission tmp = new ExerciseSubmission(rs.getInt(1));
                subList.add(tmp);
            }
            return subList;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            throw new SQLException();
        }
    }
    public void addSubmission(ExerciseSubmission submission){
        try
        {
            PreparedStatement query1 = con.prepareStatement("UPDATE EXERCISE_SUBMISSION SET User_ID = ?, SET Workout_ID = ?, WHERE SUBMISSION_ID =?;");
            query1.setInt(1, userID);
            query1.setInt(2, workoutID);
            query1.setInt(3, submission.getID());
            query1.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    public void removeSubmission(ExerciseSubmission submission){
       submission.deleteSubmission();
    }
    public LocalDate getDate() throws SQLException{
         try
        {
            PreparedStatement query1 = con.prepareStatement("SELECT Day FROM WORKOUT WHERE Workout_ID =?;");
            query1.setInt(1, workoutID);
            ResultSet rs = query1.executeQuery();
            LocalDate date = rs.getDate(1).toLocalDate();
            return date;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            throw new SQLException();
        }
    }
    //helper methods..
    public void initConnection(){
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost/tracker", "athlete", "cpsc");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void setID(){//look for most likely next available ID
        try
        {
            PreparedStatement query1 = con.prepareStatement("SELECT COUNT(Workout_ID) FROM WORKOUT;");
            int maybeID = query1.executeQuery().getInt(1);
            verifyID(maybeID);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    private void verifyID(int maybeID){ //check if ID is actually valid.  If so, set ID
        try
        {
            PreparedStatement query1 = con.prepareStatement("SELECT * FROM WORKOUT WHERE Workout_ID = ?;");
            query1.setInt(1, maybeID);
            ResultSet existing = query1.executeQuery();
            if (existing.next()){//if not empty
                maybeID++;
                verifyID(maybeID);// try next possible ID
            }
            else{
                workoutID = maybeID;
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    private void insertWorkout(){//put workout into database
        try
        {
            PreparedStatement query1 = con.prepareStatement("INSERT INTO WORKOUT(Workout_ID, Day_owner_ID) VALUES(?,?);");
            query1.setInt(1, workoutID);
            query1.setInt(2, userID);
            query1.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
}
