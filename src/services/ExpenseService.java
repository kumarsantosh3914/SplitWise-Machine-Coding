package services;

import models.Expense;
import models.Group;
import repositories.ExpenseRepository;
import repositories.GroupRepository;
import repositories.UserRepository;
import strategies.SplitStrategy;

import java.util.List;
import java.util.Map;

public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public ExpenseService(ExpenseRepository expenseRepository, GroupRepository groupRepository, UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    public Expense addExpense(String description, double amount, String paidBy,
                              String groupId, SplitStrategy splitStrategy,
                              List<String> participants, Map<String, Double> splitData) {
        // validate amount
        if(amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        // Validate user exists
        if(!userRepository.exists(paidBy)) {
            throw new IllegalArgumentException("User with id " + paidBy + " does not exist");
        }

        // Validate group exists
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group with id " + groupId + " does not exist"));

        // Validate all participants are group members
        for(String participantId : participants) {
            if(!group.getMemberIds().contains(participantId)) {
                throw new IllegalArgumentException("User with id " + participantId + " is not a member of group " + groupId);
            }
        }

        // Calculate splits using strategy
        Map<String, Double> splits = splitStrategy.calculateSplit(amount, participants, splitData);

        // Create and save expense
        Expense expense = new Expense(description, amount, paidBy, groupId, splits);
        return expenseRepository.save(expense);
    }

    public Expense getExpenseById(String expenseId) {
        return expenseRepository.findById(expenseId)
                .orElseThrow(() -> new IllegalArgumentException("Expense with id " + expenseId + " not found"));
    }

    public List<Expense> getExpensesByGroupId(String groupId) {
        return expenseRepository.findByGroupId(groupId);
    }

    public List<Expense> getExpensesByUserId(String userId) {
        return expenseRepository.findByUserId(userId);
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }
}
