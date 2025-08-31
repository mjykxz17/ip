package yoyo;

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

<<<<<<< HEAD
<<<<<<< HEAD
    /**
     * Returns the symbol representing this status.
     *
     * @return the status symbol
     */
=======
>>>>>>> branch-A-CodingStandard
=======
>>>>>>> branch-Level-9
    public char symbol() {
        return symbol;
    }

<<<<<<< HEAD
<<<<<<< HEAD
    /**
     * Creates a Status from a boolean value.
     *
     * @param done true if done, false otherwise
     * @return the corresponding Status
     */
=======
>>>>>>> branch-A-CodingStandard
=======
>>>>>>> branch-Level-9
    public static Status fromBoolean(boolean done) {
        return done ? DONE : NOT_DONE;
    }

<<<<<<< HEAD
<<<<<<< HEAD
    /**
     * Returns the toggled status.
     *
     * @return the opposite status
     */
=======
>>>>>>> branch-A-CodingStandard
=======
>>>>>>> branch-Level-9
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

<<<<<<< HEAD
<<<<<<< HEAD
    /**
     * Returns the code representing this task type.
     *
     * @return the task type code
     */
=======
>>>>>>> branch-A-CodingStandard
=======
>>>>>>> branch-Level-9
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
        this.type = type;
        this.description = description;
        this.status = Status.NOT_DONE;
    }

<<<<<<< HEAD
<<<<<<< HEAD
    /**
     * Marks the task as done.
     */
    public void markDone() {
        this.status = Status.DONE;
    }
=======
    public void markDone() {
        this.status = Status.DONE;
=======
    public void markDone() {
        this.status = Status.DONE;
    }

    public void markUndone() {
        this.status = Status.NOT_DONE;
    }

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
>>>>>>> branch-Level-9
    }

    public void markUndone() {
        this.status = Status.NOT_DONE;
    }

    public boolean isDone() {
        return this.status == Status.DONE;
    }
>>>>>>> branch-A-CodingStandard

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
     * Returns the base string representation of the task.
     *
     * @return the base string
     */
    protected String baseString() {
        return "[" + type.code() + "][" + status.symbol() + "] " + description;
    }

    /**
<<<<<<< HEAD
     * Serializes the task for storage.
     *
     * @return the serialized string
=======
     * Declare this so subclasses (Todo/Deadline/Event) can @Override it.
>>>>>>> branch-Level-9
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
