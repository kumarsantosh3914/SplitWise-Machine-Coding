package repositories;

import models.Expense;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ExpenseRepository {
    private final Map<String, Expense> expenses;

    public ExpenseRepository() {
        this.expenses = new ConcurrentHashMap<>();
    }

    public Expense save(Expense expense) {
        expenses.put(expense.getExpenseId(), expense);
        return expense;
    }

    public Optional<Expense> findById(String id) {
        return Optional.ofNullable(expenses.get(id));
    }

    public List<Expense> findAll() {
        return new ArrayList<>(expenses.values());
    }

    public List<Expense> findByUserId(String userId) {
        return expenses.values().stream()
                .filter(expense -> expense.getPaidBy().equals(userId) ||
                        expense.getSplits().containsKey(userId))
                .collect(Collectors.toList());
    }
}
