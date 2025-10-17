package models;

import java.time.LocalDateTime;

public class Transaction {
    private final User from;
    private final User to;
    private final Double amount;
    private final Group grop;
    private final LocalDateTime timestamp;

    public Transaction(User from, User to, Double amount, Group grop, LocalDateTime timestamp) {
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.grop = grop;
        this.timestamp = LocalDateTime.now();
    }

    public User getFrom() {
        return from;
    }

    public User getTo() {
        return to;
    }

    public Double getAmount() {
        return amount;
    }

    public Group getGrop() {
        return grop;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "from=" + from +
                ", to=" + to +
                ", amount=" + amount +
                '}';
    }
}
