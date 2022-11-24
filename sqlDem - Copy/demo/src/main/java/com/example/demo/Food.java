package com.example.demo;

import java.net.ConnectException;
import java.sql.*;
public class Food{
  
  private String foodName;
  private int UserID;
  private Connection con;
  public void initConnection()
  {
       
      try{
          con = DriverManager.getConnection("jdbc:mysql://localhost/tracker", "athlete", "cpsc");
          
      } 
      catch (SQLException e) {
          e.printStackTrace();
      }   
  }
  public Food(int ID)
  {
        foodName = "";
        UserID = ID;
        initConnection();

        try{
            PreparedStatement query = con.prepareStatement("INSERT INTO FOOD VALUES(?,?,1,1,0,0,0,0);");
            query.setString(1, "");
            query.setInt(2, ID);
            query.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }  
  }
  public Food(int ID, String name)
  {
        foodName = name;
        UserID = ID;
        initConnection();
  }
  public String getName(){
        return foodName;
  }
  public void setName(String name){
    try{
        PreparedStatement query = con.prepareStatement("UPDATE FOOD SET Name = ? WHERE Name = ? AND User_ID = ?;");
        query.setString(1,name);
        query.setString(2, foodName);
        query.setInt(3, UserID);
        query.executeUpdate();
    }
    catch (SQLException e) {
        e.printStackTrace();
    }
  }
  public int getGramsPerServing() throws SQLException
  {
    try
    {
        PreparedStatement query1 = con.prepareStatement("SELECT Grams_Per_Servings FROM FOOD WHERE Name = ? AND User_ID = ?;");
        query1.setString(1,foodName);
        query1.setInt(2,UserID);
        ResultSet rs = query1.executeQuery();
        rs.next();
        return rs.getInt(1);
    }
    catch(SQLException e)
    {
        throw new SQLException();
    }
  }
  public void setGramsPerServing(int grams)
  {
        try{
            PreparedStatement query = con.prepareStatement("UPDATE FOOD SET Grams_Per_Servings = ? WHERE Name = ? AND User_ID = ?;");
            query.setInt(1,grams);
            query.setString(2, foodName);
            query.setInt(3, UserID);
            query.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }  
   }
  public int getmlPerServing() throws SQLException
  {
    try
    {
        PreparedStatement query1 = con.prepareStatement("SELECT Milliters_Per_Serving FROM FOOD WHERE Name = ? AND User_ID = ?;");
        query1.setString(1,foodName);
        query1.setInt(2,UserID);
        ResultSet rs = query1.executeQuery();
        rs.next();
        return rs.getInt(1);
    }
    catch(SQLException e)
    {
        throw new SQLException();
    }
  }
  public void setmlPerServing(int ml)
  {
    try{
        PreparedStatement query = con.prepareStatement("UPDATE FOOD SET Milliters_Per_Serving = ? WHERE Name = ? AND User_ID = ?;");
        query.setInt(1,ml);
        query.setString(2, foodName);
        query.setInt(3, UserID);
        query.executeUpdate();
    }
    catch (SQLException e) {
        e.printStackTrace();
    }
  }
  public int getCarbs() throws SQLException
  {
    try
    {
        PreparedStatement query1 = con.prepareStatement("SELECT Carbs FROM FOOD WHERE Name = ? AND User_ID = ?;");
        query1.setString(1,foodName);
        query1.setInt(2,UserID);
        ResultSet rs = query1.executeQuery();
        rs.next();
        return rs.getInt(1);
    }
    catch(SQLException e)
    {
        throw new SQLException();
    } 
  }
  public void setCarbs(int carb)
  {
    try{
        PreparedStatement query = con.prepareStatement("UPDATE FOOD SET Carbs = ? WHERE Name = ? AND User_ID = ?;");
        query.setInt(1,carb);
        query.setString(2, foodName);
        query.setInt(3, UserID);
        query.executeUpdate();
    }
    catch (SQLException e) {
        e.printStackTrace();
    }
  }
  public int getProtien() throws SQLException
  {
    try
    {
        PreparedStatement query1 = con.prepareStatement("SELECT Protein FROM FOOD WHERE Name = ? AND User_ID = ?;");
        query1.setString(1,foodName);
        query1.setInt(2,UserID);
        ResultSet rs = query1.executeQuery();
        rs.next();
        return rs.getInt(1);
    }
    catch(SQLException e)
    {
        throw new SQLException();
    }
  }
  public void setProtein(int protein)
  {
    try{
        PreparedStatement query = con.prepareStatement("UPDATE FOOD SET Protein = ? WHERE Name = ? AND User_ID = ?;");
        query.setInt(1,protein);
        query.setString(2, foodName);
        query.setInt(3, UserID);
        query.executeUpdate();
    }
    catch (SQLException e) {
        e.printStackTrace();
    }
  }
  public int getFats() throws SQLException
  {
    try
    {
        PreparedStatement query1 = con.prepareStatement("SELECT Fats FROM FOOD WHERE Name = ? AND User_ID = ?;");
        query1.setString(1,foodName);
        query1.setInt(2,UserID);
        ResultSet rs = query1.executeQuery();
        rs.next();
        return rs.getInt(1);
    }
    catch(SQLException e)
    {
        throw new SQLException();
    }
  }
  public void setFats(int fat)
  {
    try{
        PreparedStatement query = con.prepareStatement("UPDATE FOOD SET Fats = ? WHERE Name = ? AND User_ID = ?;");
        query.setInt(1,fat);
        query.setString(2, foodName);
        query.setInt(3, UserID);
        query.executeUpdate();
    }
    catch (SQLException e) {
        e.printStackTrace();
    }
  }
  public int getCalories() throws SQLException
  {
     //works 
    try
    {
        PreparedStatement query1 = con.prepareStatement("SELECT Calories FROM FOOD WHERE Name = ? AND User_ID = ?;");
        query1.setString(1,foodName);
        query1.setInt(2,UserID);
        ResultSet rs = query1.executeQuery();
        rs.next();
        return rs.getInt(1);
    }
    catch(SQLException e)
    {
        throw new SQLException();
    }
  }
  public void setCalories(int calorie)
  {
    try{
        PreparedStatement query = con.prepareStatement("UPDATE FOOD SET Calories = ? WHERE Name = ? AND User_ID = ?;");
        query.setInt(1,calorie);
        query.setString(2, foodName);
        query.setInt(3, UserID);
        query.executeUpdate();
    }
    catch (SQLException e) {
        e.printStackTrace();
    }
  }
  public void update(String name, int ml,int gram,int fats,int protein, int carb, int calories)
  {
    setFats(fats);
    setCalories(calories);
    setCarbs(carb);
    setGramsPerServing(gram);
    setName(name);
    setmlPerServing(ml);
    setProtein(protein);
  }
  

}