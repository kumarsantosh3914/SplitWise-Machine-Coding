package models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Group {
    private final String groupId;
    private final String groupName;
    private final List<User> members;
    private final User createdBy;
    private final LocalDateTime createdAt;

    public Group(String groupId, String groupName, User createdBy) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.members = new ArrayList<>();
        this.createdBy = createdBy;
        this.createdAt = LocalDateTime.now();
        this.members.add(createdBy);
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public List<User> getMembers() {
        return members;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void addMember(User user) {
        if(!members.contains(user)) {
            members.add(user);
        }
    }

    public void removeMember(User user) {
        members.remove(user);
    }

    public boolean isMember(User user) {
        return members.contains(user);
    }
}
