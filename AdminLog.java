/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gymmanagementsystem;

/**
 *
 * Records admin actions for audit trail
 *
 * OOP Concept: Encapsulation - data (action, time) is wrapped inside object
 * 
 * @author Victus
 */

import java.time.LocalDateTime;

public class AdminLog {

    private String action;
    private LocalDateTime time;

    public AdminLog(String action) {
        this.action = action;
        this.time = LocalDateTime.now();
    }

    public String getAction() {
        return action;
    }


    public LocalDateTime getTime() {
        return time;   
    }
}
