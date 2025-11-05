package repositories;

import models.User;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class UserRepository {
    private final Map<String, User> users;

    public UserRepository() {
        this.users = new ConcurrentHashMap<>();
    }

    public User save(User user) {
        users.put(user.getUserId(), user);
        return user;
    }

    public Optional<User> findById(String id) {
        return Optional.ofNullable(users.get(id));
    }

    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    public boolean exists(String id) {
        return users.containsKey(id);
    }
}
