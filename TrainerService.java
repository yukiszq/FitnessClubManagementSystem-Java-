/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gymmanagementsystem;

/**
 * OOP Concepts Used: - Interface Implementation (implements UserService) -
 * Polymorphism - Composition: TrainerService HAS-A Trainer, ClientManager,
 * ProgressTracker - Delegation: Client and progress tasks delegated to their
 * respective manager classes
 * 
 * @author Victus
 */
public class TrainerService implements UserService{
    private Trainer trainer;                    // The logged-in trainer (composition)
    private ClientManager clientManager;       // Handles client management
    private ProgressTracker progressTracker;   // Handles client progress tracking

    public TrainerService(Trainer trainer) {
        this.trainer = trainer;
        this.clientManager = new ClientManager(trainer);
        this.progressTracker = new ProgressTracker(trainer);
    }

    // Display personalized welcome message
    @Override
    public void displayWelcome() {
        System.out.println("\n==========================================");
        System.out.println("Welcome back, " + trainer.getName() + "!");
        System.out.println("==========================================");
        System.out.println("Role      : " + trainer.getRole());
        System.out.println("ID        : " + trainer.getId());
        System.out.println("==========================================");
    }

    // Main trainer menu loop
    @Override
    public void showMenu() {
        displayWelcome();

        while (true) {
            System.out.println("\n=== TRAINER MENU ===");
            System.out.println("1. View My Group Classes");   // Classes this trainer teaches
            System.out.println("2. Add Client");              // Add personal training client
            System.out.println("3. View My Clients");         // List all personal clients
            System.out.println("4. Track Client Progress");   // View/update client BMI
            System.out.println("5. Remove Client");           // Remove personal client
            System.out.println("6. Edit Profile");            // Edit own profile
            System.out.println("0. Logout");

            int choice = Input.getMenuChoice("Choice (0-6): ", 0, 6);

            switch (choice) {
                case 1:
                    clientManager.viewGroupClasses();   // Show upcoming group classes
                    break;
                case 2:
                    clientManager.addClient();          // Add new personal client
                    break;
                case 3:
                    clientManager.viewClients();        // View all personal clients
                    break;
                case 4:
                    progressTracker.trackProgress();    // Track client BMI progress
                    break;
                case 5:
                    clientManager.removeClient();       // Remove a client
                    break;
                case 6:
                    ProfileManager.editProfile(trainer); // Edit trainer's profile
                    break;
                case 0:
                    System.out.println("Logging out...");
                    return;
            }
        }
    }
}
