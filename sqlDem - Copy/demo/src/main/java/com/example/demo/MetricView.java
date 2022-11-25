package com.example.demo;
import java.sql.*;
import java.rmi.server.RemoteObject;
import java.util.ArrayList;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
@Route("metView")
public class MetricView extends VerticalLayout implements Editor<Metric>{
    private ArrayList<Metric> metricList = new ArrayList<Metric>();
    private int userID;
    private Connection con;
    private Button newMet = new Button("New Metric");
    private Button editMet = new Button("Edit Metric");
    private Button delMet = new Button("Delete Metric");
    ComboBox<Metric> chooseMet = new ComboBox<Metric>("Choose metric");
    public MetricView(int ID){
        userID = ID;
        setup();
        

    }
    public MetricView(){
        this(1);
    }
    private void setup(){
        initConnection();
        //make new metric button...
        newMet.addClickListener(clickEvent -> {
            MetricEditor editor = new MetricEditor(this);
            editor.open();
        });
        add(newMet);

        //make combobox...
        fetchData();
        chooseMet.setItemLabelGenerator(Metric::getName);
        add(chooseMet);
        
        //make edit exercise button...
        editMet.addClickListener(clickEvent -> {
            MetricEditor editor = new MetricEditor(this,chooseMet.getValue());
            editor.open();
        });
        add(editMet);
        delMet.addClickListener(clickEvent -> {deleteObject(chooseMet.getValue());});
        add(delMet);
    }
    public void initConnection()
    {
         
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost/tracker", "athlete", "cpsc");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }   
    }
    @Override
    public void fetchData()
    {
        try
        {
            PreparedStatement query = con.prepareStatement("SELECT Metric_name FROM PERFORMANCE_METRIC WHERE Owner_ID = ?;");
            query.setInt(1, userID);
            ResultSet rs = query.executeQuery();
            metricList = new ArrayList<Metric>();
            while(rs.next())
            {
                metricList.add(new Metric(rs.getString(1),userID));
            }
            chooseMet.setItems(metricList);
        }
        catch(SQLException e)
        {

        }


    }
    @Override
    public void addObject(Metric metric) {
        metricList.add(metric);
        fetchData();
    }
    @Override
    public void deleteObject(Metric metric) {
        try{
        metricList.remove(metric);
        PreparedStatement query1 = con.prepareStatement("DELETE FROM PERFORMANCE_METRIC WHERE Owner_ID = ? AND NAME = ? ;");
        query1.setInt(1,userID);
        query1.setString(2,metric.getName());
        query1.executeUpdate();
        PreparedStatement query2 = con.prepareStatement("DELETE FROM METRIC_DESCRIBES_EXERCISE WHERE Metric_user_ID = ? AND Metric_name = ?;");
        query2.setInt(1,userID);
        query2.setString(2,metric.getName());
        query2.executeUpdate();
        PreparedStatement query3 = con.prepareStatement("DELETE FROM METRIC MEASURES_SUBMISSION_WHERE Metric_owner_ID = ? AND Metric_name = ?;");
        query3.setInt(1,userID);
        query3.setString(2,metric.getName());
        query3.executeUpdate();
    
        }
        catch(SQLException e)
        {

        }

        fetchData();
    }
    @Override
    public int getUserID() {
        return userID;
    }


}