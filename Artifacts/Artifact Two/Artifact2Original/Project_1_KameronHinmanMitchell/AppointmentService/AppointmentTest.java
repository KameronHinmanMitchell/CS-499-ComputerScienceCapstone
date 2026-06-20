/**
 * Kameron Hinman-Mitchell
 * CS-320
 * October 2025
 */
package org.snhu.cs320.appointment;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Date;
import java.util.Calendar;

class AppointmentTest {
    
    private Date futureDate;
    private Date pastDate;
    private String validId;
    private String validDescription;
    
    @BeforeEach
    void setUp() {
        //future date 
        Calendar futureCal = Calendar.getInstance();
        futureCal.set(Calendar.YEAR, 2025);
        futureCal.set(Calendar.MONTH, Calendar.DECEMBER);
        futureCal.set(Calendar.DATE, 5);
        futureDate = futureCal.getTime();
        
        //past date
        Calendar pastCal = Calendar.getInstance();
        pastCal.set(Calendar.YEAR, 1996);
        pastCal.set(Calendar.MONTH, Calendar.DECEMBER);
        pastCal.set(Calendar.DATE, 5);
        pastDate = pastCal.getTime();
        
        validId = "1234567890";
        validDescription = "This is a valid description under 50 chars";
    }

    @Test
    @DisplayName("Test Appointment Creation with Valid Parameters")
    void testAppointmentCreationWithValidParameters() {
        Appointment appointment = new Appointment(validId, futureDate, validDescription);
        
        assertAll("Appointment properties",
            () -> assertEquals(validId, appointment.getAppointmentId()),
            () -> assertEquals(futureDate, appointment.getAppointmentDate()),
            () -> assertEquals(validDescription, appointment.getDescription())
        );
    }
    
    @Test
    @DisplayName("Test Appointment ID Validation - Too Long")
    void testAppointmentIdTooLong() {
        String longId = "12345678901"; 
        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Appointment(longId, futureDate, validDescription);
        });
        
        assertTrue(exception.getMessage().contains("Appointment ID"));
    }
    
    @Test
    @DisplayName("Test Appointment ID Validation - Null")
    void testAppointmentIdNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Appointment(null, futureDate, validDescription);
        });
        
        assertTrue(exception.getMessage().contains("Appointment ID"));
    }
    
    @Test
    @DisplayName("Test Appointment ID Validation - Empty")
    void testAppointmentIdEmpty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Appointment("", futureDate, validDescription);
        });
        
        assertTrue(exception.getMessage().contains("Appointment ID"));
    }
    
    @Test
    @DisplayName("Test Appointment Date Validation - Past Date")
    void testAppointmentDateInPast() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Appointment(validId, pastDate, validDescription);
        });
        
        assertTrue(exception.getMessage().contains("Appointment date"));
    }
    
    @Test
    @DisplayName("Test Appointment Date Validation - Null Date")
    void testAppointmentDateNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Appointment(validId, null, validDescription);
        });
        
        assertTrue(exception.getMessage().contains("Appointment date"));
    }
    
    @Test
    @DisplayName("Test Description Validation - Too Long")
    void testDescriptionTooLong() {
        String longDescription = "This description is way too long and exceeds the fifty character limit by quite a bit";
        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Appointment(validId, futureDate, longDescription);
        });
        
        assertTrue(exception.getMessage().contains("Description"));
    }
    
    @Test
    @DisplayName("Test Description Validation - Null")
    void testDescriptionNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Appointment(validId, futureDate, null);
        });
        
        assertTrue(exception.getMessage().contains("Description"));
    }
    
    @Test
    @DisplayName("Test Description Validation - Empty")
    void testDescriptionEmpty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Appointment(validId, futureDate, "");
        });
        
        assertTrue(exception.getMessage().contains("Description"));
    }
    
    @Test
    @DisplayName("Test Date Update with Valid Date")
    void testUpdateDateWithValidDate() {
        Appointment appointment = new Appointment(validId, futureDate, validDescription);
        
        Calendar newFutureCal = Calendar.getInstance();
        newFutureCal.set(Calendar.YEAR, 2026);
        Date newFutureDate = newFutureCal.getTime();
        
        appointment.setAppointmentDate(newFutureDate);
        assertEquals(newFutureDate, appointment.getAppointmentDate());
    }
    
    @Test
    @DisplayName("Test Date Update with Invalid Date")
    void testUpdateDateWithInvalidDate() {
        Appointment appointment = new Appointment(validId, futureDate, validDescription);
        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            appointment.setAppointmentDate(pastDate);
        });
        
        assertTrue(exception.getMessage().contains("Appointment date"));
    }
    
    @Test
    @DisplayName("Test Description Update with Valid Description")
    void testUpdateDescriptionWithValidDescription() {
        Appointment appointment = new Appointment(validId, futureDate, validDescription);
        String newDescription = "Updated valid description";
        
        appointment.setDescription(newDescription);
        assertEquals(newDescription, appointment.getDescription());
    }
    
    @Test
    @DisplayName("Test Appointment Equality")
    void testAppointmentEquality() {
        Appointment appointment1 = new Appointment(validId, futureDate, validDescription);
        Appointment appointment2 = new Appointment(validId, futureDate, "Different description");
        
        assertEquals(appointment1, appointment2);
        assertEquals(appointment1.hashCode(), appointment2.hashCode());
    }
    
    @Test
    @DisplayName("Test Defensive Copying of Date")
    void testDefensiveCopying() {
        Date mutableDate = new Date(futureDate.getTime());
        Appointment appointment = new Appointment(validId, mutableDate, validDescription);
        
        // Modify the date
        mutableDate.setTime(pastDate.getTime());
        
        // Appointment date should not be affected
        assertNotEquals(mutableDate, appointment.getAppointmentDate());
        assertTrue(appointment.getAppointmentDate().after(new Date()));
    }
}