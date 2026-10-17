/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gymmanagementsystem;

/**
 * Utility class for BMI calculation
 *
 * OOP Concepts: - Static methods (utility class) - no need to create instance -
 * Delegation - BMI logic is in Member class, this just handles I/O
 * 
 * @author Victus
 */
public class BMICalculator {
    /**
     * Collects weight/height from user, saves to member, and displays result
     * Data validation: weight (1-500 kg), height (0.5-3.0 m)
     *
     * @param member The member whose BMI will be calculated
     */
    public static void calculateAndDisplay(Member member) {
        double weight, height;

        System.out.println("\n======= BMI CALCULATION =======");

        // Input validation is handled by Input.getDouble()
        weight = Input.getDouble("Enter weight (kg): ", 1, 300);
        height = Input.getDouble("Enter height (m): ", 0.5, 3);

        // Save measurements to member object (encapsulation)
        member.setWeight(weight);
        member.setHeight(height);

        // BMI calculation logic is inside Member class (reusability)
        double bmi = member.calculateBMI();

        // Display results
        System.out.printf("BMI: %.2f\n", bmi);
        System.out.println("Category: " + member.getBMICategory());
    }   
}
