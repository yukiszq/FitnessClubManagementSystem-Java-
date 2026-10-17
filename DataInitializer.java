/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gymmanagementsystem;

/**
 *
 * @author Victus
 */
import java.time.LocalDate;
import java.time.DayOfWeek;

public class DataInitializer {
    
    public static void init() {

        // Clear all existing data to prevent duplicates
        DataStore.getUsers().clear();
        DataStore.getClasses().clear();
        DataStore.getEquipments().clear();
        DataStore.getLogs().clear();
        DataStore.getTransactionLogs().clear();

        // ================= TRAINERS (6 trainers - one for each class type) =================
        DataStore.getUsers().add(new Trainer("T1001", "Trainer@123", "Ahmad", "881010101011", "0130000001"));
        DataStore.getUsers().add(new Trainer("T1002", "Trainer@123", "Siti", "881010101012", "0130000002"));
        DataStore.getUsers().add(new Trainer("T1003", "Trainer@123", "Raj", "881010101013", "0130000003"));
        DataStore.getUsers().add(new Trainer("T1004", "Trainer@123", "Mei", "881010101014", "0130000004"));
        DataStore.getUsers().add(new Trainer("T1005", "Trainer@123", "Kumar", "881010101015", "0130000005"));
        DataStore.getUsers().add(new Trainer("T1006", "Trainer@123", "Linda", "881010101016", "0130000006"));

        // ================= STAFF (5 staff) =================
        DataStore.getUsers().add(new Staff("S1001", "Staff@123", "Ali", "8701010101001", "0140000001"));
        DataStore.getUsers().add(new Staff("S1002", "Staff@123", "Maya", "8701010101002", "0140000002"));
        DataStore.getUsers().add(new Staff("S1003", "Staff@123", "Chong", "8701010101003", "0140000003"));
        DataStore.getUsers().add(new Staff("S1004", "Staff@123", "Rina", "8701010101004", "0140000004"));
        DataStore.getUsers().add(new Staff("S1005", "Staff@123", "Sam", "8701010101005", "0140000005"));

        // ================= ADMIN =================
        DataStore.getUsers().add(new Admin("A1001", "Admin@123", "BigGuy", "1234567890", "0111234567"));

        // ================= MEMBERS =================
        // Expired member (Basic plan)
        DataStore.getUsers().add(new Member("M1001", "Member@123", "John", "1234567890", "0111234567",
                "6 Months", "Basic", 420.0, true,
                LocalDate.of(2025, 10, 1),
                LocalDate.of(2026, 4, 1)));

        // Active Silver member
        DataStore.getUsers().add(new Member("M1002", "Member@123", "Jane", "1234567890", "0111234567",
                "6 Months", "Silver", 550.0, true,
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 7, 1)));

        // Active Gold member
        DataStore.getUsers().add(new Member("M1003", "Member@123", "Mike", "1234567890", "0111234567",
                "6 Months", "Gold", 750.0, true,
                LocalDate.of(2026, 1, 15),
                LocalDate.of(2026, 7, 15)));

        // ================= CLASSES (Next 7 days only, excluding Sundays) =================
        LocalDate today = LocalDate.now();

// Find the upcoming Monday (or today if today is Monday)
        LocalDate startDate = today;
        while (startDate.getDayOfWeek() != DayOfWeek.MONDAY) {
            startDate = startDate.plusDays(1);
        }

        String[] classNames = {
            "Yoga", "Zumba", "HIIT", "Pilates", "Boxing", "Stretching"
        };

        String[] times = {
            "8:00am-9:00am",
            "9:00am-10:00am",
            "10:00am-11:00am",
            "11:00am-12:00pm",
            "2:00pm-3:00pm",
            "3:00pm-4:00pm"
        };

        int idCounter = 101;

         // Loop through 7 days starting from Monday
        for (int i = 0; i < 7; i++) {
            LocalDate date = startDate.plusDays(i);

            // Skip Sundays
            if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                continue;
            }

            // Create 6 classes for this day
            for (int j = 0; j < times.length; j++) {
                Trainer trainer = (Trainer) DataStore.getUsers().get(j);

                DataStore.getClasses().add(new FitnessClass(
                        "C" + idCounter++,
                        classNames[j],
                        times[j],
                        date.toString(),
                        trainer,
                        25
                ));
            }
        }
        // ================= EQUIPMENT =================
        // Cardio machines
        DataStore.getEquipments().add(new Equipment("E101", "Treadmill", "Cardio"));
        DataStore.getEquipments().add(new Equipment("E102", "Exercise Bike", "Cardio"));
        DataStore.getEquipments().add(new Equipment("E103", "Rowing Machine", "Cardio"));
        DataStore.getEquipments().add(new Equipment("E104", "Stepper Machine", "Cardio"));
        DataStore.getEquipments().add(new Equipment("E105", "Elliptical Trainer", "Cardio"));
        DataStore.getEquipments().add(new Equipment("E106", "Stair Climber", "Cardio"));

        // Strength equipment
        DataStore.getEquipments().add(new Equipment("E107", "Bench Press", "Strength"));
        DataStore.getEquipments().add(new Equipment("E108", "Dumbbell Set", "Strength"));
        DataStore.getEquipments().add(new Equipment("E109", "Barbell Rack", "Strength"));
        DataStore.getEquipments().add(new Equipment("E110", "Smith Machine", "Strength"));
        DataStore.getEquipments().add(new Equipment("E111", "Leg Press Machine", "Strength"));
        DataStore.getEquipments().add(new Equipment("E112", "Lat Pulldown Machine", "Strength"));
        DataStore.getEquipments().add(new Equipment("E113", "Chest Fly Machine", "Strength"));
        DataStore.getEquipments().add(new Equipment("E114", "Shoulder Press Machine", "Strength"));

        // Flexibility equipment
        DataStore.getEquipments().add(new Equipment("E115", "Cable Crossover", "Flexibility"));
        DataStore.getEquipments().add(new Equipment("E116", "Battle Ropes", "Flexibility"));
        DataStore.getEquipments().add(new Equipment("E117", "Kettlebells", "Flexibility"));
        DataStore.getEquipments().add(new Equipment("E118", "Medicine Balls", "Flexibility"));
        DataStore.getEquipments().add(new Equipment("E119", "Pull-up Bar", "Flexibility"));
        DataStore.getEquipments().add(new Equipment("E120", "TRX Suspension Trainer", "Flexibility"));
    }
}
