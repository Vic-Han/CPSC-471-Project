package com.example.demo;
import java.sql.*;  
import java.util.ArrayList;
public class Exercise {
    private String exName;
    private int userID;
    public Exercise(int ID){
        userID = ID;
        exName = "";
        PreparedStatement query = c.prepareStatement("INSERT INTO EXERCISE VALUES(?,?);");
        query.setInt(1, ID);
        query.setString(2, exName);
        query.executeUpdate();

    }
    public Exercise(String name, int ID){
        exName = name;
        userID = ID;
    }

    public String getName(){
        return exName;
    }
    public void setName(String name){
        exName = name;


        PreparedStatement query1 = c.prepareStatement("UPDATE EXERCISE SET Name = ?;");
        query1.setString(1,name);
        PreparedStatement query2 = c.prepareStatement("UPDATE SUBMISSION SET Exercise_Name =? WHERE Exercise_Name =  ? ;");
        query2.setString(1,name);
        query2.setString(2,exName);
        PreparedStatement query3 = c.prepareStatement("UPDATE METRIC DESCRIBES EXERCISE SET Exercise_Name = ? WHERE Exercise_Name = ?;");
        query3.setString(1,name);
        query3.setString(2,exName);
        // run queries
    
        exName = name;
    }
    public ArrayList<Metric> getMetrics()
    {
        PreparedStatement query1 = c.prepareStatement("SELECT Name FROM METRIC_DESCRIBES_EXERCISE WHERE Metric_owner_ID = ? AND Exercise_name = ?");
        query1.setInt(1,userID);
        query1.setString(2,exName);

        return new ArrayList<Metric>();
    }
    public void removeMetric(Metric metric)
    {
        PreparedStatement query = c.prepareStatement("DELETE FROM METRIC_DESCRIBES_EXERCISE WHERE  Exercise_name = ? AND Metric_name = ? AND Metric_user_ID = ?; ");
        query.setString(1, exName);
        query.setString(2, metric.getName());
        query.setInt(3,userID);
        query.executeUpdate();
        // run query
      
    }
    public void addMetric(Metric metric)
    {
        PreparedStatement query = c.prepareStatement("INSERT INTO METRIC_DESCIRBES_EXERCISE VALUES(?,?,?);");
        query.setString(1, metric.getName());
        query.setInt(2, userID);
        query.setString(3,exName);
        query.executeUpdate();
        // run query
    }
    public int getID()
    {
        return userID;
    }
}