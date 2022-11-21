package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import java.sql.*;

public class MetricEditor extends Dialog{

    //Note: This class does not actually save metric to an exercise.
    //if constructed with arraylist of metrics, it will add itself to that arraylist upon submission
    // keys and backend classes
    private int userID;
    private Metric metric;
    private Editor<Metric> parent;

    //component declarations:
    private VerticalLayout layout = new VerticalLayout();
    private Grid<Exercise> exerciseGrid;
    TextField nameField;
    TextField unitField;

    public MetricEditor(){
        setupTitle();//all of these add to the verticalLayout, not the actual dialog
        setupNameInput();
        setupUnitInput();
        //setupExView();
        setupExitButtons();
        addLayout();//add all the contents to dialog
    }
    public MetricEditor(Editor<Metric> parentEditor, Metric metric){
        this();
        parent = parentEditor;
        userID = parent.getUserID();
    }
    public MetricEditor(Editor<Metric> parentEditor){
        this(parentEditor,new Metric(parentEditor.getUserID()));
    }
    private void setupTitle(){
        H1 title = new H1("Metric Editor");
        layout.add(title);
    }
    private void setupNameInput(){
        nameField = new TextField("Metric name:");
        layout.add(nameField);
    }
    
    private void setupUnitInput(){
        unitField = new TextField("Unit of measurement:");
        layout.add(unitField);
    }
    /* 
    private void setupExView(){
        exerciseGrid = new Grid<>(Exercise.class, false);
        exerciseGrid.addColumn(Exercise::getName).setHeader("Linked exercises:")
            .setAutoWidth(true).setFlexGrow(0);
        exerciseList = new ArrayList<Exercise>();
        //setup testing metrics...
            exerciseList.add(new Exercise("Ruck"));
            exerciseList.add(new Exercise("Run"));
        //done setting up example
        exerciseGrid.setItems(exerciseList);
        exerciseGrid.setAllRowsVisible(true);
        layout.add(exerciseGrid);
    }*/
    private void setupExitButtons(){
        HorizontalLayout lastRow = new HorizontalLayout();//declare layout
        Button submit = new Button("Submit", e-> submit());//declaring buttons...
        Button delete = new Button("Delete");
        lastRow.add(submit,delete);//add all the buttons to layout
        layout.add(lastRow);//add layout
    }
    private void addLayout(){
        add(layout);
    }
    private void submit(){
        metric.update(nameField.getValue(),unitField.getValue());
        parent.addObject(metric);
        this.close();
    }
    private void delete()
    {
        parent.deleteObject(metric);
    }
}
