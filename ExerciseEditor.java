package com.example.demo;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;




@Route("")
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
        HorizontalLayout metricLayout = new HorizontalLayout();
            /*
            Example code for putting real objects in combobox (ie performance metric):

            ComboBox<Metric> comboBox = new ComboBox<>("Performance Metric");
            comboBox.setItems(SomeDatabaseClass.getMetrics());
            comboBox.setItemLabelGenerator(Metric::getName);
            add(comboBox);*/
        ComboBox<String> metrics = new ComboBox<>("Performance Metric:");
        metrics.setItems(exampleMetrics);
        Button editMetric = new Button("Edit Metric");
        Button newMetric = new Button("New Metric");
        metricLayout.setAlignItems(Alignment.END);
        metricLayout.add(metrics,editMetric,newMetric);
        add(metricLayout);
    }
    private void setupExitButtons(){
        HorizontalLayout lastRow = new HorizontalLayout();
        Button submit = new Button("Submit");
        Button cancel = new Button("Cancel");
        Button delete = new Button("Delete");
        lastRow.add(submit,cancel,delete);
        add(lastRow);

    }
}
