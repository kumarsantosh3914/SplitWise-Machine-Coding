package repositories;

import models.Group;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class GroupRepository {
    private final Map<String, Group> groups;

    public GroupRepository() {
        this.groups = new ConcurrentHashMap<>();
    }

    public Group save(Group group) {
        groups.put(group.getGroupId(), group);
        return group;
    }

    public Optional<Group> findById(String id) {
        return Optional.ofNullable(groups.get(id));
    }

    public List<Group> findAll() {
        return new ArrayList<>(groups.values());
    }

    public List<Group> findByUserId(String userId) {
        return groups.values().stream()
                .filter(group -> group.getMemberIds().contains(userId))
                .collect(Collectors.toList());
    }

    public boolean exists(String id) {
        return groups.containsKey(id);
    }
}
