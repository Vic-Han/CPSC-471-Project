package com.example.demo;
import java.sql.*;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;  

public class Login extends VerticalLayout{
    private int userID;

    public Login(){
        setupTitle();
        setupNameFieldInput();
        setPasswordFieldINput();
        sendToRegister();

    }
     
    private void setupTitle(){
         H1 title = new H1("Login:");
         add(title);
     }
    
    private void setupNameFieldInput(){
        TextField nameField = new TextField("User ID:");
        add(nameField);
    }   

    private void setPasswordFieldINput(){
        PasswordField passwordField = new PasswordField();
        passwordField.setLabel("Password");
        passwordField.setValue("Ex@mplePassw0rd");
        add(passwordField);
    }

    private void sendToRegister(){
        HorizontalLayout buttons = new HorizontalLayout();
        Button login = new Button ("Login");
        Button register = new Button("Register");
        buttons.add(login, register);
        add(buttons);
    }


}