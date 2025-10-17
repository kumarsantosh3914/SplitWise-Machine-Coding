package models;

public class EqualSplit extends Split {

    public EqualSplit(User user) {
        super(user);
    }

    @Override
    public boolean validate() {
        return amount != null && amount >= 0;
    }
}
