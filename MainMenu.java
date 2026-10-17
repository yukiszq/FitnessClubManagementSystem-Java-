/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gymmanagementsystem;

/**
 * Main entry point for users - displays welcome screen and main menu options
 *
 * OOP Concepts Used: - Static methods: Utility class, no instance needed -
 * Delegation: Login goes to LoginManagement, Registration goes to
 * RegistrationMember - Code Reuse: Uses Input.getMenuChoice() for validation
 * instead of writing own
 * 
 * @author Victus
 */
public class MainMenu {

    public static void mainMenu() {

        while (true) {
            // Display welcome banner with ASCII art
            System.out.println("+======================================================+");
            System.out.println("|        _______       _______                         |");
            System.out.println("|       |  _____|     |  _____|                        |");
            System.out.println("|       | |____       | |____                          |");
            System.out.println("|       |  ____| IT?  |  ____| AT?  FITNESS CLUB       |");
            System.out.println("|       | |           | |                              |");
            System.out.println("|       |_|           |_|                              |");
            System.out.println("|                                                      |");
            System.out.println("+======================================================+");
            System.out.println("| 1. Login                                             |");
            System.out.println("| 2. Register Member                                   |");
            System.out.println("| 0. Exit                                              |");
            System.out.println("+======================================================+");

            // Input validation is handled by Input class (reused)
            int choice = Input.getMenuChoice(" Enter choice (0-2): ", 0, 2);

            switch (choice) {
                case 1:
                    LoginManagement.showLoginMenu();  // Go to login screen
                    break;
                case 2:
                    RegistrationMember.registerMember();  // New member registration
                    break;
                case 0:
                    System.out.println("Thank you for using the system!");
                    return;  // Exit program
            }
        }
    }  
}
