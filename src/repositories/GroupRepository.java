package repositories;

import models.Group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GroupRepository {
    private final Map<String, Group> groups;
    private static GroupRepository instance;

    private GroupRepository() {
        this.groups = new HashMap<>();
    }

    public static GroupRepository getInstance() {
        if(instance == null) {
            instance = new GroupRepository();
        }
        return instance;
    }

    public Group save(Group group) {
        groups.put(group.getGroupId(), group);
        return group;
    }

    public List<Group> findByUserId(String userId) {
        return groups.values().stream()
                .filter(group -> group.getMembers().stream()
                        .anyMatch(user -> user.getUserId().equals(userId)))
                .collect(Collectors.toList());
    }

    public List<Group> findAll() {
        return new ArrayList<>(groups.values());
    }
}
