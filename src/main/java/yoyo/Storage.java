package yoyo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Storage {
    public static final String DEFAULT_PATH = "data/yoyo.txt";

    private static final Pattern TODO_P =
            Pattern.compile("^\\[T]\\[( |X)]\\s(.+)$");
    private static final Pattern DEADLINE_P =
            Pattern.compile("^\\[D]\\[( |X)]\\s(.+)\\s\\(by:\\s(.+)\\)$");
    private static final Pattern EVENT_P =
            Pattern.compile("^\\[E]\\[( |X)]\\s(.+)\\s\\(from:\\s(.+)\\s+to:\\s(.+)\\)$");

    private final Path dataFile;
    private final Path dataDir;

    public Storage(String relativePath) {
        this.dataFile = Paths.get(relativePath);
        this.dataDir = dataFile.getParent() == null ? Paths.get(".") : dataFile.getParent();
    }

    /** Loads tasks from disk. Skips corrupted lines and returns warnings. */
    public LoadResult load() {
        List<Task> tasks = new ArrayList<>();
        List<String> warnings = new ArrayList<>();

        if (!Files.exists(dataFile)) {
            return new LoadResult(tasks, warnings); // first run; nothing to load
        }

        try {
            List<String> lines = Files.readAllLines(dataFile, StandardCharsets.UTF_8);
            int lineNo = 0;
            for (String raw : lines) {
                lineNo++;
                String line = raw.trim();
                if (line.isEmpty()) continue;

                try {
                    Task t = parseLine(line);
                    tasks.add(t);
                } catch (IllegalArgumentException ex) {
                    warnings.add("Line " + lineNo + " skipped: " + ex.getMessage());
                }
            }
        } catch (IOException e) {
            warnings.add("Failed to read file: " + e.getMessage());
        }

        return new LoadResult(tasks, warnings);
    }

    /** Saves the current tasks to disk (creates folder if missing). */
    public void save(List<Task> tasks) throws IOException {
        if (!Files.exists(dataDir)) {
            Files.createDirectories(dataDir);
        }
        List<String> out = new ArrayList<>();
        for (Task t : tasks) {
            // Persist exactly as shown in list output (stable & human-readable)
            out.add(t.toString());
        }
        Files.write(dataFile, out, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    private static Task parseLine(String line) {
        Matcher m;

        m = TODO_P.matcher(line);
        if (m.matches()) {
            boolean done = m.group(1).equals("X");
            String desc = m.group(2);
            Task t = new Todo(desc);
            if (done) t.markDone();
            return t;
        }

        m = DEADLINE_P.matcher(line);
        if (m.matches()) {
            boolean done = m.group(1).equals("X");
            String desc = m.group(2);
            String by = m.group(3);
            Task t = new Deadline(desc, by);
            if (done) t.markDone();
            return t;
        }

        m = EVENT_P.matcher(line);
        if (m.matches()) {
            boolean done = m.group(1).equals("X");
            String desc = m.group(2);
            String from = m.group(3);
            String to = m.group(4);
            Task t = new Event(desc, from, to);
            if (done) t.markDone();
            return t;
        }

        throw new IllegalArgumentException("Unrecognized format: " + line);
    }

    // -------- Helper type for returning both tasks and warnings --------
    public static class LoadResult {
        public final List<Task> tasks;
        public final List<String> warnings;
        public LoadResult(List<Task> tasks, List<String> warnings) {
            this.tasks = tasks;
            this.warnings = warnings;
        }
    }
}
