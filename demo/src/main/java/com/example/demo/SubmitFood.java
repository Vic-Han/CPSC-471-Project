package com.example.demo;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;



//@Route("")
@Route("submitFood")
public class SubmitFood extends VerticalLayout{
    private String[] exampleFoods = {"Eggs", "Spinach", "Oats"};
    private String[] exampleUnits = {"ml", "g"};
    public SubmitFood(){
        setupTitle();
        setupMetricInput();
        addServings();
        setupExitButtons();
    }

    private void setupTitle(){
        H1 title = new H1("Submit Food");
        add(title);
    }
  
    private void setupMetricInput(){
        HorizontalLayout foodLayout = new HorizontalLayout();//declare layout
            /*
            Example code for putting real objects in combobox (ie performance metric):
            ComboBox<Metric> comboBox = new ComboBox<>("Performance Metric");
            comboBox.setItems(SomeDatabaseClass.getMetrics());
            comboBox.setItemLabelGenerator(Metric::getName);
            add(comboBox);*/
        ComboBox<String> name = new ComboBox<>("Name:");//declare combobox
        name.setItems(exampleFoods);//put array of objects (Strings in this case) into combobox
        Button editFood = new Button("Edit Food");//declare button
        Button newFood = new Button("New Food");//declare another button
        editFood.addClickListener(e ->//set up button as a link to metric editor...
        editFood.getUI().ifPresent(ui ->
           ui.navigate("foodEdit"))
        );
        foodLayout.setAlignItems(Alignment.END);//align buttons to bottom of layout
        foodLayout.add(name,editFood,newFood);//add combobox and buttons to layout
        add(foodLayout);//add layout to screen
    }

    private void addServings(){
        
        HorizontalLayout metric = new HorizontalLayout();
        TextField unitValue = new TextField("Quanity");
        ComboBox<String> unit = new ComboBox<>("Unit:");//declare combobox
        unit.setItems(exampleUnits);
        metric.add(unitValue);
        metric.add(unit);
        add(metric);
        
    }
    private void setupExitButtons(){
        HorizontalLayout lastRow = new HorizontalLayout();//declare layout
        Button submit = new Button("Submit");//declaring buttons...
        Button cancel = new Button("Cancel");
        Button delete = new Button("Delete");
        lastRow.add(submit,cancel,delete);//add all the buttons to layout
        add(lastRow);//add layout

    }
}