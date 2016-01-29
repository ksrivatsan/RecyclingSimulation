/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recyclingsimulation;

import java.sql.SQLException;
import static recyclingsimulation.DbConnect.st;
import java.sql.SQLException;
import java.sql.Statement;
import static recyclingsimulation.DbConnect.conn;
import static recyclingsimulation.DbConnect.rs;
import static recyclingsimulation.DbConnect.st;

/**
 *
 * @author avidekar
 */
public class Rmos 
{
    private static int id;
    private static String name;
    private static String rcm_id;
    
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
    
    public void setRcmId(String id)
    {
        this.rcm_id = id;
    }
    
    public String getRcmId()
    {
        return this.rcm_id;
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
    
        void updateRmos(int i) throws SQLException {
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
}
