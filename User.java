/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gymmanagementsystem;

/**
 * Abstract base class for all user types (Member, Trainer, Staff, Admin)
 *
 * OOP Concepts Used: - Abstraction: Abstract class cannot be instantiated
 * directly - Inheritance: All user types extend this class (IS-A relationship)
 * - Encapsulation: Fields are private, accessed via getters/setters -
 * Polymorphism: Subclasses can override methods like toString() and equals()
 *
 * @author Victus
 */
public abstract class User {
    
    // Common properties for all users
    private String id;        // Unique identifier (Mxxxx, Txxxx, Sxxxx, Axxxx)
    private String ic;        // Identity card number (12 digits)
    private String phno;      // Phone number (10-11 digits)
    private String password;  // Login password (stored as plain text for simplicity)
    protected String name;    // Full name (protected so subclasses can access directly)
    private String role;      // "Member", "Trainer", "Staff", or "Admin"

    // Constructor - called by subclasses using super()
    public User(String id, String password, String name, String ic, String phno, String role) {
        this.id = id;
        this.ic = ic;
        this.phno = phno;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    // ========== GETTERS ==========
    public String getId() {
        return id;
    }

    public String getIc() {
        return ic;
    }

    public String getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public String getPhno() {
        return phno;
    }

    public String getPassword() {
        return password;
    }

    // ========== SETTERS ==========
    public void setPhno(String phno) {
        this.phno = phno;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Authentication method - checks if entered password matches stored password
    public boolean checkPassword(String input) {
        return password.equals(input);
    }

    // Display all user information (can be overridden by subclasses)
    @Override
    public String toString() {
        return "ID: " + id + "\nIC: " + ic + "\nPhone Number: " + phno
                + "\nPassword: " + password + "\nName: " + name + "\nRole: " + role;
    }

    // Two users are equal if they have the same ID (polymorphism)
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof User) {
            User a = (User) obj;
            return this.id.equals(a.id);
        }
        return false;
    }
}
