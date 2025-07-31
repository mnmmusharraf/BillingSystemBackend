package com.pahana.billingsystembackend.service;

import com.pahana.billingsystembackend.dao.StaffDAO;
import com.pahana.billingsystembackend.model.Staff;

public class StaffService {
    
    private StaffDAO staffDAO;
    
    public StaffService(){
        this.staffDAO = new StaffDAO();
    }
    
    // Validate login by checking username and password
    public boolean login(String username, String password){
        return staffDAO.validateLogin(username, password);
    }
    
    public Staff getStaff(String username){
        return staffDAO.getStaffByUsername(username);
    }
}
