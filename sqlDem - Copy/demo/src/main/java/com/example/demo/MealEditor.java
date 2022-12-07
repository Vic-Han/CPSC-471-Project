package com.example.demo;

import java.util.ArrayList;
import java.sql.*;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;

public class MealEditor extends VerticalLayout implements Editor<FoodSubmission>{
    private int userID;
    private int mealID;
    private Editor<Meal> parent;
    private Meal meal;
    private Connection con;
    private ArrayList<FoodSubmission> foodsubList = new ArrayList<FoodSubmission>();
    private Grid<FoodSubmission> grid;
    private Dialog submissionDialog;
    public MealEditor(Editor<Meal> parentEditor,Meal inputMeal)
    {
        userID = parentEditor.getUserID();
        mealID = inputMeal.getID();
        meal = inputMeal;
        parent = parentEditor;
        initButtons();
        initGrid();
        initializeConnection();
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
            //submissionDialog.addDialogCloseActionListener(TableCleaner.foodSubCleaner(););
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
    public void initializeConnection(){
        
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost/tracker", "athlete", "cpsc");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }   
    
    }
    
    private void initGrid() {
        grid = new Grid<FoodSubmission>();
        grid.addColumn(FoodSubmission::getFoodName).setHeader("Food:")
        .setAutoWidth(true).setFlexGrow(1);
        grid.addColumn(FoodSubmission::getServings).setHeader("Servings:")
        .setAutoWidth(true).setFlexGrow(1);
        grid.addComponentColumn(e -> {
            MenuBar menuBar = new MenuBar();
            menuBar.addThemeVariants(MenuBarVariant.LUMO_TERTIARY);
            MenuItem menuItem = menuBar.addItem("•••");
            menuItem.getElement().setAttribute("aria-label", "More options");
            SubMenu subMenu = menuItem.getSubMenu();
            subMenu.addItem("Edit", event -> {
                submissionDialog = new Dialog();
                submissionDialog.add(new SubmitFood(this, e));
                submissionDialog.open();
            });
            subMenu.addItem("Delete", event -> {
                deleteObject(e);
            });
            return menuBar;
        }).setWidth("70px").setFlexGrow(0);
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
        grid.setItems(foodsubList);
        submissionDialog.close();
    }

    @Override
    public void deleteObject(FoodSubmission foodsub)
    {
        try
        {
            PreparedStatement query = con.prepareStatement("DELETE FROM FOOD_is_part_of_meal WHERE User_Food_ID = ? AND Food_Name = ? AND meal_ID = ?;");
            query.setInt(1, userID);
            query.setString(2, foodsub.getFoodName());
            query.setInt(3,mealID);
            query.executeUpdate();
        }
        catch(SQLException e)
        {

        }
        foodsubList.remove(foodsub);
        grid.setItems(foodsubList);
        //submissionDialog.close();
    }
    @Override
    public void fetchData()
    {
        foodsubList = meal.getAllSubmissions();
        grid.setItems(foodsubList);
        submissionDialog.close();
    }
    @Override
    public int getUserID()
    {
        return userID;
    }
}
