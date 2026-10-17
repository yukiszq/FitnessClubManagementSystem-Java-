/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gymmanagementsystem;

/**
 * Represents a membership package purchased by a member
 *
 * OOP Concepts Used: - Encapsulation: Member data is private, accessed via
 * getters - Static methods: displayPlans() and calculatePrice() can be called
 * without an instance - DRY Principle: Price calculation logic is centralized
 * here, reused by RegistrationMember and PaymentProcessor
 * 
 * @author Victus
 */
public class Membership {

    private String tier;        // Basic, Silver, or Gold
    private String plan;        // Monthly, 6 Months, or 1 Year
    private int months;         // Duration in months (1, 6, or 12)
    private double totalPrice;  // Amount paid for this membership

    public Membership(String tier, String plan, int months, double totalPrice) {
        this.tier = tier;
        this.plan = plan;
        this.months = months;
        this.totalPrice = totalPrice;
    }

    // Getters
    public String getTier() {
        return tier;
    }

    public String getPlan() {
        return plan;
    }

    public int getMonths() {
        return months;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Displays the membership plans table with prices and descriptions Static
     * method - can be called from anywhere without creating a Membership object
     * Used by RegistrationMember and PaymentProcessor to show available plans
     */
    public static void displayPlans() {
        System.out.println("\n================================================================================");
        System.out.println("                         MEMBERSHIP PLANS");
        System.out.println("================================================================================");
        System.out.println("Tier     Monthly     6 Months     1 Year     Description");
        System.out.println("-------------------------------------------------------------------------------");
        System.out.println("Basic    RM80        RM420        RM720      Equipment access only");
        System.out.println("Silver   RM100       RM550        RM900      Equipment + Free class booking");
        System.out.println("Gold     RM130       RM750        RM1200     Equipment + Classes + Sauna access");
        System.out.println("================================================================================");
    }

    /**
     * Calculates total price based on selected tier and plan duration
     * Centralized price logic - any change here updates everywhere
     *
     * Price calculation: - Basic: RM80 (monthly), RM420 (6 months), RM720 (1
     * year) - Silver: Basic price + RM20/130/180 extra - Gold: Basic price +
     * RM50/330/480 extra
     */
    public static double calculatePrice(String tier, int months) {
        double basePrice;
        switch (months) {
            case 1:
                basePrice = 80;
                break;
            case 6:
                basePrice = 420;
                break;
            default:  // 12 months
                basePrice = 720;
        }

        double tierExtra = 0;

        if (tier.equals("Silver")) {
            if (months == 1) {
                tierExtra = 20;
            } else if (months == 6) {
                tierExtra = 130;
            } else {
                tierExtra = 180;
            }
        } else if (tier.equals("Gold")) {
            if (months == 1) {
                tierExtra = 50;
            } else if (months == 6) {
                tierExtra = 330;
            } else {
                tierExtra = 480;
            }
        }

        return basePrice + tierExtra;
    }        
}
