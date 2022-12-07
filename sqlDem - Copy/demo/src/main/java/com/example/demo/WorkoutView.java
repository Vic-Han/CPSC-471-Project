package com.example.demo;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

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
public class WorkoutView extends VerticalLayout implements Editor<Workout>{
    private int userID;
    private LocalDate date;
    private ArrayList<Workout> workouts = new ArrayList<Workout>();

    private Accordion accordion;
    private ArrayList<AccordionPanel> panelArray = new ArrayList<AccordionPanel>();
    private Paragraph noItems = new Paragraph("No workouts on this day");

    private Connection con;
    public WorkoutView(LocalDate date, int ID)
    {
        setAlignItems(FlexComponent.Alignment.CENTER);
        this.date = date;
        userID = ID;
        initConnection();
        fetchData();
        initAccordion();
        initAddButton();
        userID = ID;
    }
   
    private void initAddButton() {
        //add buttons
        Button addWorkout = new Button("Add Workout");
        addWorkout.addClickListener(clickEvent -> {
            AccordionPanel tmp = new AccordionPanel("Workout " + workouts.size() + ":", new WorkoutEditor(this,date));
            panelArray.add(tmp);
            accordion.add(tmp);
            noItems.setVisible(false);;
        });
        add(addWorkout);
    }

    private void initAccordion(){
        //build accordion
        accordion = new Accordion();
        accordion.setWidth("700px");
        for (Workout wo: workouts){
            AccordionPanel tmp = new AccordionPanel("Workout "+workouts.indexOf(wo) +":",  new WorkoutEditor(this, wo));
            panelArray.add(tmp);
            accordion.add(tmp);
        }
        add(accordion);
        if (workouts.isEmpty()){
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
        workouts = new ArrayList<Workout>();
        try{
            PreparedStatement query1 = con.prepareStatement("SELECT Workout_ID FROM WORKOUT WHERE Day = ? AND User_ID = ?;");
            query1.setDate(1, Date.valueOf(date));
            query1.setInt(2, userID);
            ResultSet rs = query1.executeQuery();
            while (rs.next()){
                workouts.add(new Workout(rs.getInt(1), userID));
            }
            
        }
        catch(SQLException e){
            e.printStackTrace();
            Dialog d = new Dialog();
            Paragraph p = new Paragraph("Error fetching workouts for this day in database");
            d.add(p);
            d.open();
        }
        
    }
    @Override
    public void deleteObject(Workout workout)
    {
        //delete panel in accordion
        AccordionPanel doomed = null;
        for (AccordionPanel maybeDoomed : panelArray){
            WorkoutEditor tmp = (WorkoutEditor) maybeDoomed.getContent().toArray()[0];
            if (tmp.getWorkoutID() == workout.getID()){
                doomed = maybeDoomed;
                break;
            }
        }
        if (doomed != null){
            accordion.remove(doomed);
        }
        if (panelArray.size() == 0){
            noItems.setVisible(true);
        }
    }
    @Override
    public void addObject(Workout workout)
    {

    }

}
