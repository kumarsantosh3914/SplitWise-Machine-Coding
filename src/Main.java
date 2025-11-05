import facade.ExpenseSharingFacade;
import models.Expense;
import models.Group;
import models.Transaction;
import models.User;

import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        ExpenseSharingFacade facade = new ExpenseSharingFacade();

        System.out.println("=== Expense Sharing Application ===\n");

        // Create users
        System.out.println("Creating users...");
        User Santosh = facade.createUser("Santosh Kumar", "santosh@kumar.com", "1234567890");
        User Sumit = facade.createUser("Sumit Kumar", "sumit@kumar.com", "0987654321");
        User charlie = facade.createUser("Charlie", "charlie@admin.com", "5555555555");
        User david = facade.createUser("David", "david@varner.com", "6666666666");

        System.out.println("Created: " + Santosh.getName() + ", " + Sumit.getName() +
                ", " + charlie.getName() + ", " + david.getName() + "\n");

        // Create group
        System.out.println("Creating group...");
        Group tripGroup = facade.createGroup("Trip to Thailand", Santosh.getUserId());
        System.out.println("Created group: " + tripGroup.getGroupName() + "\n");

        // Add members to group
        System.out.println("Adding members to group...");
        facade.addMemberToGroup(tripGroup.getGroupId(), Sumit.getUserId());
        facade.addMemberToGroup(tripGroup.getGroupId(), charlie.getUserId());
        facade.addMemberToGroup(tripGroup.getGroupId(), david.getUserId());
        System.out.println("Members added to group: " + tripGroup.getMemberIds() + "\n");

        // Add expenses with equal split
        System.out.println("Adding Expenses");

        System.out.println("1. Hotel bill (Equal split):");
        Expense hotelExpense = facade.addEqualExpense(
                "Hotel Booking",
                8000.0,
                Santosh.getUserId(),
                tripGroup.getGroupId(),
                Arrays.asList(Santosh.getUserId(), Sumit.getUserId(), charlie.getUserId(), david.getUserId())
        );

        System.out.println(" Amount: " + hotelExpense.getAmount() + " paid by " + Santosh.getName() + "\n");
        System.out.println("   Split equally among 4 people: 2000 each\n");

        // Add expenses with percentage split
        System.out.println("2. Food expenses (Percentage split):");
        Map<String, Double> foodPercentages = new HashMap<>();
        foodPercentages.put(Santosh.getUserId(), 20.0);
        foodPercentages.put(Sumit.getUserId(), 30.0);
        foodPercentages.put(charlie.getUserId(), 25.0);
        foodPercentages.put(david.getUserId(), 25.0);

        Expense foodExpense = facade.addPercentageExpense(
                "Restaurant Bill",
                4000.0,
                Sumit.getUserId(),
                tripGroup.getGroupId(),
                foodPercentages
        );
        System.out.println("   Amount: " + foodExpense.getAmount() +
                " paid by " + Sumit.getName());
        System.out.println("   Santosh: 20% (₹800), Sumit: 30% (1200)");
        System.out.println("   Charlie: 25% (₹1000), David: 25% (1000)\n");

        // Add expense with exact split
        System.out.println("3. Shopping (Exact amounts):");
        Map<String, Double> shoppingAmounts = new HashMap<>();
        shoppingAmounts.put(Santosh.getUserId(), 500.0);
        shoppingAmounts.put(Sumit.getUserId(), 700.0);
        shoppingAmounts.put(charlie.getUserId(), 800.0);

        Expense shoppingExpense = facade.addExactExpense(
                "Shopping",
                2000.0,
                charlie.getUserId(),
                tripGroup.getGroupId(),
                shoppingAmounts
        );
        System.out.println("   Amount: " + shoppingExpense.getAmount() +
                " paid by " + charlie.getName());
        System.out.println("   Santosh owes: 500, Sumit owes: 700, Charlie owes: 800\n");

        // Get user expenses
        System.out.println("Santosh's Expenses");
        List<Expense> santoshExpenses = facade.getUserExpenses(Santosh.getUserId());
        System.out.println("Total expenses Santosh is involved in: " + santoshExpenses.size() + "\n");

        // Calculate settlement
        List<Transaction> settlements = facade.getSettlementTransactions(tripGroup.getGroupId());
        if(settlements.isEmpty()) {
            System.out.println("All settled! No transactions needed.\n");
        } else {
            System.out.println("Transactions to settle up:");
            for(Transaction transaction: settlements) {
                User from = facade.getUser(transaction.getFromUserId());
                User to = facade.getUser(transaction.getToUserId());
                System.out.printf("   %s pays ₹%.2f to %s\n",
                        from.getName(), transaction.getAmount(), to.getName());
            }
            System.out.println();
        }

        // Show balances
        System.out.println("Group Balances");
        Map<String, Double> balances = facade.getGroupBalances(tripGroup.getGroupId());
        for(Map.Entry<String, Double> entry: balances.entrySet()) {
            User user = facade.getUser(entry.getKey());
            double balance = entry.getValue();
            if(Math.abs(balance) < 0.01) continue;

            if(balance > 0) {
                System.out.printf("   %s is owed ₹%.2f\n", user.getName(), balance);
            } else {
                System.out.printf("   %s owes ₹%.2f\n", user.getName(), -balance);
            }
        }

        System.out.println("Application completed successfully.");
    }
}