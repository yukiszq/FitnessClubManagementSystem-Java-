/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gymmanagementsystem;

/**
 * Generates printable invoices/receipts for member registration and renewal
 *
 * OOP Concepts Used: - Static methods: Utility class, no instance needed -
 * Encapsulation: Transaction counter and formatters are private - Separation of
 * Concerns: Invoice generation is separate from payment processing
 *
 * @author Victus
 */
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class InvoiceGenerator {
    // Formatters for consistent date/time display
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private static int transactionCounter = 1;  // Auto-increment for unique transaction IDs

    /**
     * Prints receipt when a new member registers Shows: member details,
     * membership purchased, amount paid, expiry date
     */
    public static void printRegistrationInvoice(String name, String tier, String plan,
            double totalPrice, int months, String memberId) {
        System.out.println("\n");
        System.out.println("================================================================================");
        System.out.println("                                  RECEIPT");
        System.out.println("================================================================================");
        System.out.println("Date & Time     : " + LocalDateTime.now().format(TIME_FORMATTER));
        System.out.println("Transaction ID  : " + generateTransactionId());
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("MEMBER DETAILS");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("Member ID       : " + memberId);
        System.out.println("Name            : " + name);
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("MEMBERSHIP DETAILS");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("Tier            : " + tier);
        System.out.println("Plan            : " + plan);
        System.out.println("Duration        : " + months + " month(s)");
        System.out.println("Start Date      : " + LocalDate.now().format(DATE_FORMATTER));
        System.out.println("Expiry Date     : " + LocalDate.now().plusMonths(months).format(DATE_FORMATTER));
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("PAYMENT SUMMARY");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.printf("Total Amount    : RM %.2f\n", totalPrice);
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("Payment Status  : PAID");
        System.out.println("================================================================================");
        System.out.println("Thank you for registering!");
        System.out.println("Please keep this receipt for future reference.");
        System.out.println();
    }

    /**
     * Prints receipt when an existing member renews their membership Shows:
     * previous plan, new plan, amount paid, new expiry date
     */
    public static void printRenewalInvoice(String name, String oldTier, String newTier,
            String oldPlan, String newPlan, double totalPrice,
            int months, String memberId) {
        System.out.println("\n");
        System.out.println("================================================================================");
        System.out.println("                                RENEWAL RECEIPT");
        System.out.println("================================================================================");
        System.out.println("Date & Time     : " + LocalDateTime.now().format(TIME_FORMATTER));
        System.out.println("Transaction ID  : " + generateTransactionId());
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("MEMBER DETAILS");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("Member ID       : " + memberId);
        System.out.println("Name            : " + name);
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("MEMBERSHIP UPGRADE DETAILS");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("Previous Tier   : " + oldTier);
        System.out.println("Previous Plan   : " + oldPlan);
        System.out.println("New Tier        : " + newTier);
        System.out.println("New Plan        : " + newPlan);
        System.out.println("Duration Added  : " + months + " month(s)");
        System.out.println("New Expiry Date : " + LocalDate.now().plusMonths(months).format(DATE_FORMATTER));
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("PAYMENT SUMMARY");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.printf("Total Amount    : RM %.2f\n", totalPrice);
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("Payment Status  : PAID");
        System.out.println("================================================================================");
        System.out.println("Thank you for renewing your membership!");
        System.out.println("Please keep this receipt for future reference.");
        System.out.println();
    }

    // Generate unique transaction ID: TXN + date + sequence number
    // Example: TXN202604200001 (first transaction on April 20, 2026)
    private static String generateTransactionId() {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String seq = String.format("%04d", transactionCounter++);
        return "TXN" + date + seq;
    }    
}
