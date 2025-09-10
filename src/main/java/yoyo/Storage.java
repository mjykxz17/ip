package yoyo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Handles loading and saving of tasks to and from a file. Supports parsing
 * different task types and handles corrupted data gracefully.
 */
public class Storage {

    public static final String DEFAULT_PATH = "data/yoyo.txt";

    private static final Pattern TODO_P
            = Pattern.compile("^\\[T]\\[( |X)]\\s(.+)$");
    private static final Pattern DEADLINE_P
            = Pattern.compile("^\\[D]\\[( |X)]\\s(.+)\\s\\(by:\\s(.+)\\)$");
    private static final Pattern EVENT_P
            = Pattern.compile("^\\[E]\\[( |X)]\\s(.+)\\s\\(from:\\s(.+)\\s+to:\\s(.+)\\)$");

    private final Path dataFile;
    private final Path dataDir;

    /**
     * Constructs a new Storage instance with the given relative path.
     *
     * @param relativePath the relative path to the data file
     */
    public Storage(String relativePath) {
        assert relativePath != null && !relativePath.trim().isEmpty() : "Relative path cannot be null or empty";
        this.dataFile = Paths.get(relativePath);
        this.dataDir = dataFile.getParent() == null ? Paths.get(".") : dataFile.getParent();
    }

    /**
     * Loads tasks from the data file. Skips corrupted lines and collects
     * warnings.
     *
     * @return a LoadResult containing the loaded tasks and any warnings
     */
    public LoadResult load() {
        List<Task> tasks = new ArrayList<>();
        List<String> warnings = new ArrayList<>();

        if (!Files.exists(dataFile)) {
            return new LoadResult(tasks, warnings); // first run; nothing to load
        }

        try {
            List<String> lines = Files.readAllLines(dataFile, StandardCharsets.UTF_8);
            
            // Use streams to process lines with line numbers
            List<Task> parsedTasks = IntStream.range(0, lines.size())
                    .mapToObj(i -> new Object() {
                        final int lineNo = i + 1;
                        final String line = lines.get(i).trim();
                    })
                    .filter(obj -> !obj.line.isEmpty())
                    .map(obj -> {
                        try {
                            return parseLine(obj.line);
                        } catch (IllegalArgumentException ex) {
                            warnings.add("Line " + obj.lineNo + " skipped: " + ex.getMessage());
                            return null;
                        }
                    })
                    .filter(task -> task != null)
                    .collect(Collectors.toList());
            
            tasks.addAll(parsedTasks);
        } catch (IOException e) {
            warnings.add("Failed to read file: " + e.getMessage());
        }

        return new LoadResult(tasks, warnings);
    }

    /**
     * Saves the current tasks to disk (creates folder if missing).
     *
     * @param tasks the list of tasks to save
     * @throws IOException if an I/O error occurs
     */
    public void save(List<Task> tasks) throws IOException {
        assert tasks != null : "Tasks list cannot be null";
        if (!Files.exists(dataDir)) {
            Files.createDirectories(dataDir);
        }
        List<String> out = tasks.stream()
                .map(Task::toString)
                .collect(java.util.stream.Collectors.toList());
        Files.write(dataFile, out, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    /**
     * Parses a line from the data file into a Task object.
     *
     * @param line the line to parse
     * @return the parsed Task
     * @throws IllegalArgumentException if the line format is unrecognized
     */
    private static Task parseLine(String line) {
        assert line != null && !line.trim().isEmpty() : "Line to parse cannot be null or empty";
        Matcher m;

        m = TODO_P.matcher(line);
        if (m.matches()) {
            boolean done = m.group(1).equals("X");
            String desc = m.group(2);
            assert desc != null && !desc.trim().isEmpty() : "Todo description cannot be null or empty";
            Task t = new Todo(desc);
            if (done) {
                t.markDone();
            }
            return t;
        }

        m = DEADLINE_P.matcher(line);
        if (m.matches()) {
            boolean done = m.group(1).equals("X");
            String desc = m.group(2);
            String by = m.group(3);
            assert desc != null && !desc.trim().isEmpty() : "Deadline description cannot be null or empty";
            assert by != null && !by.trim().isEmpty() : "Deadline 'by' date cannot be null or empty";
            Task t = new Deadline(desc, by);
            if (done) {
                t.markDone();
            }
            return t;
        }

        m = EVENT_P.matcher(line);
        if (m.matches()) {
            boolean done = m.group(1).equals("X");
            String desc = m.group(2);
            String from = m.group(3);
            String to = m.group(4);
            assert desc != null && !desc.trim().isEmpty() : "Event description cannot be null or empty";
            assert from != null && !from.trim().isEmpty() : "Event 'from' time cannot be null or empty";
            assert to != null && !to.trim().isEmpty() : "Event 'to' time cannot be null or empty";
            Task t = new Event(desc, from, to);
            if (done) {
                t.markDone();
            }
            return t;
        }

        throw new IllegalArgumentException("Unrecognized format: " + line);
    }

    // -------- Helper type for returning both tasks and warnings --------
    /**
     * Result of loading tasks from storage, containing the tasks and any
     * warnings.
     */
    public static class LoadResult {

        /**
         * The list of loaded tasks.
         */
        public final List<Task> tasks;
        /**
         * The list of warning messages for corrupted lines.
         */
        public final List<String> warnings;

        /**
         * Constructs a LoadResult with the given tasks and warnings.
         *
         * @param tasks the loaded tasks
         * @param warnings the warning messages
         */
        public LoadResult(List<Task> tasks, List<String> warnings) {
            this.tasks = tasks;
            this.warnings = warnings;
        }
    }
}
