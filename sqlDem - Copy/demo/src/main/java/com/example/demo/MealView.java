package com.example.demo;
import java.sql.*;
import java.rmi.server.RemoteObject;
import java.util.ArrayList;

import org.apache.logging.log4j.util.MultiFormatStringBuilderFormattable;

import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
public class MealView extends VerticalLayout implements Editor<Meal>{
    private ArrayList<Food> foodList = new ArrayList<Food>();
    private int userID;
    private Connection con;
    private ArrayList<Meal> mealList;
    private Accordion mealAccordion;
    private Button newMeal = new Button("New Meal");
    private Button editMeal = new Button("Edit Meal");
    private Button delMeal = new Button("Delete Meal");
    private Paragraph noItems = new Paragraph("No meals on this day");
    private ArrayList<AccordionPanel> panelArray = new ArrayList<AccordionPanel>();
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
    private void initAccordion(){
        //build accordion
        mealAccordion = new Accordion();
        for (Meal m: mealList){
            AccordionPanel tmp = new AccordionPanel("Workout "+mealList.indexOf(m) +":",  new MealEditor(this, m));
            panelArray.add(tmp);
            mealAccordion.add(tmp);
        }
        add(mealAccordion);
        if (mealList.isEmpty()){
            add(noItems);
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
    @Override
    public int getUserID(){
        return userID;
    }
    @Override
    public void fetchData()
    {
       
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
