package yoyo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
        this.from = parseFlexibleDateTime(fromRaw);
        this.to = parseFlexibleDateTime(toRaw);
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

    /**
     * Parses a flexible date/time string into a LocalDateTime. Supports various
     * formats like yyyy-MM-dd, d/M/yyyy, with optional time.
     *
     * @param raw the raw date/time string
     * @return the parsed LocalDateTime
     * @throws IllegalArgumentException if parsing fails
     */
    private static LocalDateTime parseFlexibleDateTime(String raw) {
        String s = raw.trim();

        // Try datetime patterns first
        for (DateTimeFormatter f : new DateTimeFormatter[]{
            DateTimeFormatter.ofPattern(Constants.DATETIME_FORMAT_STORAGE),
            DateTimeFormatter.ofPattern(Constants.DATE_FORMAT_SHORT + " HHmm")
        }) {
            try {
                return LocalDateTime.parse(s, f);
            } catch (DateTimeParseException ignored) {
            }
        }

        // Then date-only (assume 00:00)
        for (DateTimeFormatter f : new DateTimeFormatter[]{
            DateTimeFormatter.ISO_LOCAL_DATE, // yyyy-MM-dd
            DateTimeFormatter.ofPattern(Constants.DATE_FORMAT_SHORT) // 2/12/2019
        }) {
            try {
                LocalDate d = LocalDate.parse(s, f);
                return LocalDateTime.of(d, LocalTime.MIDNIGHT);
            } catch (DateTimeParseException ignored) {
            }
        }

        throw new IllegalArgumentException(
                String.format(Constants.ERR_UNRECOGNIZED_DATE, raw));
    }
}
