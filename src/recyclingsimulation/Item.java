/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recyclingsimulation;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import static recyclingsimulation.DbConnect.conn;
import static recyclingsimulation.DbConnect.rs;
import static recyclingsimulation.DbConnect.st;

/**
 *
 * @author avidekar
 */
public class Item {
    private static int id;
    private static String name;
    private static float price;
    
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
    
        public void setPrice(float price)
    {
        this.price = price;
    }
    
    public float getPrice()
    {
        return this.price;
    }
    
    public void addItemToItemsTable(String name, String price) throws SQLException
    {
        String query = "INSERT INTO `Item`(`name`, `price`) VALUES ('" + 
                name+"',"+
                price + ");";
        // create the java statement
        Statement st = conn.createStatement();

        // execute the query, and get a java resultset
        int rowInsertCount = st.executeUpdate(query);

    }
    
    public int findItemUsingName(String name) throws SQLException
    {
        String query = "Select * from `Item` where name ='" + name + "';";
        st = conn.createStatement();
        
        rs = st.executeQuery(query);
        int i=0;
        while(rs.next()){
            i++;
        }
        return i;
    }
    
    public int updateItemPrice(String name, String price) throws SQLException
    {
//        String query = "Select * from `Item` where name ='" + name + "';";
        String query = "UPDATE `Item` SET price=" + price + " WHERE name='" + name + "';";
        // create the java statement
        Statement st = conn.createStatement();

        // execute the query, and get a java resultset
        int rowInsertCount = st.executeUpdate(query);
        return rowInsertCount;
    }
    
    public ArrayList<String> fetchAllItems() throws SQLException
    {
        String query = "Select * from `Item`;";
        ArrayList<String> mylist = new ArrayList<String>();

        st = conn.createStatement();
        
        rs = st.executeQuery(query);
        while(rs.next()){
            mylist.add(rs.getString("name")+" ");
        }
        return mylist;
    }
    
    public ArrayList<String> fetchAllItemsForRcm(String rcm_name) throws SQLException
    {
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
    
    public ArrayList<String> fetchItems(String array_ids) throws SQLException
    {
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
    
    public int findItemId(String name) throws SQLException
    {
        String query = "Select id from `Item` where name ='" + name + "';";
        st = conn.createStatement();
        
        rs = st.executeQuery(query);
        int i=0;
        while(rs.next()){
            i = (int)rs.getDouble("id");
        }
        return i;
    }    
    
    public Object[] checkIfRcmAcceptsItem(String rcm_name, String item_name) throws SQLException
    {
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
    
    public float getPriceOfItem(String name) throws SQLException
    {
        String query = "Select price from `Item` where name ='" + name + "';";
        st = conn.createStatement();
        
        rs = st.executeQuery(query);
        float i=0;
        while(rs.next()){
            i=rs.getFloat("price");
        }
        return i;
    }
}
