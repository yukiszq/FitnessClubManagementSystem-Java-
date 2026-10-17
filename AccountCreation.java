/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gymmanagementsystem;

/**
 * Handles creation of employee accounts (Staff, Trainer, Admin) Only accessible
 * by Admin users
 * 
 * @author Victus
 */
public class AccountCreation {
    /**
     * Creates a new employee account based on role selection Demonstrates: -
     * Polymorphism: Creates different User subtypes (Staff/Trainer/Admin) -
     * Encapsulation: User data is set through constructor - Code reuse: Uses
     * Input class for all user input validation
     */
    public static void createEmployeeAccount() {
        System.out.println("\n=== CREATE EMPLOYEE ACCOUNT ===");

        // Let admin choose what type of employee to create
        // 1 = Staff, 2 = Trainer, 3 = Admin, 0 = Cancel
        int roleChoice = Input.getMenuChoice("Select Role:\n1. Staff\n2. Trainer\n3. Admin\n0. Back\nChoice (0-3): ", 0, 3);

        if (roleChoice == 0) {
            return;  // User cancelled the operation
        }

        // Collect basic information for the new employee
        // All inputs have validation in the Input class
        String name = Input.getName("Enter Name: ");
        String ic = Input.getIC("Enter IC (12 digits): ");
        String phno = Input.getPhone("Enter Phone Number: ");
        String password = Input.getPassword();  // Strong password validation applied

        String role;
        String id;
        User newUser;  // Polymorphic reference - can hold any User subtype

        // Create the appropriate user object based on role choice
        // ID format: Sxxxx for Staff, Txxxx for Trainer, Axxxx for Admin
        // ID number is based on current user count
        switch (roleChoice) {
            case 1:
                role = "Staff";
                id = "S" + (1000 + DataStore.getUsers().size() + 1);
                newUser = new Staff(id, password, name, ic, phno);
                break;
            case 2:
                role = "Trainer";
                id = "T" + (1000 + DataStore.getUsers().size() + 1);
                newUser = new Trainer(id, password, name, ic, phno);
                break;
            default:
                role = "Admin";
                id = "A" + (1000 + DataStore.getUsers().size() + 1);
                newUser = new Admin(id, password, name, ic, phno);
                break;
        }

        // Record this action in admin logs for audit trail
        DataStore.getLogs().add(new AdminLog("Created " + role + " account: " + id));

        // Add the new user to the system's data storage
        DataStore.getUsers().add(newUser);

        // Confirm success and show the generated ID
        System.out.println("\nAccount created successfully!");
        System.out.println("ID: " + id);
        System.out.println("Role: " + role);
    }    
}
