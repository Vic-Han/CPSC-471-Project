package sqlDemo;

import java.sql.*;
import java.util.ArrayList;
/**
 * This class communicates with the inventory.sql file and more precisely the database.
 * The database name is FOOD_INVENTORY.
 */
public class Demo {
    /**
     * Initializes the Connection to the database
     */
    private Connection dbConnect;
    private ResultSet results;


    public void initializeConnection(){
        
        try{
            dbConnect = DriverManager.getConnection("jdbc:mysql://localhost/tracker", "athlete", "cpsc");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }   
    
    } 
   
    public void copyFoodTable(){
        
        try {                    
           Statement myStmt = dbConnect.createStatement();
           results = myStmt.executeQuery("SELECT * FROM "+ "AVAILABLE_FOOD" );
           
			//int i = 0;
           while (results.next()){
                ArrayList<String> stringArr = new ArrayList<>();
                stringArr.add(results.getString("ItemID"));
                stringArr.add(results.getString("Name"));
                stringArr.add(results.getString("GrainContent"));
                stringArr.add(results.getString("FVContent"));
                stringArr.add(results.getString("ProContent"));
                stringArr.add(results.getString("Other"));
                stringArr.add(results.getString("Calories"));
                //i++;

               //lastAndFirst.append(results.getString("LName") + ", " + results.getString("FName"));
           }
           myStmt.close();
       } catch (SQLException ex) {
           ex.printStackTrace();
       }
       
   }   
   /**
    * Clears the 2D ArrayList and copies the entire database to the 2D ArrayList again. 
    */
    public void refreshArray(){
        try {                    
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM "+ "AVAILABLE_FOOD");
            
            
            while (results.next()){
                ArrayList<String> stringArr = new ArrayList<>();
                stringArr.add(results.getString("ItemID")); // 0
                stringArr.add(results.getString("Name")); // 1
                stringArr.add(results.getString("GrainContent")); // 2
                stringArr.add(results.getString("FVContent")); // 3
                stringArr.add(results.getString("ProContent"));// 4
                stringArr.add(results.getString("Other"));// 5
                stringArr.add(results.getString("Calories"));// 6

            }
            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
   }
  
   public void newTable(){
    
    try {                    
        Statement myStmt = dbConnect.createStatement();
       myStmt.execute("DROP TABLE IF EXISTS AVAILABLE_FOOD;");
        // myStmt.execute("DROP TABLE IF EXISTS AVAILABLE_FOOD;");
        myStmt.executeUpdate( 
        "CREATE TABLE AVAILABLE_FOOD (\n" + 
            "ItemID			int not null AUTO_INCREMENT,\n"+
            "Name			varchar(50),\n"+
            "GrainContent	int,"+
            "FVContent		int,"+
            "ProContent		int,"+
            "Other			int,"+
            "Calories		int,"+
            "primary key (ItemID));");
        
        
        /* 
        for ( int i = 0; i < inventoryArray.size(); i++){
            // ('Tomato Sauce, jar', 0, 80, 10, 10, 120), 
            String all ="INSERT INTO AVAILABLE_FOOD (ItemID,Name, GrainContent, FVContent, ProContent, Other, Calories)\n"+
            "VALUES (?,?,?,?,?,?,?)";
            PreparedStatement pst = dbConnect.prepareStatement(all);
            pst.setString(1, inventoryArray.get(i).get(0));
            pst.setString(2, inventoryArray.get(i).get(1));
            pst.setString(3, inventoryArray.get(i).get(2));
            pst.setString(4, inventoryArray.get(i).get(3));
            pst.setString(5, inventoryArray.get(i).get(4));
            pst.setString(6, inventoryArray.get(i).get(5));
            pst.setString(7, inventoryArray.get(i).get(6));
            pst.executeUpdate();

            
        }*/
        myStmt.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
   }

   
}