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
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;


//@Route("reg")
public class Register extends VerticalLayout { 
    private String[] exampleGender = {"Male", "Female", "Other"};
    private String[] profileType = {"Coach", "Athlete"};
    private LocalDate date;
    private Date birthDay;
    private boolean coach;
    protected Connection con;
    protected TextField fName = new TextField("First Name:");
    protected TextField lName = new TextField("Last Name:");
    protected TextField password = new TextField("Password:");
    protected NumberField height = new NumberField("Height(cm):");
    protected NumberField weight = new NumberField("Weight(kg):");
    protected ComboBox<String> gender = new ComboBox<>("Gender:");//declare combobox
    protected ComboBox<String> type = new ComboBox<>("Profile Type:");//declare combobox
    protected DatePicker.DatePickerI18n singleFormatI18n = new DatePicker.DatePickerI18n();
    protected DatePicker singleFormatDatePicker = new DatePicker("Date of Birth:");
    protected Button make = new Button("Make");
    protected H1 title = new H1("Register");
    protected Button cancel = new Button("Cancel");
    protected HorizontalLayout buttons = new HorizontalLayout();






    public Register (){
        setupTitle();
        setupNameFieldInput();
        date();
        setupGenderInput();
        setupProfileType();
        password();
        makeOrCancel();
        initConnection();
        setID();
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
        add(title);
    }

    private void setupNameFieldInput(){
        add(fName);
        add(lName);
        add(height);
        add(weight);
    }

    private void date(){
        singleFormatI18n.setDateFormat("yyyy-MM-dd");
        singleFormatDatePicker.setI18n(singleFormatI18n);
        add(singleFormatDatePicker);

    }
    private void setupGenderInput(){
        HorizontalLayout genderLayout = new HorizontalLayout();//declare layout
        gender.setItems(exampleGender);//put array of objects (Strings in this case) into combobox
        genderLayout.add(gender);//add combobox to layout
        add(genderLayout);//add layout to screen
    }

    private void setupProfileType(){
        HorizontalLayout profileTypeLayout = new HorizontalLayout();//declare layout
        type.setItems(profileType);//put array of objects (Strings in this case) into combobox
        profileTypeLayout.add(type);//add combobox to layout
        add(profileTypeLayout);//add layout to screen
    }

    private void makeOrCancel(){
        make.addClickListener(clickEvent -> {submit();});
        cancel.addClickListener(e ->//set up button as a link to register...
        cancel.getUI().ifPresent(ui ->
           ui.navigate(""))
        );
        buttons.add(make,cancel);
        add(buttons);
    }

    private void password(){
        add(password);
    }

    public void submit(){
        int newId = setID();
        if (weight.getValue() == null)
        {
            return;
        }
        try
        {
            PreparedStatement query = con.prepareStatement("INSERT INTO USER VALUES(?,?,?,?,?,?,?,?,?);");
            query.setInt(1, newId);
            query.setDate(2, java.sql.Date.valueOf(singleFormatDatePicker.getValue())); //birthday
            query.setString(3, gender.getValue()); // gender
            query.setString(4, fName.getValue()); //fname
            query.setString(5, lName.getValue()); // lname
            query.setInt(6, height.getValue().intValue()); // height
            query.setInt(7, weight.getValue().intValue()); // weight
            query.setString(8, password.getValue()); // password
            query.setString(9,type.getValue());
            query.executeUpdate();
            //add(new HomeScreen(newId));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private int setID(){//look for most likely next available ID
        try
        {
            PreparedStatement query1 = con.prepareStatement("SELECT COUNT(ID) FROM USER;");
            ResultSet count = query1.executeQuery();
            count.next();
            int maybeID = count.getInt(1);
            return verifyID(maybeID);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return 0;
        }
    }

    private int verifyID(int maybeID){ //check if ID is actually valid.  If so, set ID
        try
        {
            PreparedStatement query1 = con.prepareStatement("SELECT * FROM USER WHERE ID = ?;");
            query1.setInt(1, maybeID);
            ResultSet existing = query1.executeQuery();
            if (existing.next()){//if not empty
                return verifyID(maybeID + 1);// try next possible ID
            }
            else{
                return maybeID;
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return 0;
        }
    }
}