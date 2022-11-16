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
@Route("submitEx")
public class SubmitExercise extends VerticalLayout{
    private String[] exampleMetrics = {"Run", "Bike", "Swim"};
    private VerticalLayout metrics = new VerticalLayout(); 
    public SubmitExercise(){
        setupTitle();
        setupMetricInput();
        setupContinue();
        add(metrics);
        setupExitButtons();
    }

    private void setupTitle(){
        H1 title = new H1("Submit Exercise");
        add(title);
    }
  
    private void setupMetricInput(){
        HorizontalLayout exerciseLayout = new HorizontalLayout();//declare layout
            /*
            Example code for putting real objects in combobox (ie performance metric):
            ComboBox<Metric> comboBox = new ComboBox<>("Performance Metric");
            comboBox.setItems(SomeDatabaseClass.getMetrics());
            comboBox.setItemLabelGenerator(Metric::getName);
            add(comboBox);*/
        ComboBox<String> name = new ComboBox<>("Name:");//declare combobox
        name.setItems(exampleMetrics);//put array of objects (Strings in this case) into combobox
        Button editExercise = new Button("Edit Exericise");//declare button
        Button newExercise = new Button("New Exericise");//declare another button
        editExercise.addClickListener(e ->//set up button as a link to metric editor...
        editExercise.getUI().ifPresent(ui ->
           ui.navigate("exerciseEdit"))
        );
        exerciseLayout.setAlignItems(Alignment.END);//align buttons to bottom of layout
        exerciseLayout.add(name,editExercise,newExercise);//add combobox and buttons to layout
        add(exerciseLayout);//add layout to screen
    }
    private void setupContinue(){
        Button cont = new Button("continue");
        cont.addClickListener(e-> addRestOfScreen());
        add(cont);
    }
    private void addRestOfScreen(){
        //metrics = new VerticalLayout();
        
        HorizontalLayout metric = new HorizontalLayout();
        TextField metricName = new TextField("metric");
        TextField metricValue = new TextField("Value");
        metric.add(metricName);
        metric.add(metricValue);
        metrics.add(metric);
        
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