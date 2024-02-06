package be.vinci.api;

import be.vinci.api.filters.Authorize;
import be.vinci.domain.Page;
import be.vinci.domain.User;
import be.vinci.services.PageDataService;
import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.server.ContainerRequest;

import java.util.List;

@Singleton
@Path("pages")
public class PageResource {

    private PageDataService myPageDataService = new PageDataService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Authorize
    public List<Page> getAll(@Context ContainerRequest request) {
        return myPageDataService.getAll((User) request.getProperty("user"));
    }


    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Authorize
    public Page getOne(@PathParam("id") int id, @Context ContainerRequest request) {
        Page pageFound = myPageDataService.getOne(id, (User) request.getProperty("user"));
        if (pageFound == null)
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity("Ressource not found").type("text/plain").build());
        return pageFound;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Authorize
    public Page createOne(Page page, @Context ContainerRequest request) {
        User authenticatedUser = (User) request.getProperty("user");
        System.out.println("A new film is added by " + authenticatedUser.getLogin() );
        if (page == null || page.getTitle() == null || page.getTitle().isBlank())
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST).entity("Lacks of mandatory info").type("text/plain").build());
        return myPageDataService.createOne(page);
    }


    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Authorize
    public Page deleteOne(@PathParam("id") int id, @Context ContainerRequest request) {
        if (id == 0) // default value of an integer => has not been initialized
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity("Lacks of mandatory id info")
                    .type("text/plain").build());
        Page deletedPage = myPageDataService.deleteOne(id, (User) request.getProperty("user"));
        if (deletedPage == null)
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity("Ressource not found").type("text/plain").build());
        return deletedPage;
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Authorize
    public Page updateOne(Page page, @PathParam("id") int id, @Context ContainerRequest request) {
        if (id == 0 || page == null || page.getTitle() == null || page.getTitle().isBlank())
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST).entity("Lacks of mandatory info").type("text/plain").build());
        Page updatedFilm = myPageDataService.updateOne(page, id, (User) request.getProperty("user"));
        if (updatedFilm == null)
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity("Ressource not found").type("text/plain").build());
        return updatedFilm;
    }
}
