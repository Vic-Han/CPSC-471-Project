// package com.example.demo;

// import java.util.ArrayList;
// import java.util.List;

// import com.vaadin.flow.component.button.Button;
// import com.vaadin.flow.component.grid.Grid;
// import com.vaadin.flow.component.html.H1;
// import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
// import com.vaadin.flow.component.orderedlayout.VerticalLayout;
// import com.vaadin.flow.component.textfield.TextField;
// import com.vaadin.flow.router.Route;
// import com.vaadin.flow.router.RouteAlias;

// @Route("")
// @RouteAlias("workoutEdit")
// public class WorkoutEditor extends VerticalLayout {
//     public WorkoutEditor(){
//         setupTitle();
//         setupExView();
//         newEntryButton();
//         setupExitButtons();

//     }    
    
//     protected List<Exercise> exerciseList;

//     private Grid<Exercise> exerciseGrid;
//     TextField nameField;
//     TextField unitField;

//     private void setupExView(){
//         HorizontalLayout test = new HorizontalLayout();//declare layout
//         exerciseGrid = new Grid<>(Exercise.class, false);
//         exerciseGrid.addColumn(Exercise::getName).setHeader("Run:")
//             .setAutoWidth(true).setFlexGrow(0);
//         exerciseList = new ArrayList<Exercise>();
//         //setup testing metrics...
//             exerciseList.add(new Exercise("Distance"));
//             exerciseList.add(new Exercise("Pace: ahahahahahahahaahhaah"));
//         //done setting up example
//         exerciseGrid.setItems(exerciseList);
//         exerciseGrid.setAllRowsVisible(true);
//         Button edit = new Button("Edit");
//         test.add(edit,exerciseGrid);
//         add(test);
//     }

//     private void setupTitle(){
//         H1 title = new H1("Workout Editor");
//         add(title);
//     }

//     private void setupExitButtons(){
//         HorizontalLayout lastRow = new HorizontalLayout();//declare layout
//         Button submit = new Button("Submit");//declaring buttons...
//         Button cancel = new Button("Cancel");
//         Button delete = new Button("Delete");
//         lastRow.add(submit,cancel,delete);//add all the buttons to layout
//         add(lastRow);//add layout

//     }

//     private void newEntryButton(){
//         Button newEntry = new Button("New Entry");
//         add(newEntry);
//     }
// }
