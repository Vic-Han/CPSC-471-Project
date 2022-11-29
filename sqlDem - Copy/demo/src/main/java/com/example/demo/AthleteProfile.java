package com.example.demo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class AthleteProfile extends ProfileScreen {
    private ArrayList<Integer> coaches = new ArrayList<Integer>(); 
    private int userID;
    ComboBox xd = new ComboBox("Choose Coach");


    public AthleteProfile(int userID){
        super(userID);        
        add(xd);
    }
    

    // ok so we first need to get all the coach id's into a combobox
    // from there when the athlete chooses a coach, that athlete id and coach id should be stored in accesses information?
    // if the athlete wants to add more coaches, we need to display available coaches to them which are all coaches - coaches they already have

    // to remove a coach we need to retrieve coach id's from accesses_information that correspond with that athlete_id
    public void registerCoach(int Coach_ID)
    {
        try
        {
        PreparedStatement query = con.prepareStatement("SELECT Coach_ID FROM accesses_information WHERE Athlete_ID = ?;");
        query.setInt(1, userID);
        ResultSet rs = query.executeQuery();
        coaches = new ArrayList<Integer>();
        while (rs.next())
        {
            coaches.add(rs.getInt(1));
        }
        xd.setItems(coaches);



        }
        catch(SQLException e)
        {

        }
    }

    public void removeCoach(int Coach_ID){
    

    }


    // add a list of coaches for the athlete to choose from in combobox
    // select coach_id from accesses_information where athlete_id = ?;
    // select coach_id from coach as c where 
                                  //    not exists (select * from acceses_information as ac where athlete_id = ? and c.coach_id = ac.coach_id)  


    // insert into accesses_information where athlete_id = ? and coach_id = ? 
}
