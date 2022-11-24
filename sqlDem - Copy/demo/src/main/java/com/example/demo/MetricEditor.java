package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import java.sql.*;
//@Route("")
public class MetricEditor extends Dialog{

    //Note: This class does not actually save metric to an exercise.
    //if constructed with arraylist of metrics, it will add itself to that arraylist upon submission
    // keys and backend classes
    private int userID;
    private Metric metric;
    private Editor<Metric> parent;
    private Connection con;
    //component declarations:
    private VerticalLayout layout = new VerticalLayout();
    //private Grid<Exercise> exerciseGrid;
    TextField nameField;
    TextField unitField;

    public MetricEditor(){
        setupTitle();//all of these add to the verticalLayout, not the actual dialog
        setupNameInput();
        setupUnitInput();
        initConnection();
        //setupExView();
        setupExitButtons();
        addLayout();//add all the contents to dialog
    }
    public MetricEditor(Editor<Metric> parentEditor, Metric inputMetric){
        parent = parentEditor;
        userID = parent.getUserID();
        metric = inputMetric;
        setupTitle();//all of these add to the verticalLayout, not the actual dialog
        setupNameInput();
        setupUnitInput();
        initConnection();
        //setupExView();
        setupExitButtons();
        addLayout();//add all the contents to dialog
    }
    public MetricEditor(Editor<Metric> parentEditor){
        this(parentEditor,new Metric(parentEditor.getUserID()));
    }
    public void initConnection()
    {
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost/tracker", "athlete", "cpsc");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    private void setupExitButtons(){
        HorizontalLayout lastRow = new HorizontalLayout();//declare layout
        Button submit = new Button("Submit");//declaring buttons...
        Button delete = new Button("Delete");
        submit.addClickListener(ClickEvent -> {submit();});
        delete.addClickListener(clickEvent ->{delete();});
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
        this.close();
    }
}