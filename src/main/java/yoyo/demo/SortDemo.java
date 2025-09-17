package yoyo.demo;

import java.util.List;

import yoyo.task.Deadline;
import yoyo.task.Event;
import yoyo.task.Task;
import yoyo.task.TaskList;
import yoyo.task.Todo;

/**
 * Simple test to demonstrate the sorting functionality.
 */
public class SortDemo {

    public static void main(String[] args) {
        // Create a TaskList with some sample tasks
        TaskList tasks = new TaskList();

        // Add some tasks with different dates and types
        tasks.add(new Todo("Buy groceries"));
        tasks.add(new Deadline("Submit report", "2025-09-15"));
        tasks.add(new Event("Team meeting", "2025-09-12 1400", "2025-09-12 1600"));
        tasks.add(new Todo("Clean room"));
        tasks.add(new Deadline("Project deadline", "2025-09-10"));
        tasks.add(new Event("Doctor appointment", "2025-09-11 1000", "2025-09-11 1100"));

        System.out.println("Original order:");
        printTasks(tasks.asList());

        // Sort by date
        System.out.println("\nSorted by date (ascending):");
        tasks.sort("date", true);
        printTasks(tasks.asList());

        // Sort by description
        System.out.println("\nSorted by description (ascending):");
        tasks.sort("description", true);
        printTasks(tasks.asList());

        // Sort by type
        System.out.println("\nSorted by type (ascending):");
        tasks.sort("type", true);
        printTasks(tasks.asList());

        // Sort by status
        System.out.println("\nSorted by status (ascending):");
        tasks.sort("status", true);
        printTasks(tasks.asList());
    }

    private static void printTasks(List<Task> taskList) {
        for (int i = 0; i < taskList.size(); i++) {
            System.out.println((i + 1) + ". " + taskList.get(i));
        }
    }
}
