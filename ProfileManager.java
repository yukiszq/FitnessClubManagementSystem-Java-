/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gymmanagementsystem;

/**
 * Handles profile viewing and editing for all user types (Member, Trainer,
 * Staff, Admin)
 *
 * OOP Concepts Used: - Polymorphism: Works with any User type, displays
 * role-specific info using instanceof - Encapsulation: Profile data accessed
 * through getters/setters - Code Reuse: Single method handles all user types
 * 
 * @author Victus
 */
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ProfileManager {
    
    /**
     * Shows current profile, lets user edit name, phone, or password Then shows
     * updated profile
     */
    public static void editProfile(User user) {
        // Show current profile first so user knows what they have
        displayCurrentProfile(user);

        System.out.println("\n=== EDIT PROFILE ===");
        System.out.println("1. Name");
        System.out.println("2. Phone");
        System.out.println("3. Password");
        System.out.println("0. Back");

        int choice = Input.getMenuChoice("Choice: ", 0, 3);

        switch (choice) {
            case 1:
                String newName = Input.getName("Enter new name: ");
                user.setName(newName);
                System.out.println("\nName updated successfully!");
                break;
            case 2:
                String newPhone = Input.getPhone("Enter new phone number: ");
                user.setPhno(newPhone);
                System.out.println("\nPhone updated successfully!");
                break;
            case 3:
                String newPassword = Input.getPassword();
                user.setPassword(newPassword);
                System.out.println("\nPassword updated successfully!");
                break;
            case 0:
                return;
        }

        // Show updated profile after change for confirmation
        System.out.println("\n=== UPDATED PROFILE ===");
        displayCurrentProfile(user);
    }

    /**
     * Displays user profile with role-specific information - Members: Shows
     * tier, plan, status, expiry date, days left - Trainers: Shows client count
     * (if needed) - Staff/Admin: Shows basic info only
     */
    public static void displayCurrentProfile(User user) {
        System.out.println("\n========== CURRENT PROFILE ==========");
        System.out.println("ID       : " + user.getId());
        System.out.println("Name     : " + user.getName());
        System.out.println("Phone    : " + user.getPhno());
        System.out.println("Role     : " + user.getRole());

        // Member-specific info (polymorphism - checking actual type)
        if (user instanceof Member) {
            Member member = (Member) user;
            member.checkStatus();  // Update status before displaying
            System.out.println("Tier     : " + member.getTier());
            System.out.println("Plan     : " + member.getPlan());
            System.out.println("Status   : " + member.getStatus());

            if (member.getStatus().equals("ACTIVE")) {
                System.out.println("Expiry   : " + member.getExpireDate());
                long daysLeft = ChronoUnit.DAYS.between(LocalDate.now(), member.getExpireDate());
                System.out.println("Days Left: " + daysLeft + " days");
            } else {
                System.out.println("Expired  : " + member.getExpireDate());
            }
        }

        // Trainer-specific info could be added here if needed
        // if (user instanceof Trainer) { ... }
        System.out.println("=====================================");
    }
}
