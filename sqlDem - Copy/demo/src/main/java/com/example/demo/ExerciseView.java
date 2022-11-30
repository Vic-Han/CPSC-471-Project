package com.example.demo;
import java.sql.*;
import java.rmi.server.RemoteObject;
import java.util.ArrayList;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
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
    Dialog editor = new Dialog();
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
        //init new ex
        newEx.addClickListener(clickEvent -> {
            //remove(current);
            Dialog d = new Dialog();
            d.add(new ExerciseEditor(this));
            d.open();
        });
        add(newEx);

        //init edit ex
        editEx.addClickListener(clickEvent -> {
            
            editor.add(new ExerciseEditor(this, chooseEx.getValue()));
            editor.open();
        });
        add(editEx);

        //init delete ex
        delEx.addClickListener(clickEvent -> {deleteObject(chooseEx.getValue());});
        add(delEx);

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
    public void fetchData(){}
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