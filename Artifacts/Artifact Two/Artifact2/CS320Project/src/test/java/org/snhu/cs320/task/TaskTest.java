/**
 * Kameron Hinman-Mitchell
 * CS-320
 * September 2025
 * Updated May 2026
 */

package org.snhu.cs320.task;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TaskTest {
    
    private Task task;
    private final String validTaskId = "1234567890";
    private final String validTaskName = "Valid Task Name";
    private final String validDescription = "This is a valid description";
    
    @BeforeEach
    void setUp() {
        task = new Task(validTaskId, validTaskName, validDescription);
    }
    
    @Test
    @DisplayName("Task constructor with valid arguments")
    void testTaskConstructorValid() {
        assertNotNull(task);
        assertEquals(validTaskId, task.getTaskId());
        assertEquals(validTaskName, task.getTaskName());
        assertEquals(validDescription, task.getDescription());
    }
    
    @Test
    @DisplayName("Task constructor with null task ID")
    void testTaskConstructorNullId() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Task(null, validTaskName, validDescription);//Red
        });
    }
    
    @Test
    @DisplayName("Task constructor with long task ID")
    void testTaskConstructorLongId() {
        String longId = "12345678901"; // 11 characters
        assertThrows(IllegalArgumentException.class, () -> {
            new Task(longId, validTaskName, validDescription);//Red
        });
    }
    
    @Test
    @DisplayName("Task constructor with null task name")
    void testTaskConstructorNullName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Task(validTaskId, null, validDescription);//Red
        });
    }
    
    @Test
    @DisplayName("Task constructor with long task name")
    void testTaskConstructorLongName() {
        String longName = "This task name is way too long for the requirements";
        assertThrows(IllegalArgumentException.class, () -> {
            new Task(validTaskId, longName, validDescription);//Red
        });
    }
    
    @Test
    @DisplayName("Task constructor with empty task name")
    void testTaskConstructorEmptyName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Task(validTaskId, "", validDescription);//Red
        });
    }
    
    @Test
    @DisplayName("Task constructor with null description")
    void testTaskConstructorNullDescription() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Task(validTaskId, validTaskName, null);//Red
        });
    }
    
    @Test
    @DisplayName("Task constructor with long description")
    void testTaskConstructorLongDescription() {
        String longDescription = "This description is definitely way too long and exceeds the fifty character limit";
        assertThrows(IllegalArgumentException.class, () -> {
            new Task(validTaskId, validTaskName, longDescription);//Red
        });
    }
    
    @Test
    @DisplayName("Task constructor with empty description")
    void testTaskConstructorEmptyDescription() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Task(validTaskId, validTaskName, "");//Red
        });
    }
    
    @Test
    @DisplayName("Set valid task name")
    void testSetValidTaskName() {
        String newName = "Updated Task Name";
        task.setTaskName(newName);
        assertEquals(newName, task.getTaskName());
    }
    
    @Test
    @DisplayName("Set invalid task name (null)")
    void testSetNullTaskName() {
        assertThrows(IllegalArgumentException.class, () -> {
            task.setTaskName(null);//Red
        });
    }
    
    @Test
    @DisplayName("Set invalid task name (too long)")
    void testSetLongTaskName() {
        String longName = "This task name is way too long for the requirements";
        assertThrows(IllegalArgumentException.class, () -> {
            task.setTaskName(longName);//Red
        });
    }
    
    @Test
    @DisplayName("Set valid description")
    void testSetValidDescription() {
        String newDescription = "Updated valid description";
        task.setDescription(newDescription);
        assertEquals(newDescription, task.getDescription());
    }
    
    @Test
    @DisplayName("Set invalid description (null)")
    void testSetNullDescription() {
        assertThrows(IllegalArgumentException.class, () -> {
            task.setDescription(null);//Red
        });
    }
    
    @Test
    @DisplayName("Task ID is immutable")
    void testTaskIdImmutability() {  
        String originalId = task.getTaskId();
        assertEquals(validTaskId, originalId);
    }
    @Test
    @DisplayName("Test toString returns expected format")
    void testToString() {
        Task task = new Task("ID123", "Test Task", "Test Description");
        String expected = "Task{taskId='ID123', taskName='Test Task', description='Test Description'}";
        assertEquals(expected, task.toString());
    }
}