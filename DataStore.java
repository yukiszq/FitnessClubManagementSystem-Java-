/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gymmanagementsystem;

/**
 * Central storage for all application data
 *
 * OOP Concepts Used: - Encapsulation: Data is private, accessed only through
 * getters - Singleton-like pattern: Static collections shared across entire
 * application - Data hiding: Other classes don't know how data is stored
 * internally
 * 
 * @author Victus
 */
import java.util.*;

public class DataStore {
    // All data collections are static so they can be accessed from anywhere
    private static ArrayList<User> users = new ArrayList<>();           // All users (members, trainers, staff, admin)
    private static ArrayList<FitnessClass> classes = new ArrayList<>(); // All fitness classes
    private static ArrayList<Equipment> equipments = new ArrayList<>(); // All gym equipment
    private static ArrayList<AdminLog> logs = new ArrayList<>();        // Admin activity audit trail
    private static ArrayList<TransactionLog> transactionLogs = new ArrayList<>(); // Payment records

    // Getter for transaction logs
    public static ArrayList<TransactionLog> getTransactionLogs() {
        return transactionLogs;
    }

    // Add a new transaction record
    public static void addTransactionLog(TransactionLog log) {
        transactionLogs.add(log);
    }

    // Get all users (members, trainers, staff, admin)
    public static ArrayList<User> getUsers() {
        return users;
    }

    // Get all fitness classes
    public static ArrayList<FitnessClass> getClasses() {
        return classes;
    }

    // Get all equipment
    public static ArrayList<Equipment> getEquipments() {
        return equipments;
    }

    // Get admin activity logs
    public static ArrayList<AdminLog> getLogs() {
        return logs;
    }
}
