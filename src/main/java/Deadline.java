// Deadline.java
public class Deadline extends Task {
    private final String by; // keep as raw text for Level 6 formatting

    public Deadline(String description, String by) {
        super(TaskType.DEADLINE, description);
        this.by = by;
    }

    @Override
    public String toString() {
        return baseString() + " (by: " + by + ")";
    }
}
