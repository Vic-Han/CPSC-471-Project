package com.example.demo;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@Route("")
@RouteAlias("four")
public class s4 extends VerticalLayout{
    private String[] testExercises = {"Run", "Squat", "Bench Press"};
    public s4(){
        setupExerciseInput();
        setupButtons();

    }
    

    private void setupExerciseInput(){
        ComboBox<String> ex = new ComboBox<>("Exercise:");//declare combobox
        ex.setItems(testExercises);//put array of objects (Strings in this case) into combobox
        add(ex);//add layout to screen
    }

    private void setupButtons(){
        HorizontalLayout lastRow = new HorizontalLayout();//declare layout
        Button continue1 = new Button("Continue");//declaring buttons...
        Button back = new Button("Back");
        lastRow.add(continue1,back);//add all the buttons to layout
        add(lastRow);//add layout

    }
}
