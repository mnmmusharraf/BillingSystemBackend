package com.pahana.billingsystembackend.api;

import com.google.gson.Gson;
import com.pahana.billingsystembackend.model.Billing;
import com.pahana.billingsystembackend.service.BillingService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.List;

@Path("/billing")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BillingAPI {

    private final BillingService billingService = new BillingService();
    private final Gson gson = new Gson();

    // Create a new bill (with items)
    @POST
    public Response createBill(String billJson) {
        try {
            Billing bill = gson.fromJson(billJson, Billing.class);
            int billId = billingService.createBill(bill);
            bill.setId(billId);
            return Response.status(Response.Status.CREATED).entity(gson.toJson(bill)).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Failed to create bill.\"}").build();
        }
    }

    // Get bill by ID
    @GET
    @Path("/{id}")
    public Response getBillById(@PathParam("id") int id) {
        try {
            Billing bill = billingService.getBillById(id);
            if (bill == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("{\"message\":\"Bill not found.\"}").build();
            }
            return Response.ok(gson.toJson(bill)).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Failed to retrieve bill.\"}").build();
        }
    }

    // Get all bills
    @GET
    public Response getAllBills() {
        try {
            List<Billing> bills = billingService.getAllBills();
            return Response.ok(gson.toJson(bills)).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Failed to retrieve bills.\"}").build();
        }
    }

    // Update a bill
    @PUT
    @Path("/{id}")
    public Response updateBill(@PathParam("id") int id, String billJson) {
        try {
            Billing bill = gson.fromJson(billJson, Billing.class);
            bill.setId(id);
            boolean updated = billingService.updateBill(bill);
            if (updated) {
                return Response.ok("{\"message\":\"Bill updated successfully.\"}").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("{\"message\":\"Bill not found.\"}").build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Failed to update bill.\"}").build();
        }
    }

    // Delete a bill
    @DELETE
    @Path("/{id}")
    public Response deleteBill(@PathParam("id") int id) {
        try {
            boolean deleted = billingService.deleteBill(id);
            if (deleted) {
                return Response.ok("{\"message\":\"Bill deleted successfully.\"}").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("{\"message\":\"Bill not found.\"}").build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Failed to delete bill.\"}").build();
        }
    }
}