/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gymmanagementsystem;

/**
 * Admin can create, edit, and delete fitness classes here
 *
 * OOP Concepts Used: - Encapsulation: Private helper methods hide internal
 * logic - DRY: Time slots and days defined once in FitnessClass, reused
 * everywhere - Delegation: Display work sent to ClassViewer instead of
 * duplicate code
 * 
 * @author Victus
 */
import java.time.LocalDate;
import java.util.ArrayList;


public class ClassManagement {
    // Get from FitnessClass - single source of truth
    private static final String[] TIME_SLOTS = FitnessClass.TIME_SLOTS;
    private static final String[] VALID_DAYS = FitnessClass.VALID_DAYS;

    // ==========MAIN MENU LOOP ==========
    public void manageClassMenu() {
        while (true) {
            System.out.println("\n========== FITNESS CLASS MENU ==========");
            System.out.println("1. Create Class");
            System.out.println("2. Edit Class");
            System.out.println("3. Delete Class");
            System.out.println("4. View All Classes (Grouped by Day)");
            System.out.println("0. Back");
            System.out.println("========================================");

            int choice = Input.getMenuChoice("Choice (0-5): ", 0, 5);

            switch (choice) {
                case 1:
                    createClass();
                    break;
                case 2:
                    editClass();
                    break;
                case 3:
                    deleteClass();
                    break;
                case 4:
                    ClassViewer.viewAllClassesGroupedByDay();
                    break;
                case 0:
                    return;
            }
        }
    }

   

    // ========== DISPLAY HELPERS ==========
    // OOP: Encapsulation - display logic hidden from outside
    private void displayTimeSlots() {
        System.out.println("\n--- AVAILABLE TIME SLOTS ---");
        for (int i = 0; i < TIME_SLOTS.length; i++) {
            System.out.println((i + 1) + ". " + TIME_SLOTS[i]);
        }
        System.out.println("============================");
    }

    private void displayDays() {
        System.out.println("\n--- AVAILABLE DAYS ---");
        for (int i = 0; i < VALID_DAYS.length; i++) {
            System.out.println((i + 1) + ". " + VALID_DAYS[i]);
        }
        System.out.println("======================");
    }

    private void displayTrainers() {
        System.out.println("\n=== AVAILABLE TRAINERS ===");
        boolean hasTrainer = false;
        for (User u : DataStore.getUsers()) {
            if (u instanceof Trainer) {
                Trainer t = (Trainer) u;
                System.out.println("ID: " + t.getId() + " | Name: " + t.getName());
                hasTrainer = true;
            }
        }
        if (!hasTrainer) {
            System.out.println("No trainer available!");
        }
    }

    // ========== SELECTION HELPERS ==========
    // OOP: Encapsulation - returns value, hides selection logic
    private String selectTimeSlot() {
        displayTimeSlots();
        int choice = Input.getMenuChoice("Choose time slot (1-6): ", 1, TIME_SLOTS.length);
        return TIME_SLOTS[choice - 1];
    }

    private String selectDay() {
        displayDays();
        int choice = Input.getMenuChoice("Choose day (1-6): ", 1, VALID_DAYS.length);
        return VALID_DAYS[choice - 1];
    }

    private Trainer selectTrainer() {
        displayTrainers();

        boolean hasTrainer = false;
        for (User u : DataStore.getUsers()) {
            if (u instanceof Trainer) {
                hasTrainer = true;
                break;
            }
        }

        if (!hasTrainer) {
            return null;
        }

        while (true) {
            String trainerId = Input.getString("\nEnter Trainer ID: ");
            for (User u : DataStore.getUsers()) {
                if (u instanceof Trainer && u.getId().equalsIgnoreCase(trainerId)) {
                    return (Trainer) u;
                }
            }
            System.out.println("Invalid Trainer ID! Please enter a valid Trainer ID.");
        }
    }

    private int selectCapacity(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                int cap = Integer.parseInt(Input.sc.nextLine());
                if (cap >= min && cap <= max) {
                    return cap;
                }
                System.out.println("Error: Capacity must be between " + min + " and " + max + "!");
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number (e.g., 25)");
            }
        }
    }

    // OOP: Code Reuse - used by both edit and delete
    private FitnessClass findClassById(String id) {
        for (FitnessClass fc : DataStore.getClasses()) {
            if (fc.getClassID().equalsIgnoreCase(id)) {
                return fc;
            }
        }
        return null;
    }

    private void displayClassInfo(FitnessClass fc) {
        System.out.println("\n--- Current Class Information ---");
        System.out.println("Class Name  : " + fc.getClassName());
        System.out.println("Time Slot   : " + fc.getTimeSlot());
        System.out.println("Day         : " + fc.getDate());
        System.out.println("Capacity    : " + fc.getMaxCapacity());
        System.out.println("Trainer     : " + fc.getTrainer().getName());
        System.out.println("----------------------------------");
    }

    // ========== CREATE CLASS ==========
    private void createClass() {
        System.out.println("\n=== CREATE CLASS ===");

        String name = Input.getName("Enter Class Name: ");
        String timeSlot = selectTimeSlot();
        String day = selectDay();
        int cap = selectCapacity("Enter Max Capacity (10 - 50): ", 10, 50);

        Trainer trainer = selectTrainer();
        if (trainer == null) {
            System.out.println("Cannot create class without trainer!");
            return;
        }

        String id = "C" + (101 + DataStore.getClasses().size());
        DataStore.getClasses().add(new FitnessClass(id, name, timeSlot, day, trainer, cap));

        DataStore.getLogs().add(new AdminLog("Created FitnessClass: " + id + " | " + name));

        System.out.println("Class ID: " + id);
        System.out.println("Class created successfully!\n");
    }

    // ========== EDIT CLASS ==========
    private void editClass() {
        while (true) {
            // Show classes first so admin knows what to pick
            System.out.println("\n=== CURRENT CLASSES ===");
            ClassViewer.viewAllClassesGroupedByDay();

            System.out.println("\n=== EDIT CLASS ===");
            String id = Input.getString("Enter Class ID to edit (or 0 to cancel): ");

            if (id.equals("0")) {
                System.out.println("Edit cancelled.");
                return;
            }

            FitnessClass target = findClassById(id);

            if (target == null) {
                System.out.println("Invalid Class ID! Class '" + id + "' not found.");
                System.out.println("Press Enter to continue...");
                Input.sc.nextLine();
                continue;
            }

            displayClassInfo(target);

            String name = Input.getName("Enter new class name: ");
            String timeSlot = selectTimeSlot();
            String day = selectDay();
            int cap = selectCapacity("Enter new capacity (10-50): ", 10, 50);

            Trainer trainer = selectTrainer();
            if (trainer == null) {
                System.out.println("Cannot update class without trainer!");
                return;
            }

            target.setClassName(name);
            target.setTimeSlot(timeSlot);
            target.setDate(day);
            target.setMaxCapacity(cap);
            target.setTrainer(trainer);

            DataStore.getLogs().add(new AdminLog("Edited class [" + id + "] | Trainer: " + trainer.getName()));
            System.out.println("\nClass updated successfully!");
            return;
        }
    }

    // ========== DELETE CLASS ==========
    private void deleteClass() {
        while (true) {
            System.out.println("\n=== CURRENT CLASSES ===");
            ClassViewer.viewAllClassesGroupedByDay();

            System.out.println("\n=== DELETE CLASS ===");
            String id = Input.getString("Enter Class ID to delete (or 0 to cancel): ");

            if (id.equals("0")) {
                System.out.println("Delete cancelled.");
                return;
            }

            FitnessClass target = findClassById(id);

            if (target == null) {
                System.out.println("Invalid Class ID! Class '" + id + "' not found.");
                System.out.println("Press Enter to continue...");
                Input.sc.nextLine();
                continue;
            }

            System.out.println("\n------- Class to Delete -------");
            System.out.println("Class Name  : " + target.getClassName());
            System.out.println("Day         : " + target.getDate());
            System.out.println("Time Slot   : " + target.getTimeSlot());
            System.out.println("Trainer     : " + target.getTrainer().getName());
            System.out.println("------------------------------");

            if (Input.getConfirmation("Confirm delete class " + target.getClassName())) {
                DataStore.getClasses().remove(target);
                DataStore.getLogs().add(new AdminLog("Deleted class: " + target.getClassID() + " (" + target.getClassName() + ")"));
                System.out.println("Class deleted successfully!");
            } else {
                System.out.println("Delete cancelled.");
            }
            return;
        }
    }
}
