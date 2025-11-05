package strategies;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EqualSplitStrategy implements SplitStrategy {

    @Override
    public Map<String, Double> calculateSplit(double amount, List<String> userIds, Map<String, Double> splitData) {
        Map<String, Double> splits = new HashMap<>();
        double splitAmount = amount / userIds.size();

        for(String userId: userIds) {
            splits.put(userId, splitAmount);
        }

        return splits;
    }
}
