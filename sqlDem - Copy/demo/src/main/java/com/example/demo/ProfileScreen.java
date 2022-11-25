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
public class ProfileScreen extends Register{ 
     private int userID;

     public ProfileScreen(int userID)

     {
         super();
         this.userID = userID;
         retrieveInfo();
         title.setText("Profile");
         make.setText("Update");
         cancel.setText("Logout");
     }

    public void submit(){

        if (weight.getValue() == null)
        {
            return;
        }
        updateInfo();

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
      //  retrieveBirthDay();
        retrieveFname();
        retrieveHeight();
        retrieveLname();
        retrieveWeight();
        retrieveGender();

    }

    public void updateFname(){
        try
        {
            PreparedStatement query2 = con.prepareStatement("Update USER SET First_name = ? WHERE ID = ?");
            query2.setString(1, fName.getValue()); //fname
            query2.setInt(2, userID);
            query2.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void updateLname(){
        try
        {
            PreparedStatement query2 = con.prepareStatement("Update USER SET Last_name = ? WHERE ID = ?");
            query2.setString(1, lName.getValue()); 
            query2.setInt(2, userID);
            query2.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void updateHeight(){
        try
        {
            PreparedStatement query2 = con.prepareStatement("Update USER SET height = ? WHERE ID = ?");
            query2.setInt(1, height.getValue().intValue()); 
            query2.setInt(2, userID);
            query2.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateWeight(){
        try
        {
            PreparedStatement query2 = con.prepareStatement("Update USER SET weight = ? WHERE ID = ?");
            query2.setInt(1, weight.getValue().intValue()); 
            query2.setInt(2, userID);
            query2.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateInfo(){
        updateFname();
        updateLname();
        updateWeight();
        updateHeight();
    }
}

