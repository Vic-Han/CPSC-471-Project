package com.example.demo;
import java.sql.*;
public class Metric {
    private String metName;
    private int UserID;
    private Connection con;
    public Metric(String name, int id){
        setName(name);
        UserID = id;
        initConnection();
    }
    public Metric(int id)
    {
        metName = "";
        UserID = id;
        initConnection();
        try{
        PreparedStatement query = con.prepareStatement("INSERT INTO PERFORMANCE_METRIC VALUES(?,?,?);");
        query.setString(1, "");
        query.setInt(2, id);
        query.setString(3, "");
        query.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }  
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
        return metName;
    }
    public int getID(){
        return UserID;
    }
    public void setName(String name){
       
        
        try{
            PreparedStatement query1 = con.prepareStatement("UPDATE PERFORMANCE_METRIC SET Name = ?;");
            query1.setString(1,name);
            query1.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        try
        {
            PreparedStatement query2 = con.prepareStatement("UPDATE METRIC_MEASURES_SUBMISSION SET Metric_name = ? WHERE Metric_name =  ? ;");
            query2.setString(1,name);
            query2.setString(2,metName);
            query2.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        try{
            PreparedStatement query3 = con.prepareStatement("UPDATE METRIC_DESCRIBES_EXERCISE SET Metric_name = ? WHERE Metric_name = ?;");
            query3.setString(1,name);
            query3.setString(2,metName);
            query3.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        
        metName = name;
    }
    public String getUnit()
    {
         
        try
        {
            PreparedStatement query1 = con.prepareStatement("SELECT units FROM PERFORMANCE_METRIC WHERE Name = ? AND Owner_ID = ?;");
            query1.setString(1,metName);
            query1.setInt(2,UserID);
            query1.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        
        return "";
    }
    public void setUnit(String unit)
    {
         
        try
        {
            PreparedStatement query1 = con.prepareStatement("UPDATE PERFORMANCE_METRIC SET Unit = ?;");
            query1.setString(1,unit);
            query1.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        
    }
    public void update(String name, String unit)
    {
        setName(name);
        setUnit(unit);
    }
}