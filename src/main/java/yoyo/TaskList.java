package yoyo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TaskList {
    private final List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(List<Task> seed) {
        this.tasks = new ArrayList<>(seed);
    }

    public int size() {
        return tasks.size();
    }

    public Task get(int idx1Based) {
        return tasks.get(idx1Based - 1);
    }

    public void add(Task t) {
        tasks.add(t);
    }

    public Task remove(int idx1Based) {
        return tasks.remove(idx1Based - 1);
    }

    public void mark(int idx1Based) {
        get(idx1Based).markDone();
    }

    public void unmark(int idx1Based) {
        get(idx1Based).markUndone();
    }

    public List<Task> asList() {
        return Collections.unmodifiableList(tasks);
    }
}
