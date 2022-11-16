// package com.example.demo;

// import com.vaadin.flow.component.button.Button;
// import com.vaadin.flow.component.combobox.ComboBox;
// import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
// import com.vaadin.flow.component.orderedlayout.VerticalLayout;
// import com.vaadin.flow.router.Route;
// import com.vaadin.flow.router.RouteAlias;

// @Route("")
// @RouteAlias("five")
// public class s5 extends VerticalLayout{
//     private String[] testMetrics = {"kg", "km"};
//     public s5(){
//         setupMetricInput();
//         setupButtons();

//     }
    

//     private void setupMetricInput(){
//         ComboBox<String> ex = new ComboBox<>("Metric:");//declare combobox
//         ex.setItems(testMetrics);//put array of objects (Strings in this case) into combobox
//         add(ex);//add layout to screen
//     }

//     private void setupButtons(){
//         HorizontalLayout lastRow = new HorizontalLayout();//declare layout
//         Button continue1 = new Button("Continue");//declaring buttons...
//         Button back = new Button("Back");
//         lastRow.add(continue1,back);//add all the buttons to layout
//         add(lastRow);//add layout

//     }
// }
