/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gymmanagementsystem;

/**
 * Records payment transactions for audit and reporting purposes
 *
 * OOP Concepts Used: - Encapsulation: All transaction data is private, accessed
 * via getters - Static members: Shared counter for generating unique
 * transaction IDs - Data persistence ready: toFileString() method for saving to
 * file
 * 
 * @author Victus
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TransactionLog {
    // Transaction details
    private String transactionId;      // Unique ID (TXN202604200001)
    private String memberId;           // Who made the transaction
    private String memberName;         // Member's name
    private String transactionType;    // "REGISTRATION" or "RENEWAL"

    // Before and after values (for renewal tracking)
    private String oldTier;            // Previous tier (or "NONE" for registration)
    private String newTier;            // New tier purchased
    private String oldPlan;            // Previous plan (or "NONE" for registration)
    private String newPlan;            // New plan purchased

    private double amount;             // Amount paid
    private LocalDateTime transactionDate;  // When transaction occurred

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static int counter = 1;    // Auto-increment for transaction ID sequence

    /**
     * Constructor - automatically generates transaction ID and timestamp
     */
    public TransactionLog(String memberId, String memberName, String transactionType,
            String oldTier, String newTier, String oldPlan,
            String newPlan, double amount) {
        this.transactionId = generateTransactionId();
        this.memberId = memberId;
        this.memberName = memberName;
        this.transactionType = transactionType;
        this.oldTier = oldTier;
        this.newTier = newTier;
        this.oldPlan = oldPlan;
        this.newPlan = newPlan;
        this.amount = amount;
        this.transactionDate = LocalDateTime.now();
    }

    /**
     * Generates unique transaction ID Format: TXN + yyyyMMdd + 4-digit sequence
     * Example: TXN202604200001 (first transaction on April 20, 2026)
     */
    private static String generateTransactionId() {
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String seq = String.format("%04d", counter++);
        return "TXN" + date + seq;
    }

    // ========== GETTERS ==========
    public String getTransactionId() {
        return transactionId;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public String getOldTier() {
        return oldTier;
    }

    public String getNewTier() {
        return newTier;
    }

    public String getOldPlan() {
        return oldPlan;
    }

    public String getNewPlan() {
        return newPlan;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    /**
     * Display transaction in table format (used by ReportGenerator)
     */
    public void display() {
        System.out.printf("| %-12s | %-10s | %-15s | %-12s | %-10s | %-10s | RM%-8.2f |\n",
                transactionId, memberId, memberName, transactionType,
                newTier, newPlan, amount);
    }

    /**
     * Convert transaction to file format for data persistence (save/load)
     * Fields separated by "|" for easy parsing
     */
    public String toFileString() {
        return transactionId + "|" + memberId + "|" + memberName + "|" + transactionType + "|"
                + oldTier + "|" + newTier + "|" + oldPlan + "|" + newPlan + "|" + amount + "|"
                + transactionDate.format(FORMATTER);
    }    
}
