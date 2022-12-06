package com.example.demo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.example.demo.Dashboard;

public class CoachProfile extends ProfileScreen {
    private ArrayList<Integer> athletes = new ArrayList<Integer>(); 
    ComboBox<Integer> athleteBox = new ComboBox<Integer>("View Athlete:");
    Button view = new Button("View Athlete");


    public CoachProfile(int userID, LoginController controller){
        super(userID, controller);
        getAthlete(userID);
        add(athleteBox);
        viewButton();
    }

    public void getAthlete(int Athlete_ID)
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
        athleteBox.setItems(athletes);
        add(athleteBox);

        }
        catch(SQLException e)

        {
            Dialog d = new Dialog();
            Paragraph p = new Paragraph("Error");
            d.add(p);
            d.open();
        }
    }
    
    public void viewAthlete(int Athlete_id){

        Dialog dialog = new Dialog();

        dialog.setHeaderTitle(String.format("Athlete ID: %d", Athlete_id));

        dialog.add(new Dashboard(Athlete_id));

        Button cancelButton = new Button("Cancel", e -> dialog.close());
        dialog.getFooter().add(cancelButton);
        dialog.open();

    }


    public void viewButton(){
        view.addClickListener(clickEvent -> {viewAthlete(athleteBox.getValue().intValue());});
        add(view);
    }

}
