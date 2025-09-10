package yoyo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a deadline task with a due date and time.
 */
public class Deadline extends Task {

    private final LocalDateTime by;

    // Pretty output like: "Dec 2 2019, 18:00"
    private static final DateTimeFormatter OUTPUT_FORMAT
            = DateTimeFormatter.ofPattern("MMM d yyyy, HH:mm");

    // Stable storage like: "2019-12-02 1800"
    private static final DateTimeFormatter STORAGE_FORMAT
            = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    public Deadline(String description, String byRaw) {
        super(TaskType.DEADLINE, description);
        assert byRaw != null && !byRaw.trim().isEmpty() : "Deadline 'by' parameter cannot be null or empty";
        this.by = parseFlexibleDateTime(byRaw);
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
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"),
            DateTimeFormatter.ofPattern("d/M/yyyy HHmm")
        }) {
            try {
                return LocalDateTime.parse(s, f);
            } catch (DateTimeParseException ignored) {
            }
        }

        // Then try date-only (assume 00:00 time)
        for (DateTimeFormatter f : new DateTimeFormatter[]{
            DateTimeFormatter.ISO_LOCAL_DATE, // yyyy-MM-dd
            DateTimeFormatter.ofPattern("d/M/yyyy") // 2/12/2019
        }) {
            try {
                LocalDate d = LocalDate.parse(s, f);
                return LocalDateTime.of(d, LocalTime.MIDNIGHT);
            } catch (DateTimeParseException ignored) {
            }
        }

        // Fallback: let the exception explain the expected formats
        throw new IllegalArgumentException(
                "Unrecognized date/time: \"" + raw
                + "\". Use yyyy-MM-dd or d/M/yyyy, optionally with time HHmm.");
    }
}
