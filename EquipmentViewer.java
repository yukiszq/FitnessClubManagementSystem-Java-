/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gymmanagementsystem;

/**
 * Displays equipment information to users (members, staff, admin)
 *
 * OOP Concepts Used: - Static methods: Can be called directly without creating
 * an object - DRY Principle: Single display method handles all viewing
 * scenarios - Filter pattern: Same method shows different views based on filter
 * parameter - Code Reuse: Other classes call these methods instead of writing
 * their own display code
 * 
 * @author Victus
 */
import java.util.ArrayList;

public class EquipmentViewer {

    // Show all equipment in the gym (admin/staff view)
    public static void viewAllEquipment() {
        displayEquipment("EQUIPMENT LIST", "all", null);
    }

    // Show only equipment that is currently available (member view)
    public static void showAvailableOnly() {
        displayEquipment("AVAILABLE EQUIPMENT", "available", null);
    }

    // Show only equipment under maintenance (staff view)
    public static void showFlaggedEquipment() {
        displayEquipment("FLAGGED EQUIPMENT (Maintenance)", "flagged", null);
    }

    // Quick check if any equipment is currently under maintenance
    public static boolean hasFlaggedEquipment() {
        for (Equipment e : DataStore.getEquipments()) {
            if (!e.getStatus().equalsIgnoreCase("Available")) {
                return true;
            }
        }
        return false;
    }

    // ========== PRIVATE HELPER METHOD ==========
    // One method handles all equipment display scenarios
    // filter: "all" = show everything, "available" = show only working, "flagged" = show only broken
    private static void displayEquipment(String title, String filter, String category) {
        System.out.println("\n=== Available " + title + " ===");

        if (DataStore.getEquipments().isEmpty()) {
            System.out.println("No equipment available.");
            return;
        }

        // Step 1: Filter equipment based on what user wants to see
        ArrayList<Equipment> filteredList = new ArrayList<>();

        for (Equipment e : DataStore.getEquipments()) {
            if (filter.equals("available") && e.getStatus().equalsIgnoreCase("Available")) {
                filteredList.add(e);
            } else if (filter.equals("flagged") && !e.getStatus().equalsIgnoreCase("Available")) {
                filteredList.add(e);
            } else if (filter.equals("all")) {
                filteredList.add(e);
            }
        }

        // Step 2: Show message if nothing matches the filter
        if (filteredList.isEmpty()) {
            if (filter.equals("available")) {
                System.out.println("No available equipment.");
            } else if (filter.equals("flagged")) {
                System.out.println("No flagged equipment. All equipment is available.");
            }
            return;
        }

        // Step 3: Display the filtered equipment - grouped by category for ALL views
        // Group equipment by category
        ArrayList<String> categories = new ArrayList<>();
        for (Equipment e : filteredList) {
            if (!categories.contains(e.getCategory())) {
                categories.add(e.getCategory());
            }
        }

        // Display each category separately
        for (String cat : categories) {
            System.out.println("\nCategory: " + cat);
            
            // Table header without Status column
            System.out.println("+----------+------------------------+");
            System.out.printf("| %-8s | %-22s |\n", "ID", "Name");
            System.out.println("+----------+------------------------+");

            for (Equipment e : filteredList) {
                if (e.getCategory().equalsIgnoreCase(cat)) {
                    System.out.printf("| %-8s | %-22s |\n",
                            e.getId(), e.getName());
                }
            }
            System.out.println("+----------+------------------------+");
        }
        
        // If showing flagged equipment, also show maintenance reason
        if (filter.equals("flagged")) {
            System.out.println("\nNote: Flagged equipment requires maintenance.");
        }
    }    
}
