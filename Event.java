package Event_Reminder_System;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
* 1. Title
* 2. Description
* 3. Date
* 4. Status(done / pending)
*/
public class Event {
    private String title;
    private String description;
    private LocalDateTime date;
    private boolean status; // true = done, false = pending
    
    // Default constructor
    public Event() {
        this.status = false; // default to pending
    }
    
    // Parameterized constructor
    public Event(String title, String description, LocalDateTime date) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.status = false; // default to pending
    }
    
    // Parameterized constructor with status
    public Event(String title, String description, LocalDateTime date, boolean status) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.status = status;
    }
    
    // Getter and Setter for Title
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    // Getter and Setter for Description
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    // Getter and Setter for Date
    public LocalDateTime getDate() {
        return date;
    }
    
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    
    // Getter and Setter for Status
    public boolean getStatus() {
        return status;
    }
    
    public void setStatus(boolean status) {
        this.status = status;
    }
    
    // Helper method to check if event is done
    public boolean isDone() {
        return status;
    }
    
    // Helper method to check if event is pending
    public boolean isPending() {
        return !status;
    }
    
    // Helper method to mark as done
    public void markAsDone() {
        this.status = true;
    }
    
    // Helper method to mark as pending
    public void markAsPending() {
        this.status = false;
    }
    
    // Get status as string
    public String getStatusString() {
        return status ? "done" : "pending";
    }
    
    // toString method for displaying event information
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String dateStr = (date != null) ? date.format(formatter) : "No date set";
        return String.format("Event: %s | Description: %s | Date: %s | Status: %s", 
                           title, description, dateStr, getStatusString());
    }
    
    // equals method for comparing events
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Event event = (Event) obj;
        return status == event.status &&
               title.equals(event.title) &&
               description.equals(event.description) &&
               date.equals(event.date);
    }
    
    // hashCode method
    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (status ? 1 : 0);
        return result;
    }
}