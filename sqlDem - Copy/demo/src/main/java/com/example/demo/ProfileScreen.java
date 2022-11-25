package com.example.demo;

    
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.ZoneId;
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


@Route("ProfScreen")
public class ProfileScreen extends VerticalLayout{ 
    private int userID;
    private String[] exampleGender = {"Male", "Female", "Other"};
    private String[] profileType = {"Coach", "Athlete"};
    private LocalDate date;
    private Date birthDay;
    private boolean coach;
    private Connection con;
    TextField fName = new TextField("First Name:");
    TextField lName = new TextField("Last Name:");
    NumberField height = new NumberField("Height(cm):");
    NumberField weight = new NumberField("Weight(kg):");
    ComboBox<String> gender = new ComboBox<>("Gender:");//declare combobox
    ComboBox<String> type = new ComboBox<>("Profile Type:");//declare combobox
    DatePicker.DatePickerI18n singleFormatI18n = new DatePicker.DatePickerI18n();
    DatePicker singleFormatDatePicker = new DatePicker("Date of Birth:");

    public ProfileScreen (int userID) {
        this.userID = userID;
        setupTitle();
        setupNameFieldInput();
        date();
        setupGenderInput();
        setupProfileType();
        makeOrCancel();
        initConnection();
        retrieveInfo();
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
        HorizontalLayout buttons = new HorizontalLayout();
        Button make = new Button("Make");
       // make.addClickListener(clickEvent -> {submit();});
        Button cancel = new Button("Cancel");
        cancel.addClickListener(e ->//set up button as a link to register...
        cancel.getUI().ifPresent(ui ->
           ui.navigate(""))
        );
        buttons.add(make,cancel);
        add(buttons);
    }

    public void submit(){

        if (weight.getValue() == null)
        {
            return;
        }
        try
        {
            PreparedStatement query = con.prepareStatement("INSERT INTO USER VALUES(?,?,?,?,?,?,?);");
            query.setInt(1, userID);
            query.setDate(2, java.sql.Date.valueOf(singleFormatDatePicker.getValue())); //birthday
            query.setString(3, gender.getValue()); // gender
            query.setString(4, fName.getValue()); //fname
            query.setString(5, lName.getValue()); // lname
            query.setInt(6, height.getValue().intValue()); // height
            query.setInt(7, weight.getValue().intValue()); // weight
            query.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void retrieveFname() {
        try
        {
            PreparedStatement query1 = con.prepareStatement("SELECT First_name from USER WHERE ID = ?;");
            query1.setInt(1, userID);
            ResultSet rs = query1.executeQuery();
            rs.next();
            fName.setValue(rs.getString(1));
  
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void retrieveLname() {
        try
        {
            PreparedStatement query1 = con.prepareStatement("SELECT Last_name from USER WHERE ID = ?;");
            query1.setInt(1, userID);
            ResultSet rs = query1.executeQuery();
            rs.next();
            lName.setValue(rs.getString(1));
  
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void retrieveHeight() {
        try
        {
            PreparedStatement query1 = con.prepareStatement("SELECT Height from USER WHERE ID = ?;");
            query1.setInt(1, userID);
            ResultSet rs = query1.executeQuery();
            rs.next();
            double d = rs.getInt(1);
            height.setValue(d);
  
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void retrieveWeight() {
        try
        {
            PreparedStatement query1 = con.prepareStatement("SELECT weight from USER WHERE ID = ?;");
            query1.setInt(1, userID);
            ResultSet rs = query1.executeQuery();
            rs.next();
            double d = rs.getInt(1);
            weight.setValue(d);
  
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void retrieveBirthDay() {
        try
        {
            PreparedStatement query1 = con.prepareStatement("SELECT date_of_birth from USER WHERE ID = ?;");
            query1.setInt(1, userID);
            ResultSet rs = query1.executeQuery();
            rs.next();
            Date dummy = rs.getDate(1);
            LocalDate temp = dummy.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
         //   singleFormatDatePicker.setValue(rs.getDate(1).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void retrieveGender() {
        try
        {
            PreparedStatement query1 = con.prepareStatement("SELECT sex from USER WHERE ID = ?;");
            query1.setInt(1, userID);
            ResultSet rs = query1.executeQuery();
            rs.next();
            gender.setValue(rs.getString(1));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void retrieveProfType() {
        try
        {
            PreparedStatement query1 = con.prepareStatement("SELECT sex from USER WHERE ID = ?;");
            query1.setInt(1, userID);
            ResultSet rs = query1.executeQuery();
            rs.next();
            type.setValue(rs.getString(1));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void retrieveInfo(){
        retrieveBirthDay();
        retrieveFname();
        retrieveHeight();
        retrieveLname();
        retrieveWeight();
        retrieveGender();

    }
}

