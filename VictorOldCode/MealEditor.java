package com.example.demo;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
@Route("")
//@Route("mealEdit")
public class MealEditor extends VerticalLayout{
    private VerticalLayout submisions = new VerticalLayout();;
    //private String[] macros = {"Protien", "Carbs", "Fats","Calories"};
    private Grid<Food> submission_grid;
    public MealEditor(){
        setupTitle();
        //addVariables();
        setupSubmissionGrid();
        //add(submisions);
        //setupAddVariableButtons();
        setupExitButtons();
    }

    private void setupTitle(){
        H1 title = new H1("Meal Editor");
        add(title);
    }
    private void addVariables(){
       
        HorizontalLayout food = new HorizontalLayout();
        Button delete = new Button("-");
        delete.addClickListener(e ->  submisions.remove(food)  );
        food.add(delete);
        TextField name = new TextField("Food Name:");
        food.add(name);
        TextField servings = new TextField("servings:");
        food.add(servings);
        submisions.add(food);
    }
    private void setupSubmissionGrid(){
        submission_grid = new Grid<Food>();
        submission_grid.addColumn(Food::getName).setHeader("Food")
            .setAutoWidth(true).setFlexGrow(1);
        submission_grid.addColumn(Food::getProtien).setHeader("Protein")
            .setAutoWidth(true).setFlexGrow(1);
        submission_grid.addColumn(Food::getCarbs).setHeader("Carbs")
            .setAutoWidth(true).setFlexGrow(1);
        submission_grid.addColumn(Food::getFats).setHeader("Fats")
            .setAutoWidth(true).setFlexGrow(1);
        submission_grid.addColumn(Food::getCalories).setHeader("Calories")
            .setAutoWidth(true).setFlexGrow(1);
        add(submission_grid);
    }
    private void setupAddVariableButtons(){
        Button addvar = new Button("Add Submission");//make both buttons
        addvar.addClickListener(e -> addVariables());
        add(addvar);
    }
    private void setupExitButtons(){
        HorizontalLayout lastRow = new HorizontalLayout();// horizontal layout for the graph and delete buttons
        Button submit = new Button("Submit");//make both buttons
        Button delete = new Button("Delete");
        Button cancel = new Button("Cancel");

        lastRow.add(submit,delete,cancel);//add all the buttons to layout
        add(lastRow);//add layout

    }
}
