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
//@Route("test")
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
                Food test = new Food(1,"Egg");
                test.update("Egg",50,50,6,7,99,80);
                try{
                    tf.setValue(Integer.toString(test.getGramsPerServing()));
                }
                catch(SQLException e)
                {

                }
        });
        add(tf);
        add(b);
    }

}
