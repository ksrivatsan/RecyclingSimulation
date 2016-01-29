/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recyclingsimulation;

import java.sql.SQLException;
import static recyclingsimulation.DbConnect.conn;
import static recyclingsimulation.DbConnect.rs;
import static recyclingsimulation.DbConnect.st;

/**
 *
 * @author avidekar
 */
public class User 
{
    private static int id;
    private static String name;
    private static String username;
    private static String password;
    
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
    
        public void setUsername(String uname)
    {
        this.username = uname;
    }
    
    public String getUsername()
    {
        return this.username;
    }
    
    public void setPassword(String pwd)
    {
        this.password = pwd;
    }
    
    public String getPassword()
    {
        return this.password;
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
}

