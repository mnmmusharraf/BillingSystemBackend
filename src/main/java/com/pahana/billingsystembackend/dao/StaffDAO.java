package com.pahana.billingsystembackend.dao;

import com.pahana.billingsystembackend.db.DBConnection;
import com.pahana.billingsystembackend.model.Staff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StaffDAO {
    
    // Check if the username and password match an admin in the database
    
    public boolean validateLogin(String username, String password){
        String sql = "SELECT * FROM staff WHERE username = ? AND password = ?";
        
        try(Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)){
            
            stmt.setString(1, username);
            stmt.setString(2, password);
            
            ResultSet rs = stmt.executeQuery();
            
            return rs.next();
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    
    public Staff getStaffByUsername(String username){
        String sql = "SELECT * FROM staff WHERE username = ?";
        
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
                String password = rs.getString("password");
                return new Staff(username, password);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
}
