/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gymmanagementsystem;

/**
 * Handles user authentication and routes to appropriate service after login
 *
 * OOP Concepts Used: - Polymorphism: authenticate() returns User type,
 * chooseRole() handles different subtypes - Static methods: Utility class, no
 * instance needed - Delegation: After login, control passed to respective
 * service classes
 *
 * @author Victus
 */
import java.time.LocalDate;

public class UserAuthentication {
//    // ========== AUTHENTICATION ==========
//    // Verifies user credentials and returns the User object if valid
//    // Returns null if authentication fails
//    public static User authenticate(String id, String password, String role) {
//        for (User u : DataStore.getUsers()) {
//            if (u.getId().equalsIgnoreCase(id)
//                    && u.checkPassword(password)
//                    && u.getRole().equalsIgnoreCase(role)) {
//                return u;
//            }
//        }
//        return null;
//    }
//
//    // ========== ROUTING AFTER LOGIN ==========
//    // Polymorphism: same method handles different User subtypes
//    public static void chooseRole(User user) {
//        String userRole = user.getRole();
//
//        if (userRole.equals("Member")) {
//            handleMemberLogin((Member) user);
//        } else if (userRole.equals("Staff")) {
//            handleStaffLogin((Staff) user);
//        } else if (userRole.equals("Trainer")) {
//            handleTrainerLogin((Trainer) user);
//        } else if (userRole.equals("Admin")) {
//            handleAdminLogin((Admin) user);
//        } else {
//            System.out.println("Unknown role.");
//        }
//    }
//
//    // Member login - checks expiry before allowing access
//    private static void handleMemberLogin(Member member) {
//        member.checkStatus();  // Update expiry status
//
//        // If membership expired, offer renewal option
//        if (member.getStatus().equals("EXPIRED")) {
//            System.out.println("\nYour membership is EXPIRED!");
//            System.out.println("1. Renew Membership");
//            System.out.println("2. Exit");
//
//            int choice = Input.getMenuChoice("Enter choice (1-2): ", 1, 2);
//
//            if (choice == 1) {
//                PaymentProcessor paymentProcessor = new PaymentProcessor(member);
//                paymentProcessor.renew();
//                member.checkStatus();  // Check again after renewal
//
//                if (member.getStatus().equals("EXPIRED")) {
//                    return; // Still expired, deny login
//                }
//            } else {
//                return; // User chose to exit
//            }
//        }
//
//        System.out.println("Login Successful!");
//        MemberService memberService = new MemberService(member);
//        memberService.showMenu();
//    }
//
//    // Staff login - direct access
//    private static void handleStaffLogin(Staff staff) {
//        System.out.println("Login Successful!");
//        StaffService staffService = new StaffService(staff);
//        staffService.showMenu();
//    }
//
//    // Trainer login - direct access
//    private static void handleTrainerLogin(Trainer trainer) {
//        System.out.println("Login Successful!");
//        TrainerService trainerService = new TrainerService(trainer);
//        trainerService.showMenu();
//    }
//
//    // Admin login - direct access
//    private static void handleAdminLogin(Admin admin) {
//        System.out.println("Login Successful!");
//        AdminService adminService = new AdminService(admin);
//        adminService.showMenu();
//    }
    // Verifies user credentials and returns the User object if valid
    // Returns null if authentication fails
    public static User authenticate(String id, String password, String role) {
        for (User u : DataStore.getUsers()) {
            if (u.getId().equalsIgnoreCase(id)
                    && u.checkPassword(password)
                    && u.getRole().equalsIgnoreCase(role)) {
                return u;
            }
        }
        return null;
    }

    public static void chooseRole(User user) {

        System.out.println("Login Successful!");

        // POLYMORPHISM (runtime object type check only once)
        UserService service;

        if (user instanceof Member member) {
            service = new MemberService(member);

        } else if (user instanceof Staff staff) {
            service = new StaffService(staff);

        } else if (user instanceof Trainer trainer) {
            service = new TrainerService(trainer);

        } else if (user instanceof Admin admin) {
            service = new AdminService(admin);

        } else {
            System.out.println("Unknown role.");
            return;
        }

        // SAME METHOD CALL, DIFFERENT BEHAVIOR
//        service.displayWelcome();
        service.showMenu();
    }    
}
