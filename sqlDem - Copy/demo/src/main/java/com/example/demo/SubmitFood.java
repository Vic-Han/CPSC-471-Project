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
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.Route;

import ch.qos.logback.core.joran.conditional.ElseAction;
public class SubmitFood extends VerticalLayout implements Editor<Food>{
    private int userID;
    private Connection con;
    private ArrayList<Food> foodList; 
    private FoodSubmission foodSub;
    private Editor<FoodSubmission> parent;
    private HorizontalLayout mealview_components = new HorizontalLayout(); 
    private NumberField quantity = new NumberField("Quantity");
    private ComboBox<String> units = new ComboBox<String>("Unit");
    private HorizontalLayout serving_input = new HorizontalLayout();
    private HorizontalLayout buttons = new HorizontalLayout();
    private Button submitButton = new Button("Submit");
    private Button deleteButton = new Button("Delete");
    private Button newFood = new Button("New Food");
    private Button editFood = new Button("Edit Food");
    private Button delFood = new Button("Delete Food");
    private ComboBox<Food> chooseFood = new ComboBox<Food>("Choose Food");
  
    public SubmitFood(Editor<FoodSubmission> parentEditor, FoodSubmission currentsub)
    {
        userID = parentEditor.getUserID();
        parent = parentEditor;
        foodSub = currentsub;
        initConnection();
        setupButtons();

    }
    private void setupMealView()
    {
        chooseFood.setItemLabelGenerator(Food::getName);
        if(!(foodSub.getFoodName() == ""))
        {
            chooseFood.setValue(new Food(userID,foodSub.getFoodName()));
        }
        mealview_components.add(chooseFood);

        newFood.addClickListener(clickEvent -> {
            FoodEditor editor = new FoodEditor(this);
            editor.open();
        });
        mealview_components.add(newFood);
        editFood.addClickListener(clickEvent -> {
            FoodEditor editor = new FoodEditor(chooseFood.getValue(),this);
            editor.open();
        });
        mealview_components.add(editFood);
        delFood.addClickListener(clickEvent -> {deleteObject(chooseFood.getValue());});
        mealview_components.add(delFood);
        add(mealview_components);
    }
    private void setupQuantityFeild()
    {
        double d = foodSub.getServings();
        quantity.setValue(d);
        serving_input.add(quantity);
        String[] possibleUnits = {"grams","ml","servings"};
        units.setItems(possibleUnits);
        units.setValue("servings");
        serving_input.add(units);
        add(serving_input);
    }
    private void setupButtons(){
        fetchData();
        setupMealView();
        setupQuantityFeild();
        submitButton.addClickListener(clickEvent -> {submit();});
        deleteButton.addClickListener(clickEvent -> {parent.deleteObject(foodSub);});
        buttons.add(submitButton,deleteButton);
        add(buttons);
    }
    public SubmitFood(Editor<FoodSubmission> parentEditor, int mealID)
    {
        this(parentEditor,new FoodSubmission(parentEditor.getUserID(), mealID));
        parent.addObject(foodSub);
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
    public int getUserID(){
        return userID;
    }
    @Override
    public void fetchData()
    {
        try
        {
            PreparedStatement query = con.prepareStatement("SELECT Name FROM FOOD WHERE User_ID = ?;");
            query.setInt(1, userID);
            ResultSet rs = query.executeQuery();
            foodList = new ArrayList<Food>();
            while(rs.next())
            {
                foodList.add(new Food(userID,rs.getString(1)));
            }
            chooseFood.setItems(foodList);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            Dialog d = new Dialog();
            d.add(new Paragraph("Trouble fetching data food submission"));
            d.open();
        }
    }
    @Override
    public void deleteObject(Food food)
    {
        try
        {
            PreparedStatement query2 = con.prepareStatement("DELETE FROM FOOD_IS_PART_OF_MEAL WHERE User_food_ID = ? AND Food_Name = ?;");
            query2.setInt(1, userID);
            query2.setString(2, food.getName());
            query2.executeUpdate();
            PreparedStatement query = con.prepareStatement("delete from food where user_id = ? and name = ?");
            query.setInt(1, userID);
            query.setString(2, food.getName());
            query.executeUpdate();
        }
        catch(SQLException e)
        {

        }
        fetchData();
    }
    @Override
    public void addObject(Food food)
    {
        foodList.add(food);
        chooseFood.setItems(foodList);
    }
    private void submit(){
        try{
            Food f = chooseFood.getValue();
            Float servings = 0f;
            if(units.getValue() == "ml")
            {
                servings = (quantity.getValue().floatValue()) / f.getmlPerServing();
            }
            else if(units.getValue() == "grams")
            {
                servings = (quantity.getValue().floatValue()) / f.getGramsPerServing();
            }
            else{
                servings = quantity.getValue().floatValue();
            }
            foodSub.update(f.getName(), servings);
        }
        catch(SQLException e)
        {

        }
        parent.fetchData();
    }
}
