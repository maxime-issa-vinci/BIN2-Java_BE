package be.vinci.services;

import be.vinci.domain.Page;
import be.vinci.services.utils.Json;
import org.apache.commons.text.StringEscapeUtils;

import java.util.List;

public class PageDataService {
    private static final String COLLECTION_NAME = "pages";
    private static Json<Page> jsonDB = new Json<>(Page.class);

    public List<Page> getAll(int minimumDuration) {
        var films = jsonDB.parse(COLLECTION_NAME);
        if (minimumDuration != -1) {
            List<Page> filmsFiltered = films.stream()
                    .filter(film -> film.getDuration() >= minimumDuration)
                    .toList();
            return filmsFiltered;
        }
        return films;
    }


    public Film getOne(int id) {
        var films = jsonDB.parse(COLLECTION_NAME);
        Film filmFound = films.stream().filter(film -> film.getId() == id).findAny().orElse(null);
        return filmFound;
    }

    public Film createOne(Film film) {
        var films = jsonDB.parse(COLLECTION_NAME);
        film.setId(nextFilmId());
        film.setTitle(StringEscapeUtils.escapeHtml4(film.getTitle()));
        film.setLink(StringEscapeUtils.escapeHtml4(film.getLink()));
        films.add(film);
        jsonDB.serialize(films, COLLECTION_NAME);
        return film;
    }


    public Film deleteOne(int id) {
        var films = jsonDB.parse(COLLECTION_NAME);
        Film filmToDelete = films.stream().filter(film -> film.getId() == id).findAny().orElse(null);
        films.remove(filmToDelete);
        jsonDB.serialize(films, COLLECTION_NAME);
        return filmToDelete;
    }

    public Film updateOne(Film film, int id) {
        var films = jsonDB.parse(COLLECTION_NAME);
        Film filmToUpdate = films.stream().filter(f -> f.getId() == id).findAny().orElse(null);
        film.setId(id);
        film.setTitle(StringEscapeUtils.escapeHtml4(film.getTitle()));
        film.setLink(StringEscapeUtils.escapeHtml4(film.getLink()));
        films.remove(film); // thanks to equals(), films is found via its id
        films.add(film);
        jsonDB.serialize(films, COLLECTION_NAME);
        return film;
    }
}
