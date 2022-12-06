package com.example.demo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Paragraph;

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
    public Workout(int userID, Date date){
        initConnection();
        this.userID = userID;
        workoutID = setID();
        insertWorkout(userID,date);
        
    }
    //methods..
    public ArrayList<ExerciseSubmission> getSubmissionList(){
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
            return new ArrayList<ExerciseSubmission>();
        }
    }
    public void addSubmission(ExerciseSubmission submission){
        try
        {
            PreparedStatement query1 = con.prepareStatement("UPDATE EXERCISE_SUBMISSION SET User_ID = ?, Workout_ID = ? WHERE Submission_ID =?;");
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
    private int setID(){//look for most likely next available ID
        try
        {
            PreparedStatement query1 = con.prepareStatement("SELECT COUNT(Workout_ID) FROM WORKOUT;");
            ResultSet count = query1.executeQuery();
            count.next();
            int maybeID = count.getInt(1);
            return verifyID(maybeID);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return 0;
        }
    }

    private int verifyID(int maybeID){ //check if ID is actually valid.  If so, set ID
        try
        {
            PreparedStatement query1 = con.prepareStatement("SELECT * FROM WORKOUT WHERE Workout_ID = ?;");
            query1.setInt(1, maybeID);
            ResultSet existing = query1.executeQuery();
            if (existing.next()){//if not empty
                return verifyID(maybeID + 1);// try next possible ID
            }
            else{
                return maybeID;
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return 0;
        }
    }
    private void insertWorkout(int userID, Date day){//put workout into database
        try
        {
            PreparedStatement query1 = con.prepareStatement("INSERT INTO WORKOUT(Workout_ID, User_ID, Day) VALUES(?,?,?);");
            query1.setInt(1, workoutID);
            query1.setInt(2, userID);
            query1.setDate(3,day);
            query1.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            Dialog d = new Dialog();
            d.add(new Paragraph("Trouble inserting workout into database"));
            d.open();
        }
    }
    public void deleteWorkout(){
        try
        {
            PreparedStatement query2 = con.prepareStatement("DELETE FROM Exercise_submission WHERE Workout_ID = ? ;");
            query2.setInt(1, workoutID);
            query2.executeUpdate();
            PreparedStatement query1 = con.prepareStatement("DELETE FROM WORKOUT WHERE Workout_ID = ? ;");
            query1.setInt(1, workoutID);
            query1.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    public int getID()
    {
        return workoutID;
    }
}
