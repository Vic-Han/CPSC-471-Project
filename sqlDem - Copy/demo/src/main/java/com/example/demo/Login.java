package com.example.demo;
import java.sql.*;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;  


@Route("")
public class Login extends VerticalLayout{
    private int userID;
    private Connection con;
    PasswordField passwordField = new PasswordField();
    NumberField numberField = new NumberField("User ID:");

 
    public void initConnection()
    {
         
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost/tracker", "athlete", "cpsc");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }   
    }
    


    public Login(){
        setupTitle();
        setupNameFieldInput();
        setPasswordFieldINput();
        sendToRegister();
        initConnection();

    }
     
    private void setupTitle(){
         H1 title = new H1("Login:");
         add(title);
     }
    
    private void setupNameFieldInput(){
        add(numberField);
    }   

    private void setPasswordFieldINput(){
        passwordField.setLabel("Password");
        passwordField.setValue("");
        add(passwordField);
    }

    private void sendToRegister(){
        HorizontalLayout buttons = new HorizontalLayout();
        Button login = new Button ("Login");
        login.addClickListener(clickEvent -> {validate();});
        Button register = new Button("Register");
        register.addClickListener(e ->//set up button as a link to metric editor...
        register.getUI().ifPresent(ui ->
           ui.navigate("reg"))
        );
        buttons.add(login, register);
        add(buttons);
    }

     private void validate(){
        if (numberField.getValue() == null)
        {
           
            return;
        }
        try {

        PreparedStatement query1 = con.prepareStatement("select first_name from USER where password = ? AND ID = ?;");
        query1.setString(1, passwordField.getValue());
        query1.setInt(2, numberField.getValue().intValue());
        ResultSet rs = query1.executeQuery();
        
        if (rs.next())
            passwordField.setValue("success");
        else
            passwordField.setValue("fail");
        }

        catch(SQLException e)
        {
            e.printStackTrace();
            passwordField.setValue("exception");
        }
    }
}