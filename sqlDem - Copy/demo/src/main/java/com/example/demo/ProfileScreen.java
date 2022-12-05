package com.example.demo;

    
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.sql.*;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;


//@Route("ProfScreen")
public class ProfileScreen extends Register{ 
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
        super(controller);
        this.userID = userID;
        retrieveInfo();
        title.setText("Profile");
        make.setText("Update");
        cancel.setText("Logout");//need to make this do something useful
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
            PreparedStatement query1 = con.prepareStatement("SELECT WEIGHT FROM DAY AS d1 where d1.day_owner_id = ? and NOT EXISTS(SELECT * FROM DAY AS d2 where d2.day_owner_id = ? and d2.date > d1.date);");
            query1.setInt(1, userID);
            query1.setInt(2, userID);
            ResultSet rs = query1.executeQuery();
            rs.next();
            double d = rs.getInt(1);
            weight.setValue(d);
  
        }
        catch (SQLException e) {
            e.printStackTrace();
            Dialog d = new Dialog();
            Paragraph p = new Paragraph("Error in retrieve weight");
            d.add(p);
            d.open();
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
            verifyDay();
            PreparedStatement query2 = con.prepareStatement("Update Day SET weight = ? WHERE Day_owner_ID = ? AND Date = ?;");
            query2.setInt(1, weight.getValue().intValue()); 
            query2.setInt(2, userID);
            query2.setDate(3, today);
            query2.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updatePassword(){
        try
        {
            PreparedStatement query2 = con.prepareStatement("Update USER SET Password = ? WHERE ID = ?");
            query2.setString(1, password.getValue()); 
            query2.setInt(2, userID);
            query2.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }

    // we are woke 
    public void updateGender() {
        try
        {
            PreparedStatement query1 = con.prepareStatement("UPDATE USER SET Sex = ? WHERE ID = ?;");
            query1.setString(1, gender.getValue());
            query1.setInt(2, userID);
            query1.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateInfo(){
        updateFname();
        updateLname();
        updateWeight();
        updateHeight();
        updateGender();
        updatePassword();
    }
}

