package repositories;

import models.Expense;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExpenseRepository {
    private final Map<String, Expense> expenses;
    private static ExpenseRepository instance;

    private ExpenseRepository() {
        this.expenses = new HashMap<>();
    }

    public static ExpenseRepository getInstance() {
        if(instance == null) {
            instance = new ExpenseRepository();
        }
        return instance;
    }

    public Expense save(Expense expense) {
        expenses.put(expense.getExpenseId(), expense);
        return expense;
    }

    public Expense findById(String id) {
        return expenses.get(id);
    }

    public List<Expense> findByGroupId(String groupId) {
        return expenses.values().stream()
                .filter(expense -> expense.getGroup().getGroupId().equals(groupId))
                .collect(Collectors.toList());
    }

    public List<Expense> findByUserId(String userId) {
        return expenses.values().stream()
                .filter(expense -> expense.getPaidBy().getUserId().equals(userId) ||
                        expense.getSplits().stream()
                                .anyMatch(split -> split.getUser().getUserId().equals(userId)))
                .collect(Collectors.toList());
    }

    public List<Expense> findAll() {
        return new ArrayList<>(expenses.values());
    }
}
