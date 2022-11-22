package com.example.demo;
import java.sql.*;  
import java.util.ArrayList;
public class Exercise {
    private String exName;
    private int userID;
    private Connection con;
    public Exercise(int ID){
        userID = ID;
        exName = "";
        initConnection();
        try
        {
        PreparedStatement query = con.prepareStatement("INSERT INTO EXERCISE VALUES(?,?);");
        query.setInt(1, ID);
        query.setString(2, exName);
        query.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

    }
    public Exercise(String name, int ID){
        exName = name;
        userID = ID;
        initConnection();
    }
    public void initConnection()
    {
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost/tracker", "athlete", "cpsc");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public String getName(){
        return exName;
    }
    public void setName(String name){
        exName = name;

        try
        {
            PreparedStatement query1 = con.prepareStatement("UPDATE EXERCISE SET Name = ?;");
            query1.setString(1,name);
            query1.executeQuery();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        try
        {
            PreparedStatement query2 = con.prepareStatement("UPDATE SUBMISSION SET Exercise_Name =? WHERE Exercise_Name =  ? ;");
            query2.setString(1,name);
            query2.setString(2,exName);
            query2.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        try
        {
            PreparedStatement query3 = con.prepareStatement("UPDATE METRIC DESCRIBES EXERCISE SET Exercise_Name = ? WHERE Exercise_Name = ?;");
            query3.setString(1,name);
            query3.setString(2,exName);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    
        exName = name;
    }
    public ArrayList<Metric> getMetrics()
    {
        try
        {
            PreparedStatement query1 = con.prepareStatement("SELECT Name FROM METRIC_DESCRIBES_EXERCISE WHERE Metric_owner_ID = ? AND Exercise_name = ?");
            query1.setInt(1,userID);
            query1.setString(2,exName);
            query1.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return new ArrayList<Metric>();
    }
    public void removeMetric(Metric metric)
    {
        
        try
        {
            PreparedStatement query = con.prepareStatement("DELETE FROM METRIC_DESCRIBES_EXERCISE WHERE  Exercise_name = ? AND Metric_name = ? AND Metric_user_ID = ?; ");
            query.setString(1, exName);
            query.setString(2, metric.getName());
            query.setInt(3,userID);
            query.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
      
    }
    public void addMetric(Metric metric)
    {
        try
        {
            PreparedStatement query = con.prepareStatement("INSERT INTO METRIC_DESCIRBES_EXERCISE VALUES(?,?,?);");
            query.setString(1, metric.getName());
            query.setInt(2, userID);
            query.setString(3,exName);
            query.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    public void update(String name,ArrayList<Metric> newMetricList)
    {
        setName(name);
        ArrayList<Metric> oldList = getMetrics();
        for(int index = 0; index < oldList.size(); index++)
        {
            removeMetric(oldList.get(index));
        }
        for(int index = 0; index < newMetricList.size(); index++)
        {
            addMetric(newMetricList.get(index));
        }
    }
    public int getID()
    {
        return userID;
    }
}