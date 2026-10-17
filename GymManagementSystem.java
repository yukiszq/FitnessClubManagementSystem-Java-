/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gymmanagementsystem;

/**
 * * Entry point of the Gym Management System
 *
 * OOP Concepts Used: - Separation of Concerns: Main only starts the system,
 * delegates to DataInitializer and MainMenu - Static methods: Called directly
 * without creating objects
 * 1
 * @author Victus
 */
public class GymManagementSystem {

    public static void main(String[] args) {

        // Step 1: Load all sample data (trainers, staff, members, classes, equipment)
        // DataInitializer sets up the system with default values for testing
        DataInitializer.init();

        // Step 2: Launch the main menu (login screen)
        // User will choose to login as Member, Trainer, Staff, or Admin
        MainMenu.mainMenu();
    }
}
