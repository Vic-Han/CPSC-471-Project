package com.example.demo;
import java.sql.*;
import java.rmi.server.RemoteObject;
import java.util.ArrayList;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
//@Route("foodView")
public class FoodView extends VerticalLayout implements Editor<Food>{
    private ArrayList<Food> foodList = new ArrayList<Food>();
    private int userID;
    private Connection con;
    private Button newFood = new Button("New Food");
    private Button editFood = new Button("Edit Food");
    private Button delFood = new Button("Delete Food");
    ComboBox<Food> chooseFood = new ComboBox<Food>("Choose Food");
    public FoodView(int ID)
    {
        setAlignItems(FlexComponent.Alignment.CENTER);
        userID = ID;
        setup();
    }
    public void setup()
    {
        initConnection();

        //make combobox...
        fetchData();
        chooseFood.setItemLabelGenerator(Food::getName);
        add(chooseFood);
        HorizontalLayout buttons = new HorizontalLayout();
        //make new food button
        newFood.addClickListener(clickEvent -> {
            FoodEditor editor = new FoodEditor(this);
            editor.open();
        });
        buttons.add(newFood);
        //make edit food button...
        editFood.addClickListener(clickEvent -> {
            FoodEditor editor = new FoodEditor(chooseFood.getValue(),this);
            editor.open();
        });
        buttons.add(editFood);
        delFood.addClickListener(clickEvent -> {deleteObject(chooseFood.getValue());});
        buttons.add(delFood);
        add(buttons);


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
