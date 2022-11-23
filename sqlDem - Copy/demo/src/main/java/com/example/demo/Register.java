package com.example.demo;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.sql.*;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;


@Route("reg")
public class Register extends VerticalLayout { 
    private String[] exampleGender = {"Male", "Female", "Other"};
    private String[] profileType = {"Coach", "Athlete"};
    private LocalDate date;
    private String fname;
    private String lname;
    private Date birthDay;
    private int height;
    private int weight;
    private String gender;
    private boolean coach;
    private int userID;
    private Connection con;


    public Register (){
        setupTitle();
        setupNameFieldInput();
        date();
        setupGenderInput();
        setupProfileType();
        Make();
        initConnection();
    }

    public void initConnection()
    {
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost/tracker", "athlete", "cpsc");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setupTitle(){
        H1 title = new H1("Register");
        add(title);
    }

    private void setupNameFieldInput(){
        TextField nameField = new TextField("First Name:");
        add(nameField);
        TextField nameField1 = new TextField("Last Name:");
        add(nameField1);
        TextField nameField2 = new TextField("Height(cm):");
        add(nameField2);
        TextField nameField3 = new TextField("Weight(kg):");
        add(nameField3);
    }

    private void date(){
        DatePicker.DatePickerI18n singleFormatI18n = new DatePicker.DatePickerI18n();
        singleFormatI18n.setDateFormat("yyyy-MM-dd");
        DatePicker singleFormatDatePicker = new DatePicker("Date of Birth:");
        singleFormatDatePicker.setI18n(singleFormatI18n);
        add(singleFormatDatePicker);

    }
    private void setupGenderInput(){
        HorizontalLayout genderLayout = new HorizontalLayout();//declare layout
        ComboBox<String> gender = new ComboBox<>("Gender:");//declare combobox
        gender.setItems(exampleGender);//put array of objects (Strings in this case) into combobox
        genderLayout.add(gender);//add combobox to layout
        add(genderLayout);//add layout to screen
    }

    private void setupProfileType(){
        HorizontalLayout profileTypeLayout = new HorizontalLayout();//declare layout
        ComboBox<String> type = new ComboBox<>("Profile Type:");//declare combobox
        type.setItems(profileType);//put array of objects (Strings in this case) into combobox
        profileTypeLayout.add(type);//add combobox to layout
        add(profileTypeLayout);//add layout to screen
    }

    private void Make(){
        Button updateMake = new Button("Make");
        add(updateMake);
    }

    public void submit(){

        try
        {
            PreparedStatement query = con.prepareStatement("INSERT INTO USER VALUES(?,?,?,?,?,?,?);");
            query.setInt(1, userID);
            query.setDate(2, birthDay);
            query.setString(3, gender);
            query.setString(4, fname);
            query.setString(5, lname);
            query.setInt(6, weight);
            query.setInt(7, height);
            query.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

}

