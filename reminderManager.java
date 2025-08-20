package Event_Reminder_System;

import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
*1. Adding events
* 2. Viewing events
*3. Marking as complete
* 4. Removing events
*/
public class reminderManager {
    
    // Data structure to store events - using ArrayList for dynamic storage
    private ArrayList<Event> events;
    private int nextId;
    
    // Constructor
    public reminderManager() {
        this.events = new ArrayList<>();
        this.nextId = 1;
    }
    
    // Inner class to represent an Event with ID and priority
    public static class EventWrapper {
        private int id;
        private Event event;
        private String priority; // HIGH, MEDIUM, LOW
        
        public EventWrapper(int id, Event event, String priority) {
            this.id = id;
            this.event = event;
            this.priority = priority;
        }
        
        // Getters
        public int getId() { return id; }
        public Event getEvent() { return event; }
        public String getPriority() { return priority; }
        
        // Setters
        public void setPriority(String priority) { this.priority = priority; }
        
        @Override
        public String toString() {
            return String.format("ID: %d | %s | Priority: %s", 
                               id, event.toString(), priority);
        }
    }
    
    private ArrayList<EventWrapper> eventWrappers = new ArrayList<>();
    
    // 1. Adding events
    public boolean addEvent(String title, String description, LocalDateTime dateTime, String priority) {
        if (title == null || title.trim().isEmpty()) {
            System.out.println("Error: Event title cannot be empty!");
            return false;
        }
        
        if (dateTime.isBefore(LocalDateTime.now())) {
            System.out.println("Error: Cannot add event in the past!");
            return false;
        }
        
        Event newEvent = new Event(title, description, dateTime);
        EventWrapper wrapper = new EventWrapper(nextId++, newEvent, priority.toUpperCase());
        eventWrappers.add(wrapper);
        
        // Sort events by date time
        Collections.sort(eventWrappers, (e1, e2) -> 
            e1.getEvent().getDate().compareTo(e2.getEvent().getDate()));
        
        System.out.println("Event added successfully! Event ID: " + wrapper.getId());
        return true;
    }
    
    // 2. Viewing events
    public void viewAllEvents() {
        if (eventWrappers.isEmpty()) {
            System.out.println("No events found!");
            return;
        }
        
        System.out.println("\n=== ALL EVENTS ===");
        for (EventWrapper wrapper : eventWrappers) {
            System.out.println(wrapper);
        }
        System.out.println("Total events: " + eventWrappers.size());
    }
    
    public void viewPendingEvents() {
        List<EventWrapper> pendingEvents = new ArrayList<>();
        for (EventWrapper wrapper : eventWrappers) {
            if (wrapper.getEvent().isPending()) {
                pendingEvents.add(wrapper);
            }
        }
        
        if (pendingEvents.isEmpty()) {
            System.out.println("No pending events found!");
            return;
        }
        
        System.out.println("\n=== PENDING EVENTS ===");
        for (EventWrapper wrapper : pendingEvents) {
            System.out.println(wrapper);
        }
    }
    
    public void viewCompletedEvents() {
        List<EventWrapper> completedEvents = new ArrayList<>();
        for (EventWrapper wrapper : eventWrappers) {
            if (wrapper.getEvent().isDone()) {
                completedEvents.add(wrapper);
            }
        }
        
        if (completedEvents.isEmpty()) {
            System.out.println("No completed events found!");
            return;
        }
        
        System.out.println("\n=== COMPLETED EVENTS ===");
        for (EventWrapper wrapper : completedEvents) {
            System.out.println(wrapper);
        }
    }
    
    public void viewEventsByPriority(String priority) {
        List<EventWrapper> priorityEvents = new ArrayList<>();
        for (EventWrapper wrapper : eventWrappers) {
            if (wrapper.getPriority().equalsIgnoreCase(priority)) {
                priorityEvents.add(wrapper);
            }
        }
        
        if (priorityEvents.isEmpty()) {
            System.out.println("No events found with priority: " + priority);
            return;
        }
        
        System.out.println("\n=== " + priority.toUpperCase() + " PRIORITY EVENTS ===");
        for (EventWrapper wrapper : priorityEvents) {
            System.out.println(wrapper);
        }
    }
    
    // 3. Marking as complete
    public boolean markAsComplete(int eventId) {
        for (EventWrapper wrapper : eventWrappers) {
            if (wrapper.getId() == eventId) {
                if (wrapper.getEvent().isDone()) {
                    System.out.println("Event is already marked as completed!");
                    return false;
                }
                wrapper.getEvent().markAsDone();
                System.out.println("Event marked as completed: " + wrapper.getEvent().getTitle());
                return true;
            }
        }
        System.out.println("Event with ID " + eventId + " not found!");
        return false;
    }
    
    public boolean markAsPending(int eventId) {
        for (EventWrapper wrapper : eventWrappers) {
            if (wrapper.getId() == eventId) {
                if (wrapper.getEvent().isPending()) {
                    System.out.println("Event is already pending!");
                    return false;
                }
                wrapper.getEvent().markAsPending();
                System.out.println("Event marked as pending: " + wrapper.getEvent().getTitle());
                return true;
            }
        }
        System.out.println("Event with ID " + eventId + " not found!");
        return false;
    }
    
    // 4. Removing events
    public boolean removeEvent(int eventId) {
        Iterator<EventWrapper> iterator = eventWrappers.iterator();
        while (iterator.hasNext()) {
            EventWrapper wrapper = iterator.next();
            if (wrapper.getId() == eventId) {
                iterator.remove();
                System.out.println("Event removed successfully: " + wrapper.getEvent().getTitle());
                return true;
            }
        }
        System.out.println("Event with ID " + eventId + " not found!");
        return false;
    }
    
    public void removeCompletedEvents() {
        Iterator<EventWrapper> iterator = eventWrappers.iterator();
        int removedCount = 0;
        
        while (iterator.hasNext()) {
            EventWrapper wrapper = iterator.next();
            if (wrapper.getEvent().isDone()) {
                iterator.remove();
                removedCount++;
            }
        }
        
        if (removedCount > 0) {
            System.out.println(removedCount + " completed events removed successfully!");
        } else {
            System.out.println("No completed events to remove!");
        }
    }
    
    // Additional utility methods
    public EventWrapper findEventById(int eventId) {
        for (EventWrapper wrapper : eventWrappers) {
            if (wrapper.getId() == eventId) {
                return wrapper;
            }
        }
        return null;
    }
    
    public int getTotalEvents() {
        return eventWrappers.size();
    }
    
    public int getPendingEventsCount() {
        int count = 0;
        for (EventWrapper wrapper : eventWrappers) {
            if (wrapper.getEvent().isPending()) {
                count++;
            }
        }
        return count;
    }
    
    public int getCompletedEventsCount() {
        int count = 0;
        for (EventWrapper wrapper : eventWrappers) {
            if (wrapper.getEvent().isDone()) {
                count++;
            }
        }
        return count;
    }
    
    // Get upcoming events (events in next 24 hours)
    public void getUpcomingEvents() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime next24Hours = now.plusDays(1);
        List<EventWrapper> upcomingEvents = new ArrayList<>();
        
        for (EventWrapper wrapper : eventWrappers) {
            if (wrapper.getEvent().isPending() && 
                wrapper.getEvent().getDate().isAfter(now) && 
                wrapper.getEvent().getDate().isBefore(next24Hours)) {
                upcomingEvents.add(wrapper);
            }
        }
        
        if (upcomingEvents.isEmpty()) {
            System.out.println("No upcoming events in the next 24 hours!");
            return;
        }
        
        System.out.println("\n=== UPCOMING EVENTS (Next 24 Hours) ===");
        for (EventWrapper wrapper : upcomingEvents) {
            System.out.println(wrapper);
        }
    }
}
