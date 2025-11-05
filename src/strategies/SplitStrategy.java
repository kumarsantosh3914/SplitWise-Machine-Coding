package strategies;

import java.util.List;
import java.util.Map;

public interface SplitStrategy {
    Map<String, Double> calculateSplit(double amount, List<String> userIds, Map<String, Double> splitData);
}
