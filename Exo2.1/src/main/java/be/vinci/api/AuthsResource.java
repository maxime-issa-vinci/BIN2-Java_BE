package be.vinci.api;

import be.vinci.services.UserDataService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Singleton
@Path("/auths")
public class AuthsResource {

    private UserDataService myUserDataService = new UserDataService();

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ObjectNode login(JsonNode json) {
        // Get and check credentials
        if (!json.hasNonNull("login") || !json.hasNonNull("password")) {
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
                    .entity("login or password required").type("text/plain").build());
        }
        String login = json.get("login").asText();
        String password = json.get("password").asText();

        // Try to login
        ObjectNode publicUser = myUserDataService.login(login, password);
        if (publicUser == null) {
            throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Login or password incorrect").type(MediaType.TEXT_PLAIN)
                    .build());
        }
        return publicUser;

    }

    @POST
    @Path("register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ObjectNode register(JsonNode json) {
        // Get and check credentials
        if (!json.hasNonNull("login") || !json.hasNonNull("password")) {
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
                    .entity("login or password required").type("text/plain").build());
        }
        String login = json.get("login").asText();
        String password = json.get("password").asText();

        // Try to login
        ObjectNode publicUser = myUserDataService.register(login, password);
        if (publicUser == null) {
            throw new WebApplicationException(Response.status(Response.Status.CONFLICT)
                    .entity("this resource already exists").type(MediaType.TEXT_PLAIN)
                    .build());
        }
        return publicUser;

    }

}