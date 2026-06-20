/**
 * Kameron Hinman-Mitchell
 * CS-320
 * September 2025
 */

package org.snhu.cs320.task;

import java.util.HashMap;
import java.util.Map;

public class TaskService {
    
    private final Map<String, Task> tasks;
    private int currentIdCounter;
    
    public TaskService() {
        this.tasks = new HashMap<>();
        this.currentIdCounter = 1; // Start from 1
    }
    
    /**
     * Adds a new task with auto-generated unique ID
     */
    public void addTask(String taskName, String description) {
        String newTaskId = generateUniqueId();
        Task newTask = new Task(newTaskId, taskName, description);
        tasks.put(newTaskId, newTask);
    }
    
    /**
     * Adds a task with a specific ID (for testing)
     */
    public void addTaskWithId(String taskId, String taskName, String description) {
        if (tasks.containsKey(taskId)) {
            throw new IllegalArgumentException("Task ID already exists");
        }
        Task newTask = new Task(taskId, taskName, description);
        tasks.put(taskId, newTask);
    }
    
    /**
     * Deletes a task by ID
     */
    public void deleteTask(String taskId) {
        if (!tasks.containsKey(taskId)) {
            throw new IllegalArgumentException("Task ID not found");
        }
        tasks.remove(taskId);
    }
    
    /**
     * Updates task name
     */
    public void updateTaskName(String taskId, String newTaskName) {
        Task task = getTaskById(taskId);
        task.setTaskName(newTaskName);
    }
    
    /**
     * Updates task description
     */
    public void updateTaskDescription(String taskId, String newDescription) {
        Task task = getTaskById(taskId);
        task.setDescription(newDescription);
    }
    
    /**
     * Gets a task by ID
     */
    public Task getTaskById(String taskId) {
        Task task = tasks.get(taskId);
        if (task == null) {
            throw new IllegalArgumentException("Task ID not found");
        }
        return task;
    }
    
    /**
     * Gets all tasks (for testing)
     */
    public Map<String, Task> getAllTasks() {
        return new HashMap<>(tasks); // Return copy to preserve encapsulation
    }
    
    /**
     * Generates unique task ID
     */
    private String generateUniqueId() {
        return String.valueOf(currentIdCounter++);
    }
    
    /**
     * Clears all tasks (for testing)
     */
    public void clearAllTasks() {
        tasks.clear();
        currentIdCounter = 1;
    }
}
