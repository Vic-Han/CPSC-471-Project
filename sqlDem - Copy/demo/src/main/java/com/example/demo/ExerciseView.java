package com.example.demo;
import java.util.ArrayList;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import java.sql.*;
import com.vaadin.flow.router.Route;
public class ExerciseView extends VerticalLayout implements Editor<Exercise>{
    private HorizontalLayout editOrCreate;
    private VerticalLayout editorLayout;
    private int userID;
    private ArrayList<Exercise> exerciseList = new ArrayList<Exercise>();
    private Connection con;
    private Button newEx = new Button("New Exercise");
    private Button editEx = new Button("Edit Exercise");
    private Button delEx = new Button("Delete Exercise");
    private ComboBox<Exercise> chooseEx = new ComboBox<Exercise>("Choose exercise");
    public ExerciseView(int ID){
        setupEOCLayout();
        userID = ID;
        initConnection();
        //setupEditorLayout();
    }
    private void setupEOCLayout(){
        editOrCreate = new HorizontalLayout();
        //make new exercise button...
        newEx.addClickListener(clickEvent -> {
            ExerciseEditor editor = new ExerciseEditor(this);
            add(editor);
            // route alias?
        });
        editOrCreate.add(newEx);
        //make combobox...
        fetchData();
        chooseEx.setItemLabelGenerator(Exercise::getName);
        editOrCreate.add(chooseEx);
        //make edit exercise button...
        editEx.addClickListener(clickEvent -> {
            ExerciseEditor editor = new ExerciseEditor(this,chooseEx.getValue());
            add(editor);
        });
        editOrCreate.add(editEx);

        delEx.addClickListener(ClickEvent -> {
            deleteObject(chooseEx.getValue());
        });
        editOrCreate.add(delEx);
        add(editOrCreate);
    }
    public void initConnection()
    {
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost/tracker", "athlete", "cpsc");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /* 
    private void setupEditorLayout(){
        editorLayout = new VerticalLayout();
        add(editorLayout);
    }
    private void refreshEditorLayout(){
        editorLayout.removeAll();
        editorLayout.add(new ExerciseEditor());
    }
    private void refreshEditorLayout(Exercise ex){
        editorLayout.removeAll();
        editorLayout.add(new ExerciseEditor());//should add ExistingExerciseEditor but that doesn't exist yet
        editorLayout.add(new Paragraph("Should be editting "+ ex.getName()));
    }
    */
    @Override
    public void fetchData(){
        try
        {
            PreparedStatement query1 = con.prepareStatement("SELECT Name FROM  EXERCISE WHERE Owner_ID = ? ;");
            query1.setInt(1,userID);
            query1.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        chooseEx.setItems(exerciseList);
    }
    @Override
    public void addObject(Exercise exercise) {
        exerciseList.add(exercise);
        fetchData();
    }
    @Override
    public void deleteObject(Exercise exercise) {
        
        exerciseList.remove(exercise);
        try{
            PreparedStatement query1 = con.prepareStatement("DELETE FROM EXERCISE WHERE User_ID = ? AND NAME = ? ;");
            query1.setInt(1,userID);
            query1.setString(2,exercise.getName());
            query1.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        try
        {
            PreparedStatement query2 = con.prepareStatement("DELETE FROM METRIC_DESCRIBES_EXERCISE WHERE Metric_user_ID = ? AND Exercise_name = ?;");
            query2.setInt(1,userID);
            query2.setString(2,exercise.getName());
            query2.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        try
        {
            PreparedStatement query3 = con.prepareStatement("DELETE FROM EXERCISE_SUBMISSION User_ID = ? AND Exercise_name = ?;");
            query3.setInt(1,userID);
            query3.setString(2,exercise.getName());
            query3.executeUpdate();
            }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        fetchData();
        return;
    }
    @Override
    public int getUserID() {
        return userID;
    }
}