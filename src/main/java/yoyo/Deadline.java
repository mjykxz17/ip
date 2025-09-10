package yoyo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Optional;

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

        // Try datetime patterns first using streams
        Optional<LocalDateTime> dateTimeResult = Arrays.stream(new DateTimeFormatter[]{
            DateTimeFormatter.ofPattern(Constants.DATETIME_FORMAT_STORAGE),
            DateTimeFormatter.ofPattern(Constants.DATE_FORMAT_SHORT + " HHmm")
        })
        .map(formatter -> {
            try {
                return LocalDateTime.parse(s, formatter);
            } catch (DateTimeParseException e) {
                return null;
            }
        })
        .filter(result -> result != null)
        .findFirst();

        if (dateTimeResult.isPresent()) {
            return dateTimeResult.get();
        }

        // Then try date-only (assume 00:00 time) using streams
        Optional<LocalDateTime> dateResult = Arrays.stream(new DateTimeFormatter[]{
            DateTimeFormatter.ISO_LOCAL_DATE, // yyyy-MM-dd
            DateTimeFormatter.ofPattern(Constants.DATE_FORMAT_SHORT) // 2/12/2019
        })
        .map(formatter -> {
            try {
                LocalDate d = LocalDate.parse(s, formatter);
                return LocalDateTime.of(d, LocalTime.MIDNIGHT);
            } catch (DateTimeParseException e) {
                return null;
            }
        })
        .filter(result -> result != null)
        .findFirst();

        if (dateResult.isPresent()) {
            return dateResult.get();
        }

        // Fallback: let the exception explain the expected formats
        throw new IllegalArgumentException(
                String.format(Constants.ERR_UNRECOGNIZED_DATE, raw));
    }
}
