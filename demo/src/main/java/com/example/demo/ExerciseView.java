package com.example.demo;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class ExerciseView extends VerticalLayout{
    private HorizontalLayout editOrCreate;
    private VerticalLayout editorLayout;
    private Exercise[] exampleArr = {new Exercise("Ruck"), new Exercise("Squats"), new Exercise("Bench Press")};
    public ExerciseView(){
        setupEOCLayout();
        setupEditorLayout();
    }
    private void setupEOCLayout(){
        editOrCreate = new HorizontalLayout();
        //make new exercise button...
        Button newEx = new Button("New Exercise");
        newEx.addClickListener(clickEvent -> {
            refreshEditorLayout();
        });
        editOrCreate.add(newEx);
        //make combobox...
        ComboBox<Exercise> chooseEx = new ComboBox<Exercise>("Choose exercise");
        chooseEx.setItems(exampleArr);
        chooseEx.setItemLabelGenerator(Exercise::getName);
        editOrCreate.add(chooseEx);
        //make edit exercise button...
        Button editEx = new Button("Edit Exercise");
        editEx.addClickListener(clickEvent -> {
            refreshEditorLayout(chooseEx.getValue());
        });
        editOrCreate.add(editEx);
        add(editOrCreate);
    }
    private void setupEditorLayout(){
        editorLayout = new VerticalLayout();
        add(editorLayout);
    }
    private void refreshEditorLayout(){
        editorLayout.removeAll();
        editorLayout.add(new ExerciseEditor());
    }
    private void refreshEditorLayout(Exercise ex){
        editorLayout.removeAll();
        editorLayout.add(new ExerciseEditor());//should add ExistingExerciseEditor but that doesn't exist yet
        editorLayout.add(new Paragraph("Should be editting "+ ex.getName()));
    }

}
