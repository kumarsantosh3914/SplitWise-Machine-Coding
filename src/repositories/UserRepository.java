package repositories;

import models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepository {
    private final Map<String, User> users;
    private static UserRepository instance;

    private UserRepository() {
        this.users = new HashMap<>();
    }

    public static  UserRepository getInstance() {
        if(instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    public User save(User user) {
        users.put(user.getUserId(), user);
        return user;
    }

    public User findById(String userId) {
        return users.get(userId);
    }

    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }
}
