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
            d.add(new Paragraph("Could not create food submission"));
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
    public String getFoodName()
    {
        return foodName;
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
            PreparedStatement query1 = con.prepareStatement("SELECT servings FROM food_is_part_of_meal WHERE Food_Name = ? AND User_Food_ID = ? And Meal_ID = ?;");
            query1.setString(1,foodName);
            query1.setInt(2,userID);
            query1.setInt(3,mealID);
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
         try
        {   
            System.out.println("Checking if foodsub needs to be stacked");
            PreparedStatement query1 = con.prepareStatement("SELECT servings FROM food_is_part_of_meal WHERE Food_Name = ? AND User_Food_ID = ? And Meal_ID = ?;");
            query1.setString(1,food_name);
            query1.setInt(2,userID);
            query1.setInt(3,mealID);
            ResultSet rs = query1.executeQuery();
            //if there is a blank one it means that the duplicate entry is from a new submission, not an edit
            PreparedStatement checkBlank = con.prepareStatement("SELECT * FROM food_is_part_of_meal WHERE Food_Name = ? AND User_Food_ID = ? And Meal_ID = ?;");
            checkBlank.setString(1,"");
            checkBlank.setInt(2,userID);
            checkBlank.setInt(3,mealID);
            ResultSet blankSet = checkBlank.executeQuery();
            if(rs.next() && blankSet.next())
            {
                System.out.println("Confirmed foodsub needs to be stacked.. attempting to stack");
                PreparedStatement query2 = con.prepareStatement("Update food_is_part_of_meal SET servings = ? WHERE Food_Name = ? AND User_Food_ID = ? And Meal_ID = ?;");
                query2.setString(2,food_name);
                query2.setInt(3,userID);
                query2.setInt(4,mealID);
                query2.setFloat(1,new_servings + rs.getFloat(1));
                query2.executeUpdate();
                System.out.println("stacking: add old servings: "+ rs.getFloat(1)+"to new servings: "+ new_servings);
            }
            else
            {
                setFood(food_name);
                setServings(new_servings);
            }
        
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

}
