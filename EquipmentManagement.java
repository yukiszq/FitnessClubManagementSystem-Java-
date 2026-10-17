/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gymmanagementsystem;

/**
 * Admin can add, edit, and delete gym equipment here Staff can change equipment
 * status (Available/Maintenance)
 *
 * OOP Concepts Used: - Encapsulation: Private helper methods for internal
 * operations - DRY Principle: Categories defined once, reused everywhere - Code
 * Reuse: Uses EquipmentViewer for displaying equipment lists - Separation of
 * Concerns: CRUD operations separated from maintenance operations
 *
 * @author Victus
 */
public class EquipmentManagement {
    // Fixed categories - gym only has these three types of equipment
    private static final String[] CATEGORIES = {
        "Cardio", "Strength", "Flexibility"
    };

    // ========== ADMIN MENU (CRUD OPERATIONS) ==========
    public void manageEquipmentMenu() {
        while (true) {
            System.out.println("\n========== EQUIPMENT MENU ==========");
            System.out.println("1. Add Equipment");
            System.out.println("2. Edit Equipment");
            System.out.println("3. Delete Equipment");
            System.out.println("0. Back");
            System.out.println("===================================");

            int choice = Input.getMenuChoice("Choice (0-3): ", 0, 3);

            switch (choice) {
                case 1:
                    addEquipment();
                    break;
                case 2:
                    editEquipment();
                    break;
                case 3:
                    deleteEquipment();
                    break;
                case 0:
                    return;
            }
        }
    }

    // ========== DISPLAY HELPERS ==========
    // Show category menu so admin can pick by number
    private void displayCategories() {
        System.out.println("\n--- AVAILABLE CATEGORIES ---");
        for (int i = 0; i < CATEGORIES.length; i++) {
            System.out.println((i + 1) + ". " + CATEGORIES[i]);
        }
        System.out.println("===========================");
    }

    // Show all equipment using EquipmentViewer (reuse - no duplicate code)
    private void displayAllEquipment() {
        System.out.println("\n=== CURRENT EQUIPMENT ===");
        EquipmentViewer.viewAllEquipment();
    }

    // ========== SELECTION HELPERS ==========
    // Let admin choose category from menu instead of typing
    private String selectCategory() {
        displayCategories();
        int choice = Input.getMenuChoice("Choose category (1-3): ", 1, CATEGORIES.length);
        return CATEGORIES[choice - 1];
    }

    // Search for equipment by ID - used in edit and delete
    private Equipment findEquipmentById(String id) {
        for (Equipment e : DataStore.getEquipments()) {
            if (e.getId().equalsIgnoreCase(id)) {
                return e;
            }
        }
        return null;
    }

    // Auto-generate equipment ID: E101, E102, E103...
    private String getEquipmentId() {
        return "E" + (101 + DataStore.getEquipments().size());
    }

    // ========== CREATE ==========
    private void addEquipment() {
        System.out.println("\n=== ADD EQUIPMENT ===");

        String id = getEquipmentId();           // Auto-generated ID
        String name = Input.getName("Enter equipment name: ");
        String category = selectCategory();     // Pick from menu

        DataStore.getEquipments().add(new Equipment(id, name, category));
        DataStore.getLogs().add(new AdminLog("Added equipment: " + name));

        System.out.println("Equipment created successfully!");
        System.out.println("Equipment ID: " + id);
    }

    // ========== UPDATE ==========
    private void editEquipment() {
        while (true) {
            displayAllEquipment();  // Show everything first so admin knows what to pick
            System.out.println("\n=== EDIT EQUIPMENT ===");

            String id = Input.getString("Enter Equipment ID to edit (or 0 to cancel): ");

            if (id.equals("0")) {
                System.out.println("Edit cancelled.");
                return;
            }

            Equipment target = findEquipmentById(id);

            if (target == null) {
                System.out.println("Invalid Equipment ID! Equipment '" + id + "' not found. Please try again.");
                System.out.println("Press Enter to continue...");
                Input.sc.nextLine();
                continue;  // Go back and ask again
            }

            // Show current values before editing (confirmation)
            System.out.println("\n--- Current Equipment Information ---");
            System.out.println("Equipment ID: " + target.getId());
            System.out.println("Name        : " + target.getName());
            System.out.println("Category    : " + target.getCategory());
            System.out.println("Status      : " + target.getStatus());
            System.out.println("--------------------------------------");

            String name = Input.getName("Enter new name: ");
            String category = selectCategory();

            target.setName(name);
            target.setCategory(category);
            DataStore.getLogs().add(new AdminLog("Edited equipment: " + id));

            System.out.println("Equipment updated successfully!");
            return;
        }
    }

    // ========== DELETE ==========
    private void deleteEquipment() {
        while (true) {
            displayAllEquipment();
            System.out.println("\n=== DELETE EQUIPMENT ===");

            String id = Input.getString("Enter Equipment ID to delete (or 0 to cancel): ");

            if (id.equals("0")) {
                System.out.println("Delete cancelled.");
                return;
            }

            Equipment target = findEquipmentById(id);

            if (target == null) {
                System.out.println("Invalid Equipment ID! Equipment '" + id + "' not found. Please try again.");
                System.out.println("Press Enter to continue...");
                Input.sc.nextLine();
                continue;
            }

            // Double-check before deleting (prevent accidents)
            System.out.println("\n--- Equipment to Delete ---");
            System.out.println("Equipment ID: " + target.getId());
            System.out.println("Name        : " + target.getName());
            System.out.println("Category    : " + target.getCategory());
            System.out.println("Status      : " + target.getStatus());
            System.out.println("--------------------------");

            if (Input.getConfirmation("Confirm delete " + target.getName())) {
                DataStore.getEquipments().remove(target);
                DataStore.getLogs().add(new AdminLog("Deleted equipment: " + target.getId()));
                System.out.println("Equipment deleted successfully!");
            } else {
                System.out.println("Delete cancelled.");
            }
            return;
        }
    }

    // ========== STAFF MAINTENANCE METHODS ==========
    // Staff can mark equipment as "Maintenance" when it's broken
    public static void setMaintenance() {
        EquipmentViewer.showAvailableOnly();  // Show only working equipment

        // Check if anything is available to mark as maintenance
        boolean hasAvailable = false;
        for (Equipment e : DataStore.getEquipments()) {
            if (e.getStatus().equalsIgnoreCase("Available")) {
                hasAvailable = true;
                break;
            }
        }

        if (!hasAvailable) {
            System.out.println("\nNo available equipment to set to maintenance.");
            System.out.print("\nPress ENTER to go back...");
            Input.sc.nextLine();
            return;
        }

        // Let staff pick which equipment to mark as maintenance
        while (true) {
            String id = Input.getString("\nEnter Equipment ID to set to Maintenance (type 0 to back): ");

            if (id.equals("0")) {
                return;
            }

            boolean found = false;
            for (Equipment e : DataStore.getEquipments()) {
                if (e.getId().equalsIgnoreCase(id)) {
                    found = true;

                    if (e.getStatus().equalsIgnoreCase("Maintenance")) {
                        System.out.println("This equipment is already under maintenance!");
                    } else {
                        e.setStatus("Maintenance");
                        System.out.println("\n"+e.getName() + " is now under MAINTENANCE.");
                    }
                    return;
                }
            }

            if (!found) {
                System.out.println("Invalid ID. Please re-enter.");
            }
        }
    }

    // Staff can mark equipment back to "Available" after repair
    public static void setAvailable() {
        // Only show equipment that is currently under maintenance
        if (!EquipmentViewer.hasFlaggedEquipment()) {
            System.out.println("\nNo equipment in maintenance.");
            System.out.print("\nPress ENTER to go back...");
            Input.sc.nextLine();
            return;
        }

        EquipmentViewer.showFlaggedEquipment();

        while (true) {
            String id = Input.getString("\nEnter Equipment ID (type 0 to cancel): ");

            if (id.equals("0")) {
                return;
            }

            boolean found = false;
            for (Equipment e : DataStore.getEquipments()) {
                if (e.getId().equalsIgnoreCase(id)) {
                    found = true;

                    if (e.getStatus().equals("Available")) {
                        System.out.println("Already available.");
                    } else {
                        e.setStatus("Available");
                        System.out.println("\n"+e.getName() + " is now Available.");
                    }
                    return;
                }
            }

            if (!found) {
                System.out.println("Invalid ID. Please re-enter.");
            }
        }
    }    
}
