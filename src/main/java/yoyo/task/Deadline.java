package yoyo.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import yoyo.util.Constants;
import yoyo.util.DateTimeUtil;

/**
 * Represents a deadline task with a due date and time.
 */
public class Deadline extends Task {

    private final LocalDateTime by;

    // Pretty output like: "Dec 2 2019, 18:00"
    private static final DateTimeFormatter OUTPUT_FORMAT
            = DateTimeFormatter.ofPattern(Constants.DATETIME_FORMAT_OUTPUT);

    // Stable storage like: "2019-12-02 1800"
    private static final DateTimeFormatter STORAGE_FORMAT
            = DateTimeFormatter.ofPattern(Constants.DATETIME_FORMAT_STORAGE);

    public Deadline(String description, String byRaw) {
        super(TaskType.DEADLINE, description);
        assert byRaw != null && !byRaw.trim().isEmpty() : "Deadline 'by' parameter cannot be null or empty";
        this.by = DateTimeUtil.parseFlexibleDateTime(byRaw);
    }

    /**
     * Returns the due time of the deadline.
     *
     * @return the due LocalDateTime
     */
    public LocalDateTime getBy() {
        return by;
    }

    /**
     * Returns a string representation of the deadline.
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        return baseString() + " (by: " + by.format(OUTPUT_FORMAT) + ")";
    }

    /**
     * Serializes the deadline for storage.
     *
     * @return the serialized string
     */
    @Override
    public String serialize() {
        return String.format("%c | %d | %s | %s",
                type.code(),
                isDone() ? 1 : 0, // <- changed from status.flag()
                description,
                by.format(STORAGE_FORMAT));
    }
}
