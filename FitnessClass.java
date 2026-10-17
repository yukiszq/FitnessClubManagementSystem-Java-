/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gymmanagementsystem;

/**
 * Represents a fitness class that members can book
 *
 * OOP Concepts Used: - Encapsulation: Class data is private, accessed via
 * getters/setters - Composition: Class HAS-A Trainer and HAS-A list of Member
 * participants - Aggregation: Participants can exist independently without the
 * class - Polymorphism: Overrides equals() and toString()
 * 
 * @author Victus
 */
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.time.DayOfWeek;

public class FitnessClass {
    
    // Core class information
    private String classID;          // Unique identifier (C100, C101, etc.)
    private String className;        // Yoga, Zumba, HIIT, etc.
    private String timeSlot;         // Class time (8:00am-9:00am, etc.)
    private String date;             // Either specific date OR day of week (MONDAY)
    private Trainer trainer;         // Who teaches this class (composition)
    private int maxCapacity;         // Maximum number of participants (10-50)
    private ArrayList<Member> participants;  // List of members who booked this class

    // Shared constants - single source of truth (DRY principle)
    // Used by ClassManagement and ClassViewer to avoid duplication
    public static final String[] TIME_SLOTS = {
        "8:00am-9:00am",
        "9:00am-10:00am",
        "10:00am-11:00am",
        "11:00am-12:00pm",
        "2:00pm-3:00pm",
        "3:00pm-4:00pm"
    };

    public static final String[] VALID_DAYS = {
        "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"
    };

    // Constructor - creates a new fitness class with empty participant list
    public FitnessClass(String classID, String className, String timeSlot, String date, Trainer trainer, int maxCapacity) {
        this.classID = classID;
        this.className = className;
        this.timeSlot = timeSlot;
        this.date = date;
        this.trainer = trainer;
        this.maxCapacity = maxCapacity;
        this.participants = new ArrayList<>();  // Start with no participants
    }

    // Add a member to the class (called when member books)
    // Returns true if successful, false if class is full or member already booked
    public boolean addMember(Member m) {
        if (participants.size() < maxCapacity && !participants.contains(m)) {
            participants.add(m);
            return true;
        } else {
            return false;
        }
    }

    // Remove a member from the class (called when member cancels)
    public boolean removeMember(Member m) {
        return participants.remove(m);
    }

    // Check if a specific member is already in this class
    public boolean hasMember(Member m) {
        return participants.contains(m);
    }

    // Calculate the next actual date this class runs
    // If date is a weekday (MONDAY), finds next occurrence
    // If date is a specific date, returns that date
    public String getNextRealDate() {
        try {
            DayOfWeek day = DayOfWeek.valueOf(date.toUpperCase());
            LocalDate today = LocalDate.now();
            LocalDate nextDate = today.with(TemporalAdjusters.nextOrSame(day));
            return nextDate.toString();
        } catch (IllegalArgumentException e) {
            // Not a day of week (MONDAY, TUESDAY...), return as specific date
            return date;
        }
    }

    // Get all dates this class occurs within a date range
    // Used by ClassViewer to show classes on specific dates
    public ArrayList<String> getAllDatesInRange(LocalDate startDate, LocalDate endDate) {
        ArrayList<String> dates = new ArrayList<>();

        try {
            // Case 1: Class repeats weekly (e.g., every MONDAY)
            DayOfWeek targetDay = DayOfWeek.valueOf(date.toUpperCase());
            LocalDate current = startDate;
            while (!current.isAfter(endDate)) {
                if (current.getDayOfWeek() == targetDay) {
                    dates.add(current.toString());
                }
                current = current.plusDays(1);
            }
        } catch (IllegalArgumentException e) {
            // Case 2: Class happens once on a specific date
            if (date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                LocalDate realDate = LocalDate.parse(date);
                if (!realDate.isBefore(startDate) && !realDate.isAfter(endDate)) {
                    dates.add(date);
                }
            }
        }

        return dates;
    }

    // ========== GETTERS ==========
    public String getClassID() {
        return classID;
    }

    public String getClassName() {
        return className;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public String getDate() {
        return date;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public ArrayList<Member> getParticipants() {
        return participants;
    }

    public int getCurrentPax() {
        return participants.size();
    }

    // ========== SETTERS ==========
    public void setClassID(String classID) {
        this.classID = classID;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public void setParticipants(ArrayList<Member> participants) {
        this.participants = participants;
    }

    // Quick display format for class list
    @Override
    public String toString() {
        return String.format("%-6s %-20s %-15s %-15s %-5d",
                classID,
                className,
                timeSlot,
                getNextRealDate(),
                maxCapacity);
    }

    // Two classes are equal if they have the same ID
    // (polymorphism - overriding Object's equals method)
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FitnessClass)) {
            return false;
        }

        FitnessClass fc = (FitnessClass) obj;
        return this.classID != null && this.classID.equals(fc.classID);
    }
}
