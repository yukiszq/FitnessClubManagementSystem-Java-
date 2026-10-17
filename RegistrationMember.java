/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gymmanagementsystem;

/**
 * Handles new member registration process
 *
 * OOP Concepts Used: - Static methods: Utility class, no instance needed - Code
 * Reuse: Reuses Membership.displayPlans(), Membership.calculatePrice(),
 * PaymentProcessor.processPayment() - Encapsulation: Member creation delegated
 * to Member constructor - Delegation: Invoice and transaction log handled by
 * separate classes
 * 
 * @author Victus
 */
public class RegistrationMember {

    public static void registerMember() {

        System.out.println("\n=== MEMBER REGISTRATION ===");

        // Step 1: Collect personal information (with validation from Input class)
        String name = Input.getName("Enter Name: ");
        String ic = Input.getIC("Enter IC (12 digits): ");
        String phno = Input.getPhone("Enter Phone: ");
        String password = Input.getPassword();  // Strong password validation applied

        // Step 2: Generate unique Member ID (M1000, M1001, M1002...)
        String id = "M" + (1000 + getMemberCount() + 1);

        // Step 3: Show membership plans (reused from Membership class - DRY principle)
        Membership.displayPlans();

        // Step 4: Select membership tier
        int tierChoice = Input.getMenuChoice("Choose Tier (1=Basic, 2=Silver, 3=Gold): ", 1, 3);
        String tier = tierChoice == 1 ? "Basic" : tierChoice == 2 ? "Silver" : "Gold";

        // Step 5: Select plan duration
        System.out.println("\nChoose Plan:");
        System.out.println("1. Monthly");
        System.out.println("2. 6 Months");
        System.out.println("3. 1 Year");

        int planChoice = Input.getMenuChoice("Choice: ", 1, 3);

        String plan;
        int months;

        switch (planChoice) {
            case 1:
                plan = "Monthly";
                months = 1;
                break;
            case 2:
                plan = "6 Months";
                months = 6;
                break;
            default:
                plan = "1 Year";
                months = 12;
        }

        // Step 6: Calculate total price (reused from Membership class)
        double totalPrice = Membership.calculatePrice(tier, months);

        // Step 7: Show invoice summary
        System.out.println("\n================ INVOICE ===============");
        System.out.println("Tier  : " + tier);
        System.out.println("Plan  : " + plan);
        System.out.printf("Total : RM %.2f\n", totalPrice);
        System.out.println("========================================");

        // Step 8: Process payment (Card or E-Wallet only)
        PaymentProcessor.processPayment(totalPrice);

        // Step 9: Create the Member object using constructor 1 (auto-calculates dates)
        Member newMember = new Member(
                id, password, name, ic, phno,
                plan, tier, totalPrice, months, true
        );

        // Step 10: Add to system storage
        DataStore.getUsers().add(newMember);

        // Step 11: Print receipt for the member
        InvoiceGenerator.printRegistrationInvoice(name, tier, plan, totalPrice, months, id);

        // Step 12: Save transaction record for admin audit
        TransactionLog log = new TransactionLog(
                id, name, "REGISTRATION",
                "NONE", tier, "NONE", plan, totalPrice
        );
        DataStore.addTransactionLog(log);

        // Step 13: Confirmation message
        System.out.println("\nRegistration successful!");
        System.out.println("Your ID: " + id);
        System.out.println("Tier: " + tier);
        System.out.println("Plan: " + plan);
        System.out.printf("Amount Paid: RM %.2f\n", totalPrice);
        System.out.println("Expiry Date: " + newMember.getExpireDate());
        System.out.println("\nPlease login using your Member ID and password.");
    }

    // Count how many members already exist (for ID generation)
    private static int getMemberCount() {
        int count = 0;
        for (User u : DataStore.getUsers()) {
            if (u instanceof Member) {
                count++;
            }
        }
        return count;
    }
}
