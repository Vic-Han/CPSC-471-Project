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
    private Button newEx = new Button("New Exercise");
    private Button editEx = new Button("Edit Exercise");
    private Button delEx = new Button("Delete Exercise");
    ComboBox<Exercise> chooseEx = new ComboBox<Exercise>("Choose Exercise");
    public ExerciseView(int ID){
        userID = ID;
        setup();
        

    }
    public ExerciseView(){
        this(1);
    }
    private void setup(){
        
        initConnection();
        //make new metric button...
        newEx.addClickListener(clickEvent -> {
            //remove(current);
            Dialog d = new Dialog();
            d.add(new ExerciseEditor(this));
            d.open();
        });
        add(newEx);

        //make combobox...
        fetchData();
        chooseEx.setItemLabelGenerator(Exercise::getName);
        add(chooseEx);
        
        //make edit exercise button...
        editEx.addClickListener(clickEvent -> {
            Dialog d = new Dialog();
            d.add(new ExerciseEditor(this, chooseEx.getValue()));
            d.open();
        });
        add(editEx);
        delEx.addClickListener(clickEvent -> {deleteObject(chooseEx.getValue());});
        add(delEx);
        
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
    public void fetchData()
    {
         
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
            chooseEx.setItems(exList);
        }
        catch(SQLException e)
        {

        }
        

    }
    @Override
    public void addObject(Exercise exericse) {
        fetchData();
    }
    @Override
    public void deleteObject(Exercise exercise) {
        /* 
        try{
        metricList.remove(metric);
        PreparedStatement query1 = con.prepareStatement("DELETE FROM PERFORMANCE_METRIC WHERE Owner_ID = ? AND NAME = ? ;");
        query1.setInt(1,userID);
        query1.setString(2,metric.getName());
        query1.executeUpdate();
        PreparedStatement query2 = con.prepareStatement("DELETE FROM METRIC_DESCRIBES_EXERCISE WHERE Metric_user_ID = ? AND Metric_name = ?;");
        query2.setInt(1,userID);
        query2.setString(2,metric.getName());
        query2.executeUpdate();
        PreparedStatement query3 = con.prepareStatement("DELETE FROM METRIC MEASURES_SUBMISSION_WHERE Metric_owner_ID = ? AND Metric_name = ?;");
        query3.setInt(1,userID);
        query3.setString(2,metric.getName());
        query3.executeUpdate();
    
        }
        catch(SQLException e)
        {

        }

        fetchData();
        */
    }
    @Override
    public int getUserID() {
        return userID;
    }


}