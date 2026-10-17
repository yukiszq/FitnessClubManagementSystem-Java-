/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gymmanagementsystem;

/**
 * Manages sauna booking for Gold members and token validation for staff
 *
 * OOP Concepts Used: - Static members: allActiveTokens shared across all
 * instances (class-level storage) - Encapsulation: TokenInfo inner class hides
 * token validation logic - Composition: SaunaManagement HAS-A Member - HashMap
 * usage: Efficient token lookup by key
 * @author Victus
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SaunaManagement {
    private Member member;

    // Static storage for ALL active sauna tokens (shared across all members)
    // This allows staff to validate tokens without knowing which member owns it
    private static Map<String, TokenInfo> allActiveTokens = new HashMap<>();

    // Inner class to store token information (encapsulation)
    private static class TokenInfo {

        String memberId;
        String memberName;
        LocalDateTime expiry;

        TokenInfo(String memberId, String memberName, LocalDateTime expiry) {
            this.memberId = memberId;
            this.memberName = memberName;
            this.expiry = expiry;
        }

        // Check if token has expired
        boolean isValid() {
            return expiry.isAfter(LocalDateTime.now());
        }
    }

    public SaunaManagement(Member member) {
        this.member = member;
    }

    // ========== MEMBER BOOKING (Gold members only) ==========
    public void book() {
        System.out.println("\n=== SAUNA BOOKING ===");

        // Check if member already has a valid token
        if (member.getSaunaToken() != null && member.getTokenExpiry() != null
                && member.getTokenExpiry().isAfter(LocalDateTime.now())) {

            System.out.println("You already have a valid sauna token!");
            System.out.println("Token: " + member.getSaunaToken());
            System.out.println("Valid until: " + member.getTokenExpiry().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            return;
        }

        // Generate new token (valid for 2 hours)
        String token = generateToken();
        LocalDateTime expiry = LocalDateTime.now().plusHours(2);

        // Save to member object
        member.setSaunaToken(token);
        member.setTokenExpiry(expiry);

        // Save to global token map for staff validation
        allActiveTokens.put(token, new TokenInfo(member.getId(), member.getName(), expiry));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        System.out.println("Sauna booking successful!");
        System.out.println("Your Access Token: " + token);
        System.out.println("Valid until: " + expiry.format(formatter));
        System.out.println("\nPlease show this token to staff for sauna access.");
    }

    // Generate random 6-character token with TOK- prefix
    // Format: TOK-XXXXXX (e.g., TOK-ABC123)
    private String generateToken() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder token = new StringBuilder();
        Random rand = new Random();

        for (int i = 0; i < 6; i++) {
            token.append(chars.charAt(rand.nextInt(chars.length())));
        }

        return "TOK-" + token.toString();
    }

    // ========== STAFF VALIDATION ==========
    // Staff can validate any token to grant sauna access
    public static void validateToken() {
        System.out.println("\n=== SAUNA TOKEN VALIDATION ===");

        String token = Input.getString("Enter Sauna Token (or 0 to exit): ");

        if (token.equals("0")) {
            return;
        }

        TokenInfo info = allActiveTokens.get(token);

        if (info == null) {
            System.out.println("\nACCESS DENIED! Invalid token.");
            System.out.print("\nPress ENTER to go back...");
        } else if (!info.isValid()) {
            System.out.println("\nACCESS DENIED! Token expired.");
            System.out.print("\nPress ENTER to go back...");
            allActiveTokens.remove(token); // Clean up expired token from storage
        } else {
            System.out.println("\nACCESS GRANTED!");
            System.out.println("Member: " + info.memberName);
            System.out.print("\nPress ENTER to go back...");
        }

        Input.sc.nextLine();
    }   
}
