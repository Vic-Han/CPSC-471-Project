package com.example.demo;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import java.sql.SQLException;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.dialog.Dialog;


//@Route("")
//@Route("foodEdit")
public class FoodEditor extends Dialog{
    private int userID;
    private Editor<Food> parent;
    private Food food;
    TextField nameField = new TextField("Name:");
    NumberField grams = new NumberField("g/serving:");
    NumberField ml = new NumberField("ml/serving:");
    NumberField protein = new NumberField("protein:");
    NumberField carbohydrates = new NumberField("carbohydrates:");
    NumberField fats = new NumberField("fats:");
    NumberField calories = new NumberField("calories:");
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
    /*public FoodEditor()
    {   
        food = new Food(1,"Egg");
        setupTitle();
        setupTextFields();
        setupNutritionFacts();
        setupExitButtons();
    }  */

    private void setupTitle(){
        H1 title = new H1("Food Editor");
        add(title);
    }
    private void setupTextFields(){
        try{
            nameField.setValue(food.getName());
            add(nameField);
            double d = food.getGramsPerServing();
            grams.setValue(d);
            add(grams);
            d = food.getmlPerServing();
            ml.setValue(d);
            add(ml);
        }
        catch(SQLException e)
        {
            Dialog d = new Dialog();
            d.add(new Paragraph(food.getName()+", "+ userID));
            d.open();
        }
    }
    private void setupNutritionFacts(){
        HorizontalLayout nutritionFacts = new HorizontalLayout();
        try{
            H1 title = new H1("Nutrients");
            add(title);
            double d = food.getProtien();
            protein.setValue(d);
            nutritionFacts.add(protein);
            d = food.getCarbs();
            carbohydrates.setValue(d);
            nutritionFacts.add(carbohydrates);
            d = food.getFats();
            fats.setValue(d);
            nutritionFacts.add(fats);
            d = food.getCalories();
            calories.setValue(d);
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
        submit.addClickListener(ClickEvent -> {submit();});
        Button delete = new Button("Delete");
        delete.addClickListener(ClickEvent -> {delete();});
        lastRow.add(submit,delete);//add all the buttons to layout
        add(lastRow);//add layout

    }
    private void submit()
    {
        food.update(nameField.getValue(),ml.getValue().intValue(),grams.getValue().intValue(),
        fats.getValue().intValue(),protein.getValue().intValue(),carbohydrates.getValue().intValue(),calories.getValue().intValue());
        parent.fetchData();
        this.close();
    }
    private void delete()
    {
        parent.deleteObject(food);
        this.close();
    }
}