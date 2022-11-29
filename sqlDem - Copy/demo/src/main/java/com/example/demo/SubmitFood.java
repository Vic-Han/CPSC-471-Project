package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.event.ChangeListener;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.Route;
public class SubmitFood extends VerticalLayout implements Editor<Food>{
    private int userID;
    private Connection con;
    private ArrayList<Food> foodList; 
    private Button newFood = new Button("New Food");
    private Button editFood = new Button("Edit Food");
    private Button delFood = new Button("Delete Food");
    private ComboBox<Food> chooseFood = new ComboBox<Food>("Choose Food");
    public SubmitFood(int ID)
    {
    
    }
    public SubmitFood(int ID, int mealID, String foodName)
    {

    }
    public void initConnection()
    {
         
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost/tracker", "athlete", "cpsc");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }   
    }
    @Override
    public int getUserID(){
        return userID;
    }
    @Override
    public void fetchData()
    {
        try
        {
            PreparedStatement query = con.prepareStatement("SELECT Name FROM FOOD WHERE User_ID = ?;");
            query.setInt(1, userID);
            ResultSet rs = query.executeQuery();
            foodList = new ArrayList<Food>();
            while(rs.next())
            {
                foodList.add(new Food(userID,rs.getString(1)));
            }
            chooseFood.setItems(foodList);
        }
        catch(SQLException e)
        {

        }
    }
    @Override
    public void deleteObject(Food food)
    {
        try
        {
            PreparedStatement query = con.prepareStatement("DELETE FROM FOOD WHERE User_ID = ? AND Name = ?;");
            query.setInt(1, userID);
            query.setString(2, food.getName());
            query.executeUpdate();
        }
        catch(SQLException e)
        {

        }
        fetchData();
    }
    @Override
    public void addObject(Food food)
    {

    }
}
