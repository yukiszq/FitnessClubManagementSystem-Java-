/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gymmanagementsystem;

/**
 * Represents a Staff user who handles equipment maintenance and sauna token
 * validation
 *
 * OOP Concepts Used: - Inheritance: Extends User class (Staff IS-A User) -
 * Polymorphism: Overrides equals() method from Object class - Encapsulation:
 * Inherits protected fields from User via getters/setters
 * 
 * @author Victus
 */
public class Staff extends User{

    // Constructor - role is automatically set to "Staff" in parent class
    public Staff(String id, String password, String name, String ic, String phno) {
        super(id, password, name, ic, phno, "Staff");
    }

    // Reuse parent's toString() method
    @Override
    public String toString() {
        return super.toString();
    }

    // Two Staff members are equal if they have the same ID
    // (Polymorphism - overriding Object's equals method)
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Staff) {
            Staff staff = (Staff) obj;
            return this.getId().equals(staff.getId());
        } else {
            return false;
        }
    }
}
