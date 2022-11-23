package com.example.demo;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import java.sql.SQLException;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.dialog.Dialog;


//@Route("")
//@Route("foodEdit")
public class FoodEditor extends VerticalLayout{
    private int userID;
    private Editor<Food> parent;
    private Food food;
    public FoodEditor(Food mainFood, Editor<Food> parentEditor)
    {
        parent = parentEditor;
        userID = parent.getUserID();
        food = mainFood;
        setupTitle();
        setupTextFields();
        setupNutritionFacts();
        setupExitButtons();
    }
    public FoodEditor(Editor<Food> parentEditor)
    {
        this(new Food(parentEditor.getUserID()),parentEditor);
    }
    public FoodEditor(){
        setupTitle();
        setupTextFields();
        setupNutritionFacts();
        setupExitButtons();
    }

    private void setupTitle(){
        H1 title = new H1("Food Editor");
        add(title);
    }
    private void setupTextFields(){
        try{
            TextField nameField = new TextField("Name:");
            nameField.setValue(food.getName());
            add(nameField);
            NumberField grams = new NumberField("g/serving:");
            double d = food.getGramsPerServing();
            grams.setValue(d);
            add(grams);
            NumberField ml = new NumberField("ml/serving:");
            d = food.getmlPerServing();
            ml.setValue(d);
            add(ml);
        }
        catch(SQLException e)
        {

        }
    }
    private void setupNutritionFacts(){
        HorizontalLayout nutritionFacts = new HorizontalLayout();
        try{
        H1 title = new H1("Nutrients");
        nutritionFacts.add(title);
        NumberField protein = new NumberField("protein:");
        double d = food.getProtien();
        protein.setValue(d);
        nutritionFacts.add(protein);
        NumberField carbohydrates = new NumberField("carbohydrates:");
        nutritionFacts.add(carbohydrates);
        NumberField fats = new NumberField("fats:");
        nutritionFacts.add(fats);
        NumberField calories = new NumberField("calories:");
        nutritionFacts.add(calories);
        add(nutritionFacts);
        }
        catch(SQLException e)
        {
            
        }
    }
    private void setupExitButtons(){
        HorizontalLayout lastRow = new HorizontalLayout();//declare layout
        Button submit = new Button("Submit");//declaring buttons...
        Button cancel = new Button("Cancel");
        Button delete = new Button("Delete");
        lastRow.add(submit,cancel,delete);//add all the buttons to layout
        add(lastRow);//add layout

    }
}