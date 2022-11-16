package com.example.demo;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;


//load s2-s5 in s1


@Route("")
@RouteAlias("one")
public class s1 extends VerticalLayout{
    public s1(){
        setupTitle();
        setupVariableEditor();
        setupButtons();
    }

    private void setupTitle(){
        H1 title = new H1("Variable Editor");
        add(title);
    }

    
    private void setupVariableEditor(){
        RadioButtonGroup<String> radioGroup = new RadioButtonGroup<>();
        radioGroup.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        radioGroup.setLabel("Choose a variable to edit:");
        radioGroup.setItems("Food", "Nutrient", "Weight", "Exercise Performance");
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
