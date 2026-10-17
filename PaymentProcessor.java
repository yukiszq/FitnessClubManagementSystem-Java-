/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gymmanagementsystem;

/**
 * Handles payment processing for membership registration and renewal
 *
 * OOP Concepts Used: - Composition: PaymentProcessor HAS-A Member - Code Reuse:
 * Reuses Membership.displayPlans() and Membership.calculatePrice() - Static
 * methods: processPayment() can be called without instance (used by
 * RegistrationMember) - Delegation: Payment details saved to TransactionLog and
 * InvoiceGenerator
 * 
 * @author Victus
 */
import java.time.LocalDate;

public class PaymentProcessor {
    private Member member;  // The member making payment (composition)

    public PaymentProcessor(Member member) {
        this.member = member;
    }

    /**
     * Shows membership plans, gets user selection, processes payment Returns a
     * Membership object with the selected package details
     */
    public Membership selectAndPay() {
        // Reuse display method from Membership class (DRY principle)
        Membership.displayPlans();

        int tierChoice = Input.getMenuChoice("Choose Tier (1=Basic, 2=Silver, 3=Gold): ", 1, 3);
        String tier = tierChoice == 1 ? "Basic" : tierChoice == 2 ? "Silver" : "Gold";

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

        // Reuse calculate method from Membership class (single source of truth)
        double totalPrice = Membership.calculatePrice(tier, months);

        System.out.println("\n================ INVOICE ================");
        System.out.println("Tier  : " + tier);
        System.out.println("Plan  : " + plan);
        System.out.printf("Total : RM %.2f\n", totalPrice);
        System.out.println("========================================");

        processPayment(totalPrice);

        return new Membership(tier, plan, months, totalPrice);
    }

    /**
     * Static method - can be called from RegistrationMember without creating
     * PaymentProcessor object Supports Card and E-Wallet payments only (Cash
     * removed per requirement)
     */
    public static void processPayment(double totalPrice) {
        boolean paid = false;

        while (!paid) {
            System.out.println("\nPayment Method:");
            System.out.println("1. Card");
            System.out.println("2. E-Wallet");

            int method = Input.getMenuChoice("Choice (1-2): ", 1, 2);

            switch (method) {
                case 1:
                    paid = processCard();
                    break;
                case 2:
                    paid = processEWallet();
                    break;
            }
        }
    }

    // Card payment validation - 16 digits required
    private static boolean processCard() {
        while (true) {
            System.out.print("Card (16 digits, 0 to change): ");
            String card = Input.sc.nextLine();

            if (card.equals("0")) {
                return false;
            }

            if (card.matches("\\d{16}")) {
                System.out.println("Payment success (Card)");
                return true;
            } else {
                System.out.println("Invalid card number.");
            }
        }
    }

    // E-Wallet payment - phone number + PIN validation
    private static boolean processEWallet() {
        while (true) {
            System.out.print("Phone (10-11 digits, 0 to change): ");
            String phone = Input.sc.nextLine();

            if (phone.equals("0")) {
                return false;
            }

            if (phone.matches("\\d{10,11}")) {
                while (true) {
                    System.out.print("PIN (6 digits): ");
                    String pin = Input.sc.nextLine();

                    if (pin.matches("\\d{6}")) {
                        System.out.println("Payment success (E-Wallet)");
                        return true;
                    } else {
                        System.out.println("Invalid PIN.");
                    }
                }
            } else {
                System.out.println("Invalid phone number.");
            }
        }
    }

    /**
     * Renews existing member's membership Captures old values before update,
     * saves transaction log, prints invoice
     */
    public void renew() {
        System.out.println("\n===== RENEW MEMBERSHIP =====");

        // Save old values BEFORE updating (needed for transaction log and invoice)
        String oldTier = member.getTier();
        String oldPlan = member.getPlan();
        String memberName = member.getName();
        String memberId = member.getId();

        System.out.println("Current Plan: " + oldPlan);
        System.out.println("Current Tier: " + oldTier);

        Membership ms = selectAndPay();  // Get new membership package

        // Update member with new values
        member.setTier(ms.getTier());
        member.setPlan(ms.getPlan());
        member.setPrice(ms.getTotalPrice());
        member.setPaid(true);
        member.setStatus("ACTIVE");
        member.setExpireDate(LocalDate.now().plusMonths(ms.getMonths()));

        // Save to transaction log for admin audit
        TransactionLog log = new TransactionLog(
                memberId, memberName, "RENEWAL",
                oldTier, ms.getTier(), oldPlan, ms.getPlan(), ms.getTotalPrice()
        );
        DataStore.addTransactionLog(log);

        // Print receipt for member
        InvoiceGenerator.printRenewalInvoice(
                memberName, oldTier, ms.getTier(), oldPlan, ms.getPlan(),
                ms.getTotalPrice(), ms.getMonths(), memberId
        );

        System.out.println("\nRenew successful!\n");
        System.out.println("New Tier: " + ms.getTier());
        System.out.println("New Plan: " + ms.getPlan());
        System.out.println("New Expiry Date: " + member.getExpireDate());
    }     
}
