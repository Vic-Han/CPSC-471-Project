package com.example.demo;
import java.sql.*;
import java.util.ArrayList;


import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Paragraph;
import java.sql.*;

public class Meal{
    private int meal_ID;
    private Connection con;
    public Meal(int ID)
    {
        meal_ID = ID;
        initConnection();
    }
    public Meal(int user, Date day)
    {
        initConnection();
        meal_ID = setID();
        try{
            PreparedStatement query = con.prepareStatement("INSERT INTO MEAL(Meal_ID, User_ID, Day) VALUES(?,?,?);");
            query.setInt(1, meal_ID);
            query.setDate(3, day);
            query.setInt(2, user);
            query.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            Dialog d = new Dialog();
            d.add(new Paragraph("Trouble Creating new meal"));
            d.open();
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
    private int setID(){
        //look for most likely next available ID
        try
        {
            PreparedStatement query1 = con.prepareStatement("SELECT COUNT(Meal_ID) FROM Meal;");
            ResultSet count = query1.executeQuery();
            count.next();
            int maybeID = count.getInt(1);
            return verifyID(maybeID);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            Dialog d = new Dialog();
            d.add(new Paragraph("Error generating mealID"));
            d.open();

            return 0;
        }
    }

    private int verifyID(int maybeID)
    { //check if ID is actually valid.  If so, set ID
        try
        {
            PreparedStatement query1 = con.prepareStatement("SELECT * FROM Meal WHERE Meal_ID = ?;");
            query1.setInt(1, maybeID);
            ResultSet existing = query1.executeQuery();
            if (existing.next()){//if not empty
                return verifyID(maybeID + 1);// try next possible ID
            }
            else{
                return maybeID;
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            Dialog d = new Dialog();
            d.add(new Paragraph("Error accessing existing mealIDS"));
            d.open();
            return 0;
        }
    }
    public void removeFoodSubmission(FoodSubmission foodsub)
    {
        try{
            PreparedStatement query = con.prepareStatement("DELETE FROM INTO FOOD_IS_PART_OF_MEAL WHERE Meal_ID = ?, User_food_id = ? food_name = ?;");
            query.setInt(1, meal_ID);
            query.setString(3, foodsub.getFoodName());
            query.setInt(2, foodsub.getUserID());
            query.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            Dialog d = new Dialog();
            d.add(new Paragraph("Error trying to remove submission"+ meal_ID));
            d.open();
        }  
    }
    public ArrayList<FoodSubmission> getAllSubmissions()
    {
        ArrayList<FoodSubmission> allSubs = new ArrayList<FoodSubmission>();
        try
        {
            PreparedStatement query = con.prepareStatement("SELECT Food_Name From Food_is_part_of_meal WHERE Meal_ID = ?;");
            query.setInt(1, meal_ID);
            ResultSet rs = query.executeQuery();
            while(rs.next())
            {
                allSubs.add(new FoodSubmission(getUserID(), meal_ID, rs.getString(1)));
            }
        }
        catch(SQLException e)
        {
            Dialog d = new Dialog();
            d.add(new Paragraph("Error trying to get submissions"+ meal_ID));
            d.open();
        }
        return allSubs;
    }
    public int getUserID()
    {
        try
        {
            PreparedStatement query = con.prepareStatement("SELECT User_ID From Meal WHERE Meal_ID = ?;");
            query.setInt(1, meal_ID);
            ResultSet rs = query.executeQuery();
            rs.next();
            return rs.getInt(1);
        }
        catch(SQLException e)
        {
            return 0;
        }
    }
    public int getID()
    {
        return meal_ID;
    }
    public Date getDate()
    {
        try
        {
            PreparedStatement query1 = con.prepareStatement("SELECT day FROM MEAL where Meal_ID = ?;");
            query1.setInt(1, meal_ID);
            ResultSet count = query1.executeQuery();
            count.next();
            return count.getDate(1);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            Dialog d = new Dialog();
            d.add(new Paragraph("Error getting date"));
            d.open();
            return new Date(2002-02-02);
        }
       
    }
    
}
