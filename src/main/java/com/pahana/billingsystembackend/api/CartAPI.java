package com.pahana.billingsystembackend.api;

import com.pahana.billingsystembackend.model.CartItem;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;


@Path("/cart")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CartAPI {
    
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
        return Response.ok(cart).build();
    }

    @DELETE
    @Path("/clear")
    public Response clearCart(@Context HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("cart");
        return Response.ok().build();
    }
    
}
