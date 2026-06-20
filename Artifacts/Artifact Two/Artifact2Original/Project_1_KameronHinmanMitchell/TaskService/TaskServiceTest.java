/**
 * Kameron Hinman-Mitchell
 * CS-320
 * September 2025
 */

package org.snhu.cs320.task;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TaskServiceTest {
    
    private TaskService taskService;
    private final String validTaskName = "Test Task";
    private final String validDescription = "Test Description";
    
    @BeforeEach
    void setUp() {
        taskService = new TaskService();
    }
    
    @Test
    @DisplayName("Add task with auto-generated ID")
    void testAddTask() {
        taskService.addTask(validTaskName, validDescription);
        
        Map<String, Task> tasks = taskService.getAllTasks();
        assertEquals(1, tasks.size());
        
        Task addedTask = tasks.values().iterator().next();
        assertEquals(validTaskName, addedTask.getTaskName());
        assertEquals(validDescription, addedTask.getDescription());
        assertNotNull(addedTask.getTaskId());
    }
    
    @Test
    @DisplayName("Add task with specific ID")
    void testAddTaskWithSpecificId() {
        String specificId = "TASK001";
        taskService.addTaskWithId(specificId, validTaskName, validDescription);
        
        Task retrievedTask = taskService.getTaskById(specificId);
        assertNotNull(retrievedTask);
        assertEquals(specificId, retrievedTask.getTaskId());
        assertEquals(validTaskName, retrievedTask.getTaskName());
    }
    
    @Test
    @DisplayName("Add task with duplicate ID")
    void testAddTaskWithDuplicateId() {
        String duplicateId = "TASK001";
        taskService.addTaskWithId(duplicateId, validTaskName, validDescription);
        
        assertThrows(IllegalArgumentException.class, () -> {
            taskService.addTaskWithId(duplicateId, "Another Task", "Another Description");
        });
    }
    
    @Test
    @DisplayName("Delete existing task")
    void testDeleteExistingTask() {
        String taskId = "TASK001";
        taskService.addTaskWithId(taskId, validTaskName, validDescription);
        
        assertTrue(taskService.getAllTasks().containsKey(taskId));
        
        taskService.deleteTask(taskId);
        
        assertFalse(taskService.getAllTasks().containsKey(taskId));
    }
    
    @Test
    @DisplayName("Delete non-existent task")
    void testDeleteNonExistentTask() {
        assertThrows(IllegalArgumentException.class, () -> {
            taskService.deleteTask("NON_EXISTENT");
        });
    }
    
    @Test
    @DisplayName("Update task name")
    void testUpdateTaskName() {
        String taskId = "TASK001";
        taskService.addTaskWithId(taskId, validTaskName, validDescription);
        
        String newTaskName = "Updated Task Name";
        taskService.updateTaskName(taskId, newTaskName);
        
        Task updatedTask = taskService.getTaskById(taskId);
        assertEquals(newTaskName, updatedTask.getTaskName());
        assertEquals(validDescription, updatedTask.getDescription()); // Description unchanged
    }
    
    @Test
    @DisplayName("Update task description")
    void testUpdateTaskDescription() {
        String taskId = "TASK001";
        taskService.addTaskWithId(taskId, validTaskName, validDescription);
        
        String newDescription = "Updated description";
        taskService.updateTaskDescription(taskId, newDescription);
        
        Task updatedTask = taskService.getTaskById(taskId);
        assertEquals(validTaskName, updatedTask.getTaskName()); // Name unchanged
        assertEquals(newDescription, updatedTask.getDescription());
    }
    
    @Test
    @DisplayName("Update non-existent task")
    void testUpdateNonExistentTask() {
        assertThrows(IllegalArgumentException.class, () -> {
            taskService.updateTaskName("NON_EXISTENT", "New Name");
        });
    }
    
    @Test
    @DisplayName("Add multiple tasks")
    void testAddMultipleTasks() {
        taskService.addTask("Task 1", "Description 1");
        taskService.addTask("Task 2", "Description 2");
        taskService.addTask("Task 3", "Description 3");
        
        assertEquals(3, taskService.getAllTasks().size());
    }
    
    @Test
    @DisplayName("Get non-existent task")
    void testGetNonExistentTask() {
        assertThrows(IllegalArgumentException.class, () -> {
            taskService.getTaskById("NON_EXISTENT");
        });
    }
    
    @Test
    @DisplayName("Clear all tasks")
    void testClearAllTasks() {
        taskService.addTask("Task 1", "Description 1");
        taskService.addTask("Task 2", "Description 2");
        
        assertEquals(2, taskService.getAllTasks().size());
        
        taskService.clearAllTasks();
        
        assertEquals(0, taskService.getAllTasks().size());
    }
}