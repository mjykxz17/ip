package yoyo.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Optional;

/**
 * Utility class for parsing flexible date/time strings.
 */
public final class DateTimeUtil {

    private DateTimeUtil() {
        // Utility class
    }

    /**
     * Parses a flexible date/time string into a LocalDateTime. Supports various
     * formats like yyyy-MM-dd, d/M/yyyy, with optional time.
     *
     * @param raw the raw date/time string
     * @return the parsed LocalDateTime
     * @throws IllegalArgumentException if parsing fails
     */
    public static LocalDateTime parseFlexibleDateTime(String raw) {
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
