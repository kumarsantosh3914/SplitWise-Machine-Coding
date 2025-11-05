package services;

import models.User;
import repositories.UserRepository;

import java.util.List;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(String name, String email, String phone) {
        User user = new User(name, name, email, phone);
        return userRepository.save(user);
    }

    public User getUserById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() ->  new IllegalArgumentException("User with id " + userId + " not found"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public boolean userExists(String userId) {
        return userRepository.exists(userId);
    }
}
