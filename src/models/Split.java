package models;

public abstract class Split {
    protected final User user;
    protected Double amount;

    public  Split(User user) {
        this.user = user;
    }

    public Double getAmount() {
        return amount;
    }

    public User getUser() {
        return user;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public abstract boolean validate();
}
