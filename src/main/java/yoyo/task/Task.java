package yoyo.task;

/**
 * Represents the completion status of a task.
 */
enum Status {
    /**
     * Task is not done.
     */
    NOT_DONE(' '),
    /**
     * Task is done.
     */
    DONE('X');

    private final char symbol;

    Status(char symbol) {
        this.symbol = symbol;
    }

    /**
     * Returns the symbol representing this status.
     *
     * @return the status symbol
     */
    public char symbol() {
        return symbol;
    }

    /**
     * Creates a Status from a boolean value.
     *
     * @param done true if done, false otherwise
     * @return the corresponding Status
     */
    public static Status fromBoolean(boolean done) {
        return done ? DONE : NOT_DONE;
    }

    /**
     * Returns the toggled status.
     *
     * @return the opposite status
     */
    public Status toggled() {
        return this == DONE ? NOT_DONE : DONE;
    }
}

/**
 * Represents the type of a task.
 */
enum TaskType {
    /**
     * Todo task type.
     */
    TODO('T'),
    /**
     * Deadline task type.
     */
    DEADLINE('D'),
    /**
     * Event task type.
     */
    EVENT('E');

    private final char code;

    TaskType(char code) {
        this.code = code;
    }

    /**
     * Returns the code representing this task type.
     *
     * @return the task type code
     */
    public char code() {
        return code;
    }
}

/**
 * Abstract base class for all task types. Provides common functionality for
 * tasks such as marking done/undone and serialization.
 */
public abstract class Task {

    protected final TaskType type;
    protected final String description;
    protected Status status;

    /**
     * Constructs a new Task with the given type and description.
     *
     * @param type the type of the task
     * @param description the description of the task
     */
    protected Task(TaskType type, String description) {
        assert type != null : "Task type cannot be null";
        assert description != null && !description.trim().isEmpty() : "Task description cannot be null or empty";
        this.type = type;
        this.description = description;
        this.status = Status.NOT_DONE;
    }

    /**
     * Marks the task as done.
     */
    public void markDone() {
        this.status = Status.DONE;
    }

    /**
     * Marks the task as not done.
     */
    public void markUndone() {
        this.status = Status.NOT_DONE;
    }

    /**
     * Checks if the task is done.
     *
     * @return true if the task is done, false otherwise
     */
    public boolean isDone() {
        return this.status == Status.DONE;
    }

    /**
     * Returns the description of the task.
     *
     * @return the task description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the task type.
     *
     * @return the task type
     */
    public TaskType getType() {
        return type;
    }

    /**
     * Returns a comparable date/time for sorting purposes. For deadlines,
     * returns the due date. For events, returns the start date. For todos,
     * returns null (they don't have dates).
     *
     * @return the LocalDateTime for sorting, or null if not applicable
     */
    public java.time.LocalDateTime getSortDateTime() {
        if (this instanceof Deadline) {
            return ((Deadline) this).getBy();
        } else if (this instanceof Event) {
            return ((Event) this).getFrom();
        }
        return null; // Todos don't have dates
    }

    /**
     * Returns the base string representation of the task.
     *
     * @return the base string
     */
    protected String baseString() {
        return "[" + type.code() + "][" + status.symbol() + "] " + description;
    }

    /**
     * Serializes the task for storage.
     *
     * @return the serialized string
     */
    public abstract String serialize();

    /**
     * Returns a string representation of the task.
     *
     * @return the string representation
     */
    @Override
    public abstract String toString();
}
