package models;

import java.time.LocalDateTime;
import java.util.*;

public class Expense {
    private final String expenseId;
    private final String description;
    private final Double amount;
    private final String paidBy;
    private final String groupId;
    private final Date createdAt;
    private final Map<String, Double> splits;

    public Expense(String description, Double amount, String paidBy, String groupId, Map<String, Double> splits) {
        this.expenseId = UUID.randomUUID().toString();
        this.description = description;
        this.amount = amount;
        this.paidBy = paidBy;
        this.groupId = groupId;
        this.splits = new HashMap<>(splits);
        this.createdAt = new Date();
    }

    public String getExpenseId() {
        return expenseId;
    }

    public String getDescription() {
        return description;
    }

    public Double getAmount() {
        return amount;
    }

    public String getPaidBy() {
        return paidBy;
    }

    public String getGroupId() {
        return groupId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Map<String, Double> getSplits() {
        return splits;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "expenseId='" + expenseId + '\'' +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", paidBy='" + paidBy + '\'' +
                ", groupId='" + groupId + '\'' +
                ", createdAt=" + createdAt +
                ", splits=" + splits +
                '}';
    }
}
