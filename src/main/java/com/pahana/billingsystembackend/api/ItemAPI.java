package com.pahana.billingsystembackend.api;

import com.google.gson.Gson;
import com.pahana.billingsystembackend.model.Item;
import com.pahana.billingsystembackend.service.ItemService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/item")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ItemAPI {
    
    private final ItemService itemService = new ItemService();
    private final Gson gson = new Gson();
    
    @GET
    public Response getAllItems(){
        List<Item> itemList = itemService.getAllItems();
        String json = gson.toJson(itemList);
        return Response.ok(json,MediaType.APPLICATION_JSON).build();
    }
    
    @GET
    @Path("/search")
    public Response searchItems(@QueryParam("searchTerms") String searchTerms){
        List<Item> itemList = itemService.searchItems(searchTerms);
        String json = gson.toJson(itemList);
        return Response.ok(json,MediaType.APPLICATION_JSON).build();
    }
    
    @GET
    @Path("/{id}")
    public Response getItemById(@PathParam("id") int itemId){
        Item item = itemService.getItemById(itemId);
        if(item!=null){
            String json = gson.toJson(item);
            return Response.ok(json,MediaType.APPLICATION_JSON).build();
        }else{
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(gson.toJson("Item Not found."))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }
    
    @POST 
    public Response createItem(String itemJson){
        Item item = gson.fromJson(itemJson, Item.class);
        boolean success = itemService.addItem(item);
        
        if(success){
            return Response.status(Response.Status.CREATED)
                    .entity(gson.toJson("Item added successfully."))
                    .build();
        }else{
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(gson.toJson("Failed to add item."))
                    .build();
        }
    }
    
    @PUT
    @Path("/{id}")
    public Response updateItem(@PathParam("id") int itemId, String itemJson){
        Item item = gson.fromJson(itemJson, Item.class);
        boolean success = itemService.updateItem(item, itemId);
        if(success){
            return Response.ok(gson.toJson("Item updated successfully.")).build();
        }else{
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(gson.toJson("Failed to update item."))
                    .build();
        }
    }
    
    @DELETE
    @Path("/{id}")
    public Response deleteItem(@PathParam("id") int itemId){
        boolean success = itemService.deleteItem(itemId);
        
        if(success){
            return Response.ok(gson.toJson("Item deleted successfully.")).build();
        }else{
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(gson.toJson("Failed to delete item."))
                    .build();
        }
    }
    
    private static class UniqueCheckResponse{
        boolean itemNameExists;
        
        public UniqueCheckResponse(boolean itemNameExists){
            this.itemNameExists = itemNameExists;
        }
    }
    
    @GET
    @Path("/check-unique")
    public Response checkUnique(@QueryParam("itemName") String itemName){
        boolean itemNameExists = itemService.isItemNameExists(itemName);
        
        String json = gson.toJson(new UniqueCheckResponse(itemNameExists));
        return Response.ok(json,MediaType.APPLICATION_JSON).build();
    }
}
