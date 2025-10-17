package models;

public class PercentSplit extends Split {
    private Double percent;

    public PercentSplit(User user) {
        super(user);
    }

    public Double getPercent() {
        return percent;
    }

    @Override
    public boolean validate() {
        return percent != null && percent >= 0 && percent <= 100 && amount != null && amount >= 0;
    }
}
