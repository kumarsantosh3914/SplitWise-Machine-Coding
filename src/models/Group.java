package models;

import java.time.LocalDateTime;
import java.util.*;

public class Group {
    private final String groupId;
    private final String groupName;
    private final Set<String> memberIds;
    private final String createdBy;
    private final Date createdAt;

    public Group(String groupName, String createdBy) {
        this.groupId = UUID.randomUUID().toString();
        this.groupName = groupName;
        this.memberIds = new HashSet<>();
        this.memberIds.add(createdBy);
        this.createdBy = createdBy;
        this.createdAt = new Date();
    }

    public String getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public Set<String> getMemberIds() {
        return memberIds;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void addMember(String userId) {
        memberIds.add(userId);
    }

    public void removeMember(String userId) {
        memberIds.remove(userId);
    }

    @Override
    public String toString() {
        return "Group{" +
                "groupId='" + groupId + '\'' +
                ", groupName='" + groupName + '\'' +
                ", memberIds=" + memberIds +
                ", createdBy='" + createdBy + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
