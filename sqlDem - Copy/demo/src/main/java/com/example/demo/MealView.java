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

        userID = ID;
        locDate = d;
        setup();
    }
    public MealView()
    {
        //this(1);
    }
    public void setup()
    {
        
        initConnection();
        initAccordion();
        initButton();
        
    }
    private void initAccordion(){
        //build accordion
        mealAccordion = new Accordion();
        for (Meal m: mealList){
            AccordionPanel tmp = new AccordionPanel("Meal: "+mealList.indexOf(m) +":",  new MealEditor(this, m));
            panelArray.add(tmp);
            mealAccordion.add(tmp);
        }
        add(mealAccordion);
        if (mealList.isEmpty()){
            add(noItems);
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
        //delete panel in accordion
        AccordionPanel doomed = null;
        for (AccordionPanel maybeDoomed : panelArray){
            WorkoutEditor tmp = (WorkoutEditor) maybeDoomed.getContent().toArray()[0];
            if (tmp.getWorkoutID() == meal.getID()){
                doomed = maybeDoomed;
                break;
            }
        }
        if (doomed != null){
            mealAccordion.remove(doomed);
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
