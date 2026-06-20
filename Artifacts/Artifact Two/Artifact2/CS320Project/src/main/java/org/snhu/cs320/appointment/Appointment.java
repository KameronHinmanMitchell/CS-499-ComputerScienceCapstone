/**
 * Kameron Hinman-Mitchell
 * CS-320
 * October 2025
 */

package org.snhu.cs320.appointment;

import java.util.Date;

public class Appointment {
    
    private final String appointmentId; // Made immutable
    private Date appointmentDate;
    private String description;

    /**
     * Validates the appointment ID
     */
    private boolean validateAppointmentId(String appointmentId) {
        return appointmentId != null && 
               !appointmentId.isEmpty() && 
               appointmentId.length() <= 10;
    }
    
    /**
     * Validates the appointment date
     */
    private boolean validateAppointmentDate(Date date) {
        return date != null && !date.before(new Date());
    }
    
    /**
     * Validates the description
     */
    private boolean validateDescription(String description) {
        return description != null && 
               !description.isEmpty() && 
               description.length() <= 50;
    }
    
    /**
     * Constructor with validation
     */
    public Appointment(String appointmentId, Date appointmentDate, String description) {
        // Validate parameters before assignment
        if (!validateAppointmentId(appointmentId)) {
            throw new IllegalArgumentException(
                "Appointment ID must be non-null, non-empty, and 10 characters or less");
        }
        
        if (!validateAppointmentDate(appointmentDate)) {
            throw new IllegalArgumentException(
                "Appointment date must be non-null and not in the past");
        }
        
        if (!validateDescription(description)) {
            throw new IllegalArgumentException(
                "Description must be non-null, non-empty, and 50 characters or less");
        }
        
        // Assign validated values
        this.appointmentId = appointmentId;
        this.appointmentDate = new Date(appointmentDate.getTime()); 
        this.description = description;
    }

    // Getters
    public String getAppointmentId() {
        return appointmentId;
    }

    public Date getAppointmentDate() {
        return new Date(appointmentDate.getTime()); 
    }

    public String getDescription() {
        return description;
    }

    // Setters
    public void setAppointmentDate(Date appointmentDate) {
        if (!validateAppointmentDate(appointmentDate)) {
            throw new IllegalArgumentException(
                "Appointment date must be non-null and not in the past");
        }
        this.appointmentDate = new Date(appointmentDate.getTime());
    }

    public void setDescription(String description) {
        if (!validateDescription(description)) {
            throw new IllegalArgumentException(
                "Description must be non-null, non-empty, and 50 characters or less");
        }
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return appointmentId.equals(that.appointmentId);
    }

    @Override
    public int hashCode() {
        return appointmentId.hashCode();
    }
}
