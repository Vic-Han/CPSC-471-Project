package com.example.demo;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class MetricView implements Editor<Metric>{
    private Metric[] exampleArr = {new Metric("Pace", "mins/km"), new Metric("Distance", "km")};
    private int userID;
    public MetricView(){
        setup();
    }
    private void setup(){
        //make new metric button...
        Button newMet = new Button("New Metric");
        newMet.addClickListener(clickEvent -> {
            MetricEditor editor = new MetricEditor(userID);
            editor.open();
        });
        add(newMet);
        //make combobox...
        ComboBox<Metric> chooseMet = new ComboBox<Metric>("Choose metric");
        chooseMet.setItems(exampleArr);
        chooseMet.setItemLabelGenerator(Metric::getName);
        Metric selectedMetric = chooseMet.getItemAt(chooseMet.getSelectedIndex());

        add(chooseMet);
        //make edit exercise button...
        Button editMet = new Button("Edit Metric");
        editMet.addClickListener(clickEvent -> {
            MetricEditor editor = new ExistingMetricEditor(userID,selectedMetric);
            editor.open();
        });
        add(editMet);
    }
    

}