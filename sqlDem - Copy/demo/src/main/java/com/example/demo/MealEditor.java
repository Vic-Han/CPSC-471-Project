package com.example.demo;

import java.util.ArrayList;
import java.sql.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class MealEditor extends VerticalLayout implements Editor<FoodSubmission>{
    private int userID;
    private int mealID;
    private Editor<Meal> parent;
    private Meal meal;
    private ArrayList<FoodSubmission> foodsubList;

    public MealEditor(Editor<Meal> parentEditor,Meal inputMeal)
    {
        
    }
    public MealEditor(Editor<Meal> parentEditor)
    {

    }


    public int getMealID()
    {
        return mealID;
    }
    @Override
    public void addObject(FoodSubmission foodsub)
    {

    }

    @Override
    public void deleteObject(FoodSubmission foodsub)
    {

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
