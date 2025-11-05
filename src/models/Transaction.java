package models;

import java.time.LocalDateTime;

public class Transaction {
    private final User fromUserId;
    private final User toUserId;
    private final double amount;
    private final String groupId;

    public Transaction(User from, User to, Double amount, String groupId) {
        this.fromUserId = from;
        this.toUserId = to;
        this.amount = amount;
        this.groupId = groupId;
    }

    public User getFromUserId() {
        return fromUserId;
    }

    public User getToUserId() {
        return toUserId;
    }

    public double getAmount() {
        return amount;
    }

    public String getGroupId() {
        return groupId;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "fromUserId=" + fromUserId +
                ", toUserId=" + toUserId +
                ", amount=" + amount +
                ", groupId='" + groupId + '\'' +
                '}';
    }
}
