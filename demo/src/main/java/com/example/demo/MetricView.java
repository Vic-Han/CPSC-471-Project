package com.example.demo;
import java.sql.*;
import java.rmi.server.RemoteObject;
import java.util.ArrayList;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
public class MetricView extends VerticalLayout implements Editor<Metric>{
    private ArrayList<Metric> metricList = new ArrayList<Metric>();
    private int userID;

    private Button newMet = new Button("New Metric");
    private Button editMet = new Button("Edit Metric");
    private Button delMet = new Button("Delete Metric");
    ComboBox<Metric> chooseMet = new ComboBox<Metric>("Choose metric");
    public MetricView(){
        setup();
    }
    private void setup(){
        //make new metric button...
        newMet.addClickListener(clickEvent -> {
            MetricEditor editor = new MetricEditor(this);
            editor.open();
        });
        add(newMet);

        //make combobox...
        fetchData();
        chooseMet.setItemLabelGenerator(Metric::getName);
        add(chooseMet);

        //make edit exercise button...
        editMet.addClickListener(clickEvent -> {
            MetricEditor editor = new MetricEditor(this,chooseMet.getValue());
            editor.open();
        });
        add(editMet);
        delMet.addClickListener(clickEvent -> {deleteObject(chooseMet.getValue());});
    }
    @Override
    public void fetchData(){
<<<<<<< HEAD
        /* 
        PreparedStatement query1 = con.prepareStatement("SELECT Metric_Name FROM  PERFORMAMCE_METRIC WHERE Owner_ID = ? ;");
=======
        PreparedStatement query1 = com.prepareStatement("SELECT Metric_Name FROM  PERFORMAMCE_METRIC WHERE Owner_ID = ? ;");
>>>>>>> 89bb101648b9813c488013de0c9ab7a0b63838bb
        query1.setInt(1,userID);
        */


        chooseMet.setItems(metricList);
    }
    @Override
    public void addObject(Metric metric) {
        metricList.add(metric);
        fetchData();
    }
    @Override
    public void deleteObject(Metric metric) {
        /*
        metricList.remove(metric);
        PreparedStatement query1 = c.prepareStatement("DELETE FROM PERFORMANCE_METRIC WHERE Owner_ID = ? AND NAME = ? ;");
        query1.setInt(1,userID);
        query1.setString(2,metric.getName());
        PreparedStatement query2 = c.prepareStatement("DELETE FROM METRIC_DESCRIBES_EXERCISE WHERE Metric_user_ID = ? AND Metric_name = ?;");
        query2.setInt(1,userID);
        query2.setString(2,metric.getName());
        PreparedStatement query3 = c.prepareStatement("DELETE FROM METRIC MEASURES_SUBMISSION_WHERE Metric_owner_ID = ? AND Metric_name = ?;");
        query3.setInt(1,userID);
        query3.setString(2,metric.getName());
        // run queries
        */

        fetchData();
    }
    @Override
    public int getUserID() {
        return userID;
    }


}