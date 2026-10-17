/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gymmanagementsystem;

/**
 * Handles class booking and cancellation for members
 *
 * OOP Concepts: - Composition - ClassBooking HAS-A Member (the member who
 * books) - Delegation - Display logic delegated to ClassViewer (code reuse) -
 * Encapsulation - Member data is accessed through getters/setters
 * 
 * @author Victus
 */
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class ClassBooking {
    private Member member;  // The member who is booking (composition)

    public ClassBooking(Member member) {
        this.member = member;
    }

    /**
     * Main booking flow: validate date, show classes, let member choose Data
     * validation: date must be within 14 days, not Sunday, not past
     */
    public void book() {
//        String inputDate;
//        LocalDate input;
//        LocalDate today = LocalDate.now();
//        LocalDate maxDate = today.plusDays(14);
//
//        // Step 1: Get and validate the booking date
//        while (true) {
//            System.out.print("Enter the date you want to book(YYYY-MM-DD): ");
//            inputDate = Input.sc.nextLine();
//
//            try {
//                input = LocalDate.parse(inputDate);
//
//                if (input.isAfter(today) && !input.isAfter(maxDate)) {
//                    break;  // Valid date
//                } else {
//                    if (!input.isAfter(today)) {
//                        System.out.println("Cannot book today or past dates!");
//                    } else {
//                        System.out.println("Only allowed within 14 days!");
//                    }
//                }
//            } catch (DateTimeParseException e) {
//                System.out.println("Invalid format! Example: YYYY-MM-DD");
//            }
//        }
//
//        // Step 2: Let ClassViewer display classes (reuse - no duplicate code)
//        boolean success = ClassViewer.viewMemberClassesByDate(input);
//
//        if (!success) {
//            return; // Validation failed (Sunday, past date, etc.)
//        }
//
//        // Step 3: Get available classes for this date
//        ArrayList<FitnessClass> available = ClassViewer.getClassesByDate(input);
//
//        if (available.isEmpty()) {
//            return;
//        }
//
//        // Step 4: Let member choose which class to book
//        int choice = Input.getMenuChoice("Choose class: ", 1, available.size());
//        FitnessClass fc = available.get(choice - 1);
//
//        // Step 5: Add member to class (FitnessClass handles capacity check)
//        if (fc.addMember(member)) {
//            System.out.println("Booking successful!");
//        } else {
//            System.out.println("Class full or already booked!");
//        }
        String inputDate;
        LocalDate input = null;
        LocalDate today = LocalDate.now();
        LocalDate maxDate = today.plusDays(7);
        boolean validDate = false;

        // Step 1: Keep asking until valid date is entered
        while (!validDate) {
            System.out.print("Enter the date you want to book (YYYY-MM-DD): ");
            inputDate = Input.sc.nextLine();

            try {
                input = LocalDate.parse(inputDate);

                // Check Sunday
                if (input.getDayOfWeek() == DayOfWeek.SUNDAY) {
                    System.out.println("Gym is closed on Sundays! Please choose another day.");
                    System.out.println("Please try again.\n");
                    continue;
                }

                // Check past date
                if (!input.isAfter(today)) {
                    System.out.println("Cannot book today or past dates!");
                    System.out.println("Please try again.\n");
                    continue;
                }

                // Check within 7 days
                if (input.isAfter(maxDate)) {
                    System.out.println("Only allowed within 7 days! (Today to " + maxDate + ")");
                    System.out.println("Please try again.\n");
                    continue;
                }

                // If we get here, date is valid
                validDate = true;

            } catch (DateTimeParseException e) {
                System.out.println("Invalid format! Example: YYYY-MM-DD");
                System.out.println("Please try again.\n");
            }
        }

        // Step 2: Display classes
        ClassViewer.viewMemberClassesByDate(input);

        // Step 3: Get available classes for this date
        ArrayList<FitnessClass> available = ClassViewer.getClassesByDate(input);

        if (available.isEmpty()) {
            System.out.println("\nNo classes available on " + input);
            return;
        }

        // Step 4: Let member choose which class to book
        int choice = Input.getMenuChoice("Choose class: ", 1, available.size());
        FitnessClass fc = available.get(choice - 1);

        // Step 5: Add member to class
        if (fc.addMember(member)) {
            System.out.println("Booking successful!");
        } else {
            System.out.println("Class full or already booked!");
        }
    }

    /**
     * Display all classes booked by this member and offer cancellation
     */
    public void viewAndCancel() {
        System.out.println("\n+========================================================================+");
        System.out.printf("|  %-4s %-15s %-15s %-20s %-10s  |\n",
                "No", "Class", "Trainer", "Time", "Date");
        System.out.println("+========================================================================+");

        ArrayList<FitnessClass> myClasses = new ArrayList<>();

        // Find all classes where this member is registered
        for (FitnessClass fc : DataStore.getClasses()) {
            if (fc.hasMember(member)) {
                myClasses.add(fc);
                System.out.printf("|  %-4d %-15s %-15s %-20s %-10s  |\n",
                        myClasses.size(), fc.getClassName(), fc.getTrainer().getName(),
                        fc.getTimeSlot(), fc.getNextRealDate());
            }
        }

        if (myClasses.isEmpty()) {
            System.out.println("| No classes booked.                                                     |");
            System.out.println("+========================================================================+");
            return;
        }

        System.out.println("+========================================================================+");
        System.out.println("1. Cancel Class");
        System.out.println("0. Back");

        int choice = Input.getMenuChoice("Choice (0-1): ", 0, 1);

        if (choice == 1) {
            cancel(myClasses);
        }
    }

    /**
     * Handle cancellation of a booked class
     *
     * @param myClasses List of classes the member has booked
     */
    private void cancel(ArrayList<FitnessClass> myClasses) {

        int choice = Input.getMenuChoice("Choose class to cancel (0 to back): ", 0, myClasses.size());

        if (choice == 0) {
            return;
        }

        FitnessClass fc = myClasses.get(choice - 1);

        // Remove member from class (delegated to FitnessClass)
        if (fc.removeMember(member)) {
            System.out.println("Cancelled successfully!");
        } else {
            System.out.println("Error cancelling class.");
        }
    }  
}
