/**
 * Kameron Hinman-Mitchell
 * CS-320
 * September 2025
 */

package org.snhu.cs320.task;

public class Task {
    
    private final String taskId; 
    private String taskName;
    private String description;
    
    /**
     * Validates the task ID
     */
    private boolean validateTaskId(String taskId) {
        return taskId != null && taskId.length() <= 10;
    }
    
    /**
     * Validates the task name
     */
    private boolean validateTaskName(String taskName) {
        return taskName != null && taskName.length() <= 20 && !taskName.isEmpty();
    }
    
    /**
     * Validates the description
     */
    private boolean validateDescription(String description) {
        return description != null && description.length() <= 50 && !description.isEmpty();
    }
    
    /**
     * Constructor
     */
    public Task(String taskId, String taskName, String description) {
        if (!validateTaskId(taskId)) {
            throw new IllegalArgumentException("Invalid task ID");
        }
        if (!validateTaskName(taskName)) {
            throw new IllegalArgumentException("Invalid task name");
        }
        if (!validateDescription(description)) {
            throw new IllegalArgumentException("Invalid description");
        }
        
        this.taskId = taskId;
        this.taskName = taskName;
        this.description = description;
    }
    
    // Getters
    public String getTaskId() {
        return taskId;
    }
    
    public String getTaskName() {
        return taskName;
    }
    
    public String getDescription() {
        return description;
    }
    
    // Setters (only for updatable fields)
    public void setTaskName(String taskName) {
        if (!validateTaskName(taskName)) {
            throw new IllegalArgumentException("Invalid task name");
        }
        this.taskName = taskName;
    }
    
    public void setDescription(String description) {
        if (!validateDescription(description)) {
            throw new IllegalArgumentException("Invalid description");
        }
        this.description = description;
    }
    
    @Override
    public String toString() {
        return "Task{taskId='" + taskId + "', taskName='" + taskName + "', description='" + description + "'}";
    }
}
