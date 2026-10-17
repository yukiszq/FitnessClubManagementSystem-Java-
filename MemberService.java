/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gymmanagementsystem;

/**
 * Controls all member functions based on membership tier (Basic, Silver, Gold)
 *
 * OOP Concepts Used: - Interface Implementation (implements UserService) -
 * Polymorphism - Composition - MemberService HAS-A Member, ClassBooking,
 * SaunaManagement, PaymentProcessor - Delegation - Each feature delegated to
 * specialized classes - Method Overriding - Implements displayWelcome() and
 * showMenu() from interface
 * 
 * @author Victus
 */
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class MemberService implements UserService{
    
    // Composition: MemberService owns these helper objects
    private Member member;                     // The logged-in member
    private ClassBooking classBooking;         // For booking classes
    private SaunaManagement saunaManager;      // For sauna access (Gold only)
    private PaymentProcessor paymentProcessor; // For renewals

    public MemberService(Member member) {
        this.member = member;
        this.classBooking = new ClassBooking(member);
        this.saunaManager = new SaunaManagement(member);
        this.paymentProcessor = new PaymentProcessor(member);
    }

    // Interface method - displays personalized welcome message with membership info
    @Override
    public void showMenu() {
        displayWelcome();  // Show welcome message first

        String tier = member.getTier();

        // Different menu based on membership tier
        if (tier.equalsIgnoreCase("Basic")) {
            basicMenu();
        } else if (tier.equalsIgnoreCase("Silver")) {
            silverMenu();
        } else if (tier.equalsIgnoreCase("Gold")) {
            goldMenu();
        } else {
            System.out.println("Unknown tier.");
        }
    }

    // Display welcome message with membership status and days remaining
    @Override
    public void displayWelcome() {
        member.checkStatus();  // Update expiry status first

        System.out.println("\n==========================================");
        System.out.println("Welcome back, " + member.getName() + "!");
        System.out.println("==========================================");
        System.out.println("Tier      : " + member.getTier());
        System.out.println("Plan      : " + member.getPlan());
        System.out.println("Status    : " + member.getStatus());

        if (member.getStatus().equals("ACTIVE")) {
            long daysLeft = ChronoUnit.DAYS.between(LocalDate.now(), member.getExpireDate());
            System.out.println("Expiry    : " + member.getExpireDate());
            System.out.println("Days Left : " + daysLeft + " days remaining");
        }
        System.out.println("==========================================");
    }

    // Check if membership expired and prompt renewal
    // Returns true if active or just renewed, false if expired and user declined
    private boolean checkAndPromptRenewal() {
        member.checkStatus();

        if (member.getStatus().equals("EXPIRED")) {
            System.out.println("\n==========================================");
            System.out.println("MEMBERSHIP EXPIRED");
            System.out.println("==========================================");
            System.out.println("Your membership expired on: " + member.getExpireDate());
            System.out.println("You cannot access member features until you renew.");
            System.out.println("==========================================");

            System.out.print("\nDo you want to renew now? (y/n): ");
            String choice = Input.sc.nextLine().trim().toLowerCase();

            if (choice.equals("y") || choice.equals("yes")) {
                paymentProcessor.renew();
                return true;  // Renewed successfully
            } else {
                System.out.println("\nYou cannot use member features without renewing.");
                return false;  // Still expired
            }
        }
        return true;  // Membership is active
    }

    // ========== BASIC MEMBER MENU ==========
    // Basic members: equipment access only, no class booking
    private void basicMenu() {
        while (true) {
            if (!checkAndPromptRenewal()) {
                System.out.println("Returning to login...");
                return;
            }

            System.out.println("\n=== BASIC MEMBER MENU ===");
            System.out.println("1. BMI");
            System.out.println("2. View Equipment");
            System.out.println("3. Edit Profile");
            System.out.println("0. Logout");

            int choice = Input.getMenuChoice("Choice (0-3): ", 0, 3);

            switch (choice) {
                case 1:
                    BMICalculator.calculateAndDisplay(member);
                    break;
                case 2:
                    EquipmentViewer.showAvailableOnly();  // Only show working equipment
                    break;
                case 3:
                    ProfileManager.editProfile(member);
                    break;
                case 0:
                    return;
            }
        }
    }

    // ========== SILVER MEMBER MENU ==========
    // Silver members: can book classes, no sauna access
    private void silverMenu() {
        while (true) {
            if (!checkAndPromptRenewal()) {
                System.out.println("Returning to login...");
                return;
            }

            System.out.println("\n=== SILVER MEMBER MENU ===");
            System.out.println("1. Book Class");
            System.out.println("2. View/Cancel Class");
            System.out.println("3. BMI");
            System.out.println("4. View Equipment");
            System.out.println("5. Edit Profile");
            System.out.println("0. Logout");

            int choice = Input.getMenuChoice("Choice (0-5): ", 0, 5);

            switch (choice) {
                case 1:
                    classBooking.book();           // Book a fitness class
                    break;
                case 2:
                    classBooking.viewAndCancel();  // View or cancel bookings
                    break;
                case 3:
                    BMICalculator.calculateAndDisplay(member);
                    break;
                case 4:
                    EquipmentViewer.showAvailableOnly();
                    break;
                case 5:
                    ProfileManager.editProfile(member);
                    break;
                case 0:
                    return;
            }
        }
    }

    // ========== GOLD MEMBER MENU ==========
    // Gold members: can book classes + sauna access
    private void goldMenu() {
        while (true) {
            if (!checkAndPromptRenewal()) {
                System.out.println("Returning to login...");
                return;
            }

            System.out.println("\n=== GOLD MEMBER MENU ===");
            System.out.println("1. Book Class");
            System.out.println("2. View/Cancel Class");
            System.out.println("3. Book Sauna");
            System.out.println("4. BMI");
            System.out.println("5. View Equipment");
            System.out.println("6. Edit Profile");
            System.out.println("0. Logout");

            int choice = Input.getMenuChoice("Choice (0-6): ", 0, 6);

            switch (choice) {
                case 1:
                    classBooking.book();
                    break;
                case 2:
                    classBooking.viewAndCancel();
                    break;
                case 3:
                    saunaManager.book();           // Sauna access (Gold exclusive)
                    break;
                case 4:
                    BMICalculator.calculateAndDisplay(member);
                    break;
                case 5:
                    EquipmentViewer.showAvailableOnly();
                    break;
                case 6:
                    ProfileManager.editProfile(member);
                    break;
                case 0:
                    return;
            }
        }
    }
}