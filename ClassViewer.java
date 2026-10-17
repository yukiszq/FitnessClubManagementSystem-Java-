/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gymmanagementsystem;

/**
/**
 * Displays gym classes to users (members, staff, admin)
 *
 * OOP: Encapsulation (private helpers), Method Overloading, Code Reuse
 * @author Victus
 */
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClassViewer {
//    // ===== MEMBER METHODS (view by date, within next 7 days only) =====
//    // For MEMBERS: Shows classes on a specific date (only available slots)
//    // Members can ONLY view within next 7 days from today
//    public static boolean viewMemberClassesByDate(LocalDate date) {
//        // Validate: cannot view beyond next 7 days
//        if (!isWithinNext7Days(date)) {
//            System.out.println("\n==========================================================================");
//            System.out.println("DATE OUT OF RANGE");
//            System.out.println("==========================================================================");
//            System.out.println("Members can only view classes within the next 7 days.");
//            System.out.println("==========================================================================");
//            return false;
//        }
//
//        if (!validateDate(date)) {
//            return false;
//        }
//        displayClasses("CLASSES ON " + date + " (Available Slots Only)", true, date, date);
//        return true;
//    }
//
//    // Check if date is within next 7 days from today
//    private static boolean isWithinNext7Days(LocalDate date) {
//        LocalDate today = LocalDate.now();
//        LocalDate maxDate = today.plusDays(7);
//        return !date.isBefore(today) && !date.isAfter(maxDate);
//    }
//
//    // ===== ADMIN METHODS (view all classes grouped by day, no date filter) =====
//    // For ADMIN: Shows ALL classes (recurring) grouped by day (Monday to Saturday)
//    public static void viewAllClassesGroupedByDay() {
//        System.out.println("\n+==================================================================================+");
//        System.out.println("|                          ALL CLASSES (Grouped by Day)                            |");
//        System.out.println("+==================================================================================+");
//
//        // Group all classes by day of week
//        Map<String, ArrayList<FitnessClass>> classesByDay = new HashMap<>();
//        String[] days = {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"};
//
//        for (String day : days) {
//            classesByDay.put(day, new ArrayList<>());
//        }
//
//        for (FitnessClass fc : DataStore.getClasses()) {
//            String classDay = fc.getDate().toUpperCase();
//            if (classDay.matches("\\d{4}-\\d{2}-\\d{2}")) {
//                LocalDate date = LocalDate.parse(classDay);
//                classDay = date.getDayOfWeek().toString();
//            }
//            ArrayList<FitnessClass> dayList = classesByDay.get(classDay);
//            if (dayList != null) {
//                dayList.add(fc);
//            }
//        }
//
//        boolean hasAnyClass = false;
//        for (String day : days) {
//            ArrayList<FitnessClass> dayClasses = classesByDay.get(day);
//            if (!dayClasses.isEmpty()) {
//                hasAnyClass = true;
//                System.out.println("\n--- " + day + " ---");
//                System.out.println("+----------+----------------------+------------------+---------------+-------------+");
//                System.out.printf("| %-8s | %-20s | %-16s | %-13s | %-11s |\n",
//                        "ID", "Class Name", "Time", "Trainer", "Capacity");
//                System.out.println("+----------+----------------------+------------------+---------------+-------------+");
//
//                for (FitnessClass fc : dayClasses) {
//                    int booked = fc.getParticipants().size();
//                    int maxCap = fc.getMaxCapacity();
//                    System.out.printf("| %-8s | %-20s | %-16s | %-13s | %5d/%-5d |\n",
//                            fc.getClassID(),
//                            truncate(fc.getClassName(), 20),
//                            fc.getTimeSlot(),
//                            fc.getTrainer().getName(),
//                            booked, maxCap);
//                }
//                System.out.println("+----------+----------------------+------------------+---------------+-------------+");
//            }
//        }
//
//        if (!hasAnyClass) {
//            System.out.println("\n+------------------------------------------------------------------+");
//            System.out.println("|                       No classes available.                       |");
//            System.out.println("+------------------------------------------------------------------+");
//        }
//    }
//
//    // Returns list of classes that occur on a specific date (used by booking system)
//    public static ArrayList<FitnessClass> getClassesByDate(LocalDate date) {
//        ArrayList<FitnessClass> available = new ArrayList<>();
//        for (FitnessClass fc : DataStore.getClasses()) {
//            boolean occursOnDate = false;
//            try {
//                DayOfWeek targetDay = DayOfWeek.valueOf(fc.getDate().toUpperCase());
//                if (date.getDayOfWeek() == targetDay) {
//                    occursOnDate = true;
//                }
//            } catch (IllegalArgumentException e) {
//                try {
//                    LocalDate classDate = LocalDate.parse(fc.getDate());
//                    if (classDate.equals(date)) {
//                        occursOnDate = true;
//                    }
//                } catch (Exception ex) {
//                    // Skip invalid formats
//                }
//            }
//            if (occursOnDate) {
//                available.add(fc);
//            }
//        }
//        return available;
//    }
//
//    // ===== PRIVATE HELPER METHODS =====
//    private static boolean validateDate(LocalDate date) {
//        if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
//            System.out.println("\n==========================================================================");
//            System.out.println("SUNDAY - GYM CLOSED");
//            System.out.println("==========================================================================");
//            System.out.println("The gym is closed on Sundays for maintenance.");
//            System.out.println("No classes are available on this day.");
//            System.out.println("\nAvailable days: Monday to Saturday (8:00 AM - 4:00 PM)");
//            System.out.println("==========================================================================");
//            return false;
//        }
//        if (date.isBefore(LocalDate.now())) {
//            System.out.println("\n==========================================================================");
//            System.out.println("INVALID DATE");
//            System.out.println("==========================================================================");
//            System.out.println("Cannot view classes on past dates (" + date + ").");
//            System.out.println("==========================================================================");
//            return false;
//        }
//        return true;
//    }
//
//    private static void displayClasses(String title, boolean onlyAvailable, LocalDate startDate, LocalDate endDate) {
//        System.out.println("\n" + title);
//        System.out.println("+==============================================================================================+");
//        System.out.printf("| %-3s | %-10s | %-8s | %-15s | %-12s | %-20s | %-6s |\n",
//                "No", "Date", "ID", "Class", "Trainer", "Time", "Slots");
//        System.out.println("+==============================================================================================+");
//
//        if (DataStore.getClasses().isEmpty()) {
//            System.out.printf("| %-89s |\n", "No classes available.");
//            System.out.println("+==============================================================================================+");
//            return;
//        }
//
//        int counter = 1;
//        boolean hasData = false;
//
//        for (FitnessClass fc : DataStore.getClasses()) {
//            boolean shouldDisplay = false;
//            String displayDate = "";
//
//            if (startDate != null) {
//                LocalDate end = (endDate != null) ? endDate : startDate;
//                ArrayList<String> dates = fc.getAllDatesInRange(startDate, end);
//                shouldDisplay = !dates.isEmpty();
//                if (shouldDisplay && !dates.isEmpty()) {
//                    displayDate = dates.get(0);
//                }
//            }
//
//            if (!shouldDisplay) {
//                continue;
//            }
//
//            int booked = fc.getParticipants().size();
//            int maxCapacity = fc.getMaxCapacity();
//            int available = maxCapacity - booked;
//
//            if (onlyAvailable && available <= 0) {
//                continue;
//            }
//
//            hasData = true;
//            System.out.printf("| %-3d | %-10s | %-8s | %-15s | %-12s | %-20s | %-6s |\n",
//                    counter++, displayDate, fc.getClassID(),
//                    fc.getClassName(),
//                    fc.getTrainer().getName(),
//                    fc.getTimeSlot(),
//                    booked + "/" + maxCapacity);
//        }
//
//        if (!hasData) {
//            System.out.printf("| %-89s |\n", onlyAvailable ? "No available classes found on this date." : "No classes found on this date.");
//        }
//        System.out.println("+==============================================================================================+");
//    }
//
//    private static String truncate(String str, int maxLength) {
//        if (str == null) {
//            return "";
//        }
//        if (str.length() <= maxLength) {
//            return str;
//        }
//        return str.substring(0, maxLength - 3) + "...";
//    } 
    // ===== MEMBER METHODS (view by date, within next 7 days only) =====
    // For MEMBERS: Shows classes on a specific date (only available slots)
    // Members can ONLY view within next 7 days from today
    public static boolean viewMemberClassesByDate(LocalDate date) {
        displayClasses("CLASSES ON " + date + " (Available Slots Only)", true, date, date);
        return true;
    }

    // ===== ADMIN METHODS (view all classes grouped by day, no date filter) =====
    // For ADMIN: Shows ALL classes (recurring) grouped by day (Monday to Saturday)
    public static void viewAllClassesGroupedByDay() {
        System.out.println("\n+==================================================================================+");
        System.out.println("|                          ALL CLASSES (Grouped by Day)                            |");
        System.out.println("+==================================================================================+");

        // Group all classes by day of week
        Map<String, ArrayList<FitnessClass>> classesByDay = new HashMap<>();
        String[] days = {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"};

        for (String day : days) {
            classesByDay.put(day, new ArrayList<>());
        }

        for (FitnessClass fc : DataStore.getClasses()) {
            String classDay = fc.getDate().toUpperCase();
            if (classDay.matches("\\d{4}-\\d{2}-\\d{2}")) {
                LocalDate date = LocalDate.parse(classDay);
                classDay = date.getDayOfWeek().toString();
            }
            ArrayList<FitnessClass> dayList = classesByDay.get(classDay);
            if (dayList != null) {
                dayList.add(fc);
            }
        }

        boolean hasAnyClass = false;
        for (String day : days) {
            ArrayList<FitnessClass> dayClasses = classesByDay.get(day);
            if (!dayClasses.isEmpty()) {
                hasAnyClass = true;
                System.out.println("\n--- " + day + " ---");
                System.out.println("+----------+----------------------+------------------+---------------+-------------+");
                System.out.printf("| %-8s | %-20s | %-16s | %-13s | %-11s |\n",
                        "ID", "Class Name", "Time", "Trainer", "Capacity");
                System.out.println("+----------+----------------------+------------------+---------------+-------------+");

                for (FitnessClass fc : dayClasses) {
                    int booked = fc.getParticipants().size();
                    int maxCap = fc.getMaxCapacity();
                    System.out.printf("| %-8s | %-20s | %-16s | %-13s | %5d/%-5d |\n",
                            fc.getClassID(),
                            truncate(fc.getClassName(), 20),
                            fc.getTimeSlot(),
                            fc.getTrainer().getName(),
                            booked, maxCap);
                }
                System.out.println("+----------+----------------------+------------------+---------------+-------------+");
            }
        }

        if (!hasAnyClass) {
            System.out.println("\n+------------------------------------------------------------------+");
            System.out.println("|                       No classes available.                       |");
            System.out.println("+------------------------------------------------------------------+");
        }
    }

    // Returns list of classes that occur on a specific date (used by booking system)
    public static ArrayList<FitnessClass> getClassesByDate(LocalDate date) {
        ArrayList<FitnessClass> available = new ArrayList<>();
        for (FitnessClass fc : DataStore.getClasses()) {
            boolean occursOnDate = false;
            try {
                DayOfWeek targetDay = DayOfWeek.valueOf(fc.getDate().toUpperCase());
                if (date.getDayOfWeek() == targetDay) {
                    occursOnDate = true;
                }
            } catch (IllegalArgumentException e) {
                try {
                    LocalDate classDate = LocalDate.parse(fc.getDate());
                    if (classDate.equals(date)) {
                        occursOnDate = true;
                    }
                } catch (Exception ex) {
                    // Skip invalid formats
                }
            }
            if (occursOnDate) {
                available.add(fc);
            }
        }
        return available;
    }



    private static void displayClasses(String title, boolean onlyAvailable, LocalDate startDate, LocalDate endDate) {
        System.out.println("\n" + title);
        System.out.println("+==============================================================================================+");
        System.out.printf("| %-3s | %-10s | %-8s | %-15s | %-12s | %-20s | %-6s |\n",
                "No", "Date", "ID", "Class", "Trainer", "Time", "Slots");
        System.out.println("+==============================================================================================+");

        if (DataStore.getClasses().isEmpty()) {
            System.out.printf("| %-89s |\n", "No classes available.");
            System.out.println("+==============================================================================================+");
            return;
        }

        int counter = 1;
        boolean hasData = false;

        for (FitnessClass fc : DataStore.getClasses()) {
            boolean shouldDisplay = false;
            String displayDate = "";

            if (startDate != null) {
                LocalDate end = (endDate != null) ? endDate : startDate;
                ArrayList<String> dates = fc.getAllDatesInRange(startDate, end);
                shouldDisplay = !dates.isEmpty();
                if (shouldDisplay && !dates.isEmpty()) {
                    displayDate = dates.get(0);
                }
            }

            if (!shouldDisplay) {
                continue;
            }

            int booked = fc.getParticipants().size();
            int maxCapacity = fc.getMaxCapacity();
            int available = maxCapacity - booked;

            if (onlyAvailable && available <= 0) {
                continue;
            }

            hasData = true;
            System.out.printf("| %-3d | %-10s | %-8s | %-15s | %-12s | %-20s | %-6s |\n",
                    counter++, displayDate, fc.getClassID(),
                    fc.getClassName(),
                    fc.getTrainer().getName(),
                    fc.getTimeSlot(),
                    booked + "/" + maxCapacity);
        }

        if (!hasData) {
            System.out.printf("| %-89s |\n", onlyAvailable ? "No available classes found on this date." : "No classes found on this date.");
        }
        System.out.println("+==============================================================================================+");
    }

    private static String truncate(String str, int maxLength) {
        if (str == null) {
            return "";
        }
        if (str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength - 3) + "...";
    }
}
