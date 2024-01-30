package be.vinci;

import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Root resource (exposed at "myresource" path)
 */
@Singleton
@Path("films")
public class FilmRessource {

    private Film[] defaultFilms = {
            new Film(1, "No Time To Die", 163, 301, "https://en.wikipedia.org/wiki/No_Time_to_Die"),
            new Film(2, "Dune", 156, 165, "https://en.wikipedia.org/wiki/Dune_(2021_film)"),
            new Film(3, "Shang-Chi and the Legend of the Ten Rings", 132, 200, "https://en.wikipedia.org/wiki/Shang-Chi_and_the_Legend_of_the_Ten_Rings"),
            new Film(4, "Peter Rabbit 2: The Runaway", 93, 45, "https://en.wikipedia.org/wiki/Peter_Rabbit_2:_The_Runaway")
    };
    private List<Film> films = new ArrayList<>(Arrays.asList(defaultFilms)); // to get a changeable list, asList is fixed size

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Film> getAll(@DefaultValue("-1") @QueryParam("minimum-duration") int minimumDuration) {
        if (minimumDuration != -1) {
            List<Film> filmsFiltered = films.stream().filter(film -> film.getDuration() >= minimumDuration)
                    .toList();
            return filmsFiltered;
        }
        return films;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Film getOne(@PathParam("id") int id) {
        Film filmFound = films.stream().filter(film -> film.getId() == id).findAny().orElse(null);
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
        film.setId(films.size() + 1);
        films.add(film);
        return film;
    }
 




}
