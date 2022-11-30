package com.example.demo;
import java.sql.*;
import java.rmi.server.RemoteObject;
import java.util.ArrayList;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
//@Route("metView")
public class ExerciseEditor extends VerticalLayout implements Editor<Metric>{
    private Editor<Exercise> parent;
    private ArrayList<Metric> metricList = new ArrayList<Metric>();
    private ArrayList<Metric> usedMetrics = new ArrayList<Metric>();
    private Exercise exercise;
    private int userID;
    private Connection con;
    TextField nameField = new TextField("Exercise name");
    private Button newMet = new Button("Create new metric");
    private Button editMet = new Button("Edit this metric");
    private Button useMet = new Button("Add this metric");
    private Button done = new Button("Done");
    private ComboBox<Metric> chooseMet = new ComboBox<Metric>("Add a performance metric");
    private Grid<Metric> metricGrid = new Grid<Metric>();
    
    public ExerciseEditor(Editor<Exercise> parent, Exercise exercise){
        initConnection();
        this.parent = parent;
        userID = parent.getUserID();
        this.exercise = exercise;
        initTitle();
        initNameField();
        initGrid();
        initMetricCB();
        initMetricButtons();
        initDone();
    }
    public ExerciseEditor(Editor<Exercise> parent){
        this(parent, new Exercise(parent.getUserID()));
        parent.addObject(exercise);
    }
    private void initDone() {
        done.addClickListener(ClickEvent -> {
            exercise.setName(nameField.getValue());
            for (Metric m : usedMetrics){
                exercise.addMetric(m);
            }
            
        });
        add(done);
    }
    private void initMetricButtons() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        //add new metric button 
        newMet.addClickListener(clickEvent -> {
            MetricEditor editor = new MetricEditor(this);
            editor.open();
        });
        buttonLayout.add(newMet);

        //make edit metric button...
        editMet.addClickListener(clickEvent -> {
            MetricEditor editor = new MetricEditor(this,chooseMet.getValue());
            editor.open();
        });
        buttonLayout.add(editMet);
        
        //make choose metric button..
        useMet.addClickListener(clickEvent -> {
            useMetric(chooseMet.getValue());
        });
        buttonLayout.add(useMet);
        add(buttonLayout);
    }
    private void initMetricCB() {
        //make combobox...
        try
        {
            PreparedStatement query = con.prepareStatement("SELECT Metric_name FROM PERFORMANCE_METRIC WHERE Owner_ID = ?;");
            query.setInt(1, userID);
            ResultSet rs = query.executeQuery();
            metricList = new ArrayList<Metric>();
            while(rs.next())
            {
                metricList.add(new Metric(rs.getString(1),userID));
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        chooseMet.setItems(metricList);
        chooseMet.setItemLabelGenerator(Metric::getName);
        add(chooseMet);
    }
    private void initGrid() {
            if (exercise != null){
                usedMetrics = new ArrayList<Metric> (exercise.getMetrics());
            } 
            metricGrid.addColumn(Metric::getName).setHeader("Metric name:")
            .setAutoWidth(true).setFlexGrow(1);
            metricGrid.addColumn(Metric::getUnit).setHeader("Units")
            .setAutoWidth(true).setFlexGrow(1);
            
            metricGrid.setItems(usedMetrics);
            //add context menu
            metricGrid.addComponentColumn(m -> {
                MenuBar menuBar = new MenuBar();
                menuBar.addThemeVariants(MenuBarVariant.LUMO_TERTIARY);
                MenuItem menuItem = menuBar.addItem("•••");
                menuItem.getElement().setAttribute("aria-label", "More options");
                SubMenu subMenu = menuItem.getSubMenu();
                subMenu.addItem("Edit", event -> {
                    MetricEditor me = new MetricEditor(this, m);
                    me.open();
                });
                subMenu.addItem("Delete", event -> {
                    deleteObject(m);
                });
                return menuBar;
            }).setWidth("70px").setFlexGrow(0);

            add(metricGrid);
    }
    private void initNameField() {
        if (exercise != null){
            nameField.setValue(exercise.getName());
        }
        add(nameField);
    }
    
    private void initTitle(){
        H1 title = new H1("Exercise Editor");
        add(title);
    }
    
    public void initConnection()
    {
         
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost/tracker", "athlete", "cpsc");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }   
    }
    @Override
    public void fetchData()
    {
        chooseMet.setItems(metricList);
        metricGrid.setItems(usedMetrics);
    }
    @Override
    public void addObject(Metric metric) {
        metricList.add(metric);
        chooseMet.setItems(metricList);
        useMetric(metric);
        
    }
    private void useMetric(Metric metric){
        usedMetrics.add(metric);
        metricGrid.setItems(usedMetrics);
        
    }
    @Override
    public void deleteObject(Metric metric) {
        try{
        PreparedStatement query2 = con.prepareStatement("DELETE FROM METRIC_DESCRIBES_EXERCISE WHERE Metric_user_ID = ? AND Metric_name = ?;");
        query2.setInt(1,userID);
        query2.setString(2,metric.getName());
        query2.executeUpdate();
        PreparedStatement query3 = con.prepareStatement("DELETE FROM METRIC MEASURES_SUBMISSION_WHERE Metric_owner_ID = ? AND Metric_name = ?;");
        query3.setInt(1,userID);
        query3.setString(2,metric.getName());
        query3.executeUpdate();
    
        }
        catch(SQLException e)
        {

        }
        usedMetrics.remove(metric);
        metricGrid.setItems(usedMetrics);
    }
    @Override
    public int getUserID() {
        return userID;
    }


}