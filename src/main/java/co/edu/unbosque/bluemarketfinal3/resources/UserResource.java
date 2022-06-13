package co.edu.unbosque.bluemarketfinal3.resources;

import co.edu.unbosque.bluemarketfinal3.dtos.User;
import co.edu.unbosque.bluemarketfinal3.services.UserService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/users")
public class UserResource {

    private UserService service;

    public UserResource() {
        service = new UserService();
    }

    @POST
    @Path("/signup")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registered(User user) {
        System.out.println(user.toString());
        return Response.ok()
                .status(201)
                .entity(service.addElement(user.getName(), user.getEmail(), user.getPassword(), user.getRole()))
                .build();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(User user) {
        return Response.ok()
                .entity(service.login(user.getName(), user.getPassword()))
                .build();
    }

    @PUT
    @Path("/{email}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePrice(User user, @PathParam("email") String email) {
        return null;
    }
}
