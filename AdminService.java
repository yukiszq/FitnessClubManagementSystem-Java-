/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gymmanagementsystem;

/**
 * Admin Service - Controls admin menu and actions
 *
 * OOP Concepts: - Interface Implementation (implements UserService) -
 * Polymorphism - Composition - AdminService HAS-A admin, classService,
 * equipService, reportService - Delegation - Most tasks are delegated to other
 * service classes
 * 
 * @author Victus
 */
public class AdminService implements UserService{

    // Composition: AdminService owns these helper objects
    private Admin admin;
    private ClassManagement classService;      // For class operations
    private EquipmentManagement equipService;  // For equipment operations
    private ReportGenerator reportService;     // For generating reports

    public AdminService(Admin admin) {
        this.admin = admin;
        this.classService = new ClassManagement();
        this.equipService = new EquipmentManagement();
        this.reportService = new ReportGenerator();
    }

    // Interface method - displays personalized welcome message
    @Override
    public void displayWelcome() {
        System.out.println("\n==========================================");
        System.out.println("Welcome back, " + admin.getName() + "!");
        System.out.println("==========================================");
        System.out.println("Role      : " + admin.getRole());
        System.out.println("ID        : " + admin.getId());
        System.out.println("==========================================");
    }

    // Main menu loop - grouped by categories for better UX
    @Override
    public void showMenu() {
        displayWelcome();

        while (true) {
            System.out.println("\n=== ADMIN MENU ===");
            System.out.println("\n--- MANAGEMENT ---");
            System.out.println("1. Create Employee Account");
            System.out.println("2. Modify Fitness Class");
            System.out.println("3. Modify Equipment");

            System.out.println("\n--- REPORTS ---");
            System.out.println("4. View Reports");

            System.out.println("\n--- PROFILE ---");
            System.out.println("5. Edit Profile");
            System.out.println("0. Logout");

            int choice = Input.getMenuChoice("Choice (0-5): ", 0, 5);

            switch (choice) {
                case 1:
                    AccountCreation.createEmployeeAccount();  // Static call - utility class
                    break;
                case 2:
                    classService.manageClassMenu();   // Delegate to ClassManagement
                    break;
                case 3:
                    equipService.manageEquipmentMenu(); // Delegate to EquipmentManagement
                    break;
                case 4:
                    reportsSubMenu();  // Nested sub-menu
                    break;
                case 5:
                    ProfileManager.editProfile(admin);  // Reusable profile editor
                    break;
                case 0:
                    System.out.println("Logging out...");
                    return;
            }
        }
    }

    // Sub-menu for all report options - keeps main menu clean
    private void reportsSubMenu() {
        while (true) {
            System.out.println("\n=== REPORTS MENU ===");
            System.out.println("1. Track Member Status");
            System.out.println("2. Track Employee Status");
            System.out.println("3. View Revenue Report");
            System.out.println("4. View Transaction Logs");
            System.out.println("5. View Admin Logs");
            System.out.println("0. Back");

            int choice = Input.getMenuChoice("Choice (0-5): ", 0, 5);

            switch (choice) {
                case 1:
                    reportService.trackMemberStatus();
                    break;
                case 2:
                    reportService.trackEmployeeStatus();
                    break;
                case 3:
                    reportService.viewRevenueReport();
                    break;
                case 4:
                    viewTransactionSubMenu();  // Another nested sub-menu
                    break;
                case 5:
                    reportService.viewAdminLogs();
                    break;
                case 0:
                    return;
            }
        }
    }

    // Deep nested menu for transaction filtering
    private void viewTransactionSubMenu() {
        while (true) {
            System.out.println("\n=== TRANSACTION LOGS ===");
            System.out.println("1. View All Transactions");
            System.out.println("2. View Member Transactions");
            System.out.println("0. Back");

            int choice = Input.getMenuChoice("Choice (0-2): ", 0, 2);

            switch (choice) {
                case 1:
                    reportService.viewAllTransactions();  // Show all payments
                    break;
                case 2:
                    String memberId = Input.getString("Enter Member ID: ");
                    reportService.viewMemberTransactions(memberId);  // Filter by member
                    break;
                case 0:
                    return;
            }
        }
    }
}
