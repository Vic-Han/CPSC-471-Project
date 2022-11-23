package com.example.demo;
import com.vaadin.flow.router.Route;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
@Route("ex")
public class ExampleLayout extends VerticalLayout {
    private Connection dbConnect;
    public ExampleLayout(){
        initializeConnection();
        initializeTextField();
    }
    public void initializeConnection(){
        
        try{
            dbConnect = DriverManager.getConnection("jdbc:mysql://localhost/tracker", "athlete", "cpsc");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }   
    
    }
    private void initializeTextField(){
        TextField tf = new TextField("add shit");
        Button b = new Button("enter");
        b.addClickListener(clickEvent -> {
            Exercise test = new Exercise("Biking",1);
            ArrayList<Metric> testList = test.getMetrics();
            testList.get(1).setUnit("km/h");
            Metric wind = new Metric("wind",1);
            tf.setValue(testList.get(1).getUnit());
        });
        add(tf);
        add(b);
    }

}
