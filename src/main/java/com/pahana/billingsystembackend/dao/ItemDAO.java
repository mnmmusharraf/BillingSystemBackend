package com.pahana.billingsystembackend.dao;

import com.pahana.billingsystembackend.db.DBConnection;
import com.pahana.billingsystembackend.model.Item;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class ItemDAO {
    
    //Method to add item
    public boolean addItem(Item item){
        String query = "INSERT INTO item (name, price, stock_quantity) VALUES (?,?,?)";
        
        try(Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1, item.getItemName());
            stmt.setDouble(2, item.getItemPrice());
            stmt.setInt(3, item.getStockQuantity());
            
            int rows  = stmt.executeUpdate();
            
            return rows>0;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    
    //Method to get all items from the db
    public List<Item> getAllItems(){
        List<Item> itemList = new ArrayList<>();
        
        String query = "SELECT * FROM item";
        
        try(Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()){
                  while(rs.next()){
                      Item item = new Item();
                      item.setItemId(rs.getInt("item_id"));
                      item.setItemName(rs.getString("name"));
                      item.setItemPrice(rs.getDouble("price"));
                      item.setStockQuantity(rs.getInt("stock_quantity"));
                      
                      itemList.add(item);
                  }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return itemList;
    }
    
    // get item by id
    public Item getItemById(int itemId){
        String query = "SELECT * FROM item WHERE item_id =?";
        
        try(Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1,itemId);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
                Item item = new Item();
                item.setItemId(rs.getInt("item_id"));
                item.setItemName(rs.getString("name"));
                item.setItemPrice(rs.getDouble("price"));
                item.setStockQuantity(rs.getInt("stock_quantity"));
                
                return item;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
    // Update item
    public boolean updateItem(Item item, int itemId){
        String query = "UPDATE item SET name =?, price =?, stock_quantity =? WHERE item_id =?";
        
        try(Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1, item.getItemName());
            stmt.setDouble(2, item.getItemPrice());
            stmt.setInt(3, item.getStockQuantity());
            stmt.setInt(4, itemId);
            
            int rows = stmt.executeUpdate();
            
            return rows>0;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
        
    }
    
    // Delete Item
    public boolean deleteItem(int itemId){
        String query = "DELETE FROM item WHERE item_id =?";
        
        try(Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1,itemId);
            int rows = stmt.executeUpdate();
            return rows > 0;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean isItemNameExists(String itemName){
        String query = "SELECT item_id FROM item WHERE name = ?";
        try(Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1,itemName);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Item> searchItems(String searchTerms){
        List<Item> itemList = new ArrayList<>();
        
        String query = "SELECT * FROM item " +
                "WHERE item_id LIKE ? OR name LIKE ?";
        
        try(Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)){
            String searchPattern = "%" + searchTerms + "%";
            
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                Item item = new Item();
                 item.setItemId(rs.getInt("item_id"));
                item.setItemName(rs.getString("name"));
                item.setItemPrice(rs.getDouble("price"));
                item.setStockQuantity(rs.getInt("stock_quantity"));
                
                itemList.add(item);
            }
            return itemList;
        }catch(SQLException e){
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
