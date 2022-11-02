package com.example.demo;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;



//@Route("")
@Route("foodEdit")
public class FoodEditor extends VerticalLayout{
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
        TextField nameField = new TextField("Name:");
        add(nameField);
        TextField grams = new TextField("g/serving:");
        add(grams);
        TextField ml = new TextField("ml/serving:");
        add(ml);
    }
    private void setupNutritionFacts(){
        HorizontalLayout nutritionFacts = new HorizontalLayout();
        H1 title = new H1("Nutrients");
        nutritionFacts.add(title);
        TextField protein = new TextField("protein:");
        nutritionFacts.add(protein);
        TextField carbohydrates = new TextField("carbohydrates:");
        nutritionFacts.add(carbohydrates);
        TextField fats = new TextField("fats:");
        nutritionFacts.add(fats);
        TextField calories = new TextField("calories:");
        nutritionFacts.add(calories);
        add(nutritionFacts);
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