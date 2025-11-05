package facade;

import models.Expense;
import models.Group;
import models.Transaction;
import models.User;
import repositories.ExpenseRepository;
import repositories.GroupRepository;
import repositories.UserRepository;
import services.ExpenseService;
import services.GroupService;
import services.SettlementService;
import services.UserService;
import strategies.EqualSplitStrategy;
import strategies.ExactSplitStrategy;
import strategies.PercentageSplitStrategy;
import strategies.SplitStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpenseSharingFacade {
    private final UserService userService;
    private final GroupService groupService;
    private final ExpenseService expenseService;
    private final SettlementService settlementService;

    public ExpenseSharingFacade() {
        UserRepository userRepository = new UserRepository();
        GroupRepository groupRepository = new GroupRepository();
        ExpenseRepository expenseRepository = new ExpenseRepository();

        // Initialize services
        this.userService = new UserService(userRepository);
        this.groupService = new GroupService(groupRepository, userRepository);
        this.expenseService = new ExpenseService(expenseRepository, groupRepository, userRepository);
        this.settlementService = new SettlementService(expenseRepository);
    }

    public User createUser(String name, String email, String phone) {
        return userService.createUser(name, email, phone);
    }

    public User getUser(String userId) {
        return userService.getUserById(userId);
    }

    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    public Group createGroup(String name, String createdBy) {
        return groupService.createGroup(name, createdBy);
    }

    public Group getGroup(String groupId) {
        return groupService.getGroupById(groupId);
    }

    public List<Group> getAllGroups() {
        return groupService.getAllGroups();
    }

    public void addMemberToGroup(String groupId, String userId) {
        groupService.addMemberToGroup(groupId, userId);
    }

    public void removeMemberFromGroup(String groupId, String userId) {
        groupService.removeMemberFromGroup(groupId, userId);
    }

    public Expense addEqualExpense(String description, double amount, String paidBy,
                                   String groupId, List<String> participants) {
        SplitStrategy strategy = new EqualSplitStrategy();
        return expenseService.addExpense(description, amount, paidBy, groupId, strategy, participants, new HashMap<>());
    }

    public Expense addPercentageExpense(String description, double amount, String paidBy,
                                        String groupId, Map<String, Double> percentages) {
        SplitStrategy strategy = new PercentageSplitStrategy();
        List<String> participantIds = new ArrayList<>(percentages.keySet());
        return expenseService.addExpense(description, amount, paidBy, groupId, strategy, participantIds, percentages);
    }

    public Expense addExactExpense(String description, double amount, String paidBy,
                                  String groupId, Map<String, Double> exactAmounts) {
        SplitStrategy strategy = new ExactSplitStrategy();
        List<String> participantIds = new ArrayList<>(exactAmounts.keySet());
        return expenseService.addExpense(description, amount, paidBy, groupId, strategy, participantIds, exactAmounts);
    }

    public Expense getExpense(String expenseId) {
        return expenseService.getExpenseById(expenseId);
    }

    public List<Expense> getUserExpenses(String userId) {
        return expenseService.getExpensesByUserId(userId);
    }

    public List<Expense> getGroupExpenses(String groupId) {
        return expenseService.getExpensesByGroupId(groupId);
    }

    public List<Transaction> getSettlementTransactions(String userId) {
        return settlementService.calculateSettlement(userId);
    }

    public Map<String, Double> getGroupBalances(String groupId) {
        return settlementService.getUserBalances(groupId);
    }
}
