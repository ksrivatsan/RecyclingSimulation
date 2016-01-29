/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recyclingsimulation;

/**
 *
 * @author avidekar
 */
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import static recyclingsimulation.DbConnect.conn;
import static recyclingsimulation.DbConnect.rs;
import static recyclingsimulation.DbConnect.st;

public class Rcm
{
    private static int id;
    private static String name;
    private static String location;
    private static int active;
    private static String item_list;
    private static int total_capacity;
    private static float total_amount;
    private static Date lastEmptied;
    
    public void setId(int usernameid)
    {
        this.id = usernameid;
    }
    
    public int getId()
    {
        return this.id;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getName()
    {
        return this.name;
    }
    
     public void setLocation(String location)
    {
        this.location = location;
    }
    
    public String getLocation()
    {
        return this.location;
    }
    
    public void setActive(int active)
    {
        this.active = active;
    }
    
    public int getActive()
    {
        return this.active;
    }
    
    public void setItemLitst(String list)
    {
        this.item_list = list;
    }
    
    public String getItemList()
    {
        return this.item_list;
    }
    
    public void setTotalCapacity(int capacity)
    {
        this.total_capacity = capacity;
    }
    
    public int getTotalCapacity()
    {
        return this.total_capacity;
    }
    
    public void setTotalAmount(float amount)
    {
        this.total_amount= amount;
    }
    
    public float getTotalAmount()
    {
        return this.total_amount;
    }
    
    public void setLastEmptied(Date tdate)
    {
        this.lastEmptied = tdate;
    }
    
    public Date getLastEmptied()
    {
        return this.getLastEmptied();
    }
    
    public ArrayList<String> fetchRcmDetails(String name) throws SQLException
    {
       String query = "Select * from `Rcm` where name ='" + name + "';";
       Statement st = conn.createStatement();

        // execute the query, and get a java resultset
        
        rs = st.executeQuery(query);
        String name_of_rcm, location, active, item_list, total_capacity, total_amount;
        ArrayList<String> mylist = new ArrayList<String>();
        name = location = item_list = total_capacity = total_amount = "";
        while(rs.next()){
            mylist.add(rs.getString("id"));
            mylist.add(rs.getString("name"));
            mylist.add(rs.getString("location"));
            mylist.add(rs.getString("active"));
            mylist.add(rs.getString("item_list"));
            mylist.add(rs.getString("total_capacity"));
            mylist.add(rs.getString("total_amount"));
        }
        return mylist;

    }
    
     public void addNewRcm(String name, String location, String capacity, String amount, int item_ids[]) throws SQLException
     {
        String query = "Insert INTO `Rcm`(`name`, `location`, `active`, `item_list`, `total_capacity`, `total_amount`) VALUES (\"" + 
                name+"\",\""+
                location+"\","+
                true+",'"+
                Arrays.toString(item_ids)+"',"+
                capacity+","+
                amount+");";
        
        Statement st = conn.createStatement();

        // execute the query, and get a java resultset
        int rowInsertCount = st.executeUpdate(query);
        
        if(rowInsertCount > 0){
            addRcmIdToRmos();
        }
    }
    
   public void updateRcm(String update_name, String update_location, Boolean update_active, String update_capacity, String update_amount, int newArray[], String id) throws SQLException
   {
        String query = "Update `Rcm` set name = '"+ update_name + "', location ='" + update_location + "', active=" + update_active + ", item_list='" +Arrays.toString(newArray) +"', total_capacity = " + update_capacity + ", total_amount = "+ update_amount + " where id=" + id;
        Statement st = conn.createStatement();
        // execute the query, and get a java resultset
        int rowInsertCount = st.executeUpdate(query);
   }     
    
    public void addRcmIdToRmos() throws SQLException
    {
        String query = "SELECT id FROM Rcm ORDER BY id DESC LIMIT 1";
        Statement st = conn.createStatement();

        // execute the query, and get a java resultset
        
        rs = st.executeQuery(query);
        if(rs.next()){
            Rmos r = new Rmos();
            r.updateRmos((int)rs.getDouble("id"));
        }
    } 
    public ArrayList<String> getAllRcmNames() throws SQLException
    {
        String query = "Select name from `Rcm`";
        st = conn.createStatement();
        
        rs = st.executeQuery(query);
        ArrayList<String> mylist = new ArrayList<String>();
        while(rs.next()){
            mylist.add(rs.getString("name"));
        }
        return mylist;
    }    
 
    public Object[] getLastEmptiedDate(String name) throws SQLException
    {
        String query = "Select * from `Rcm` where name='" + name + "'";
        st = conn.createStatement();
        
        rs = st.executeQuery(query);
        ArrayList<String> mylist_capacity = new ArrayList<String>();
        ArrayList<String> mylist_amount = new ArrayList<String>();
        ArrayList<String> mylist_date = new ArrayList<String>();
        Object[] obj = new Object[10];
        while(rs.next()){
            mylist_capacity.add(rs.getString("total_capacity"));
            mylist_amount.add(rs.getString("total_amount"));
            mylist_date.add(rs.getString("lastEmptied"));
        }
        obj[0] = mylist_capacity.get(0);
        obj[1] = mylist_amount.get(0);
        obj[2] = mylist_date.get(0);
        return obj;
    }    
    
    public ArrayList<String> getAllActiveRcmNames() throws SQLException
    {
        String query = "Select name from `Rcm` where active=1";
        st = conn.createStatement();
        
        rs = st.executeQuery(query);
        ArrayList<String> mylist = new ArrayList<String>();
        while(rs.next()){
            mylist.add(rs.getString("name"));
        }
        return mylist;
    }
    
 public void addTransaction(String name_of_rcm, String id_of_rcm, String name_of_item, String id_of_item, float weight, float price) throws SQLException{
        DateFormat newDate = new SimpleDateFormat("yyyy/MM/dd");
        Date x = new Date();
        String todayDate = newDate.format(x);
        System.out.println(todayDate);
        
        String query = "Insert INTO `Transaction`(`rcm_name`, `rcm_id`, `item_name`, `item_id`, `weight`, `price`, `dateOfTransaction`) VALUES (\"" + 
                name_of_rcm+"\","+
                id_of_rcm+",'"+
                name_of_item+"',"+
                id_of_item+","+
                weight+","+
                price+",'" + todayDate +"');";
        System.out.println(query);
        
        Statement st = conn.createStatement();

        // execute the query, and get a java resultset
        int rowInsertCount = st.executeUpdate(query);
        
        if(rowInsertCount > 0){
            updateRcmAmount(id_of_rcm, weight, price);
        }
    }
 
    public void updateRcmAmount(String id, float weight, float price) throws SQLException
    {
        String query= "Select * from `Rcm` where id=" + id;
        Statement st = conn.createStatement();

        // execute the query, and get a java resultset
        rs = st.executeQuery(query);
        int capacity = 0;
        int total_capacity = 0;
        float amount =0;
        float total_amount = 0;
        while(rs.next()){
            capacity = rs.getInt("total_capacity");
            amount = rs.getFloat("total_amount");
        }
        System.out.println("Cap TABLEis " +capacity);
        System.out.println("Amount TABLE is " +amount);
        total_amount = amount - price;
        total_capacity = capacity - Math.round(weight);
        System.out.println(total_amount);
        System.out.println(total_capacity);
        String query2 = "Update `Rcm` set total_amount="+ total_amount + ", total_capacity=" + total_capacity + " where id=" + id;
        Statement st1 = conn.createStatement();
        // execute the query, and get a java resultset
        int rowInsertCount = st1.executeUpdate(query2);
    }
    
    public void refillRcm(String name) throws SQLException
    {
        DateFormat newDate = new SimpleDateFormat("yyyy/MM/dd");
        Date x = new Date();
        String todayDate = newDate.format(x);
        System.out.println(todayDate);
        
        String query = "Update `Rcm` set total_capacity=1000, total_amount=2000, lastEmptied='" + todayDate + "' where name='" + name + "'";
        Statement st = conn.createStatement();
        // execute the query, and get a java resultset
        int rowInsertCount = st.executeUpdate(query);
    }

    public Boolean[] getRcmCapacityAndAmount(String id, float weight, float price) throws SQLException
    {
        String query = "Select * from `Rcm` where id =" + id;
        Statement st = conn.createStatement();

        // execute the query, and get a java resultset
        rs = st.executeQuery(query);
        Boolean a=false;
        Boolean b=false;
        ArrayList<String> mylist = new ArrayList<String>();
        String[] totalExpenseLimit = new String[1];
        while(rs.next()){
            totalExpenseLimit[0] = rs.getString("total_capacity");
            mylist.add(rs.getString("total_capacity"));
            mylist.add(rs.getString("total_amount"));
        }
        if(Float.valueOf(totalExpenseLimit[0]) >= weight){
            a= true;
        }
        if(Float.valueOf(mylist.get(0)) >= weight){
            a = true;
        }
        if(Float.valueOf(mylist.get(1)) >= price){
            b = true;
        }
        return new Boolean[] {a, b};
    }    
    
    public Object[] getCapacityOfRcms() throws SQLException
    {
        String query = "select * from `Rcm`";
        Statement st = conn.createStatement();

        // execute the query, and get a java resultset
        rs = st.executeQuery(query);
        ArrayList<String> mylist_capacity = new ArrayList<String>();
        ArrayList<String> mylist_name = new ArrayList<String>();
        Object[] obj = new Object[2];
        while(rs.next()){
            mylist_name.add(rs.getString("name"));
            mylist_capacity.add(rs.getString("total_capacity"));
        }
        obj[0] = mylist_name;
        obj[1] = mylist_capacity;
        return obj;
    }    
    
   public void deleteRcm(String name) throws SQLException
   {
        String query = "Delete from `Rcm` where name='" + name + "'";
        Statement st = conn.createStatement();

        // execute the query, and get a java resultset
        st.executeUpdate(query);
        
        String query1 = "Delete from `Transaction` where rcm_name='" + name + "'";
        Statement st1 = conn.createStatement();

        // execute the query, and get a java resultset
        st1.executeUpdate(query1);
        
    }    
}
