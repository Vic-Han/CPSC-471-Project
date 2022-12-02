package com.example.demo;

import java.util.ArrayList;
import java.sql.*;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class MealEditor extends VerticalLayout implements Editor<FoodSubmission>{
    private int userID;
    private int mealID;
    private Editor<Meal> parent;
    private Meal meal;
    private ArrayList<FoodSubmission> foodsubList;
    private Grid<FoodSubmission> grid;
    private Dialog submissionDialog;
    public MealEditor(Editor<Meal> parentEditor,Meal inputMeal)
    {
        userID = parentEditor.getUserID();
        meal = inputMeal;
        parent = parentEditor;
        initButtons();
        initGrid();
    }
    public MealEditor(Editor<Meal> parentEditor,Date date)
    {
        this(parentEditor, new Meal(parentEditor.getUserID(), date));
    }

     private void initButtons() {
        HorizontalLayout buttons = new HorizontalLayout();
        Button addSubmission = new Button("Add food submission");
        addSubmission.addClickListener(ClickEvent -> {
            TableCleaner.foodSubCleaner();
            submissionDialog = new Dialog();
            submissionDialog.add(new SubmitFood(this,getMealID()));
            submissionDialog.open();
        });
        buttons.add(addSubmission);
        Button deleteMeal = new Button("Delete this meal");
        deleteMeal.addClickListener(ClickEvent ->{
            parent.deleteObject(meal);
        });
        buttons.add(deleteMeal);
        add(buttons);

    }
    private void initGrid() {
        grid = new Grid<FoodSubmission>();
        grid.addColumn(FoodSubmission::getFoodName).setHeader("Food:")
        .setAutoWidth(true).setFlexGrow(1);
        grid.addColumn(FoodSubmission::getServings).setHeader("Servings:")
        .setAutoWidth(true).setFlexGrow(1);
        foodsubList = meal.getAllSubmissions();
        grid.setItems(foodsubList);
        add(grid);
    }
    public int getMealID()
    {
        return mealID;
    }
    @Override
    public void addObject(FoodSubmission foodsub)
    {
        foodsubList.add(foodsub);
        grid.setItems(foodsub);
        submissionDialog.close();
    }

    @Override
    public void deleteObject(FoodSubmission foodsub)
    {
        submissionDialog.close();
    }
    @Override
    public void fetchData()
    {

    }
    @Override
    public int getUserID()
    {
        return userID;
    }
}
