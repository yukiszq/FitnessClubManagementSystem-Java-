/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gymmanagementsystem;

/**
 * Represents a gym member with membership details, payment status, and health
 * metrics
 *
 * OOP Concepts Used: - Inheritance: Extends User class (Member IS-A User) -
 * Encapsulation: All fields private, accessed via getters/setters -
 * Polymorphism: Overrides toString() and equals() from User - Constructor
 * Overloading: Two constructors for different use cases
 * 
 * @author Victus
 */
import java.time.LocalDateTime;
import java.time.LocalDate;

public class Member extends User{

    // Membership details
    private String plan;          // Monthly, 6 Months, 1 Year
    private String tier;          // Basic, Silver, Gold
    private double price;         // Amount paid
    private boolean paid;         // Payment status

    // Dates
    private LocalDate registerDate;  // When member joined
    private LocalDate expireDate;    // When membership expires
    private String status;           // ACTIVE or EXPIRED

    // Health metrics (for BMI tracking)
    private double weight = 0.0;
    private double height = 0.0;

    // Sauna access (Gold members only)
    private String saunaToken;
    private LocalDateTime tokenExpiry;

    // CONSTRUCTOR 1: FOR NEW REGISTRATION
    // Automatically calculates register date and expiry date based on months selected
    public Member(String id, String password, String name, String ic, String phno,
            String plan, String tier, double price, int months, boolean paid) {

        super(id, password, name, ic, phno, "Member");

        this.plan = plan;
        this.tier = tier;
        this.price = price;
        this.paid = paid;

        this.registerDate = LocalDate.now();
        this.expireDate = registerDate.plusMonths(months);  // Expiry = register + months
        this.status = "ACTIVE";
    }

    
    // Constructor 2: For creating members with custom dates Used when
    //initializing test data or manually adding members with specific register/expiry dates
    public Member(String id, String password, String name, String ic, String phno,
            String plan, String tier, double price, boolean paid,
            LocalDate registerDate, LocalDate expireDate) {

        super(id, password, name, ic, phno, "Member");

        this.plan = plan;
        this.tier = tier;
        this.price = price;
        this.paid = paid;
        this.registerDate = registerDate;
        this.expireDate = expireDate;

        // Set status based on whether membership has expired
        if (expireDate.isBefore(LocalDate.now())) {
            this.status = "EXPIRED";
        } else {
            this.status = "ACTIVE";
        }
    }

    // ===== STATUS METHODS =====
    // Update status based on current date vs expiry date
    public void checkStatus() {
        if (expireDate == null) {
            status = "EXPIRED";
            return;
        }

        if (expireDate.isBefore(LocalDate.now())) {
            status = "EXPIRED";
        } else {
            status = "ACTIVE";
        }
    }

    public String getStatus() {
        return status;
    }

    // ===== GETTERS & SETTERS =====
    public String getPlan() {
        return plan;
    }

    public String getTier() {
        return tier;
    }

    public double getPrice() {
        return price;
    }

    public boolean isPaid() {
        return paid;
    }

    public String getSaunaToken() {
        return saunaToken;
    }

    public LocalDateTime getTokenExpiry() {
        return tokenExpiry;
    }

    public void setTokenExpiry(LocalDateTime tokenExpiry) {
        this.tokenExpiry = tokenExpiry;
    }

    public void setSaunaToken(String saunaToken) {
        this.saunaToken = saunaToken;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
    }

    // ===== BMI METHODS =====
    // Calculate Body Mass Index using weight(kg) / height(m)^2
    public double calculateBMI() {
        if (height <= 0) {
            return 0;
        }
        return weight / (height * height);
    }

    // Get BMI category based on standard health ranges
    public String getBMICategory() {
        double bmi = calculateBMI();

        if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi < 25) {
            return "Normal weight";
        } else if (bmi < 30) {
            return "Overweight";
        } else {
            return "Obese";
        }
    }

    public double getWeight() {
        return weight;
    }

    public double getHeight() {
        return height;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public LocalDate getRegisterDate() {
        return registerDate;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    // Display all member information (overrides User's toString)
    @Override
    public String toString() {
        return super.toString() + "\nPlan: " + plan + "\nTier: " + tier
                + "\nPrice: " + price + "\nPaid: " + paid
                + "\nRegister Date: " + registerDate + "\nExpire Date=" + expireDate
                + "\nStatus=" + status + "\nWeight=" + weight + "\nHeight=" + height;
    }

    // Two members are equal if they have the same ID (polymorphism)
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Member) {
            Member member = (Member) obj;
            return this.getId().equals(member.getId());
        } else {
            return false;
        }
    }
}
