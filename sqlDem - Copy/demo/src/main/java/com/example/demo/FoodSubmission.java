package com.example.demo;
import java.sql.*;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Paragraph;
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
         try{
            PreparedStatement query = con.prepareStatement("INSERT INTO FOOD_IS_PART_OF_MEAL(Food_name, User_food_ID, Meal_ID, servings) VALUES(?,?,?,null);");
            query.setString(1, "");
            query.setInt(2, userID);
            query.setInt(3, mealID);
            query.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            Dialog d = new Dialog();
            d.add(new Paragraph("fuck you"+ userID));
            d.open();
        }  

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
    public void setFood(String food)
    {
        try
        {
            PreparedStatement query1 = con.prepareStatement("UPDATE FOOD_IS_PART_OF_MEAL SET food_name = ? WHERE User_Food_ID = ? AND Meal_ID = ? AND food_name = ?;");
            query1.setString(1,food);
            query1.setInt(2,userID);
            query1.setString(4,foodName);
            query1.setInt(3,mealID);
            query1.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        foodName = food;
    }
    public void setServings(float servings)
    {
        try
        {
            PreparedStatement query1 = con.prepareStatement("UPDATE FOOD_IS_PART_OF_MEAL SET Servings = ? WHERE User_Food_ID = ? AND Meal_ID = ? AND food_name = ?;");
            query1.setFloat(1,servings);
            query1.setInt(2,userID);
            query1.setString(4,foodName);
            query1.setInt(3,mealID);
            query1.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    public float getServings()
    {
        try
        {
            PreparedStatement query1 = con.prepareStatement("SELECT servings FROM FOOD WHERE Name = ? AND User_ID = ?;");
            query1.setString(1,foodName);
            query1.setInt(2,userID);
            ResultSet rs = query1.executeQuery();
            rs.next();
            return rs.getFloat(1);
        }
        catch(SQLException e)
        {
            return 0;
            //throw new SQLException();
        }
    }
    public void update(String food_name, float new_servings)
    {
        setFood(food_name);
        setServings(new_servings);
    }

}
