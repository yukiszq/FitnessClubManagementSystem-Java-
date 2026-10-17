/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gymmanagementsystem;

/**
 *
 * @author Victus
 */
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ReportGenerator {
    
    /**
     * Shows all members grouped by tier (Basic, Silver, Gold) Includes: ID,
     * Name, Tier, Plan, Status, Payment status, Expiry date, Price paid
     */
    public void trackMemberStatus() {
        System.out.println("\n=============================================================================================");
        System.out.println("                              MEMBER STATUS REPORT");
        System.out.println("=============================================================================================");

        String[] tiers = {"Basic", "Silver", "Gold"};

        for (String tier : tiers) {
            System.out.println("\n--- " + tier.toUpperCase() + " MEMBERSHIP ---");
            System.out.println("=============================================================================================");
            System.out.printf("%-10s %-15s %-10s %-10s %-10s %-12s %-12s %-10s\n",
                    "ID", "Name", "Tier", "Plan", "Status", "Paid", "Expire", "Price");
            System.out.println("=============================================================================================");

            for (User u : DataStore.getUsers()) {
                if (u instanceof Member) {
                    Member m = (Member) u;
                    m.checkStatus();  // Update expiry status first

                    if (m.getTier().equalsIgnoreCase(tier)) {
                        System.out.printf("%-10s %-15s %-10s %-10s %-10s %-12s %-12s %-10.2f\n",
                                m.getId(), m.getName(), m.getTier(), m.getPlan(),
                                m.getStatus(), m.isPaid(), m.getExpireDate(), m.getPrice());
                    }
                }
            }
            System.out.println("=============================================================================================");
        }

        // Log this action for audit trail
        DataStore.getLogs().add(new AdminLog("Viewed Member Status Report (Basic/Silver/Gold)"));
    }

    /**
     * Shows all employees (Trainers and Staff) Includes: ID, Name, IC, Phone
     * number
     */
    public void trackEmployeeStatus() {
        System.out.println("\n============================================================");
        System.out.println("              EMPLOYEE STATUS REPORT");
        System.out.println("============================================================");

        String[] roles = {"Trainer", "Staff"};

        for (String roleType : roles) {
            System.out.println("\n--- " + roleType.toUpperCase() + " ---");
            System.out.println("============================================================");
            System.out.printf("%-10s %-15s %-15s %-12s\n", "ID", "Name", "IC", "Phone");
            System.out.println("============================================================");

            for (User u : DataStore.getUsers()) {
                if (u.getRole().equalsIgnoreCase(roleType)) {
                    System.out.printf("%-10s %-15s %-15s %-12s\n",
                            u.getId(), u.getName(), u.getIc(), u.getPhno());
                }
            }
            System.out.println("============================================================");
        }

        System.out.println("============================================================");
        DataStore.getLogs().add(new AdminLog("Viewed Employee Status Report"));
    }

    /**
     * Shows revenue from each membership tier with subtotals and grand total
     */
    public void viewRevenueReport() {
        System.out.println("\n================================================================================");
        System.out.println("                           REVENUE REPORT");
        System.out.println("================================================================================");

        double totalRevenue = 0.0;
        String[] tiers = {"Basic", "Silver", "Gold"};

        for (String tierType : tiers) {
            System.out.println("\n--- " + tierType.toUpperCase() + " MEMBERSHIP ---");
            System.out.println("================================================================================");
            System.out.printf("%-10s %-15s %-12s %-12s %-12s %-10s\n",
                    "ID", "Name", "Plan", "Phone", "Status", "Price");
            System.out.println("================================================================================");

            double subtotal = 0.0;
            boolean hasMember = false;

            for (User u : DataStore.getUsers()) {
                if (u instanceof Member) {
                    Member m = (Member) u;
                    m.checkStatus();

                    if (m.getTier().equalsIgnoreCase(tierType)) {
                        hasMember = true;
                        System.out.printf("%-10s %-15s %-12s %-12s %-12s %-10.2f\n",
                                m.getId(), m.getName(), m.getPlan(), m.getPhno(), m.getStatus(), m.getPrice());
                        subtotal += m.getPrice();
                    }
                }
            }

            if (!hasMember) {
                System.out.println("No members in this tier.");
            }

            System.out.println("================================================================================");
            System.out.printf("SUBTOTAL (%s): RM%.2f\n", tierType.toUpperCase(), subtotal);
            System.out.println("================================================================================");
            totalRevenue += subtotal;
        }

        System.out.println("\n================================================================================");
        System.out.printf("TOTAL REVENUE: RM%.2f\n", totalRevenue);
        System.out.println("================================================================================");
        DataStore.getLogs().add(new AdminLog("Viewed Revenue Report"));
    }

    /**
     * Shows all admin actions with timestamps for audit purposes
     */
    public void viewAdminLogs() {
        System.out.println("\n================================================================================");
        System.out.println("                           ADMIN ACTIVITY LOGS");
        System.out.println("================================================================================");

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        if (DataStore.getLogs().isEmpty()) {
            System.out.println("No logs found.");
            return;
        }

        System.out.println("================================================================================");
        System.out.printf("%-5s %-22s %-40s\n", "No", "Date Time", "Action");
        System.out.println("================================================================================");

        int i = 1;
        for (AdminLog log : DataStore.getLogs()) {
            System.out.printf("%-5d %-22s %-40s\n", i++, log.getTime().format(fmt), log.getAction());
        }

        System.out.println("================================================================================");
    }

    /**
     * Shows all payment transactions (registration and renewal) Includes total
     * revenue
     */
    public void viewAllTransactions() {
        System.out.println("\n====================================================================================================");
        System.out.println("                                        ALL TRANSACTIONS");
        System.out.println("====================================================================================================");

        ArrayList<TransactionLog> logs = DataStore.getTransactionLogs();

        if (logs.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        System.out.println("====================================================================================================");
        System.out.printf("%-20s %-10s %-18s %-14s %-10s %-12s %-12s\n",
                "TXN ID", "Member ID", "Member Name", "Type", "Tier", "Plan", "Amount(RM)");
        System.out.println("====================================================================================================");

        double totalRevenue = 0;

        for (TransactionLog log : logs) {
            System.out.printf("%-20s %-10s %-18s %-14s %-10s %-12s %-12.2f\n",
                    log.getTransactionId(),
                    log.getMemberId(),
                    log.getMemberName(),
                    log.getTransactionType(),
                    log.getNewTier(),
                    log.getNewPlan(),
                    log.getAmount()
            );
            totalRevenue += log.getAmount();
        }

        System.out.println("====================================================================================================");
        System.out.printf("%-89s %-12.2f\n", "TOTAL", totalRevenue);
        System.out.println("====================================================================================================");
    }

    /**
     * Shows transactions for a specific member (filtered view) Used by admin to
     * check individual member's payment history
     */
    public void viewMemberTransactions(String memberId) {
        System.out.println("\n====================================================================================================");
        System.out.println("TRANSACTIONS FOR MEMBER: " + memberId);
        System.out.println("====================================================================================================");

        ArrayList<TransactionLog> logs = DataStore.getTransactionLogs();

        if (logs.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        System.out.println("====================================================================================================");
        System.out.printf("%-20s %-12s %-14s %-10s %-12s %-12s\n",
                "TXN ID", "Date", "Type", "Tier", "Plan", "Amount(RM)");
        System.out.println("====================================================================================================");

        double totalSpent = 0;
        boolean found = false;

        for (TransactionLog log : logs) {
            if (log.getMemberId().equalsIgnoreCase(memberId)) {
                found = true;
                System.out.printf("%-20s %-12s %-14s %-10s %-12s %-12.2f\n",
                        log.getTransactionId(),
                        log.getTransactionDate().toLocalDate().toString(),
                        log.getTransactionType(),
                        log.getNewTier(),
                        log.getNewPlan(),
                        log.getAmount()
                );
                totalSpent += log.getAmount();
            }
        }

        if (!found) {
            System.out.println("No transactions found for this member.");
        } else {
            System.out.println("====================================================================================================");
            System.out.printf("%-78s %-12.2f\n", "TOTAL", totalSpent);
        }

        System.out.println("====================================================================================================");
    }    
}
