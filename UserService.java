/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gymmanagementsystem;

/**
 * Interface for all user service classes (MemberService, TrainerService,
 * StaffService, AdminService)
 *
 * OOP Concepts Used: - Abstraction: Defines contract without implementation
 * details - Polymorphism: Different service classes implement the same
 * interface - Interface Segregation: Only two methods, focused on user menu
 * operations
 * 
 * @author Victus
 */
public interface UserService {
    /**
     * Displays the main menu for the logged-in user Each user type (Member,
     * Trainer, Staff, Admin) has its own menu options
     */
    void showMenu();

    /**
     * Displays personalized welcome message when user logs in Shows user name,
     * role, and role-specific info (e.g., membership expiry for members)
     */
    void displayWelcome();
}
