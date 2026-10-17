/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gymmanagementsystem;

/**
/**
 * Trainer can manage their personal training clients here
 *
 * OOP Concepts Used: - Composition: ClientManager HAS-A Trainer (the trainer
 * who owns this manager) - Encapsulation: Client data is accessed through
 * proper methods, not directly - DRY Principle: updateMeasurements() reused in
 * both add and track
 * 
 * @author Victus
 */
import java.time.LocalDate;
import java.util.ArrayList;

public class ClientManager {
    private Trainer trainer;  // Which trainer this manager belongs to (composition)

    public ClientManager(Trainer trainer) {
        this.trainer = trainer;
    }

    // Find members who don't have a personal trainer yet and are still active
    public ArrayList<Member> getAvailableMembers() {
        ArrayList<Member> availableMembers = new ArrayList<>();

        for (User u : DataStore.getUsers()) {
            if (u instanceof Member) {
                Member m = (Member) u;
                m.checkStatus();  // Update expiry status first

                // Only active members can get personal training
                if (!m.getStatus().equals("ACTIVE")) {
                    continue;
                }

                // Check if this member already has a personal trainer
                boolean alreadyHasTrainer = false;
                for (User t : DataStore.getUsers()) {
                    if (t instanceof Trainer) {
                        Trainer tr = (Trainer) t;
                        if (tr.hasClient(m)) {
                            alreadyHasTrainer = true;
                            break;
                        }
                    }
                }

                if (!alreadyHasTrainer) {
                    availableMembers.add(m);
                }
            }
        }

        return availableMembers;
    }

    // Show all group classes that this trainer is teaching (next 2 weeks only)
    public void viewGroupClasses() {
        System.out.println("\n=== MY GROUP CLASSES ===");

        ArrayList<FitnessClass> myClasses = new ArrayList<>();
        LocalDate today = LocalDate.now();
        LocalDate twoWeeksLater = today.plusWeeks(2);

        // Find classes where this trainer is the instructor
        for (FitnessClass fc : DataStore.getClasses()) {
            if (fc.getTrainer().getId().equals(trainer.getId())) {
                LocalDate classDate = LocalDate.parse(fc.getNextRealDate());
                if (!classDate.isBefore(today) && !classDate.isAfter(twoWeeksLater)) {
                    myClasses.add(fc);
                }
            }
        }

        if (myClasses.isEmpty()) {
            System.out.println("No upcoming group classes assigned to you.");
            return;
        }

        // Display in a clean table
        System.out.println("+----------+------------------+------------+------------------+-------------+");
        System.out.printf("| %-8s | %-16s | %-10s | %-16s | %-10s |\n",
                "Class ID", "Class Name", "Date", "Time", "Capacity");
        System.out.println("+----------+------------------+------------+------------------+-------------+");

        for (FitnessClass fc : myClasses) {
            int booked = fc.getParticipants().size();
            int maxCap = fc.getMaxCapacity();
            System.out.printf("| %-8s | %-16s | %-10s | %-16s | %-5d/%-5d |\n",
                    fc.getClassID(),
                    fc.getClassName(),
                    fc.getNextRealDate(),
                    fc.getTimeSlot(),
                    booked, maxCap);
        }
        System.out.println("+----------+------------------+------------+------------------+-------------+");
    }

    // Take initial weight and height measurements when adding a new client
    // This same method could be reused elsewhere if needed
    private void updateMeasurements(Member member) {
        System.out.println("\n--- INITIAL MEASUREMENTS ---");

        // Get weight with validation (1-299 kg)
        System.out.print("Enter weight (kg): ");
        while (true) {
            try {
                double weight = Double.parseDouble(Input.sc.nextLine());
                if (weight > 0 && weight < 300) {
                    member.setWeight(weight);
                    break;
                } else {
                    System.out.print("Invalid! Enter weight (1-299 kg): ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input! Enter weight (kg): ");
            }
        }

        // Get height with validation (0.5-2.5 meters)
        System.out.print("Enter height (m): ");
        while (true) {
            try {
                double height = Double.parseDouble(Input.sc.nextLine());
                if (height > 0 && height < 3) {
                    member.setHeight(height);
                    break;
                } else {
                    System.out.print("Invalid! Enter height (0.5-2.5 m): ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input! Enter height (m): ");
            }
        }

        // Show calculated BMI so trainer knows client's starting point
        System.out.println("\n✓ Measurements recorded!");
        System.out.printf("  BMI: %.2f (%s)\n", member.calculateBMI(), member.getBMICategory());
    }

    // Add a new client to this trainer's personal training list
    public void addClient() {
        System.out.println("\n=== ADD PERSONAL TRAINING CLIENT ===");
        System.out.println("NOTE: Payment and scheduling handled at gym counter.\n");

        ArrayList<Member> availableMembers = getAvailableMembers();

        if (availableMembers.isEmpty()) {
            System.out.println("No available members to assign.");
            System.out.println("(Only active members without a personal trainer can be added)");
            return;
        }

        // Show list of members who can be added
        System.out.println("\nAvailable Members (Active & No Personal Trainer):");
        System.out.println("+----------+--------------------+-----------------+------------+");
        System.out.printf("| %-8s | %-18s | %-15s | %-10s |\n", "ID", "NAME", "PHONE", "TIER");
        System.out.println("+----------+--------------------+-----------------+------------+");

        for (Member m : availableMembers) {
            m.checkStatus();
            System.out.printf("| %-8s | %-18s | %-15s | %-10s |\n",
                    m.getId(),
                    m.getName(),
                    m.getPhno(),
                    m.getTier());
        }
        System.out.println("+----------+--------------------+-----------------+------------+");

        String memberId = Input.getString("\nEnter Member ID to add (0 to cancel): ");

        if (memberId.equals("0")) {
            return;
        }

        // Find the selected member
        Member memberToAdd = null;
        for (Member m : availableMembers) {
            if (m.getId().equalsIgnoreCase(memberId)) {
                memberToAdd = m;
                break;
            }
        }

        if (memberToAdd == null) {
            System.out.println("Invalid Member ID! Member not found or already has a trainer.");
            return;
        }

        // Take initial measurements before adding (so trainer has baseline data)
        updateMeasurements(memberToAdd);

        // Add to trainer's client list
        if (trainer.addClient(memberToAdd)) {
            System.out.println("\n==========================================");
            System.out.println("Client added successfully!");
            System.out.println("==========================================");
            System.out.println("Name     : " + memberToAdd.getName());
            System.out.println("ID       : " + memberToAdd.getId());
            System.out.println("Phone    : " + memberToAdd.getPhno());
            System.out.println("Tier     : " + memberToAdd.getTier());
            System.out.printf("Weight   : %.2f kg\n", memberToAdd.getWeight());
            System.out.printf("Height   : %.2f m\n", memberToAdd.getHeight());
            System.out.printf("BMI      : %.2f (%s)\n", memberToAdd.calculateBMI(), memberToAdd.getBMICategory());
            System.out.println("==========================================");
            System.out.println("You now have " + trainer.getClientCount() + " client(s).");
            System.out.println("==========================================");
        } else {
            System.out.println("Failed to add client. They may already be assigned to you.");
        }
    }

    // Show all personal training clients assigned to this trainer
    public void viewClients() {
        ArrayList<Member> myClients = trainer.getAssignedClients();

        System.out.println("\n=== MY PERSONAL TRAINING CLIENTS (" + trainer.getClientCount() + " clients) ===\n");

        if (myClients.isEmpty()) {
            System.out.println("You have no personal training clients yet.");
            System.out.println("Use 'Add Client' to assign members.");
            return;
        }

        // Display clients with their BMI for quick reference
        System.out.println("+----------+--------------------+-----------------+------------+-----------------+");
        System.out.printf("| %-8s | %-18s | %-15s | %-8s | %-10s | %-4s |\n",
                "ID", "NAME", "PHONE", "TIER", "STATUS", "BMI");
        System.out.println("+----------+--------------------+-----------------+------------+-----------------+");

        for (Member m : myClients) {
            m.checkStatus();
            String bmiStatus = (m.getWeight() > 0 && m.getHeight() > 0)
                    ? String.format("%.1f", m.calculateBMI()) : "N/A";
            System.out.printf("| %-8s | %-18s | %-15s | %-8s | %-10s | %-4s |\n",
                    m.getId(),
                    m.getName(),
                    m.getPhno(),
                    m.getTier(),
                    m.getStatus(),
                    bmiStatus);
        }
        System.out.println("+----------+--------------------+-----------------+------------+-----------------+");
    }

    // Remove a client from this trainer's personal training list
    public void removeClient() {
        System.out.println("\n=== REMOVE PERSONAL TRAINING CLIENT ===");

        ArrayList<Member> myClients = trainer.getAssignedClients();

        if (myClients.isEmpty()) {
            System.out.println("You have no clients to remove.");
            return;
        }

        // Show current clients so trainer knows who to pick
        System.out.println("\nYour current clients:");
        System.out.println("+----------+--------------------+-----------------+");
        System.out.printf("| %-8s | %-18s | %-15s |\n", "ID", "NAME", "PHONE");
        System.out.println("+----------+--------------------+-----------------+");

        for (Member m : myClients) {
            System.out.printf("| %-8s | %-18s | %-15s |\n",
                    m.getId(),
                    m.getName(),
                    m.getPhno());
        }
        System.out.println("+----------+--------------------+-----------------+");

        String memberId = Input.getString("\nEnter Member ID to remove (0 to cancel): ");

        if (memberId.equals("0")) {
            return;
        }

        // Find the client in the list
        Member toRemove = null;
        for (Member m : myClients) {
            if (m.getId().equalsIgnoreCase(memberId)) {
                toRemove = m;
                break;
            }
        }

        if (toRemove == null) {
            System.out.println("Client not found in your list.");
            return;
        }

        // Remove and confirm
        if (trainer.removeClient(toRemove)) {
            System.out.println("\n==========================================");
            System.out.println("Client removed successfully!");
            System.out.println("==========================================");
            System.out.println("Name     : " + toRemove.getName());
            System.out.println("ID       : " + toRemove.getId());
            System.out.println("==========================================");
            System.out.println("You now have " + trainer.getClientCount() + " client(s).");
            System.out.println("==========================================");
        } else {
            System.out.println("Failed to remove client.");
        }
    }    
}
