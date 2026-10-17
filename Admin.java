/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gymmanagementsystem;

/**
 * Admin user with full system access
 *
 * OOP Concepts: - Inheritance (extends User) - Admin inherits all User
 * properties (id, name, etc.) - Polymorphism (overrides equals) - Custom
 * comparison logic
 * 
 * @author Victus
 */
public class Admin extends User{

    public Admin(String id, String password, String name, String ic, String phno) {
        super(id, password, name, ic, phno, "Admin");  // Reuse User constructor
    }

    @Override
    public String toString() {
        return super.toString();  // Reuse User's toString
    }

    // Polymorphism: Override equals to compare Admin by ID only
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Admin) {
            Admin admin = (Admin) obj;
            return this.getId().equals(admin.getId());
        }
        return false;
    }
}
