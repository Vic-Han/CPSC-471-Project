package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;


//@Route("HomeScreen")
public class HomeScreen extends AppLayout{
    Tab dashboard;
    Tab profile;
    Tab food;
    Tab exercise;
    Tab performanceMetric;
    Tab graph;
    private VerticalLayout content;
    private int userID;
    private LoginController controller;
    private Connection con;
    public HomeScreen(int userID) {
        this.userID = userID;
        DrawerToggle toggle = new DrawerToggle();

        H1 title = new H1("Fitness Notepad: Your user ID is "+ userID);
        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

        Tabs tabs = getTabs();
        content = new VerticalLayout();
        content.add(new Dashboard(userID));
        setContent(content);

        addToDrawer(tabs);
        addToNavbar(toggle, title);
        initializeConnection();
    }
    public HomeScreen(int userID, LoginController controller) {
        this(userID);
        this.controller = controller;
        initializeConnection();
        
    }

    public HomeScreen(){//testing only.. get rid of this shit asap
        this(1);
        initializeConnection();
    }

    public void initializeConnection(){
        
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost/tracker", "athlete", "cpsc");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }   
    
    }

    private Tabs getTabs() {

        Tabs tabs = new Tabs();
        dashboard = new Tab("Dashboard");
        profile = new Tab("Profile");
        food = new Tab("Foods");
        exercise = new Tab("Exercises");
        performanceMetric = new Tab("Performance Metrics");
        
        tabs.add( dashboard, profile, food, exercise, performanceMetric);
        tabs.addSelectedChangeListener(
                event -> setContent(event.getSelectedTab()));
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        return tabs;
    }

    private void setContent(Tab tab) {
        content.removeAll();

        if (tab.equals(dashboard)) {
            content.add(new Dashboard(userID));
        } else if (tab.equals(profile)) {
            // if the user's ID is in athlete or coach then load that corresponding profile screen
            if (athleteOrCoach(userID)){ 
                 content.add(new AthleteProfile(userID, controller));
            }
            else{
                content.add(new CoachProfile(userID, controller));
            }
        } else if (tab.equals(food)) {
            content.add(new FoodView(userID));
        } else if (tab.equals(exercise)) {
            content.add(new ExerciseView(userID));
        } else if (tab.equals(performanceMetric)) {
            content.add(new MetricView(userID));
        } 
    }

    private boolean athleteOrCoach(int userID){
        try{
        PreparedStatement query = con.prepareStatement("SELECT Athlete_ID FROM Athlete WHERE Athlete_ID = ?;");
        query.setInt(1, userID);
        ResultSet rs = query.executeQuery();
            if(rs.next()){ // if next row is not null, return true
                return true;
            }
            else{
                return false;
            }
        }
        catch(SQLException e){
            Dialog d = new Dialog();
            Paragraph p = new Paragraph("Error");
            d.add(p);
            d.open();
            return true;

        }
    
    } 
    
}