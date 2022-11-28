package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class WorkoutEditor extends VerticalLayout implements Editor<ExerciseSubmission>{
    private Connection con;

    private int userID;
    private Editor<Workout> parent;
    private ArrayList<ExerciseSubmission> submissionList;
    private Workout workout;
    private Grid<ExerciseSubmission> grid;

    public WorkoutEditor(Editor<Workout> parent){
        initConnection();
        this.parent = parent;
        this.userID = parent.getUserID();
        this.workout = new Workout(userID);
        parent.addObject(workout);
        submissionList = new ArrayList<ExerciseSubmission>();
        initGrid();
        initAddSubmission();
    }
    public WorkoutEditor(Editor<Workout> parent, Workout workout){
        initConnection();
        this.parent = parent;
        this.userID = parent.getUserID();
        this.workout = workout;
        try{
            submissionList = workout.getSubmissionList();
        }
        catch(SQLException e){
            e.printStackTrace();
            Dialog d = new Dialog();
            d.add(new Paragraph("Error connecting user interface to database ..workout:Workout shat the bed fetching submissionlist"));
            d.open();
        }
        initGrid();
        initAddSubmission();
        
    }
    private void initAddSubmission(){
        Button addSubmission = new Button("Add exercise");
        addSubmission.addClickListener(ClickEvent ->{
            Dialog d= new Dialog();
            d.add(new SubmitExercise(this));
            d.open();
        });
        add(addSubmission);
    }
    private void initGrid(){
        Grid<ExerciseSubmission> grid = new Grid<>(ExerciseSubmission.class, false);
            
        grid.setItems(submissionList);
        //add submission printout
        grid.addComponentColumn(s ->{
            String exName = new String("Exercise: " + s.getExercise().getName() + "\n");
            String paraContent = new String("Results: \n");
            ArrayList<MetricPair> statsList = s.getMetricList();
            for (MetricPair m : statsList){
                paraContent += (m.getMetric().getName() + ": " + m.getVal() + " " + m.getMetric().getUnit()+ "\n");
            }
            Paragraph para = new Paragraph(exName + paraContent);
            return para;
        });
        //add context menu
        grid.addComponentColumn(m -> {
            MenuBar menuBar = new MenuBar();
            menuBar.addThemeVariants(MenuBarVariant.LUMO_TERTIARY);
            MenuItem menuItem = menuBar.addItem("•••");
            menuItem.getElement().setAttribute("aria-label", "More options");
            SubMenu subMenu = menuItem.getSubMenu();
            subMenu.addItem("Edit", event -> {
                Dialog d = new Dialog();
                d.add(new SubmitExercise(this, m));
                grid.setItems(submissionList);
            });
            subMenu.addItem("Delete", event -> {
                this.deleteObject(m);
                grid.setItems(submissionList);
            });
            return menuBar;
        }).setWidth("70px").setFlexGrow(0);
        add(grid);
    }
    private void initConnection()
    {
         
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost/tracker", "athlete", "cpsc");
            
        } catch (SQLException e) {
            e.printStackTrace();
            Dialog d = new Dialog();
            d.add(new Paragraph("Error connecting user interface to database ..and yes we out here violating design patterns"));
            d.open();
        }   
    }

    @Override
    public void addObject(ExerciseSubmission obj) {
        submissionList.add(obj);
        workout.addSubmission(obj);
        grid.setItems(submissionList);
    }

    @Override
    public void deleteObject(ExerciseSubmission obj) {
        submissionList.remove(obj);
        workout.removeSubmission(obj);
        grid.setItems(submissionList);
        
    }

    @Override
    public void fetchData() {
        try{
            submissionList = workout.getSubmissionList();
        }
        catch(SQLException e){
            e.printStackTrace();
            Dialog d = new Dialog();
            d.add(new Paragraph("Error connecting user interface to database ..workout:Workout shat the bed fetching submissionlist"));
            d.open();
        }
        grid.setItems(submissionList);

        
    }

    @Override
    public int getUserID() {
        return userID;
    }
    
}
