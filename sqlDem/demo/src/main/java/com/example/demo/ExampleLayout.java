package com.example.demo;
import com.vaadin.flow.router.Route;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
@Route("")
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
            try{
                PreparedStatement query = dbConnect.prepareStatement("INSERT INTO test_shit VALUES(?);");
                query.setString(1, tf.getValue());
                query.executeUpdate();
            } catch(SQLException e){
                e.printStackTrace();
            }    
        });
        add(tf);
        add(b);
    }

}
