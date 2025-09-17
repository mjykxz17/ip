package yoyo.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import yoyo.util.Constants;
import yoyo.util.DateTimeUtil;

/**
 * Represents an event task with a start and end time.
 */
public class Event extends Task {

    private final LocalDateTime from;
    private final LocalDateTime to;

    // Pretty output (e.g., "Dec 2 2019, 14:00")
    private static final DateTimeFormatter OUTPUT_FORMAT
            = DateTimeFormatter.ofPattern(Constants.DATETIME_FORMAT_OUTPUT);

    // Stable storage (e.g., "2019-12-02 1400")
    private static final DateTimeFormatter STORAGE_FORMAT
            = DateTimeFormatter.ofPattern(Constants.DATETIME_FORMAT_STORAGE);

    public Event(String description, String fromRaw, String toRaw) {
        super(TaskType.EVENT, description);
        assert fromRaw != null && !fromRaw.trim().isEmpty() : "Event 'from' parameter cannot be null or empty";
        assert toRaw != null && !toRaw.trim().isEmpty() : "Event 'to' parameter cannot be null or empty";
        this.from = DateTimeUtil.parseFlexibleDateTime(fromRaw);
        this.to = DateTimeUtil.parseFlexibleDateTime(toRaw);
        if (this.to.isBefore(this.from)) {
            throw new IllegalArgumentException("Event end time must be after start time.");
        }
    }

    /**
     * Returns the start time of the event.
     *
     * @return the start LocalDateTime
     */
    public LocalDateTime getFrom() {
        return from;
    }

    /**
     * Returns the end time of the event.
     *
     * @return the end LocalDateTime
     */
    public LocalDateTime getTo() {
        return to;
    }

    /**
     * Returns a string representation of the event.
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        return baseString() + " (from: " + from.format(OUTPUT_FORMAT)
                + " to: " + to.format(OUTPUT_FORMAT) + ")";
    }

    /**
     * Serializes the event for storage.
     *
     * @return the serialized string
     */
    @Override
    public String serialize() {
        // Pipe format: E | 0/1 | description | yyyy-MM-dd HHmm | yyyy-MM-dd HHmm
        return String.format("%c | %d | %s | %s | %s",
                type.code(),
                isDone() ? 1 : 0,
                description,
                from.format(STORAGE_FORMAT),
                to.format(STORAGE_FORMAT));
    }
}
