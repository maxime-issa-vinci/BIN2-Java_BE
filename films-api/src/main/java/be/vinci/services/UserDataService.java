package be.vinci.services;

import be.vinci.domain.User;
import be.vinci.services.utils.Json;
import be.vinci.utils.Config;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

public class UserDataService {
    private static final String COLLECTION_NAME = "users";
    private static Json<User> jsonDB = new Json<>(User.class);
    private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
    private final ObjectMapper jsonMapper = new ObjectMapper();


    public List<User> getAll() {
        var items = jsonDB.parse(COLLECTION_NAME);
        return items;
    }


    public User getOne(int id) {
        var items = jsonDB.parse(COLLECTION_NAME);
        return items.stream().filter(item -> item.getId() == id).findAny().orElse(null);
    }

    public User getOne(String login) {
        var items = jsonDB.parse(COLLECTION_NAME);
        return items.stream().filter(item -> item.getLogin().equals(login)).findAny().orElse(null);
    }

    public User createOne(User item) {
        var items = jsonDB.parse(COLLECTION_NAME);
        item.setId(nextItemId());
        items.add(item);
        jsonDB.serialize(items, COLLECTION_NAME);
        return item;
    }

    public int nextItemId() {
        var items = jsonDB.parse(COLLECTION_NAME);
        if (items.size() == 0)
            return 1;
        return items.get(items.size() - 1).getId() + 1;
    }

    public ObjectNode login(String login, String password) {
        User user = getOne(login);
        if (user == null || !user.checkPassword(password))
            return null;
        String token;
        try {
            token = JWT.create().withIssuer("auth0")
                    .withClaim("user", user.getId()).sign(this.jwtAlgorithm);
            ObjectNode publicUser = jsonMapper.createObjectNode()
                    .put("token", token)
                    .put("id", user.getId())
                    .put("login", user.getLogin());
            return publicUser;

        } catch (Exception e) {
            System.out.println("Unable to create token");
            return null;
        }
    }

    public ObjectNode register(String login, String password) {
        User tempUser = getOne(login);
        if (tempUser != null) // the user already exists !
            return null;
        tempUser = new User();
        tempUser.setLogin(login);
        tempUser.setPassword(tempUser.hashPassword(password));

        User user = createOne(tempUser);
        if (user == null)
            return null;
        String token;
        try {
            token = JWT.create().withIssuer("auth0")
                    .withClaim("user", user.getId()).sign(this.jwtAlgorithm);
            ObjectNode publicUser = jsonMapper.createObjectNode()
                    .put("token", token)
                    .put("id", user.getId())
                    .put("login", user.getLogin());
            return publicUser;

        } catch (Exception e) {
            System.out.println("Unable to create token");
            return null;
        }
    }

}
