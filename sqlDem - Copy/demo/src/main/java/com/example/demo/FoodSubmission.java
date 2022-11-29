package com.example.demo;
import java.sql.*;
public class FoodSubmission {
    private int userID;
    private int mealID;
    private String foodName;
    private Connection con;
    public FoodSubmission(int user, int meal, String name)
    {
        userID = user;
        mealID = meal;
        foodName = name;
        initConnection();
    }
    public FoodSubmission(int user, int meal)
    {
        this(user,meal,"");
    }
    public void initConnection()
    {
         
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost/tracker", "athlete", "cpsc");
            
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }   
    }
    public Food getFood()
    {
        return new Food(userID,foodName);
    }
    public int getUserID()
    {
        return userID;
    }
    public int getMealID()
    {
        return mealID;
    }
    public void setFood(Food food)
    {

    }
    public void setServings(float servings)
    {

    }
    public float getServings()
    {
        try
        {
            PreparedStatement query1 = con.prepareStatement("SELECT servings FROM FOOD WHERE Name = ? AND User_ID = ?;");
            query1.setString(1,foodName);
            query1.setInt(2,UserID);
            ResultSet rs = query1.executeQuery();
            rs.next();
            return rs.getInt(1);
        }
        catch(SQLException e)
        {
            //throw new SQLException();
        }
    }

}
