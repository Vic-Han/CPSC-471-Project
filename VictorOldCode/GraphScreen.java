package com.example.demo;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.datepicker.DatePicker;
//import com.vaadin.flow.component.label;

//@Route("")
@Route("graph")
public class GraphScreen extends VerticalLayout{
    private VerticalLayout variables = new VerticalLayout();;
    private int num_of_variables = 0;
    private HorizontalLayout timeRange;
    DatePicker start,end;
    public GraphScreen(){
        setupTitle();
        addVariables();
        add(variables);
        setupAddVariableButtons();
        setupTimeRange();
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
    private void setupTimeRange(){
        timeRange = new HorizontalLayout();
        start = new DatePicker();
        end = new DatePicker();
        //timeRange.add(new Label("Start"));
        timeRange.add(start);
        //timeRange.add(new Label("Start"));
        timeRange.add(end);

        add(timeRange);
    }
}
