/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gymmanagementsystem;

/**
 * Handles all user input validation and password encryption
 *
 * OOP Concepts Used: - Encapsulation: All input validation logic is hidden
 * inside private methods - Static Methods: Utility class that doesn't need
 * instantiation - Code Reuse: Validation methods can be called from anywhere in
 * the system
 * 
 * @author Victus
 */
import java.util.Scanner;

public class Input {
    public static Scanner sc = new Scanner(System.in);

    // OOP: Encapsulation - Scanner is hidden inside this class
    public static String getString(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    // OOP: Encapsulation - validation loop hidden from caller
    public static int getInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("*** Invalid input. Please enter a number.\n");
            }
        }
    }

    // OOP: Method Overloading - same method name, different parameters (double version)
    public static double getDouble(String prompt, double min, double max) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = Double.parseDouble(sc.nextLine().trim());
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println("*** Value must be between " + min + " - " + max + "!");
            } catch (NumberFormatException e) {
                System.out.println("*** Please enter a valid number!\n");
            }
        }
    }

    // OOP: Encapsulation - regex validation hidden inside
    public static String getName(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            if (input.matches("[a-zA-Z ]+")) {
                return input;
            }
            System.out.println("*** Only letters allowed!\n");
        }
    }

    public static String getPhone(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            if (input.matches("\\d{10,11}")) {
                return input;
            }
            System.out.println("*** Invalid phone number (10-11 digits)!\n");
        }
    }

    public static String getIC(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            if (input.matches("\\d{12}")) {
                return input;
            }
            System.out.println("*** Invalid IC (12 digits required)!\n");
        }
    }

    public static String getTimeSlot(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            if (input.matches("\\d{1,2}:\\d{2}(am|pm)\\s-\\s\\d{1,2}:\\d{2}(am|pm)")) {
                return input;
            }
            System.out.println("*** Invalid format! Example: 2:00pm - 3:00pm\n");
        }
    }

    public static String getDay(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim().toUpperCase();
            try {
                java.time.DayOfWeek.valueOf(input);
                return input;
            } catch (Exception e) {
                System.out.println("*** Invalid day! Example: MONDAY, TUESDAY...\n");
            }
        }
    }

    public static int getCapacity(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                int cap = Integer.parseInt(sc.nextLine().trim());
                if (cap >= min && cap <= max) {
                    return cap;
                }
                System.out.println("*** Capacity must be " + min + " - " + max + "!\n");
            } catch (NumberFormatException e) {
                System.out.println("*** Please enter a valid number!\n");
            }
        }
    }

    // OOP: Encapsulation - menu logic with range checking hidden
    public static int getMenuChoice(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("*** No input detected.\n");
                continue;
            }
            try {
                int choice = Integer.parseInt(input);
                if (choice >= min && choice <= max) {
                    return choice;
                }
                System.out.println("Invalid choice. Please enter " + min + "-" + max + ".\n");
            } catch (NumberFormatException e) {
                System.out.println("*** Invalid input. Please enter a number.\n");
            }
        }
    }

    // OOP: Delegation - calls validatePasswordStrength() helper method
    public static String getPassword() {
        while (true) {
            System.out.print("Set Password: ");
            String password = sc.nextLine().trim();

            // OOP: Delegation - password validation handled by separate method
            String error = validatePasswordStrength(password);

            if (error != null) {
                System.out.println(error);
                continue;
            }

            System.out.print("Confirm Password: ");
            String confirm = sc.nextLine().trim();

            if (password.equals(confirm)) {
                return password;
            }
            System.out.println("Passwords do not match.\n");
        }
    }

    // OOP: Encapsulation - password strength rules hidden from caller
    private static String validatePasswordStrength(String password) {
        if (password.length() < 8) {
            return "Password must be at least 8 characters long.";
        }

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        String specialChars = "!@#$%^&*()_+-=[]{}|;:'\",.<>?/";

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpper = true;
            } else if (Character.isLowerCase(c)) {
                hasLower = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (specialChars.indexOf(c) != -1) {
                hasSpecial = true;
            }
        }

        if (!hasUpper) {
            return "Password must contain at least one uppercase letter.";
        }
        if (!hasLower) {
            return "Password must contain at least one lowercase letter.";
        }
        if (!hasDigit) {
            return "Password must contain at least one number.";
        }
        if (!hasSpecial) {
            return "Password must contain at least one special character (!@#$%^&* etc).";
        }

        return null;
    }

    // OOP: Encapsulation - confirmation logic simplified for caller
    public static boolean getConfirmation(String prompt) {
        System.out.print(prompt + " (Y/N): ");
        String input = sc.nextLine().trim();
        return input.equalsIgnoreCase("Y");
    }
}
