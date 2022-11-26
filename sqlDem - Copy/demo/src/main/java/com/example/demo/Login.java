package com.example.demo;
import java.sql.*;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;  


//@Route("")
public class Login extends VerticalLayout{
    private int userID;
    private Connection con;
    PasswordField passwordField = new PasswordField();
    NumberField numberField = new NumberField("User ID:");
    Button login = new Button ("Login");
    Button register = new Button("Register");
    Button forgotPassword = new Button("Forgot username or password");
    LoginController controller;




 
    public void initConnection()
    {
         
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost/tracker", "athlete", "cpsc");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }   
    }
    
    public Login(LoginController controller){
        setupTitle();
        setupNameFieldInput();
        setPasswordFieldINput();
        sendToRegister();
        initConnection();
        this.controller = controller;
        

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
        login.addClickListener(clickEvent -> {validate();});
        register.addClickListener(e -> controller.registerUser());
        forgotPassword.addClickListener(e ->{
            Dialog d = new Dialog();
            d.add(new Paragraph("That really sucks, you're on your own buddy"));
            d.open();
        });
        buttons.add(login, register, forgotPassword);
        add(buttons);
    }

     private void validate(){
        if (numberField.getValue() == null)
        {     
            return;
        }
        try {

        PreparedStatement query1 = con.prepareStatement("select First_name from USER where Password = ? AND ID = ?;");
        query1.setString(1, passwordField.getValue());
        query1.setInt(2, numberField.getValue().intValue());
        ResultSet rs = query1.executeQuery();
        
        if (rs.next()){
            controller.loginSuccess(userID);;
        }
        else{
            Dialog d = new Dialog();
            d.add(new Paragraph("UserID or password did not match.  Please try again"));
            d.open();
        }

        }catch(SQLException e)
        {
            e.printStackTrace();
            passwordField.setValue("exception");
        }
    }
}