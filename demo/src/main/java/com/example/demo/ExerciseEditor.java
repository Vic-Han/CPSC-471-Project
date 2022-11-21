package com.example.demo;
import com.vaadin.flow.router.Route;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;




@Route("")
public class ExerciseEditor extends VerticalLayout implements Editor<Metric>{
    private ArrayList<Metric> allMetricList;
    private ArrayList<Metric> exMetricList;
    private Grid<Metric> metricGrid;
    private Editor<Exercise> parent;
    Exercise exercise;
    int userID;
    TextField nameFeild = new TextField("Exercise Name");
    
    public ExerciseEditor(Editor<Exercise> parentEditor, Exercise ex)
    {
        parent = parentEditor;
        exercise = ex;
        fetchData();
        setupTitle();
        setupNameInput();
        ////setupMetricGrid();
        //setupAddMetric();
        setupExitButtons();
    }
    public ExerciseEditor(Editor<Exercise> parentEditor){
        this(parentEditor,new Exercise(parentEditor.getUserID()));
    }
    
    private void setupTitle(){
        H1 title = new H1("Exercise Editor");
        add(title);
    }
    private void setupNameInput(){
        TextField nameField = new TextField("Name:");
        add(nameField);
    }
    

    private void setupMetricGrid(){
        //setup grid...
        metricGrid = new Grid<>(Metric.class, false);
        metricGrid.addColumn(Metric::getName).setHeader("Metric")
            .setAutoWidth(true).setFlexGrow(1);
        metricGrid.addColumn(Metric::getUnit).setHeader("Unit")
            .setAutoWidth(true).setFlexGrow(1);
        fetchData();
        metricGrid.setAllRowsVisible(true);

        //add menu options...
        metricGrid.addComponentColumn(met -> {
            MenuBar menuBar = new MenuBar();
            menuBar.addThemeVariants(MenuBarVariant.LUMO_TERTIARY);
            MenuItem menuItem = menuBar.addItem("•••");
            menuItem.getElement().setAttribute("aria-label", "More options");
            SubMenu subMenu = menuItem.getSubMenu();
            subMenu.addItem("Edit", event -> {
                openExistingMetricEditor(met);
            });
            subMenu.addItem("Delete", event -> {
                allMetricList.remove(met);//remove from arraylist
                fetchData();
            });
            return menuBar;
        }).setWidth("70px").setFlexGrow(0);
        add(metricGrid);
    }
    private void openMetricEditor(){
        MetricEditor metricEdit = new MetricEditor(this);//instantiate metric editor, passing on metric list
                                                            // so metric editor can add its own metric to the list
        metricEdit.open();// open that shit

    }
    private void openExistingMetricEditor(Metric met){
        MetricEditor existMetricEdit = new MetricEditor(this, met);//instantiate metric editor, passing on metric list
                                                            // so metric editor can add its own metric to the list
        existMetricEdit.open();// open that shit

    }
    private void setupAddMetric(){
        Button addMetric = new Button("Add metric");
        addMetric.addClickListener(e ->
            openMetricEditor()
        );
        add(addMetric);
    }
    private void setupExitButtons(){
        HorizontalLayout lastRow = new HorizontalLayout();//declare layout
        Button submit = new Button("Submit");//declaring buttons...
        submit.addAttachListener(ClickEvent -> {
            exercise.update(nameFeild.getValue(),exMetricList);
            parent.addObject(exercise);
        });
        Button delete = new Button("Delete");
        delete.addAttachListener(ClickEvent -> {
            //SQL
            parent.deleteObject(exercise);
        
        });
        lastRow.add(submit,delete);//add all the buttons to layout
        add(lastRow);//add layout

    }
    @Override
    public void fetchData()
    {
        exMetricList = exercise.getMetrics();
        nameFeild.setValue(exercise.getName());
        //PreparedStatement query1 = c.prepareStatement();
    }
    @Override
    public void addObject(Metric metric)
    {
        allMetricList.add(metric);
        fetchData();
    }
    @Override
    public void deleteObject(Metric metric)
    {
        allMetricList.remove(metric);
        /* 
        PreparedStatement query1 = c.PrepareStatement("DELETE FROM PERFORMANCE_METRIC WHERE Owner_ID = ? AND NAME = ? ;");
        query1.setInt(1,userID);
        query1.setString(2,metric.getName());
        PreparedStatement query2 = c.prepareStatement("DELETE FROM METRIC_DESCRIBES_EXERCISE WHERE Metric_user_ID = ? AND Metric_name = ?;");
        query2.setInt(1,userID);
        query2.setString(2,metric.getName());
        PreparedStatement query3 = c.prepareStatement("DELETE FROM METRIC MEASURES_SUBMISSION_WHERE Metric_owner_ID = ? AND Metric_name = ?;");
        query3.setInt(1,userID);
        query3.setString(2,metric.getName());
        // run queries
        */

        fetchData();
    }
    @Override
    public int getUserID()
    {
        return userID;
    }
}

