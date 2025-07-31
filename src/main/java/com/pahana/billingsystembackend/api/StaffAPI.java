package com.pahana.billingsystembackend.api;

import com.google.gson.Gson;
import com.pahana.billingsystembackend.model.Staff;
import com.pahana.billingsystembackend.service.StaffService;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/staff")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class StaffAPI {
    
    private final StaffService staffService = new StaffService();
    private final Gson gson = new Gson();
    
    // POST Login Staff
    @POST 
    @Path("/login") 
    public Response loginStaff(String staffJson){
        Staff staffRequest = gson.fromJson(staffJson, Staff.class);
        boolean isValid = staffService.login(staffRequest.getUsername(), staffRequest.getPassword());
        
        if(isValid){
            Staff staff = staffService.getStaff(staffRequest.getUsername());
            String json = gson.toJson(new LoginResponse(true, "Login Successful", staff.getUsername()));
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        }else{
            String json = gson.toJson(new LoginResponse(false, "Invalid username or password",null));
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(json)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }
    
    private static class LoginResponse{
        boolean success;
        String message;
        String username;
        
        public LoginResponse(boolean success, String message, String username){
            this.success = success;
            this.message = message;
            this.username = username;
        }
    }
    
}
