package com.example.demo;

import java.sql.*;
import java.util.ArrayList;


public class ExerciseSubmission {
    private int submissionID;
    private Connection con;
    public void initConnection()
    {
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost/tracker", "athlete", "cpsc");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ExerciseSubmission(int submissionID){
        initConnection();
        this.submissionID = submissionID;
    }
    public ExerciseSubmission(){
        initConnection();
        setID();
        insertSubmission();

        
    }
   
    public Exercise getExercise() throws SQLException{
        try
        {
            PreparedStatement query1 = con.prepareStatement("SELECT Exercise_name, User_ID FROM  EXERCISE_SUBMISSION WHERE Submission_ID = ?;");
            query1.setInt(1, submissionID);
            ResultSet rs = query1.executeQuery();
            String exName = rs.getString(1);
            int usID = rs.getInt(2);
            return new Exercise(exName, usID);
        }
        catch(SQLException e)
        {
            throw new SQLException();
        }
    }
    public void setExercise(Exercise exercise){
        try
        {
            PreparedStatement query1 = con.prepareStatement("UPDATE EXERCISE_SUBMISSION SET Exercise_name = ?, SET UserID = ?, WHERE SUBMISSION_ID =?;");
            query1.setString(1, exercise.getName());
            query1.setInt(2, exercise.getID());
            query1.setInt(3, submissionID);
            query1.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    public ArrayList<MetricPair> getMetricList() throws SQLException{
        try
        {
            PreparedStatement query1 = con.prepareStatement("SELECT Metric_name, Metric_owner_ID, Metric_value FROM METRIC_MEASURES_SUBMISSION WHERE Submission_ID = ?;");
            query1.setInt(1, submissionID);
            ResultSet rs = query1.executeQuery();
            ArrayList<MetricPair> metricList = new ArrayList<MetricPair>();
            while (rs.next()){
                Metric temp = new Metric(rs.getString(1), rs.getInt(2));
                Integer val = rs.getInt(3);
                MetricPair mp = new MetricPair(temp, val);
                metricList.add(mp);
            }
            return metricList;
        }
        catch(SQLException e)
        {
            throw new SQLException();
        }
    }
    public void setMetricList(ArrayList<MetricPair> newList){
        try
        {
            PreparedStatement query1 = con.prepareStatement("DELETE FROM METRIC_MEASURES_SUBMISSION WHERE Submission_ID = ?;");
            query1.setInt(1, submissionID);
            query1.executeUpdate();
            for (MetricPair m : newList){
                PreparedStatement query2 = con.prepareStatement("INSERT INTO METRIC_MEASURES_SUBMISSION VALUES(?,?,?,?");
                query2.setString(1, m.getMetric().getName());
                query2.setInt(2, m.getMetric().getID());
                query2.setInt(3, m.getVal());
                query2.setInt(4, submissionID);
                query2.executeUpdate();
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    //private helper methods for constructors:
    private void setID(){
        //look for most likely next available ID
        try
        {
            PreparedStatement query1 = con.prepareStatement("SELECT COUNT(Submission_ID) FROM   EXERCISE_SUBMISSION;");
            int maybeID = query1.executeQuery().getInt(1);
            verifyID(maybeID);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    private void verifyID(int maybeID){
        //check if ID is actually valid.  If so, set ID
        try
        {
            PreparedStatement query1 = con.prepareStatement("SELECT * FROM EXERCISE_SUBMISSION WHERE Submission_ID = ?;");
            query1.setInt(1, maybeID);
            ResultSet existing = query1.executeQuery();
            if (existing.next()){//if not empty
                maybeID++;
                verifyID(maybeID);// try next possible ID
            }
            else{
                submissionID = maybeID;
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    private void insertSubmission(){
        try
        {
            PreparedStatement query1 = con.prepareStatement("INSERT INTO EXERCISE_SUBMISSION(Submission_ID) VALUES(?);");
            query1.setInt(1, submissionID);
            query1.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
}
