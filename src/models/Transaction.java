package models;

import java.time.LocalDateTime;

public class Transaction {
    private final String fromUserId;
    private final String toUserId;
    private final double amount;
    private final String groupId;

    public Transaction(String from, String to, Double amount, String groupId) {
        this.fromUserId = from;
        this.toUserId = to;
        this.amount = amount;
        this.groupId = groupId;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public String getToUserId() {
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
