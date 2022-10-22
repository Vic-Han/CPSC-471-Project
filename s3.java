package com.example.demo;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@Route("")
@RouteAlias("three")
public class s3 extends VerticalLayout{
    public s3(){
        setupNutrientEditor();
        setupButtons();
    }

    
    private void setupNutrientEditor(){
        RadioButtonGroup<String> radioGroup = new RadioButtonGroup<>();
        radioGroup.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        radioGroup.setLabel("Choose a nutrient");
        radioGroup.setItems("Protein", "Carbs", "Fat", "Calories");
        add(radioGroup);
    }

    private void setupButtons(){
        HorizontalLayout lastRow = new HorizontalLayout();//declare layout
        Button continue1 = new Button("Continue");//declaring buttons...
        Button back = new Button("Back");
        lastRow.add(continue1,back);//add all the buttons to layout
        add(lastRow);//add layout

    }
}
