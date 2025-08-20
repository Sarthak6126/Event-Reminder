package Event_Reminder_System;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Executer {
    
    private static reminderManager manager = new reminderManager();
    private static Scanner scanner = new Scanner(System.in);
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("     WELCOME TO EVENT REMINDER SYSTEM");
        System.out.println("========================================");
        
        boolean running = true;
        
        while (running) {
            displayMenu();
            int choice = getChoice();
            
            switch (choice) {
                case 1:
                    addNewEvent();
                    break;
                case 2:
                    viewEventsMenu();
                    break;
                case 3:
                    markEventComplete();
                    break;
                case 4:
                    markEventPending();
                    break;
                case 5:
                    removeEventMenu();
                    break;
                case 6:
                    searchEventById();
                    break;
                case 7:
                    showStatistics();
                    break;
                case 8:
                    manager.getUpcomingEvents();
                    break;
                case 9:
                    running = false;
                    System.out.println("Thank you for using Event Reminder System!");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
            
            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
        
        scanner.close();
    }
    
    private static void displayMenu() {
        System.out.println("\n=================== MAIN MENU ===================");
        System.out.println("1. Add New Event");
        System.out.println("2. View Events");
        System.out.println("3. Mark Event as Complete");
        System.out.println("4. Mark Event as Pending");
        System.out.println("5. Remove Events");
        System.out.println("6. Search Event by ID");
        System.out.println("7. Show Statistics");
        System.out.println("8. View Upcoming Events (Next 24 Hours)");
        System.out.println("9. Exit");
        System.out.println("================================================");
        System.out.print("Enter your choice (1-9): ");
    }
    
    private static int getChoice() {
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            return choice;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private static void addNewEvent() {
        System.out.println("\n=== ADD NEW EVENT ===");
        
        System.out.print("Enter event title: ");
        String title = scanner.nextLine().trim();
        
        if (title.isEmpty()) {
            System.out.println("Error: Title cannot be empty!");
            return;
        }
        
        System.out.print("Enter event description: ");
        String description = scanner.nextLine().trim();
        
        LocalDateTime dateTime = null;
        while (dateTime == null) {
            System.out.print("Enter date and time (yyyy-MM-dd HH:mm): ");
            String dateInput = scanner.nextLine().trim();
            
            try {
                dateTime = LocalDateTime.parse(dateInput, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format! Please use yyyy-MM-dd HH:mm");
            }
        }
        
        String priority = "";
        while (!priority.matches("(?i)(HIGH|MEDIUM|LOW)")) {
            System.out.print("Enter priority (HIGH/MEDIUM/LOW): ");
            priority = scanner.nextLine().trim();
            
            if (!priority.matches("(?i)(HIGH|MEDIUM|LOW)")) {
                System.out.println("Invalid priority! Please enter HIGH, MEDIUM, or LOW");
            }
        }
        
        manager.addEvent(title, description, dateTime, priority);
    }
    
    private static void viewEventsMenu() {
        System.out.println("\n=== VIEW EVENTS MENU ===");
        System.out.println("1. View All Events");
        System.out.println("2. View Pending Events");
        System.out.println("3. View Completed Events");
        System.out.println("4. View Events by Priority");
        System.out.print("Enter your choice (1-4): ");
        
        int choice = getChoice();
        
        switch (choice) {
            case 1:
                manager.viewAllEvents();
                break;
            case 2:
                manager.viewPendingEvents();
                break;
            case 3:
                manager.viewCompletedEvents();
                break;
            case 4:
                System.out.print("Enter priority (HIGH/MEDIUM/LOW): ");
                String priority = scanner.nextLine().trim();
                manager.viewEventsByPriority(priority);
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }
    
    private static void markEventComplete() {
        System.out.println("\n=== MARK EVENT AS COMPLETE ===");
        manager.viewPendingEvents();
        
        if (manager.getPendingEventsCount() == 0) {
            return;
        }
        
        System.out.print("Enter event ID to mark as complete: ");
        int id = getChoice();
        
        if (id > 0) {
            manager.markAsComplete(id);
        } else {
            System.out.println("Invalid ID!");
        }
    }
    
    private static void markEventPending() {
        System.out.println("\n=== MARK EVENT AS PENDING ===");
        manager.viewCompletedEvents();
        
        if (manager.getCompletedEventsCount() == 0) {
            return;
        }
        
        System.out.print("Enter event ID to mark as pending: ");
        int id = getChoice();
        
        if (id > 0) {
            manager.markAsPending(id);
        } else {
            System.out.println("Invalid ID!");
        }
    }
    
    private static void removeEventMenu() {
        System.out.println("\n=== REMOVE EVENTS MENU ===");
        System.out.println("1. Remove Specific Event");
        System.out.println("2. Remove All Completed Events");
        System.out.print("Enter your choice (1-2): ");
        
        int choice = getChoice();
        
        switch (choice) {
            case 1:
                removeSpecificEvent();
                break;
            case 2:
                System.out.print("Are you sure you want to remove all completed events? (yes/no): ");
                String confirm = scanner.nextLine().trim().toLowerCase();
                if (confirm.equals("yes") || confirm.equals("y")) {
                    manager.removeCompletedEvents();
                } else {
                    System.out.println("Operation cancelled.");
                }
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }
    
    private static void removeSpecificEvent() {
        System.out.println("\n=== REMOVE SPECIFIC EVENT ===");
        manager.viewAllEvents();
        
        if (manager.getTotalEvents() == 0) {
            return;
        }
        
        System.out.print("Enter event ID to remove: ");
        int id = getChoice();
        
        if (id > 0) {
            System.out.print("Are you sure you want to remove this event? (yes/no): ");
            String confirm = scanner.nextLine().trim().toLowerCase();
            if (confirm.equals("yes") || confirm.equals("y")) {
                manager.removeEvent(id);
            } else {
                System.out.println("Operation cancelled.");
            }
        } else {
            System.out.println("Invalid ID!");
        }
    }
    
    private static void searchEventById() {
        System.out.println("\n=== SEARCH EVENT BY ID ===");
        System.out.print("Enter event ID: ");
        int id = getChoice();
        
        if (id > 0) {
            reminderManager.EventWrapper wrapper = manager.findEventById(id);
            if (wrapper != null) {
                System.out.println("\nEvent found:");
                System.out.println(wrapper);
            } else {
                System.out.println("Event with ID " + id + " not found!");
            }
        } else {
            System.out.println("Invalid ID!");
        }
    }
    
    private static void showStatistics() {
        System.out.println("\n=== EVENT STATISTICS ===");
        System.out.println("Total Events: " + manager.getTotalEvents());
        System.out.println("Pending Events: " + manager.getPendingEventsCount());
        System.out.println("Completed Events: " + manager.getCompletedEventsCount());
        
        if (manager.getTotalEvents() > 0) {
            double completionRate = (double) manager.getCompletedEventsCount() / manager.getTotalEvents() * 100;
            System.out.println("Completion Rate: " + String.format("%.1f", completionRate) + "%");
        }
    }
}