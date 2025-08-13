package com.pahana.billingsystembackend.api;

import com.google.gson.Gson;
import com.pahana.billingsystembackend.model.CartItem;
import com.pahana.billingsystembackend.model.Item;
import com.pahana.billingsystembackend.service.ItemService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Path("/cart")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CartAPI {
    
    private final ItemService itemService = new ItemService();
    private final Gson gson = new Gson();
    
    @POST
    @Path("/add")
    public Response addToCart(@Context HttpServletRequest request, CartItem item) {
        HttpSession session = request.getSession();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) cart = new ArrayList<>();

        cart.add(item);
        session.setAttribute("cart", cart);

        return Response.ok(cart).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewCart(@Context HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) cart = new ArrayList<>();

        // Merge duplicates by itemId
        Map<Integer, Integer> quantityMap = new HashMap<>();
        for (CartItem item : cart) {
            quantityMap.put(item.getItemId(),
                quantityMap.getOrDefault(item.getItemId(), 0) + item.getQuantity());
        }

        // Fetch details from DB and build final list
        List<CartItem> detailedCart = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : quantityMap.entrySet()) {
            Item dbItem = itemService.getItemById(entry.getKey());
            if (dbItem != null) {
                detailedCart.add(new CartItem(
                    dbItem.getItemId(),
                    dbItem.getItemName(),
                    dbItem.getItemPrice(),
                    entry.getValue() // merged quantity
                ));
            }
        }

        return Response.ok(detailedCart).build();
    }



    @DELETE
    @Path("/clear")
    public Response clearCart(@Context HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("cart");
        return Response.ok().build();
    }
    
}
