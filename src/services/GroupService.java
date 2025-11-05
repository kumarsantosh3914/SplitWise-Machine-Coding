package services;

import models.Group;
import repositories.GroupRepository;
import repositories.UserRepository;

import java.util.List;

public class GroupService {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public  GroupService(GroupRepository groupRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    public Group createGroup(String name, String createdBy) {
        if(!userRepository.exists(createdBy)) {
            throw new IllegalArgumentException("User with id " + createdBy + " does not exist");
        }

        Group group = new Group(name, createdBy);
        return groupRepository.save(group);
    }

    public Group getGroupById(String groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group with id " + groupId + " not found"));
    }

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    public List<Group> getGroupsByUserId(String userId) {
        return groupRepository.findByUserId(userId);
    }

    public void addMemberToGroup(String groupId, String userId) {
        if(!userRepository.exists(userId)) {
            throw new IllegalArgumentException("User with id " + userId + " does not exist");
        }

        Group group = getGroupById(groupId);
        group.addMember(userId);
        groupRepository.save(group);
    }

    public void removeMemberFromGroup(String groupId, String userId) {
        Group group = getGroupById(groupId);

        if(group.getCreatedBy().equals(userId)) {
            throw new IllegalArgumentException("Cannot remove the creator of the group");
        }

        group.removeMember(userId);
        groupRepository.save(group);
    }
}
