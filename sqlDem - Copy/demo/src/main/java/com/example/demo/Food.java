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

  }
  public int getGramsPerServing()
  {
    return 0;
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
  public int getmlPerServing()
  {
    return 0;
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
  public int getCarbs()
  {
    return 0; 
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
  public int getProtien()
  {
    return 0;
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
  public int getFats()
  {
    return 0;
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
    return 0;
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