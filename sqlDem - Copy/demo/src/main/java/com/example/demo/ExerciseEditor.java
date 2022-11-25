package com.example.demo;
import com.vaadin.flow.router.Route;

import java.net.ConnectException;
import java.sql.*;
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




@Route("exedit")
public class ExerciseEditor extends VerticalLayout implements Editor<Metric>{
    private ArrayList<Metric> allMetricList;
    private ArrayList<Metric> exMetricList;
    private Grid<Metric> metricGrid;
    private Editor<Exercise> parent;
    private Connection con;
    Exercise exercise;
    int userID;
    TextField nameField = new TextField("Exercise Name");
    public ExerciseEditor(Editor<Exercise> parentEditor, Exercise ex)
    {
        userID = parentEditor.getUserID();
        parent = parentEditor;
        exercise = ex;
        initConnection();
        fetchData();
        setupTitle();
        setupNameInput();
        setupMetricGrid();
        setupAddMetric();
        setupExitButtons();
    }
    public ExerciseEditor(Editor<Exercise> parentEditor){
        this(parentEditor,new Exercise(parentEditor.getUserID()));
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
            try
            {
                exercise.update(nameField.getValue(),exMetricList);
                parent.addObject(exercise);
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
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
        try
        {
            exMetricList = exercise.getMetrics();
            nameField.setValue(exercise.getName());
            for(int index = 0; index< exMetricList.size(); index++){
                //metricGrid.getContainerDataSource().addItem(itemId);
                
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        //PreparedStatement query1 = c.prepareStatement(SELECT metric_name FROM PERFORMANCE_METRIC);
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
        try
        {
            PreparedStatement query1 = con.prepareStatement("DELETE FROM PERFORMANCE_METRIC WHERE Owner_ID = ? AND NAME = ? ;");
            query1.setInt(1,userID);
            query1.setString(2,metric.getName());
            query1.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        try
        {
            PreparedStatement query2 = con.prepareStatement("DELETE FROM METRIC_DESCRIBES_EXERCISE WHERE Metric_user_ID = ? AND Metric_name = ?;");
            query2.setInt(1,userID);
            query2.setString(2,metric.getName());
            query2.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        try
        {
            PreparedStatement query3 = con.prepareStatement("DELETE FROM METRIC MEASURES_SUBMISSION_WHERE Metric_owner_ID = ? AND Metric_name = ?;");
            query3.setInt(1,userID);
            query3.setString(2,metric.getName());
            query3.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        fetchData();
    }
    @Override
    public int getUserID()
    {
        return userID;
    }
}