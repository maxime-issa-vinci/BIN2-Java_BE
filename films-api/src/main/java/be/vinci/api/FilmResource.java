package be.vinci.api;

import be.vinci.domain.Film;
import be.vinci.services.Json;
import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.commons.text.StringEscapeUtils;
import be.vinci.services.FilmDataService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Root resource (exposed at "myresource" path)
 */
//
@Singleton
@Path("films")
public class FilmResource {

    private FilmDataService myFilmDataService = new FilmDataService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Film> getAll(@DefaultValue("-1") @QueryParam("minimum-duration") int minimumDuration) {
        return myFilmDataService.getAll(minimumDuration);
    }


    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Film getOne(@PathParam("id") int id) {
        Film filmFound = myFilmDataService.getOne(id);
        if (filmFound == null)
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity("Ressource not found").type("text/plain").build());
        return filmFound;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Film createOne(Film film) {
        if (film == null || film.getTitle() == null || film.getTitle().isBlank())
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST).entity("Lacks of mandatory info").type("text/plain").build());
        return myFilmDataService.createOne(film);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Film deleteOne(@PathParam("id") int id) {
        if (id == 0) // default value of an integer => has not been initialized
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity("Lacks of mandatory id info")
                    .type("text/plain").build());
        Film deletedFilm = myFilmDataService.deleteOne(id);
        if (deletedFilm == null)
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity("Ressource not found").type("text/plain").build());
        return deletedFilm;
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Film updateOne(Film film, @PathParam("id") int id) {
        if (id == 0 || film == null || film.getTitle() == null || film.getTitle().isBlank())
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST).entity("Lacks of mandatory info").type("text/plain").build());
        Film updatedFilm = myFilmDataService.updateOne(film, id);
        if (updatedFilm == null)
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity("Ressource not found").type("text/plain").build());
        return updatedFilm;
    }
}

