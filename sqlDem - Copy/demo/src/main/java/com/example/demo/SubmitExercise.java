package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.event.ChangeListener;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.Route;

//@Route("asd")
public class SubmitExercise extends VerticalLayout implements Editor<Exercise> {
    private int userID;
    private WorkoutEditor parent;
    private ArrayList<MetricPair> metrics;
    private Exercise exercise;
    private ExerciseSubmission exSubmission;
    private ArrayList<Exercise> exercises;
    private Connection con;

    //front end shit..
    ComboBox<Exercise> exCB = new ComboBox<>("Choose exercise:");
    VerticalLayout metricLayout = new VerticalLayout();
    VerticalLayout gridLayout = new VerticalLayout();
    Grid<MetricPair> grid = new Grid<>(MetricPair.class, false);
    Dialog exEditor;
    Button submitValues;

    //ctors..
    public void initConnection()
    {
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost/tracker", "athlete", "cpsc");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public SubmitExercise(WorkoutEditor parent){
        initConnection();
        userID = parent.getUserID();
        this.parent = parent;
        metrics = new ArrayList<MetricPair>();
        exSubmission = new ExerciseSubmission(parent.getWorkoutID(),parent.getUserID());
        exercises = new ArrayList<Exercise>();
        initTitle();
        initExList();
        parent.addObject(exSubmission);
 
    }
    public SubmitExercise(WorkoutEditor parent, ExerciseSubmission exSubmission){
        initConnection();
        this.parent = parent;
        userID = parent.getUserID();
        metrics = new ArrayList<MetricPair>();
        this.exSubmission = exSubmission;//set submission object
        //set exercise and metrics (list of metrics and values)
        exercise = exSubmission.getExercise();
        metrics = exSubmission.getMetricList();

        exercises = new ArrayList<Exercise>();
        initTitle();
        initExList();
        initMetList();//initializes with data from submission
    }
    /*public SubmitExercise(){//default ctor testing only
        initConnection();
        metrics = new ArrayList<MetricPair>();
        exSubmission = new ExerciseSubmission(this.parent.getWorkoutID(),userID);
        exercises = new ArrayList<Exercise>();
        
        initTitle();
        initExList();
 
    }*/
    //methods..
    private void rewriteMetrics(){
        exSubmission.setMetricList(metrics);
        parent.fetchData();
    }
    private void clearMetrics(){
        metrics = new ArrayList<MetricPair>();
        gridLayout.remove(grid);
        grid.setItems(metrics);
        //gridLayout.add(grid);
        //metricLayout.removeAll();
        //metricLayout = new VerticalLayout();
    }
    private void initTitle(){
        H1 title = new H1("Submit An Exercise:");
        add(title);
    }
    private void initExList(){//add empty metriclayout immediately after
        VerticalLayout exLayout = new VerticalLayout();
        //setup combobox
        loadExercises();
        exCB.setItems(exercises);
        if (exercise != null){//if existing submission, preset correct exercise
            exCB.setValue(exercise);
        }
        exCB.setItemLabelGenerator(Exercise::getName);
        exLayout.add(exCB);
        //setup buttons
        HorizontalLayout exButtons = new HorizontalLayout();
        Button select = new Button("Select Exercise");
        Button edit = new Button("Edit Exercise");
        Button newEx = new Button("Create Exercise");
        select.addClickListener(ClickEvent ->{
            exercise = (Exercise)exCB.getValue();
            initMetList(exercise);});
        newEx.addClickListener(ClickEvent ->{
            exEditor = new Dialog();
            exEditor.add(new ExerciseEditor(this));
            exEditor.open();});
        edit.addClickListener(ClickEvent ->{
            exEditor = new Dialog();
            exEditor.add(new ExerciseEditor(this, (Exercise)exCB.getValue()));
            exEditor.open();});
        exButtons.add(select);
        exButtons.add(edit);
        exButtons.add(newEx);
        exLayout.add(exButtons);

        add(exLayout);
        add(metricLayout);
    }
    private void initMetList(Exercise ex){//initialize with selected exercise
        System.out.println("opening met list");
        //init metrics with 0 values
        metrics = new ArrayList<MetricPair>();
        grid.removeAllColumns(); 
        for (Metric m : ex.getMetrics()){
            MetricPair mp = new MetricPair(m, 0);
            metrics.add(mp);
        }
        
        grid.addColumn(MetricPair::getName).setHeader("Metric:")
        .setAutoWidth(true).setFlexGrow(1);
        //add number fields
        grid.addComponentColumn(m -> {
            NumberField nf = new NumberField();
            nf.setValue((double) m.getVal());
            nf.addValueChangeListener(ChangeListener ->{m.setVal(nf.getValue().intValue());});
            return nf;
        }).setWidth("70px").setFlexGrow(0);    
        grid.setItems(metrics);
        gridLayout.add(grid);
        metricLayout.add(gridLayout);
        
        if (submitValues == null){
            submitValues = new Button("Submit Values");
            submitValues.addClickListener(ClickListener -> {
                setName();
                rewriteMetrics();   
            });
            metricLayout.add(submitValues);
        }
        
    
        

    }
    private void initMetList(){//initialize with submission values
        grid.addColumn(MetricPair::getName).setHeader("Metric:")
        .setAutoWidth(true).setFlexGrow(1);
        //add number fields
        grid.addComponentColumn(m -> {
            NumberField nf = new NumberField();
            nf.setValue((double) m.getVal());
            nf.addValueChangeListener(ChangeListener ->{m.setVal(nf.getValue().intValue());});
            return nf;
        }).setWidth("70px").setFlexGrow(0);    
        grid.setItems(metrics);
        gridLayout.add(grid);
        metricLayout.add(gridLayout);

        if (submitValues == null){
            submitValues = new Button("Submit Values");
            submitValues.addClickListener(ClickListener -> {
                setName();
                rewriteMetrics();   
            });
            metricLayout.add(submitValues);
        }
    }
    private void setName() {
        try{
            PreparedStatement query1 = con.prepareStatement("UPDATE EXERCISE_SUBMISSION SET Exercise_name = ? WHERE Submission_ID = ?;");
            query1.setString(1,exercise.getName());
            query1.setInt(2,exSubmission.getID());
            query1.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    //inherited methods..
    @Override
    public void addObject(Exercise exercise) {
        exercises.add(exercise);
        exCB.setItems(exercises);   
        exEditor.close();
    }

    @Override
    public void deleteObject(Exercise exercise) {
        exercises.remove(exercise);
        exCB.setItems(exercises);
        if (this.exercise == exercise){
            clearMetrics();
        }
        try{
            PreparedStatement query1 = con.prepareStatement("DELETE FROM EXERCISE WHERE User_ID = ? AND NAME = ? ;");
            query1.setInt(1,userID);
            query1.setString(2,exercise.getName());
            query1.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        try
        {
            PreparedStatement query2 = con.prepareStatement("DELETE FROM METRIC_DESCRIBES_EXERCISE WHERE Metric_user_ID = ? AND Exercise_name = ?;");
            query2.setInt(1,userID);
            query2.setString(2,exercise.getName());
            query2.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        try
        {
            PreparedStatement query3 = con.prepareStatement("DELETE FROM EXERCISE_SUBMISSION User_ID = ? AND Exercise_name = ?;");
            query3.setInt(1,userID);
            query3.setString(2,exercise.getName());
            query3.executeUpdate();
            }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        exEditor.close();
        
    }

    @Override
    public void fetchData() {
        clearMetrics();
        exEditor.close();
    }
    private void loadExercises(){
        try
        {
            PreparedStatement query1 = con.prepareStatement("SELECT Name FROM  EXERCISE WHERE User_ID = ? ;");
            query1.setInt(1,userID);
            ResultSet rs = query1.executeQuery();
            while (rs.next()){
                Exercise tmp = new Exercise(rs.getString(1), userID);
                exercises.add(tmp);
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
        

    @Override
    public int getUserID() {
        return userID;
    }

    
    
}
