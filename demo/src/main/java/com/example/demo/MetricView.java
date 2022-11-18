package com.example.demo;

import java.rmi.server.RemoteObject;
import java.util.ArrayList;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class MetricView implements Editor<Metric>{
    private ArrayList<Metric> metricList = {new Metric("Pace", "mins/km"), new Metric("Distance", "km")};
    private int userID;
    public MetricView(){
        setup();
    }
    private void setup(){
        //make new metric button...
        Button newMet = new Button("New Metric");
        newMet.addClickListener(clickEvent -> {
            MetricEditor editor = new MetricEditor(this);
            editor.open();
        });
        add(newMet);
        //make combobox...
        ComboBox<Metric> chooseMet = new ComboBox<Metric>("Choose metric");
        chooseMet.setItems(metricList);
        chooseMet.setItemLabelGenerator(Metric::getName);
        Metric selectedMetric = chooseMet.getItemAt(chooseMet.getSelectedIndex());

        add(chooseMet);
        //make edit exercise button...
        Button editMet = new Button("Edit Metric");
        editMet.addClickListener(clickEvent -> {
            MetricEditor editor = new ExistingMetricEditor(this,selectedMetric);
            editor.open();
        });
        add(editMet);
    }
    fetchData(){

    }
    @Override
    public void addObject(Metric metric) {
        metricList.add(metric);
        
    }
    @Override
    public void deleteObject(Metric metric) {
        
        metricList.remove(metric);
        PreparedStatement query1 = c.prepareStatement("DELETE FROM PERFORMANCE_METRIC WHERE Owner_ID = ? AND NAME = ? ;");
        query1.setInt(1,userID);
        query1.setString(2,metric.getName());
        PreparedStatement query2 = c.prepareStatement("DELETE FROM METRIC_DESCRIBES_EXERCISE WHERE Metric_user_ID = ? AND Metric_name = ?;");
        query2.setInt(1,userID);
        query2.setString(2,metric.getName());
        PreparedStatement query3 = c.prepareStatement("DELETE FROM METRIC MEASURES_SUBMISSION_WHERE Metric_owner_ID = ? AND Metric_name = ‘MetricName’;");
        query3.setString(1,name);
        query3.setString(2,exName);
        // run queries
    }
    @Override
    public int getUserID() {
        return userID;
    }


}