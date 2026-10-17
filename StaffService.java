/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gymmanagementsystem;

/**
 * Controls staff menu and actions
 *
 * OOP Concepts Used: - Interface Implementation (implements UserService) -
 * Polymorphism - Composition: StaffService HAS-A Staff (the logged-in staff
 * member) - Delegation: Equipment and sauna tasks delegated to their respective
 * management classes
 * @author Victus
 */

public class StaffService implements UserService{

    private Staff staff;  // The logged-in staff member (composition)

    public StaffService(Staff staff) {
        this.staff = staff;
    }

    // Display personalized welcome message
    @Override
    public void displayWelcome() {
        System.out.println("\n==========================================");
        System.out.println("Welcome back, " + staff.getName() + "!");
        System.out.println("==========================================");
        System.out.println("Role      : " + staff.getRole());
        System.out.println("ID        : " + staff.getId());
        System.out.println("==========================================");
    }

    // Main staff menu loop
    @Override
    public void showMenu() {
        displayWelcome();

        while (true) {
            System.out.println("\n=== STAFF MENU ===");
            System.out.println("1. View Equipment");
            System.out.println("2. Set Maintenance");
            System.out.println("3. Set Available");
            System.out.println("4. Validate Sauna Token");
            System.out.println("5. Edit Profile");
            System.out.println("0. Logout");

            int choice = Input.getMenuChoice("Choice (0-5): ", 0, 5);

            switch (choice) {
                case 1:
                    EquipmentViewer.viewAllEquipment();  // View all gym equipment
                    break;
                case 2:
                    EquipmentManagement.setMaintenance();  // Mark equipment as broken
                    break;
                case 3:
                    EquipmentManagement.setAvailable();    // Mark equipment as working again
                    break;
                case 4:
                    SaunaManagement.validateToken();       // Validate member's sauna token
                    break;
                case 5:
                    ProfileManager.editProfile(staff);     // Edit staff's own profile
                    break;
                case 0:
                    System.out.println("Logging out...");
                    return;
            }
        }
    }    
}
