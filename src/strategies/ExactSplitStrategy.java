package strategies;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExactSplitStrategy implements SplitStrategy {

    @Override
    public Map<String, Double> calculateSplit(double amount, List<String> userIds, Map<String, Double> splitData) {
        Map<String, Double> splits = new HashMap<>();

        // validate that exact amounts sum to total amount
        double totalSplit = splitData.values().stream().mapToDouble(Double::doubleValue).sum();
        if(Math.abs(totalSplit - amount) > 0.01) {
            throw new IllegalArgumentException("Split amounts must sum to total amount. Expected: " + amount + ", Got: " + totalSplit);
        }

        return splits;
    }
}
