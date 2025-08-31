package yoyo;

enum Status {
    NOT_DONE(' '),
    DONE('X');

    private final char symbol;

    Status(char symbol) {
        this.symbol = symbol;
    }

    public char symbol() {
        return symbol;
    }

    public static Status fromBoolean(boolean done) {
        return done ? DONE : NOT_DONE;
    }

    public Status toggled() {
        return this == DONE ? NOT_DONE : DONE;
    }
}

enum TaskType {
    TODO('T'),
    DEADLINE('D'),
    EVENT('E');

    private final char code;

    TaskType(char code) {
        this.code = code;
    }

    public char code() {
        return code;
    }
}

public abstract class Task {

    protected final TaskType type;
    protected final String description;
    protected Status status;

    protected Task(TaskType type, String description) {
        this.type = type;
        this.description = description;
        this.status = Status.NOT_DONE;
    }

    public void markDone() {
        this.status = Status.DONE;
    }

    public void markUndone() {
        this.status = Status.NOT_DONE;
    }

    public boolean isDone() {
        return this.status == Status.DONE;
    }

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
