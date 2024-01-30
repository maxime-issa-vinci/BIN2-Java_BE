package be.vinci;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Json {
    private static final String DB_FILE_PATH = "db.json";
    private static Path pathToDb = Paths.get(DB_FILE_PATH);
    private static final String COLLECTION_NAME = "films";
    private final static ObjectMapper jsonMapper = new ObjectMapper();

    public static void serialize(List<Film> films) {
        try {
            // if no DB file, write a new collection to a new db file
            if (!Files.exists(pathToDb)) {
                // Create an object and add a JSON array as POJO, e.g. { films:[...]}
                ObjectNode newCollection = jsonMapper.createObjectNode().putPOJO(COLLECTION_NAME, films);
                jsonMapper.writeValue(pathToDb.toFile(), newCollection); // write the JSON Object in the DB file
                return;
            }
            // get all collections : can be read as generic JsonNode, if it can be Object or Array;
            JsonNode allCollections = jsonMapper.readTree(pathToDb.toFile()); // e.g. { users:[...], films:[...]}
            // remove current collection, e.g. remove the array of films
            if (allCollections.has(COLLECTION_NAME)) {
                ((ObjectNode) allCollections).remove(COLLECTION_NAME); //e.g. it leaves { users:[...]}
            }
            // Prepare a JSON array from the list of POJOs for the collection to be updated, e.g. [{"film1",...}, ...]
            ArrayNode updatedCollection = jsonMapper.valueToTree(films);
            // Add the JSON array in allCollections, e.g. : { users:[...], films:[...]}
            ((ObjectNode) allCollections).putArray(COLLECTION_NAME).addAll(updatedCollection);
            // write to the db file allCollections
            jsonMapper.writeValue(pathToDb.toFile(), allCollections);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Film> parse() {
        try {
            // get allCollections
            JsonNode node = jsonMapper.readTree(pathToDb.toFile());
            // accessing value of the specified field of an object node,
            // e.g. the JSON array within "films" field of { users:[...], films:[...]}
            JsonNode collection = node.get(COLLECTION_NAME);
            if (collection == null) // Send an empty list if there is not the requested collection
                return new ArrayList<Film>();
            // convert the JsonNode to a List of POJOs & return it
            return jsonMapper.readerForListOf(Film.class).readValue(collection);
        } catch (FileNotFoundException e) {
            return new ArrayList<Film>(); // send an empty list if there is no db file
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<Film>();
        }
    }

}
