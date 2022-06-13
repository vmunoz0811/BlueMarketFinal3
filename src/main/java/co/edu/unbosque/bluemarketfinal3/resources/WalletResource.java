package co.edu.unbosque.bluemarketfinal3.resources;

import co.edu.unbosque.bluemarketfinal3.dtos.User;
import co.edu.unbosque.bluemarketfinal3.services.UserService;
import co.edu.unbosque.bluemarketfinal3.services.WalletService;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;


@Path("/wallet")
public class WalletResource {
    @Context
    ServletContext context;

    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String URL = "jdbc:postgresql://localhost/BlueMarketDB";
    static final String USER = "postgres";
    static final String PASSWORD = "Pacolindo.23";

    @PUT
    @Path("/buy")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response buyArt(@FormParam("uBuyer") String uBuyer,
                           @FormParam("price") String price,
                           @FormParam("uSeller") String uSeller,
                           @FormParam("name_art") String name_art) {

        boolean buy = new WalletService().sell(uBuyer, price, name_art).get();

        Connection conn = null;

        if (buy) {
            new WalletService().sold(uSeller, price);

            List<User> users = null;
            try {
                Class.forName(JDBC_DRIVER);

                // Opening database connection
                System.out.println("Connecting to database...");
                conn = DriverManager.getConnection(URL, USER, PASSWORD);

                users = new UserService().getUsers();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                // Cleaning-up environment
                try {
                    if (conn != null) conn.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }

            User userfound = users.stream().filter(user -> uBuyer.equals(user.getName())).findFirst().orElse(null);
            if (userfound != null) {
                return Response.ok().entity(userfound).build();
            }

        }
        return Response.serverError().build();
    }
}
