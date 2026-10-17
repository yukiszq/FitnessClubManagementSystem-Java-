/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gymmanagementsystem;

/**
 * Allows trainers to track and update their clients' fitness progress (weight,
 * height, BMI)
 *
 * OOP Concepts Used: - Composition: ProgressTracker HAS-A Trainer (the trainer
 * using it) - Delegation: BMI calculation delegated to Member class (single
 * source of truth) - Encapsulation: Client data accessed through Member's
 * getters/setters
 * 
 * @author Victus
 */
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ProgressTracker {
    private Trainer trainer;  // The trainer who owns this tracker (composition)
    private static final double MIN_WEIGHT = 1.0;
    private static final double MAX_WEIGHT = 300.0;
    private static final double MIN_HEIGHT = 0.5;
    private static final double MAX_HEIGHT = 3.0;

    public ProgressTracker(Trainer trainer) {
        this.trainer = trainer;
    }

    /**
     * Main flow: show trainer's clients, let them pick one, display progress,
     * offer update
     */
    public void trackProgress() {
        System.out.println("\n=== TRACK CLIENT PROGRESS ===");

        ArrayList<Member> myClients = trainer.getAssignedClients();

        if (myClients.isEmpty()) {
            System.out.println("You have no clients to track.");
            return;
        }

        // Show list of clients so trainer knows who to pick
        System.out.println("\nYour Clients:");
        System.out.println("+----------+--------------------+-----------------+------------+");
        System.out.printf("| %-8s | %-18s | %-15s | %-10s |\n",
                "ID", "NAME", "PHONE", "TIER");
        System.out.println("+----------+--------------------+-----------------+------------+");

        for (Member m : myClients) {
            System.out.printf("| %-8s | %-18s | %-15s | %-10s |\n",
                    m.getId(),
                    m.getName(),
                    m.getPhno(),
                    m.getTier());
        }
        System.out.println("+----------+--------------------+-----------------+------------+");

        String memberId = Input.getString("\nEnter Member ID to track: ");

        Member target = null;
        for (Member m : myClients) {
            if (m.getId().equalsIgnoreCase(memberId)) {
                target = m;
                break;
            }
        }

        if (target == null) {
            System.out.println("Client not found!");
            return;
        }

        // Show current progress report
        displayClientProgress(target);

        // Ask if trainer wants to update measurements
        System.out.print("\nDo you want to update this client's measurements? (y/n): ");
        String update = Input.sc.nextLine().trim().toLowerCase();

        if (update.equals("y") || update.equals("yes")) {
            updateClientProgress(target);
        }
    }

    /**
     * Displays client's current measurements and BMI with health
     * recommendations
     */
    private void displayClientProgress(Member member) {
        System.out.println("\n==========================================");
        System.out.println("CLIENT PROGRESS REPORT");
        System.out.println("==========================================");
        System.out.println("Name   : " + member.getName());
        System.out.println("ID     : " + member.getId());
        System.out.println("Tier   : " + member.getTier());
        System.out.println("Status : " + member.getStatus());
        System.out.println("==========================================");

        // Current measurements
        System.out.println("\n--- CURRENT MEASUREMENTS ---");
        if (member.getHeight() > 0) {
            System.out.printf("Height  : %.2f m\n", member.getHeight());
        } else {
            System.out.println("Height  : Not set");
        }

        if (member.getWeight() > 0) {
            System.out.printf("Weight  : %.2f kg\n", member.getWeight());
            // BMI calculation reused from Member class (single source of truth)
            System.out.printf("BMI     : %.2f (%s)\n",
                    member.calculateBMI(),
                    member.getBMICategory());
        } else {
            System.out.println("Weight  : Not set");
            System.out.println("BMI     : Not available");
        }

        // Reference chart for trainer/client understanding
        System.out.println("\n--- HEALTHY BMI RANGE ---");
        System.out.println("Underweight : < 18.5");
        System.out.println("Normal      : 18.5 - 24.9");
        System.out.println("Overweight  : 25.0 - 29.9");
        System.out.println("Obese       : > 30.0");

        // Personalized recommendations based on BMI
        if (member.getWeight() > 0 && member.getHeight() > 0) {
            double bmi = member.calculateBMI();
            System.out.println("\n--- RECOMMENDATION ---");
            if (bmi < 18.5) {
                System.out.println("Underweight: Focus on healthy weight gain");
                System.out.println("Increase protein intake");
                System.out.println("Strength training to build muscle");
            } else if (bmi < 25) {
                System.out.println("Healthy weight range - maintain!");
                System.out.println("Continue balanced diet and exercise");
            } else if (bmi < 30) {
                System.out.println("Overweight: Focus on fat loss");
                System.out.println("Increase cardio and calorie deficit");
                System.out.println("High intensity interval training");
            } else {
                System.out.println("Obese: Consult with nutritionist");
                System.out.println("Low impact exercises recommended");
                System.out.println("Focus on diet and gradual weight loss");
            }
        }
    }

    /**
     * Allows trainer to update client's weight and/or height Pressing Enter
     * keeps current value
     */
    private void updateClientProgress(Member member) {
        System.out.println("\n=== UPDATE CLIENT MEASUREMENTS ===");

        // Weight update - optional, press Enter to skip
        System.out.print("Enter weight (kg) or press Enter to keep current: ");
        String weightInput = Input.sc.nextLine().trim();
        if (!weightInput.isEmpty()) {
            try {
                double weight = Double.parseDouble(weightInput);
                if (weight >= MIN_WEIGHT && weight <= MAX_WEIGHT) {
                    member.setWeight(weight);
                    System.out.println("Weight updated to " + weight + " kg");
                } else {
                    System.out.println("Error: Weight must be between " + MIN_WEIGHT + " - " + MAX_WEIGHT + " kg");
                    System.out.println("Keeping previous value: " + member.getWeight() + " kg");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid number format. Weight not updated.");
            }
        }

        // Height update - optional, press Enter to skip
        System.out.print("Enter height (m) or press Enter to keep current: ");
        String heightInput = Input.sc.nextLine().trim();
        if (!heightInput.isEmpty()) {
            try {
                double height = Double.parseDouble(heightInput);
                if (height >= MIN_HEIGHT && height <= MAX_HEIGHT) {
                    member.setHeight(height);
                    System.out.println("Height updated!");
                } else {
                    System.out.println("Error: Height must be between " + MIN_HEIGHT + " - " + MAX_HEIGHT + " m");
                    System.out.println("Keeping previous value: " + member.getHeight() + " m");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Height not updated.");
            }
        }

        // Show updated results
        System.out.println("\n==========================================");
        System.out.println("UPDATED PROGRESS");
        System.out.println("==========================================");
        System.out.printf("Weight  : %.2f kg\n", member.getWeight());
        System.out.printf("Height  : %.2f m\n", member.getHeight());
        System.out.printf("BMI     : %.2f (%s)\n",
                member.calculateBMI(),
                member.getBMICategory());
        System.out.println("==========================================");

        // Record this action for admin audit trail
        DataStore.getLogs().add(new AdminLog("Trainer " + trainer.getId()
                + " updated progress for member " + member.getId()));
    }    
}
