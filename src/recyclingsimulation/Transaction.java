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
import java.util.Date;
import static recyclingsimulation.DbConnect.conn;
import static recyclingsimulation.DbConnect.rs;
import static recyclingsimulation.DbConnect.st;

public class Transaction 
{
    private static int id;
    private static String rcm_name;
    private static int rcm_id;
    private static String item_name;
    private static int item_id;
    private static float weight;
    private static float price;
    private static Date dateOfTransaction;
    
    public void setId(int usernameid)
    {
        this.id = usernameid;
    }
    
    public int getId()
    {
        return this.id;
    }
    
    public void setRcmName(String name)
    {
        this.rcm_name = name;
    }
    
    public String getRcmName()
    {
        return this.rcm_name;
    }
    
    public void setRcmId(int id)
    {
        this.rcm_id = id;
    }
    
    public int getRcmId()
    {
        return this.rcm_id;
    }
    
    public void setItemName(String name)
    {
        this.item_name = name;
    }
    
    public String getItemName()
    {
        return this.item_name;
    }
    
    public void setItemId(int id)
    {
        this.item_id = id;
    }
    
    public int getItemId()
    {
        return this.item_id;
    }
    
    public void setWeight(float weight)
    {
        this.weight = weight;
    }
    
    public float getWeight()
    {
        return this.weight;
    }
    
    public void setPrice(float price)
    {
        this.price = price;
    }
    
    public float getPrice()
    {
        return this.price;
    }
    
    public void setDateOfTransaction(Date tdate)
    {
        this.dateOfTransaction = tdate;
    }
    
    public Date getDateDateOfTransaction()
    {
        return this.dateOfTransaction;
    }
    
      public void addTransaction(String name_of_rcm, String id_of_rcm, String name_of_item, String id_of_item, float weight, float price) throws SQLException
      {
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
            Rcm r = new Rcm();
            r.updateRcmAmount(id_of_rcm, weight, price);
        }
    }
 
    public Object[] getTransactionDetailsCount() throws SQLException
    {
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
    
    public String getTotalCountOfTransacations() throws SQLException
    {
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
        
          
}
