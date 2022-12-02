package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TableCleaner {
    public static Connection initConnection() throws SQLException
    {
         
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/tracker", "athlete", "cpsc");
            return con;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException();
        }   
    }
    public static void foodCleaner(){
        try{
            Connection con = initConnection();
            PreparedStatement update = con.prepareStatement("DELETE FROM FOOD WHERE Name = ? ;");
            update.setString(1, "");
            update.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        } 
    }
    public static void exerciseCleaner(){
        try{
            Connection con = initConnection();
            PreparedStatement update = con.prepareStatement("DELETE FROM MEtric_Describes_EXERCISE WHERE Exercise_Name = ? ;");
            update.setString(1, "");
            update.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }
        try{
            Connection con = initConnection();
            PreparedStatement update = con.prepareStatement("DELETE FROM EXERCISE WHERE Name = ? ;");
            update.setString(1, "");
            update.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        } 
    }
    public static void metricCleaner(){
        try{
            Connection con = initConnection();
            PreparedStatement update = con.prepareStatement("DELETE FROM Metric_Measures_Submission WHERE Metric_Name = ? ;");
            update.setString(1, "");
            update.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }
        try{
            Connection con = initConnection();
            PreparedStatement update = con.prepareStatement("DELETE FROM MEtric_Describes_EXERCISE WHERE Metric_Name = ? ;");
            update.setString(1, "");
            update.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }
        try{
            Connection con = initConnection();
            PreparedStatement update = con.prepareStatement("DELETE FROM PERFORMANCE_METRIC WHERE Metric_name = ? ;");
            update.setString(1, "");
            update.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        } 
    }
    public static void exSubCleaner(){
        try{
            Connection con = initConnection();
            PreparedStatement update = con.prepareStatement("DELETE FROM Exercise_Submission WHERE Exercise_name = ?;");
            update.setString(1, "");
            update.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        } 
    }
    public static void foodSubCleaner(){
        try{
            Connection con = initConnection();
            PreparedStatement update = con.prepareStatement("DELETE FROM Food_is_part_of_meal WHERE Food_name = ? ;");
            update.setString(1, "");
            update.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        } 
    }
    

}
