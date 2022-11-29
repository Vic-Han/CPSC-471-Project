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
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
public class WorkoutView extends VerticalLayout implements Editor<Workout>{
    private int userID;
    private LocalDate date;
    private ArrayList<Workout> workouts;

    private Accordion accordion;
    private Paragraph noItems = new Paragraph("No workouts on this day");

    private Connection con;
    public WorkoutView(LocalDate date, int ID)
    {
        this.date = date;
        userID = ID;
        initConnection();
        fetchData();
        initAccordion();
        userID = ID;
    }
   
    private void initAccordion(){
        //build accordion
        if (!workouts.isEmpty()){
            accordion = new Accordion();
            for (Workout wo: workouts){
                accordion.add("Workout "+workouts.indexOf(wo) +":",  new WorkoutEditor(this, wo));
            }
            add(accordion);
            //add buttons
            Button addWorkout = new Button("Add Workout");
            addWorkout.addClickListener(clickEvent -> {
                //open new editor in dialog, refresh accordion upon close and make noItems = ""
            });
            add(addWorkout);
        }
        else{
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
            PreparedStatement query1 = con.prepareStatement("SELECT Workout_ID FROM WORKOUT WHERE Day = ? AND Day_owner_ID = ?;");
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
        
    }
    @Override
    public void addObject(Workout workout)
    {

    }

}
