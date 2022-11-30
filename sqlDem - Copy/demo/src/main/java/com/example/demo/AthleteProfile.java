package com.example.demo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class AthleteProfile extends ProfileScreen {
    private ArrayList<Integer> coaches = new ArrayList<Integer>(); 
    HorizontalLayout ho = new HorizontalLayout();
    ComboBox<Integer> xd = new ComboBox<Integer>("Choose Coach:");
    ComboBox<Integer> rem = new ComboBox<Integer>("Remove Coach:");
    Button register = new Button("Register Coach");


    public AthleteProfile(int userID, LoginController l){
        super(userID, l); 
        registerCoach(userID);
        add(xd);
        registerButton();
    }
    

    // ok so we first need to get all the coach id's into a combobox
    // from there when the athlete chooses a coach, that athlete id and coach id should be stored in accesses information?
    // if the athlete wants to add more coaches, we need to display available coaches to them which are all coaches - coaches they already have

    // to remove a coach we need to retrieve coach id's from accesses_information that correspond with that athlete_id
    public void registerCoach(int Coach_ID)
    {
        try
        {
        PreparedStatement query = con.prepareStatement("SELECT * FROM Coach AS C WHERE NOT EXISTS(SELECT * FROM accesses_information AS A WHERE A.coach_id = C.coach_id and A.athlete_id = ?);");
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
            Dialog d = new Dialog();
            Paragraph p = new Paragraph("Error");
            d.add(p);
            d.open();
        }
    }

    public void registerButton(){
        register.addClickListener(clickEvent -> {insertToAI(); registerCoach(userID);});
        add(register);
    }


    public void insertToAI(){
        try{
            PreparedStatement query200 = con.prepareStatement("INSERT INTO accesses_information(Athlete_ID, Coach_ID) VALUES(?,?);");
            query200.setInt(1, userID);
            query200.setInt(2, xd.getValue());
            query200.executeUpdate();
        }
        catch(SQLException e){

        }

    }

    // the option to remove a coach should only be available if an athlete already has a coach 
    /* 
    public void removeCoach(int Coach_ID)
    {
        try 
        {
            PreparedStatement query9000 = con.prepareStatement("SELECT Coach_ID from accesses_information where Athlete_ID = ?;");
            query9000.setInt(1, userID);
            ResultSet rs = query9000.executeQuery();
            coaches = new ArrayList<Integer>();
            while (rs.next()){
                coaches.add(rs.getInt(1));
            }
            rem.setItems(coaches);


        }
        catch(SQLException e)
        {

        }

    

    }
*/

    // add a list of coaches for the athlete to choose from in combobox
    // select coach_id from accesses_information where athlete_id = ?;
    // select coach_id from coach as c where 
                                  //    not exists (select * from acceses_information as ac where athlete_id = ? and c.coach_id = ac.coach_id)  


    // "INSERT INTO accesses_information (Athlete_ID, Coach_ID) VALUES(?,?);";
}
