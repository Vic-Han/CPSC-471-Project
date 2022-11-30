package com.example.demo;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class WorkoutEditor extends VerticalLayout implements Editor<ExerciseSubmission>{
    
    private int userID;
    private Workout workout;
    private Grid<ExerciseSubmission> grid;
    private ArrayList<ExerciseSubmission> subList = new ArrayList<ExerciseSubmission>();

    private Editor<Workout> parent;
    private Dialog submissionDialog;
    public WorkoutEditor(Editor<Workout> parent, LocalDate date)
    {
        this(parent, new Workout(parent.getUserID(), Date.valueOf(date)));
    }
    public WorkoutEditor(Editor<Workout> parent,Workout workout)
    {
        userID = parent.getUserID();
        this.parent = parent;
        this.workout = workout;
        initGrid();
        initButtons();
    }
    private void initButtons() {
        HorizontalLayout buttons = new HorizontalLayout();
        Button addSubmission = new Button("Add exercise submission");
        addSubmission.addClickListener(ClickEvent -> {
            submissionDialog = new Dialog();
            submissionDialog.add(new SubmitExercise(this));
            submissionDialog.open();
        });
        buttons.add(addSubmission);
        Button deleteWorkout = new Button("Delete this workout");
        deleteWorkout.addClickListener(ClickEvent ->{
            workout.deleteWorkout();
            parent.deleteObject(workout);
        });
        buttons.add(deleteWorkout);
        add(buttons);

    }
    private void initGrid() {
        grid = new Grid<ExerciseSubmission>();
        subList = new ArrayList<ExerciseSubmission>(workout.getSubmissionList());
        grid.setItems(subList);
        grid = new Grid<ExerciseSubmission>();
        grid.addColumn(e -> {return e.getExercise().getName();});
        grid.addComponentColumn(e ->{
            Paragraph para = new Paragraph();
            for (MetricPair m: e.getMetricList()){
                para.add("\n" + m.getName() + ": " + m.getVal() + ' ' + m.getUnit());
            }
            return para;
        });
        grid.addComponentColumn(e -> {
            MenuBar menuBar = new MenuBar();
            menuBar.addThemeVariants(MenuBarVariant.LUMO_TERTIARY);
            MenuItem menuItem = menuBar.addItem("•••");
            menuItem.getElement().setAttribute("aria-label", "More options");
            SubMenu subMenu = menuItem.getSubMenu();
            subMenu.addItem("Edit", event -> {
                submissionDialog = new Dialog();
                submissionDialog.add(new SubmitExercise(this, e));
                submissionDialog.open();
            });
            subMenu.addItem("Delete", event -> {
                deleteObject(e);
            });
            return menuBar;
        }).setWidth("70px").setFlexGrow(0);
        add(grid);
    }
    public int getWorkoutID()
    {
        return workout.getID();
    }
    @Override
    public void fetchData()
    {
     submissionDialog.close();
     grid.setItems(subList);  
    }
    @Override
    public void addObject(ExerciseSubmission sub)
    {
        workout.addSubmission(sub);
        subList.add(sub);
        grid.setItems(subList);
    }
    @Override
    public void deleteObject(ExerciseSubmission sub)
    {
        workout.removeSubmission(sub);
        subList.remove(sub);
        grid.setItems(subList);
    }
    @Override
    public int getUserID()
    {
        return userID;
    }
}
