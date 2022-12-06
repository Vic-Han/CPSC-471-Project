package com.example.demo;
import java.sql.*;
import java.rmi.server.RemoteObject;
import java.util.ArrayList;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin.Horizontal;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//@Route("ExView")
public class ExerciseView extends VerticalLayout implements Editor<Exercise>{
    private ArrayList<Exercise> exList = new ArrayList<Exercise>();
    private int userID;
    private Connection con;
    Dialog editor;
    private Button newEx = new Button("New Exercise");
    private Button editEx = new Button("Edit Exercise");
    private Button delEx = new Button("Delete Exercise");
    ComboBox<Exercise> chooseEx = new ComboBox<Exercise>("Choose Exercise");
    public ExerciseView(int ID){
        initConnection();
        userID = ID;
        initCB();
        initButtons();
    }
    private void initButtons() {
        HorizontalLayout buttons = new HorizontalLayout();
        //init new ex
        newEx.addClickListener(clickEvent -> {
            editor = new Dialog();
            editor.add(new ExerciseEditor(this));
            editor.open();
        });
        buttons.add(newEx);

        //init edit ex
        editEx.addClickListener(clickEvent -> {
            editor = new Dialog();
            editor.add(new ExerciseEditor(this, chooseEx.getValue()));
            editor.open();
        });
        buttons.add(editEx);

        //init delete ex
        delEx.addClickListener(clickEvent -> {deleteObject(chooseEx.getValue());});
        buttons.add(delEx);

        add(buttons);

    }
    private void initCB() {
        try
        {
            PreparedStatement query = con.prepareStatement("SELECT Name FROM EXERCISE WHERE User_ID = ?;");
            query.setInt(1, userID);
            ResultSet rs = query.executeQuery();
            exList = new ArrayList<Exercise>();
            while(rs.next())
            {
                exList.add(new Exercise(rs.getString(1),userID));
            }  
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        chooseEx.setItems(exList);
        chooseEx.setItemLabelGenerator(Exercise::getName);
        add(chooseEx);
    }
    /*public ExerciseView(){
        this(1);
    }*/
    
    public void initConnection()
    {
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost/tracker", "athlete", "cpsc");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }   
    }
    @Override
    public void fetchData(){
        editor.close();
    }
    @Override
    public void addObject(Exercise exercise) {
        exList.add(exercise);
        chooseEx.setItems(exList);
        editor.close();
    }
    @Override
    public void deleteObject(Exercise exercise) {
        exList.remove(exercise);
        chooseEx.setItems(exList);
        try{
            PreparedStatement query4 = con.prepareStatement("SELECT submission_id FROM EXERCISE_submission WHERE User_ID = ? AND Exercise_Name = ?;");
            query4.setInt(1,userID);
            query4.setString(2,exercise.getName());
            ResultSet subIDset = query4.executeQuery();
            while (subIDset.next()){
                PreparedStatement query = con.prepareStatement("Delete FROM metric_measures_submission WHERE submission_ID = ? ;");
                query.setInt(1,subIDset.getInt(1));
                query.executeUpdate();
                PreparedStatement query6 = con.prepareStatement("Delete FROM EXERCISE_submission WHERE submission_ID = ? ;");
                query6.setInt(1,subIDset.getInt(1));
                query6.executeUpdate();
            }
            PreparedStatement query2 = con.prepareStatement("DELETE FROM Metric_describes_EXERCISE WHERE Metric_user_ID = ? AND Exercise_Name = ?;");
            query2.setInt(1,userID);
            query2.setString(2,exercise.getName());
            query2.executeUpdate();
            PreparedStatement query1 = con.prepareStatement("DELETE FROM EXERCISE WHERE User_ID = ? AND Name = ?;");
            query1.setInt(1,userID);
            query1.setString(2,exercise.getName());
            query1.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    @Override
    public int getUserID() {
        return userID;
    }


}