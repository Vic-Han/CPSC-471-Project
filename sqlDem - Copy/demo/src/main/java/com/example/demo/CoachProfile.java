package com.example.demo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Paragraph;

public class CoachProfile extends ProfileScreen {
    private ArrayList<Integer> athletes = new ArrayList<Integer>(); 
    ComboBox<Integer> xd = new ComboBox<Integer>("View Athlete:");

    public CoachProfile(int userID, LoginController l){
        super(userID, l);
    }

    public void viewAthlete(int Athlete_ID)
    {
        try
        {
        PreparedStatement query = con.prepareStatement("SELECT Athlete_ID FROM accesses_information where Coach_ID = ?;");
        query.setInt(1, userID);
        ResultSet rs = query.executeQuery();
        athletes = new ArrayList<Integer>();
        while (rs.next())
        {
            athletes.add(rs.getInt(1));
        }
        xd.setItems(athletes);
        add(xd);

        }
        catch(SQLException e)

        {
            Dialog d = new Dialog();
            Paragraph p = new Paragraph("Error");
            d.add(p);
            d.open();
        }
    }
    
        // select athlete_id from accesses_information where coach_id = ?;

}
