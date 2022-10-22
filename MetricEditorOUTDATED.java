package com.example.demo;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
@Route("metricEdit")
public class MetricEditor extends VerticalLayout{
    private String[] exampleExercises = {"Run", "Ruck", "Squat", "Bench press" };
    public MetricEditor(){
        setupTitle();
        setupNameInput();
        setupExInput();
        setupUnitInput();
        setupExitButtons();
    }
    private void setupTitle(){
        H1 title = new H1("Metric Editor");
        add(title);
    }
    private void setupNameInput(){
        TextField nameField = new TextField("Metric name:");
        add(nameField);
    }
    private void setupExInput(){
        ComboBox<String> exerciseField = new ComboBox<>("Related Exercise:");//declare combobox
        exerciseField.setItems(exampleExercises);//put array of objects (Strings in this case) into combobox
        add(exerciseField);
    }
    private void setupUnitInput(){
        TextField unitField = new TextField("Unit of measurement:");
        add(unitField);
    }
    private void setupExitButtons(){
        HorizontalLayout lastRow = new HorizontalLayout();//declare layout
        Button submit = new Button("Submit");//declaring buttons...
        Button cancel = new Button("Cancel");
        cancel.addClickListener(e ->//set up button as a link to metric editor...
        cancel.getUI().ifPresent(ui ->
           ui.navigate("exerciseEdit"))
        );
        Button delete = new Button("Delete");
        lastRow.add(submit,cancel,delete);//add all the buttons to layout
        add(lastRow);//add layout
    }
}
