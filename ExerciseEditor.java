package com.example.demo;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;




@Route("")
@RouteAlias("exerciseEdit")
public class ExerciseEditor extends VerticalLayout{
    private String[] exampleMetrics = {"Pace", "Distance", "Elevation"};
    public ExerciseEditor(){
        setupTitle();
        setupNameInput();
        setupMetricInput();
        setupExitButtons();
    }

    private void setupTitle(){
        H1 title = new H1("Exercise Editor");
        add(title);
    }
    private void setupNameInput(){
        TextField nameField = new TextField("Name:");
        add(nameField);
    }
    private void setupMetricInput(){
        HorizontalLayout metricLayout = new HorizontalLayout();//declare layout
            /*
            Example code for putting real objects in combobox (ie performance metric):

            ComboBox<Metric> comboBox = new ComboBox<>("Performance Metric");
            comboBox.setItems(SomeDatabaseClass.getMetrics());
            comboBox.setItemLabelGenerator(Metric::getName);
            add(comboBox);*/
        ComboBox<String> metrics = new ComboBox<>("Performance Metric:");//declare combobox
        metrics.setItems(exampleMetrics);//put array of objects (Strings in this case) into combobox
        Button editMetric = new Button("Edit Metric");//declare button
        Button newMetric = new Button("New Metric");//declare another button
        newMetric.addClickListener(e ->//set up button as a link to metric editor...
        newMetric.getUI().ifPresent(ui ->
           ui.navigate("metricEdit"))
        );
        metricLayout.setAlignItems(Alignment.END);//align buttons to bottom of layout
        metricLayout.add(metrics,editMetric,newMetric);//add combobox and buttons to layout
        add(metricLayout);//add layout to screen
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
