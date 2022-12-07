package com.example.demo;
import java.sql.*;
import java.rmi.server.RemoteObject;
import java.util.ArrayList;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;

public class MealView extends VerticalLayout implements Editor<Meal>{
    private ArrayList<Food> foodList = new ArrayList<Food>();
    private int userID;
    private Connection con;
    private ArrayList<Meal> mealList = new ArrayList<Meal>();
    private Accordion mealAccordion;
    private Button newMeal = new Button("New Meal");
    private Paragraph noItems = new Paragraph("No meals on this day");
    private ArrayList<AccordionPanel> panelArray = new ArrayList<AccordionPanel>();
    Date locDate;
    public MealView(Date d,int ID)
    {
        setAlignItems(FlexComponent.Alignment.CENTER);
        userID = ID;
        locDate = d;
        setup();
    }
    public void setup()
    {
        
        initConnection();
        setupAccordion();
        add(mealAccordion);
        noItems.setVisible(true);
        add(noItems);
        initButton();
        
    }
    private void setupAccordion(){
        //build accordion
        fetchData();
        
        mealAccordion = new Accordion();
        for (Meal m: mealList){
            AccordionPanel tmp = new AccordionPanel("Meal: "+mealList.indexOf(m) +":",  new MealEditor(this, m));
            panelArray.add(tmp);
            mealAccordion.add(tmp);
        }
        
        if (!mealList.isEmpty()){
            noItems.setVisible(false);
        }
        
    }
    private void initButton()
    {
        newMeal.addClickListener(clickEvent -> {
            AccordionPanel tmp = new AccordionPanel("Meal " + mealList.size() + ":", new MealEditor(this,locDate));
            panelArray.add(tmp);
            mealAccordion.add(tmp);

        });
        add(newMeal);
        noItems.setVisible(false);
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
            PreparedStatement query = con.prepareStatement("SELECT Meal_ID FROM Meal WHERE USer_ID = ? AND Day = ?;");
            query.setInt(1, userID);
            query.setDate(2, locDate);
            ResultSet rs = query.executeQuery();
            mealList = new ArrayList<Meal>();
            while(rs.next())
            {
                mealList.add(new Meal(rs.getInt(1)));
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public void deleteObject(Meal meal)
    {
        //delete panel in accordion
        
        AccordionPanel doomed = null;
        for (AccordionPanel maybeDoomed : panelArray){
            MealEditor tmp = (MealEditor) maybeDoomed.getContent().toArray()[0];
            if (tmp.getMealID() == meal.getID()){
                doomed = maybeDoomed;
                break;
            }
        }
        if (doomed != null){
            mealAccordion.remove(doomed);
        }
        mealList.remove(meal);

        try{
            PreparedStatement query1 = con.prepareStatement("DELETE FROM Meal WHERE Meal_ID = ? ;");
            query1.setInt(1,meal.getID());
            query1.executeUpdate();
            PreparedStatement query2 = con.prepareStatement("DELETE FROM FOOD_IS_PART_OF_MEAL WHERE MEAL_ID = ?");
            query2.setInt(1,meal.getID());
            query2.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();

        }
        if (mealList.isEmpty()){
            noItems.setVisible(true);;
        }

        
    
    }
    @Override
    public void addObject(Meal meal)
    {
        mealList.add(meal);
        fetchData();
    }
    public Date getDate()
    {
        return locDate;
    }
}
