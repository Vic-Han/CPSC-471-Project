package com.example.demo;
import com.vaadin.flow.router.Route;
import java.sql.*;
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
            Date temp = new Date(2002,04, 07);
            Workout testWorkout = new Workout(1,temp);
            ExerciseSubmission test = new ExerciseSubmission(testWorkout.getID(),1);
                //try{
                    
                    //tf.setValue();
                //}
                //catch(SQLException e)
                {

                }
        });
        add(tf);
        add(b);
    }

}
