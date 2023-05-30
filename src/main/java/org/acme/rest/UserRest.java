package org.acme.rest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.entity.User;
import org.acme.service.UserService;

import java.util.List;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class UserRest {

    @Inject
    UserService userService;

    @GET
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GET
    @Path("/{id}")
    public User getUserById(@PathParam("id") Long id){
        return userService.getUserById(id);
    }

    @POST
    public Response addUser(User user){
        return userService.addUser(user);
    }

    @PUT
    @Path("/{id}")
    public Response updateUser(@PathParam("id") Long id, User updatedUser){
        return userService.updateUser(id, updatedUser);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") Long id){
        return userService.deleteUser(id);
    }

}
