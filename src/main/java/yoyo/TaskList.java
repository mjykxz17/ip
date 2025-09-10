package yoyo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manages a list of tasks, providing operations to add, remove, mark, and
 * retrieve tasks.
 */
public class TaskList {

    private final List<Task> tasks;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a TaskList seeded with the given list of tasks.
     *
     * @param seed the initial list of tasks
     */
    public TaskList(List<Task> seed) {
        assert seed != null : "Seed list cannot be null";
        this.tasks = new ArrayList<>(seed);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return the size of the task list
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Retrieves the task at the specified 1-based index.
     *
     * @param idx1Based the 1-based index of the task
     * @return the task at the index
     */
    public Task get(int idx1Based) {
        assert idx1Based >= 1 && idx1Based <= tasks.size() : "Index must be between 1 and " + tasks.size() + ", got: " + idx1Based;
        return tasks.get(toZeroBasedIndex(idx1Based));
    }

    /**
     * Adds a task to the list.
     *
     * @param t the task to add
     */
    public void add(Task t) {
        tasks.add(t);
    }

    /**
     * Removes the task at the specified 1-based index.
     *
     * @param idx1Based the 1-based index of the task to remove
     * @return the removed task
     */
    public Task remove(int idx1Based) {
        assert idx1Based >= 1 && idx1Based <= tasks.size() : "Index must be between 1 and " + tasks.size() + ", got: " + idx1Based;
        return tasks.remove(toZeroBasedIndex(idx1Based));
    }

    /**
     * Marks the task at the specified 1-based index as done.
     *
     * @param idx1Based the 1-based index of the task
     */
    public void mark(int idx1Based) {
        assert idx1Based >= 1 && idx1Based <= tasks.size() : "Index must be between 1 and " + tasks.size() + ", got: " + idx1Based;
        get(idx1Based).markDone();
    }

    /**
     * Marks the task at the specified 1-based index as not done.
     *
     * @param idx1Based the 1-based index of the task
     */
    public void unmark(int idx1Based) {
        assert idx1Based >= 1 && idx1Based <= tasks.size() : "Index must be between 1 and " + tasks.size() + ", got: " + idx1Based;
        get(idx1Based).markUndone();
    }

    /**
     * Returns an unmodifiable view of the task list.
     *
     * @return the list of tasks
     */
    public List<Task> asList() {
        return Collections.unmodifiableList(tasks);
    }

    /**
     * Finds tasks that contain the given keyword in their description.
     *
     * @param keyword the keyword to search for
     * @return the list of matching tasks
     */
    public List<Task> find(String keyword) {
        assert keyword != null && !keyword.trim().isEmpty() : "Search keyword cannot be null or empty";
        List<Task> matching = new ArrayList<>();
        for (Task t : tasks) {
            if (t.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                matching.add(t);
            }
        }
        return matching;
    }

    /**
     * Converts a 1-based index to a 0-based index.
     *
     * @param oneBasedIndex the 1-based index
     * @return the 0-based index
     */
    private int toZeroBasedIndex(int oneBasedIndex) {
        return oneBasedIndex - 1;
    }
}
