package com.pahana.billingsystembackend.service;

import com.pahana.billingsystembackend.dao.ItemDAO;
import com.pahana.billingsystembackend.model.Item;
import java.util.List;


public class ItemService {
    
    private ItemDAO itemDAO;
    
    public ItemService(){
        this.itemDAO = new ItemDAO();
    }
    
    public List<Item> getAllItems(){
        return itemDAO.getAllItems();
    }
    
    public boolean addItem(Item item){
        return itemDAO.addItem(item);
    }
    
    public Item getItemById(int itemId){
        return itemDAO.getItemById(itemId);
    }
    
    public boolean updateItem(Item item, int itemId){
        return itemDAO.updateItem(item, itemId);
    }
    
    public boolean deleteItem(int itemId){
        return itemDAO.deleteItem(itemId);
    }
    
    public boolean isItemNameExists(String itemName){
        return itemDAO.isItemNameExists(itemName);
    }
}
