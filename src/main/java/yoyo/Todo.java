package yoyo;

/**
 * Represents a simple todo task without any deadline or time constraints.
 */
public class Todo extends Task {

    /**
     * Constructs a new Todo task with the given description.
     *
     * @param description the description of the todo task
     */
    public Todo(String description) {
        super(TaskType.TODO, description);
    }

    /**
     * Returns a string representation of the todo task.
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        return baseString();
    }

    /**
     * Serializes the todo task for storage.
     *
     * @return the serialized string
     */
    @Override
    public String serialize() {
        return String.format("%c | %d | %s", type.code(), isDone() ? 1 : 0, description);
    }
}
