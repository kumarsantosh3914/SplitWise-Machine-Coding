package strategies;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PercentageSplitStrategy implements SplitStrategy {

    @Override
    public Map<String, Double> calculateSplit(double amount, List<String> userIds, Map<String, Double> splitData) {
        Map<String, Double> splits = new HashMap<>();

        // validate that percentages sum to 100
        double totalPercentage = splitData.values().stream().mapToDouble(Double::doubleValue).sum();
        if(Math.abs(totalPercentage - 100.0) > 0.01) {
            throw new IllegalArgumentException("Total percentage must sum to 100");
        }

        for(Map.Entry<String, Double> entry: splitData.entrySet()) {
            double userShare = (amount * entry.getValue()) / 100.0;
            splits.put(entry.getKey(), userShare);
        }

        return splits;
    }
}
