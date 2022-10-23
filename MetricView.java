package com.example.demo;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class MetricView extends HorizontalLayout{
    private Metric[] exampleArr = {new Metric("Pace", "mins/km"), new Metric("Distance", "km")};
    public MetricView(){
        setup();
    }
    private void setup(){
        //make new metric button...
        Button newMet = new Button("New Metric");
        newMet.addClickListener(clickEvent -> {
            MetricEditor editor = new MetricEditor();
            editor.open();
        });
        add(newMet);
        //make combobox...
        ComboBox<Metric> chooseMet = new ComboBox<Metric>("Choose metric");
        chooseMet.setItems(exampleArr);
        chooseMet.setItemLabelGenerator(Metric::getName);
        add(chooseMet);
        //make edit exercise button...
        Button editMet = new Button("Edit Metric");
        editMet.addClickListener(clickEvent -> {
            ExistingMetricEditor editor = new ExistingMetricEditor(chooseMet.getValue());
            editor.open();
        });
        add(editMet);
    }
    

}
