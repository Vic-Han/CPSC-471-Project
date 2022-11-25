package com.example.demo;
import java.sql.*;
import java.rmi.server.RemoteObject;
import java.util.ArrayList;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
public class MealView implements Editor<Meal>{
    private ArrayList<Food> foodList = new ArrayList<Food>();
    private int userID;
    private Connection con;
    private Button newMEal = new Button("New Food");
    private Button editFood = new Button("Edit Food");
    private Button delFood = new Button("Delete Food");
    ComboBox<Food> chooseFood = new ComboBox<Food>("Choose Food");
    public MealView(int ID)
    {
        userID = ID;
        setup();
    }
    public MealView()
    {
        this(1);
    }
    public void setup()
    {
        /*
        initConnection();
        newFood.addClickListener(clickEvent -> {
            FoodEditor editor = new MealEditor(this);
            editor.open();
        });
        add(newFood);

        //make combobox...
        fetchData();
        chooseFood.setItemLabelGenerator(Food::getName);
        add(chooseFood);
        
        //make edit exercise button...
        editFood.addClickListener(clickEvent -> {
            FoodEditor editor = new FoodEditor(chooseFood.getValue(),this);
            editor.open();
        });
        add(editFood);
        delFood.addClickListener(clickEvent -> {deleteObject(chooseFood.getValue());});
        add(delFood);

        */
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
    public void deleteObject(Meal meal)
    {
        /* 
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
        */
    }
    @Override
    public void addObject(Meal meal)
    {
        fetchData();
    }
}
