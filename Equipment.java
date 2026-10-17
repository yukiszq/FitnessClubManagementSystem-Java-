/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gymmanagementsystem;

/**
 * Represents gym equipment that members can use
 *
 * OOP Concepts Used: - Encapsulation: All fields are private, accessed through
 * getters/setters - Polymorphism: Overrides equals() to compare equipment by ID
 * - Data hiding: Internal representation hidden from other classes
 * 
 * @author Victus
 */
public class Equipment {
    
    private String id;          // Unique identifier (E101, E102, etc.)
    private String name;        // Equipment name (Treadmill, Bench Press, etc.)
    private String category;    // Cardio, Strength, or Flexibility
    private String status;      // Available or Maintenance

    // Constructor - new equipment starts as "Available"
    public Equipment(String id, String name, String category) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.status = "Available";  // Default status when first added
    }

    // Getters - allow other classes to read values
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getStatus() {
        return status;
    }

    // Setters - allow other classes to modify values
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Quick display format for equipment list
    @Override
    public String toString() {
        return String.format("%-6s %-25s %-10s",
                id, name, status);
    }

    // Two equipment are equal if they have the same ID
    // (polymorphism - overriding Object's equals method)
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Equipment) {
            Equipment e = (Equipment) obj;
            return this.id.equals(e.id);
        } else {
            return false;
        }
    }
}

