package services;

import models.Expense;
import models.Transaction;
import repositories.ExpenseRepository;

import java.util.*;

public class SettlementService {
    private ExpenseRepository expenseRepository;

    public SettlementService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    /**
     * Calculate all transactions needed to settle up a group
     * Uses the Greedy Algorithm to minimize number of transactions
     */
    public List<Transaction> calculateSettlement(String groupId) {
        List<Expense> expenses = expenseRepository.findByGroupId(groupId);

        if(expenses.isEmpty()) {
            return new ArrayList<>();
        }

        // Calculate net balances for each user
        Map<String, Double> balances = calculateBalances(expenses, groupId);

        // Minimize transactions using greedy approach
        return minimizeTransactions(balances, groupId);
    }

    /**
     * Calculate net balance for each user in a group
     * Positive balance = user is owed money
     * Negative balance = user owes money
     */
    private Map<String, Double> calculateBalances(List<Expense> expenses, String groupId) {
        Map<String, Double> balances = new HashMap<>();

        for(Expense expense : expenses) {
            String paidBy = expense.getPaidBy();
            Map<String, Double> splits = expense.getSplits();

            // Person who paid gets credited
            balances.put(paidBy, balances.getOrDefault(paidBy, 0.0) + expense.getAmount());

            // Everyone who owes get debited
            for(Map.Entry<String, Double> split: splits.entrySet()) {
                String userId = split.getKey();
                Double amountOwed = split.getValue();
                balances.put(userId, balances.getOrDefault(userId, 0.0) - amountOwed);
            }
        }

        return balances;
    }

    /**
     * Minimize the number of transactions needed to settle balances
     * Using greedy algorithm: always match the highest creditor with the highest debtor
     */
    private List<Transaction> minimizeTransactions(Map<String, Double> balances, String groupId) {
        List<Transaction> transactions = new ArrayList<>();

        // Separate users into creditors (owed money) and debtors (owe money)
        PriorityQueue<UserBalance> creditors = new PriorityQueue<>((a, b) -> Double.compare(b.balance, a.balance));
        PriorityQueue<UserBalance> debtors = new PriorityQueue<>((a, b) -> Double.compare(a.balance, b.balance));

        for(Map.Entry<String, Double> entry: balances.entrySet()) {
            double balance = entry.getValue();
            if(Math.abs(balance) < 0.01) continue; // skip settled users because very small balances due to rounding

            if(balance > 0) {
                creditors.offer(new UserBalance(entry.getKey(), balance));
            } else {
                debtors.offer(new UserBalance(entry.getKey(), balance));
            }
        }

        // Match creditors and debtors
        while(!creditors.isEmpty() && !debtors.isEmpty()) {
            UserBalance creditor = creditors.poll();
            UserBalance debtor = debtors.poll();

            double settleAmount = Math.min(creditor.balance, Math.abs(debtor.balance));

            transactions.add(new Transaction(debtor.userId, creditor.userId, settleAmount, groupId));

            // Update balances
            creditor.balance -= settleAmount;
            debtor.balance += settleAmount;

            // Re-add if not fully settled
            if(creditor.balance > 0.01) {
                creditors.offer(creditor);
            }
            if(debtor.balance < -0.01) {
                debtors.offer(debtor);
            }
        }

        return transactions;
    }

    // helper class
    private static class UserBalance {
        String userId;
        double balance;

        UserBalance(String userId, double balance) {
            this.userId = userId;
            this.balance = balance;
        }
    }

    /**
     * Get user's balance in a specific group
     */
    public Map<String, Double> getUserBalances(String groupId) {
        List<Expense> expenses = expenseRepository.findByGroupId(groupId);
        return calculateBalances(expenses, groupId);
    }
}
