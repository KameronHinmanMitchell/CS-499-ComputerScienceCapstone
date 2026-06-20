/**
 * Kameron Hinman-Mitchell
 * CS-320
 * October 2025
 */
package org.snhu.cs320.appointment;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class AppointmentService {
    
    private final Map<String, Appointment> appointments;
    private final AtomicLong currentId;
    
    public AppointmentService() {
        this.appointments = new HashMap<>();
        this.currentId = new AtomicLong(0);
    }
    
    /**
     * Adds a new appointment with auto-generated unique ID
     */
    public String addAppointment(Date appointmentDate, String description) {
        String newAppointmentId = generateUniqueId();
        Appointment appointment = new Appointment(newAppointmentId, appointmentDate, description);
        
        synchronized (appointments) {
            appointments.put(newAppointmentId, appointment);
        }
        
        return newAppointmentId;
    }
    
    /**
     * Adds an appointment with a specific ID
     */
    public void addAppointment(String appointmentId, Date appointmentDate, String description) {
        Appointment appointment = new Appointment(appointmentId, appointmentDate, description);
        
        synchronized (appointments) {
            if (appointments.containsKey(appointmentId)) {
                throw new IllegalArgumentException("Appointment with ID " + appointmentId + " already exists");
            }
            appointments.put(appointmentId, appointment);
        }
    }
    
    /**
     * Deletes an appointment by ID
     */
    public boolean deleteAppointment(String appointmentId) {
        if (appointmentId == null || appointmentId.isEmpty()) {
            throw new IllegalArgumentException("Appointment ID cannot be null or empty");
        }
        
        synchronized (appointments) {
            Appointment removed = appointments.remove(appointmentId);
            return removed != null;
        }
    }
    
    /**
     * Retrieves an appointment by ID
     */
    public Appointment getAppointment(String appointmentId) {
        if (appointmentId == null || appointmentId.isEmpty()) {
            throw new IllegalArgumentException("Appointment ID cannot be null or empty");
        }
        
        synchronized (appointments) {
            Appointment appointment = appointments.get(appointmentId);
            if (appointment == null) {
                throw new IllegalArgumentException("Appointment with ID " + appointmentId + " not found");
            }
            return appointment;
        }
    }
    
    /**
     * Updates an existing appointment's date
     */
    public void updateAppointmentDate(String appointmentId, Date newDate) {
        synchronized (appointments) {
            Appointment appointment = getAppointment(appointmentId);
            appointment.setAppointmentDate(newDate);
        }
    }
    
    /**
     * Updates an existing appointment's description
     */
    public void updateAppointmentDescription(String appointmentId, String newDescription) {
        synchronized (appointments) {
            Appointment appointment = getAppointment(appointmentId);
            appointment.setDescription(newDescription);
        }
    }
    
    /**
     * Gets all appointments (returns a copy for thread safety)
     */
    public Map<String, Appointment> getAllAppointments() {
        synchronized (appointments) {
            return new HashMap<>(appointments);
        }
    }
    
    /**
     * Checks if an appointment exists
     */
    public boolean appointmentExists(String appointmentId) {
        synchronized (appointments) {
            return appointments.containsKey(appointmentId);
        }
    }
    
    /**
     * Gets the number of appointments
     */
    public int getAppointmentCount() {
        synchronized (appointments) {
            return appointments.size();
        }
    }
    
    /**
     * Clears all appointments (mainly for testing)
     */
    public void clearAllAppointments() {
        synchronized (appointments) {
            appointments.clear();
            currentId.set(0);
        }
    }
    
    /**
     * Generates a unique appointment ID
     */
    private String generateUniqueId() {
        return String.valueOf(currentId.getAndIncrement());
    }
}
