// Event.java
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task {
    private final LocalDateTime from;
    private final LocalDateTime to;

    // Pretty output (e.g., "Dec 2 2019, 14:00")
    private static final DateTimeFormatter OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("MMM d yyyy, HH:mm");

    // Stable storage (e.g., "2019-12-02 1400")
    private static final DateTimeFormatter STORAGE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    public Event(String description, String fromRaw, String toRaw) {
        super(TaskType.EVENT, description);
        this.from = parseFlexibleDateTime(fromRaw);
        this.to = parseFlexibleDateTime(toRaw);
        if (this.to.isBefore(this.from)) {
            throw new IllegalArgumentException("Event end time must be after start time.");
        }
    }

    public LocalDateTime getFrom() { return from; }
    public LocalDateTime getTo()   { return to;   }

    @Override
    public String toString() {
        return baseString() + " (from: " + from.format(OUTPUT_FORMAT)
                + " to: " + to.format(OUTPUT_FORMAT) + ")";
    }

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

    // ---- parsing helpers (accept multiple formats) ----
    private static LocalDateTime parseFlexibleDateTime(String raw) {
        String s = raw.trim();

        // Try datetime patterns first
        for (DateTimeFormatter f : new DateTimeFormatter[] {
                DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"),
                DateTimeFormatter.ofPattern("d/M/yyyy HHmm")
        }) {
            try { return LocalDateTime.parse(s, f); } catch (DateTimeParseException ignored) {}
        }

        // Then date-only (assume 00:00)
        for (DateTimeFormatter f : new DateTimeFormatter[] {
                DateTimeFormatter.ISO_LOCAL_DATE,               // yyyy-MM-dd
                DateTimeFormatter.ofPattern("d/M/yyyy")         // 2/12/2019
        }) {
            try {
                LocalDate d = LocalDate.parse(s, f);
                return LocalDateTime.of(d, LocalTime.MIDNIGHT);
            } catch (DateTimeParseException ignored) {}
        }

        throw new IllegalArgumentException(
            "Unrecognized date/time: \"" + raw +
            "\". Use yyyy-MM-dd or d/M/yyyy, optionally with time HHmm.");
    }
}
