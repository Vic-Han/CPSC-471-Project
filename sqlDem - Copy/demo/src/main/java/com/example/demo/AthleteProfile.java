package com.example.demo;

public class AthleteProfile extends ProfileScreen {

    public AthleteProfile(int userID){
        super(userID);
    }
    

    // add a list of coaches for the athlete to choose from in combobox
    // select coach_id from accesses_information where athlete_id = ?;
    // select coach_id from coach as c where 
                                  //    not exists (select * from acceses_information as ac where athlete_id = ? and c.coach_id = ac.coach_id)  

}
