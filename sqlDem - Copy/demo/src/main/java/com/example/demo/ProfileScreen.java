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


//@Route("ProfScreen")
public class ProfileScreen extends Register{ 
     private int userID;
     private LoginController controller;

     public ProfileScreen(int userID)

     {
         super();
         this.userID = userID;
         retrieveInfo();
         title.setText("Profile");
         make.setText("Update");
         cancel.setText("Logout");//need to make this do something useful
     }
    public ProfileScreen(int userID, LoginController controller){
        this(userID);
        this.controller = controller;
    }

    @Override
    protected void submit(){

        if (weight.getValue() == null)
        {
            return;
        }
        updateInfo();

    }
    @Override
    protected void cancelRegister(){
        controller.logout();
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
            PreparedStatement query1 = con.prepareStatement("SELECT Date_of_birth FROM USER WHERE ID = ?;");
            query1.setInt(1, userID);
            ResultSet rs = query1.executeQuery();
            rs.next();
            Date dummy = rs.getDate(1);
            //LocalDate temp = dummy.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate temp = dummy.toLocalDate();
            singleFormatDatePicker.setValue(temp);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void retrieveGender() {
        try
        {
            PreparedStatement query1 = con.prepareStatement("SELECT Sex FROM USER WHERE ID = ?;");
            query1.setInt(1, userID);
            ResultSet rs = query1.executeQuery();
            rs.next();
            gender.setValue(rs.getString(1));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void retrievePassword() {
        try
        {
            PreparedStatement query1 = con.prepareStatement("SELECT Password FROM USER WHERE ID = ?;");
            query1.setInt(1, userID);
            ResultSet rs = query1.executeQuery();
            rs.next();
            password.setValue(rs.getString(1));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void retrieveProfType() {
        try
        {
            PreparedStatement query1 = con.prepareStatement("SELECT * from COACH WHERE Coach_ID = ?;");
            query1.setInt(1, userID);
            ResultSet rs = query1.executeQuery();
            if (rs.next()){
                type.setValue("Coach");
            }
            else{
                type.setValue("Athlete");
            }
            
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
        retrievePassword();
        retrieveBirthDay();
        retrieveProfType();

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

