package com.example.demo;
import java.sql.*;
public class Metric {
    private String metName;
    private int UserID;
    private Connection con;
    public Metric(String name, int id){
        metName = name;
        UserID = id;
        initConnection();
    }
    public Metric(int id)
    {
        metName = "";
        UserID = id;
        initConnection();
        //works
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
       
        // works
        try{
            PreparedStatement query1 = con.prepareStatement("UPDATE PERFORMANCE_METRIC SET Metric_name = ? WHERE Owner_ID = ? AND Metric_name = ?;");
            query1.setString(1,name);
            query1.setInt(2,UserID);
            query1.setString(3,metName);
            query1.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        try
        {
            PreparedStatement query2 = con.prepareStatement("UPDATE METRIC_MEASURES_SUBMISSION SET Metric_name = ? WHERE Metric_name =  ? AND Metric_owner_ID = ?;");
            query2.setString(1,name);
            query2.setString(2,metName);
            query2.setInt(3,UserID);
            query2.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        try{
            PreparedStatement query3 = con.prepareStatement("UPDATE METRIC_DESCRIBES_EXERCISE SET Metric_name = ? WHERE Metric_name = ? AND Metric_owner_ID = ?;");
            query3.setString(1,name);
            query3.setString(2,metName);
            query3.setInt(3,UserID);
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
        //works 
        try
        {
            PreparedStatement query1 = con.prepareStatement("SELECT Units FROM PERFORMANCE_METRIC WHERE Metric_name = ? AND Owner_ID = ?;");
            query1.setString(1,metName);
            query1.setInt(2,UserID);
            ResultSet rs = query1.executeQuery();
            rs.next();
            return rs.getString(1);
        }
        catch(SQLException e)
        {
            return new String("");
        }
        // works
    }
    public void setUnit(String unit)
    {
         
        try
        {
            PreparedStatement query1 = con.prepareStatement("UPDATE PERFORMANCE_METRIC SET Units = ? WHERE Owner_ID = ? AND Metric_name = ?;");
            query1.setString(1,unit);
            query1.setInt(2,UserID);
            query1.setString(3,metName);
            query1.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        
    }
    public void update(String name, String unit)
    {
        setUnit(unit);
        setName(name);
    }
}