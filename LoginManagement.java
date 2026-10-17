/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gymmanagementsystem;

/**
 * Handles user login for different roles (Member, Trainer, Staff, Admin)
 *
 * OOP Concepts Used: - Polymorphism: Returns different User types (Member,
 * Trainer, Staff, Admin) - Delegation: Authentication logic is delegated to
 * UserAuthentication class - Encapsulation: Login process is self-contained
 *
 * 
 * @author Victus
 */
public class LoginManagement {
    
    // Main login menu - user chooses their role first
    public static void showLoginMenu() {
        System.out.println("+======================================================+");
        System.out.println("|  Login as:                                           |");
        System.out.println("|  1. Member                                           |");
        System.out.println("|  2. Trainer                                          |");
        System.out.println("|  3. Staff                                            |");
        System.out.println("|  4. Admin                                            |");
        System.out.println("|  0. Back                                             |");
        System.out.println("+======================================================+");

        int choice = Input.getMenuChoice("Choice (0-4): ", 0, 4);

        switch (choice) {
            case 1:
                performLogin("Member");
                break;
            case 2:
                performLogin("Trainer");
                break;
            case 3:
                performLogin("Staff");
                break;
            case 4:
                performLogin("Admin");
                break;
            case 0:
                return;
        }
    }

    // Handle login for a specific role
    // Keeps asking until valid credentials or user cancels with "0"
    private static void performLogin(String role) {
        System.out.println("+======================================================+");
        System.out.printf("|  %25s %-25s |\n", role.toUpperCase(), " LOGIN");
        System.out.println("+======================================================+");

        while (true) {
            String id = Input.getString("Enter ID (type 0 to exit): ");
            if (id.equals("0")) {
                return;  // User cancelled
            }
            String password = Input.getString("Enter Password: ");

            // Delegate authentication to UserAuthentication class
            User user = UserAuthentication.authenticate(id, password, role);

            if (user != null) {
                // Login successful - launch the appropriate menu for this user
                UserAuthentication.chooseRole(user);
                return;
            } else {
                System.out.println("Invalid ID or Password. Try again.\n");
            }
        }
    }
}
