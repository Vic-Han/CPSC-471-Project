package com.example.demo;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.*;
import com.vaadin.*;


//@Route("")
@Route("graph")
public class GraphScreen extends VerticalLayout{
    private VerticalLayout variables = new VerticalLayout();;
    private int num_of_variables = 0;
    public GraphScreen(){
        setupTitle();
        addVariables();
        add(variables);
        setupAddVariableButtons();
        setupExitButtons();
    }

    private void setupTitle(){
        H1 title = new H1("Graph");
        add(title);
    }
    private void addVariables(){
        if(num_of_variables > 2)
        {
            return;
        }
        HorizontalLayout default_variable = new HorizontalLayout();
        Button delete = new Button("-");
        delete.addClickListener(e ->  variables.remove(default_variable)  );
        delete.addClickListener(e -> num_of_variables--);
        default_variable.add(delete);
        ComboBox<String> metric = new ComboBox<>("Variable Name:");
        default_variable.add(metric);
        TextField y_axis = new TextField("Y axis max:");
        default_variable.add(y_axis);
        variables.add(default_variable);
        num_of_variables++;
    }
    /* 
    private void setupMetricInput(){
        HorizontalLayout metricLayout = new HorizontalLayout();//declare layout
            /*
            Example code for putting real objects in combobox (ie performance metric):
            ComboBox<Metric> comboBox = new ComboBox<>("Performance Metric");
            comboBox.setItems(SomeDatabaseClass.getMetrics());
            comboBox.setItemLabelGenerator(Metric::getName);
            add(comboBox);
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
*/
    private void setupAddVariableButtons(){
        Button addvar = new Button("Add Variable");//make both buttons
        addvar.addClickListener(e -> addVariables());
        add(addvar);
    }
    private void setupExitButtons(){
        HorizontalLayout lastRow = new HorizontalLayout();// horizontal layout for the graph and delete buttons
        Button graph = new Button("Graph");//make both buttons
        Button cancel = new Button("Cancel");

        lastRow.add(graph,cancel);//add all the buttons to layout
        add(lastRow);//add layout

    }
}
