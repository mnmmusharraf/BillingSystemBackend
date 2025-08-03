package com.pahana.billingsystembackend.api;

import com.google.gson.Gson;
import com.pahana.billingsystembackend.model.Customer;
import com.pahana.billingsystembackend.service.CustomerService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/customer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerAPI {

    private final CustomerService customerService = new CustomerService();
    private final Gson gson = new Gson();

    // GET all Customer
    @GET
    public Response getAllCustomer() {
        List<Customer> customerList = customerService.getAllCustomer();
        String json = gson.toJson(customerList);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    // GET one customer by account number
    @GET
    @Path("/{accountNum}")
    public Response getCustomerByAccountNum(@PathParam("accountNum") String accountNum) {
        Customer customer = customerService.getCustomerByAccountNum(accountNum);
        if (customer != null) {
            String json = gson.toJson(customer);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(gson.toJson("Customer Not Found"))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    // POST new Customer
    @POST
    public Response createCustomer(String customerJson) {
        Customer customer = gson.fromJson(customerJson, Customer.class);
        boolean success = customerService.addCustomer(customer);

        if (success) {
            return Response.status(Response.Status.CREATED)
                    .entity(gson.toJson("Customer added successfully!"))
                    .build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(gson.toJson("Failed to Add Customer!"))
                    .build();
        }
    }

    // PUT (update) customer
    @PUT
    @Path("/{accountNum}")
    public Response updateCustomer(@PathParam("accountNum") String accountNum, String customerJson) {
        Customer customer = gson.fromJson(customerJson, Customer.class);
        boolean success = customerService.updateCustomer(customer, accountNum);
        if (success) {
            return Response.ok(gson.toJson("Customer updated successfully!")).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(gson.toJson("Failed to update Customer"))
                    .build();
        }
    }

    //DELETE Customer
    @DELETE
    @Path("/{accountNum}")
    public Response deleteCustomer(@PathParam("accountNum") String accountNum) {
        boolean success = customerService.deleteCustomer(accountNum);
        if (success) {
            return Response.ok(gson.toJson("Customer deleted successfully")).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(gson.toJson("Failed to delete Customer"))
                    .build();
        }
    }

}
