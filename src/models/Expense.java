package models;

import java.time.LocalDateTime;
import java.util.List;

public class Expense {
    private final String expenseId;
    private final Double totalAmount;
    private final User paidBy;
    private final Group group;
    private final List<Split> splits;
    private final LocalDateTime createdAt;

    public Expense(String expenseId, Double totalAmount, User paidBy, Group group, List<Split> splits) {
        this.expenseId = expenseId;
        this.totalAmount = totalAmount;
        this.paidBy = paidBy;
        this.group = group;
        this.splits = splits;
        this.createdAt = LocalDateTime.now();
    }

    public String getExpenseId() {
        return expenseId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<Split> getSplits() {
        return splits;
    }

    public Group getGroup() {
        return group;
    }

    public User getPaidBy() {
        return paidBy;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }
}
