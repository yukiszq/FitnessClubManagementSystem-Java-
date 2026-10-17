/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gymmanagementsystem;

/**
 * Represents a Trainer who can teach group classes and have personal training
 * clients
 *
 * OOP Concepts Used: - Inheritance: Extends User class (Trainer IS-A User) -
 * Composition: Trainer HAS-A list of Member clients (aggregation) -
 * Encapsulation: Client list is private, accessed through methods -
 * Polymorphism: Overrides equals() and toString()
 * 
 * @author Victus
 */
import java.util.ArrayList;

public class Trainer extends User {
    // List of personal training clients assigned to this trainer (aggregation)
    // Members can exist independently without a trainer
    private ArrayList<Member> assignedClients;

    // Constructor - initializes empty client list
    public Trainer(String id, String password, String name, String ic, String phno) {
        super(id, password, name, ic, phno, "Trainer");
        this.assignedClients = new ArrayList<>();
    }

    // Getter for assigned clients (returns the list)
    public ArrayList<Member> getAssignedClients() {
        return assignedClients;
    }

    // Add a member as personal training client
    // Returns true if added, false if already exists
    public boolean addClient(Member member) {
        if (!assignedClients.contains(member)) {
            assignedClients.add(member);
            return true;
        }
        return false;  // Client already assigned
    }

    // Remove a client from personal training list
    public boolean removeClient(Member member) {
        return assignedClients.remove(member);
    }

    // Check if this trainer already has a specific member as client
    // Used by ClientManager to avoid duplicate assignments
    public boolean hasClient(Member member) {
        return assignedClients.contains(member);
    }

    // Get number of personal training clients
    public int getClientCount() {
        return assignedClients.size();
    }

    // Display all clients (for debugging/display purposes)
    public void displayAllClients() {
        if (assignedClients.isEmpty()) {
            System.out.println("No clients assigned.");
        } else {
            System.out.println("Total clients: " + getClientCount());
            for (Member m : assignedClients) {
                System.out.println("  - " + m.getId() + ": " + m.getName());
            }
        }
    }

    // Reuse parent's toString method
    @Override
    public String toString() {
        return super.toString();
    }

    // Two trainers are equal if they have the same ID
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Trainer) {
            Trainer trainer = (Trainer) obj;
            return this.getId().equals(trainer.getId());
        }
        return false;
    }
}

