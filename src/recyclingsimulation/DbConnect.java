/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recyclingsimulation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 *
 * @author kaushiksrivatsan
 */
public class DbConnect {
    static Connection conn=null;
    static Statement st;
    static ResultSet rs;

    static final String URL = "jdbc:mysql://localhost:8889/RecyclingSimulation";
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String USER = "root";
    static final String PASSWORD = "root";
       
    public DbConnect(){
        this.makeConnection();
    }
    
    public static Connection makeConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("insde db connect make connection try");
            conn = (Connection) DriverManager.getConnection(URL, USER, PASSWORD);
//            JOptionPane.showMessageDialog(null, "connection est");
        } catch(ClassNotFoundException e){
            System.out.println("Class not found exception catch");
            e.printStackTrace();
        } catch(SQLException e) {
            System.out.println("Class not sql exception catch");
            e.printStackTrace();
        }
        return conn;
    }
    
    public boolean authorizeAdmin(String username, String password) throws SQLException{
        String query = "Select * from User where username='" + username + "' and password='" + password + "'";
        st = conn.createStatement();
        
        rs = st.executeQuery(query);
        String[] name = new String[10];
        boolean result =false;
        while(rs.next()){
            result = true;
        }
        return result;
    }
    
    public String getRcmIds() throws SQLException{
        String query = "SELECT * FROM Rmos ORDER BY id DESC LIMIT 1";
        st = conn.createStatement();
        
        rs = st.executeQuery(query);
        String x = "";
        while (rs.next())
        {
            x = rs.getString("rcm_ids");
        }
        return x;
    }
    
    public ArrayList<String> fetchRcmDetails(String name) throws SQLException{
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
    
    public void addNewRcm(String name, String location, String capacity, String amount, int item_ids[]) throws SQLException{
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
    
    public void updateRcm(String update_name, String update_location, Boolean update_active, String update_capacity, String update_amount, int newArray[], String id) throws SQLException{
        String query = "Update `Rcm` set name = '"+ update_name + "', location ='" + update_location + "', active=" + update_active + ", item_list='" +Arrays.toString(newArray) +"', total_capacity = " + update_capacity + ", total_amount = "+ update_amount + " where id=" + id;
        Statement st = conn.createStatement();
        // execute the query, and get a java resultset
        int rowInsertCount = st.executeUpdate(query);
        }
    
    public void addRcmIdToRmos() throws SQLException{
        String query = "SELECT id FROM Rcm ORDER BY id DESC LIMIT 1";
        Statement st = conn.createStatement();

        // execute the query, and get a java resultset
        
        rs = st.executeQuery(query);
        if(rs.next()){
            updateRmos((int)rs.getDouble("id"));
        }
    }
    
    private void updateRmos(int i) throws SQLException {
        String query = "SELECT id FROM Rmos ORDER BY id DESC LIMIT 1";
        st = conn.createStatement();
        
        rs = st.executeQuery(query);
        String x;
        x = getRcmIds();
        x = x.substring(0, x.length()-1) + "," + i + "]'";
        String query2 = "UPDATE `Rmos` SET rcm_ids='" + x + " WHERE id=1;";
        Statement st2 = conn.createStatement();

        // execute the query, and get a java resultset
        int rowInsertCount2 = st2.executeUpdate(query2);
    }
    
    public void addItemToItemsTable(String name, String price) throws SQLException{
        String query = "INSERT INTO `Item`(`name`, `price`) VALUES ('" + 
                name+"',"+
                price + ");";
        // create the java statement
        Statement st = conn.createStatement();

        // execute the query, and get a java resultset
        int rowInsertCount = st.executeUpdate(query);

    }
    
    public int findItemUsingName(String name) throws SQLException{
        String query = "Select * from `Item` where name ='" + name + "';";
        st = conn.createStatement();
        
        rs = st.executeQuery(query);
        int i=0;
        while(rs.next()){
            i++;
        }
        return i;
    }
    
    public int updateItemPrice(String name, String price) throws SQLException{
//        String query = "Select * from `Item` where name ='" + name + "';";
        String query = "UPDATE `Item` SET price=" + price + " WHERE name='" + name + "';";
        // create the java statement
        Statement st = conn.createStatement();

        // execute the query, and get a java resultset
        int rowInsertCount = st.executeUpdate(query);
        return rowInsertCount;
    }

    public ArrayList<String> fetchAllItems() throws SQLException{
        String query = "Select * from `Item`;";
        ArrayList<String> mylist = new ArrayList<String>();

        st = conn.createStatement();
        
        rs = st.executeQuery(query);
        while(rs.next()){
            mylist.add(rs.getString("name")+" ");
        }
        return mylist;
    }
    
    public ArrayList<String> fetchAllItemsForRcm(String rcm_name) throws SQLException{
        System.out.println(rcm_name);
       String query = "Select * from `Item`;";
        ArrayList<String> mylist = new ArrayList<String>();
       st = conn.createStatement();
      
      rs = st.executeQuery(query);
       while(rs.next()){
           mylist.add(rs.getString("name")+" ");
        }
        return mylist;
    }
    
    public ArrayList<String> fetchItems(String array_ids) throws SQLException{
        String[] array_of_ids = new String[] {array_ids};
        ArrayList<String> mylist = new ArrayList<String>();
        String x = array_ids;
        x = x.substring(0, x.length()-1);
        x = x.substring(1, x.length());
        
        String query = "SELECT * FROM Item WHERE id IN (" + x + ")";
        st = conn.createStatement();
        rs = st.executeQuery(query);
        while(rs.next()){
            mylist.add(rs.getString("name")+" ");
        }
        return mylist;
    }
    
    public int findItemId(String name) throws SQLException{
        String query = "Select id from `Item` where name ='" + name + "';";
        st = conn.createStatement();
        
        rs = st.executeQuery(query);
        int i=0;
        while(rs.next()){
            i = (int)rs.getDouble("id");
        }
        return i;
    }
    
    public ArrayList<String> getAllRcmNames() throws SQLException{
        String query = "Select name from `Rcm`";
        st = conn.createStatement();
        
        rs = st.executeQuery(query);
        ArrayList<String> mylist = new ArrayList<String>();
        while(rs.next()){
            mylist.add(rs.getString("name"));
        }
        return mylist;
    }
    
    public Object[] getLastEmptiedDate(String name) throws SQLException{
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
    
    public ArrayList<String> getAllActiveRcmNames() throws SQLException{
        String query = "Select name from `Rcm` where active=1";
        st = conn.createStatement();
        
        rs = st.executeQuery(query);
        ArrayList<String> mylist = new ArrayList<String>();
        while(rs.next()){
            mylist.add(rs.getString("name"));
        }
        return mylist;
    }
    
    public Object[] checkIfRcmAcceptsItem(String rcm_name, String item_name) throws SQLException{
        String query = "Select * from `Item` where name ='" + item_name + "';";
        st = conn.createStatement();
        
        rs = st.executeQuery(query);
        String item_id="";
        while(rs.next()){
            item_id = rs.getString("id");
        }
        String query2 = "Select * from `Rcm` where name='" + rcm_name + "';";
        st = conn.createStatement();
        
        rs = st.executeQuery(query2);
        String rcm_id="";
        while(rs.next()){
            rcm_id = rs.getString("id");
        }
//        String query3 = "SELECT id FROM Rcm WHERE id=" + rcm_id + " AND item_list like '%" + item_id + "%'";
        String query3 = "SELECT item_list FROM Rcm WHERE id=" + rcm_id;
        System.out.println(query3);
        st = conn.createStatement();
        
        rs = st.executeQuery(query3);
        ArrayList<String> mylist = new ArrayList<String>();
        while(rs.next()){
//            mylist.add(rs.getString("id"));
            mylist.add(rs.getString("item_list"));
        }
//        return new Object[] {Integer.toString(mylist.size()), rcm_name, rcm_id, item_name, item_id};
        return new Object[] {mylist, rcm_name, rcm_id, item_name, item_id};
    }
    
    public float getPriceOfItem(String name) throws SQLException{
        String query = "Select price from `Item` where name ='" + name + "';";
        st = conn.createStatement();
        
        rs = st.executeQuery(query);
        float i=0;
        while(rs.next()){
            i=rs.getFloat("price");
        }
        return i;
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
    
    public void updateRcmAmount(String id, float weight, float price) throws SQLException{
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
    
    public void refillRcm(String name) throws SQLException{
        DateFormat newDate = new SimpleDateFormat("yyyy/MM/dd");
        Date x = new Date();
        String todayDate = newDate.format(x);
        System.out.println(todayDate);
        
        String query = "Update `Rcm` set total_capacity=1000, total_amount=2000, lastEmptied='" + todayDate + "' where name='" + name + "'";
        Statement st = conn.createStatement();
        // execute the query, and get a java resultset
        int rowInsertCount = st.executeUpdate(query);
    }
    
    public Boolean[] getRcmCapacityAndAmount(String id, float weight, float price) throws SQLException{
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
    
    public Object[] getTransactionDetailsCount() throws SQLException{
        String query = "SELECT rcm_name, count(1) as count from Transaction group by rcm_name";
        //        select count(1) as totalCount from Transaction
        Statement st = conn.createStatement();

        // execute the query, and get a java resultset
        rs = st.executeQuery(query);
        Object[] my_obj = new Object[10];
        ArrayList<String> name_array = new ArrayList<String>();
        ArrayList<String> count_array = new ArrayList<String>();
        while(rs.next()){
            name_array.add(rs.getString("rcm_name"));
            count_array.add(rs.getString("count"));
        }
        my_obj[0] = name_array;
        my_obj[1] = count_array;
        return my_obj;
    }
    
    public String getTotalCountOfTransacations() throws SQLException{
        String query = "select count(1) as totalCount from Transaction";
        Statement st = conn.createStatement();

        // execute the query, and get a java resultset
        rs = st.executeQuery(query);
        String value ="0";
        while(rs.next()){
            value = rs.getString("totalCount");
        }
        return value;
    }
    
    public Object[] getCapacityOfRcms() throws SQLException{
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
    
    public void deleteRcm(String name) throws SQLException{
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
