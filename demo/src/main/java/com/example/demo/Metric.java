package com.example.demo;
import java.sql.*;
public class Metric {
    private String metName;
    private int UserID;
    public Metric(String name, int id){
        setName(name);
        UserID = id;
    }
    public Metric(int id)
    {
        metName = "";
        UserID = id;
        PreparedStatement query = c.prepareStatement("INSERT INTO PERFORMANCE_METRIC VALUES(?,?,?);");
        query.setString(1, "");
        query.setInt(2, id);
        query.setString(3, "");
        query.executeUpdate();
        // run 
    }
    public String getName(){
        return metName;
    }
    public int getID(){
        return UserID;
    }
    public void setName(String name){
       
        //SQL
        PreparedStatement query1 = c.prepareStatement("UPDATE PERFORMANCE_METRIC SET Name = ?;");
        query1.setString(1,name);
        PreparedStatement query2 = c.prepareStatement("UPDATE METRIC_MEASURES_SUBMISSION SET Metric_name = ? WHERE Metric_name =  ? ;");
        query2.setString(1,name);
        query2.setString(2,metName);
        PreparedStatement query3 = c.prepareStatement("UPDATE METRIC_DESCRIBES_EXERCISE SET Metric_name = ? WHERE Metric_name = ?;");
        query3.setString(1,name);
        query3.setString(2,metName);

        metName = name;
    }
    public String getUnit()
    {
        PreparedStatement query1 = c.prepareStatement("SELECT units FROM PERFORMANCE_METRIC WHERE Name = ? AND Owner_ID = ?;");
        query1.setString(1,metName);
        query1.setInt(2,UserID);
        return "";
    }
    public void setUnit(String unit)
    {
        PreparedStatement query1 = c.prepareStatement("UPDATE PERFORMANCE_METRIC SET Unit = ?;");
        query1.setString(1,unit);
    }
    public void update(String name, String unit)
    {
        setName(name);
        setUnit(unit);
    }
}
