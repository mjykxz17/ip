import java.util.Scanner;

public class Yoyo {

    /* ===== Single lightweight exception kept inside Yoyo (only one class changed) ===== */
    private static class YoyoException extends Exception {
        public YoyoException(String msg) { super(msg); }
    }

    private static final String LINE = "____________________________________________________________";

    private static void boxed(String... lines) {
        System.out.println(LINE);
        for (String s : lines) {
            System.out.println(" " + s);
        }
        System.out.println(LINE);
    }

    private static void boxedAdded(Task t, int count) {
        boxed(
            "Got it. I've added this task:",
            "  " + t.toString(),
            "Now you have " + count + " tasks in the list."
        );
    }

    private static void showList(Task[] tasks, int size) {
        if (size == 0) {
            boxed("(no tasks yet)");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Here are the tasks in your list:\n");
        for (int i = 0; i < size; i++) {
            sb.append(" ").append(i + 1).append(".")
              .append(tasks[i].toString())
              .append("\n");
        }
        boxed(sb.toString().trim().split("\n"));
    }

    // ===== Helpers that throw YoyoException to centralise error handling =====
    private static void requireCapacity(int size) throws YoyoException {
        if (size >= 100) throw new YoyoException("Task list is full (100 items).");
    }

    private static int parseIndex(String arg, int size) throws YoyoException {
        if (arg == null || arg.isEmpty()) throw new YoyoException("Usage: mark <taskNumber> or unmark <taskNumber>.");
        int idx;
        try {
            idx = Integer.parseInt(arg.trim());
        } catch (NumberFormatException e) {
            throw new YoyoException("Task number must be an integer.");
        }
        if (idx < 1 || idx > size) throw new YoyoException("Invalid task number: " + idx);
        return idx;
    }

    private static String[] splitOnce(String src, String token, String usageIfMissing) throws YoyoException {
        String[] parts = src.split(token, 2);
        if (parts.length < 2) throw new YoyoException(usageIfMissing);
        String left = parts[0].trim(), right = parts[1].trim();
        if (left.isEmpty() || right.isEmpty()) throw new YoyoException(usageIfMissing);
        return new String[]{left, right};
    }

    public static void main(String[] args) {
        String logo = """
 ___                    __   __                
|_ _|   __ _ _ __ ___   \\ \\ / /__  _   _  ___  
 | |   / _` | '_ ` _ \\   \\ V / _ \\| | | |/ _ \\ 
 | |  | (_| | | | | | |   | | (_) | |_| | (_) |
|___|  \\__,_|_| |_| |_|   |_|\\___/ \\__, |\\___/ 
                                   |___/       
                """;

        System.out.println("Hello from\n" + logo);
        System.out.println("WHAT IS UP, my g");

        Scanner sc = new Scanner(System.in);

        // Storage for up to 100 Task objects (polymorphism)
        Task[] tasks = new Task[100];
        int size = 0;

        while (true) {
            String input = sc.nextLine().trim();
            if (input.isEmpty()) {
                continue;
            }

            try {
                String[] parts = input.split("\\s+", 2);
                String cmd = parts[0].toLowerCase();
                String argsRest = (parts.length > 1) ? parts[1].trim() : "";

                switch (cmd) {
                    case "bye" -> {
                        boxed("Bye. Hope to see you again soon!");
                        sc.close();
                        return;
                    }

                    case "list" ->  {
                        showList(tasks, size);
                    }

                    case "mark" ->  {
                        int idx = parseIndex(argsRest, size);
                        tasks[idx - 1].markAsDone();
                        boxed(
                            "Nice! I've marked this task as done:",
                            "  " + tasks[idx - 1].toString()
                        );
                    }

                    case "unmark" ->  {
                        int idx = parseIndex(argsRest, size);
                        tasks[idx - 1].markAsUndone();
                        boxed(
                            "OK, I've marked this task as not done yet:",
                            "  " + tasks[idx - 1].toString()
                        );
                    }

                    case "todo" ->  {
                        if (argsRest.isEmpty()) {
                            throw new YoyoException("A todo needs a description.\nHint: todo <description>");
                        }
                        requireCapacity(size);
                        Task t = new Todo(argsRest);
                        tasks[size++] = t;
                        boxedAdded(t, size);
                    }

                    case "deadline" ->  {
                        if (argsRest.isEmpty()) {
                            throw new YoyoException("Usage: deadline <description> /by <when>");
                        }
                        String[] ab = splitOnce(argsRest, "/by", "Usage: deadline <description> /by <when>");
                        requireCapacity(size);
                        Task t = new Deadline(ab[0], ab[1]);
                        tasks[size++] = t;
                        boxedAdded(t, size);
                    }

                    case "event" ->  {
                        if (argsRest.isEmpty()) {
                            throw new YoyoException("Usage: event <description> /from <start> /to <end>");
                        }
                        String[] p1 = splitOnce(argsRest, "/from", "Usage: event <description> /from <start> /to <end>");
                        String[] p2 = splitOnce(p1[1], "/to",   "Usage: event <description> /from <start> /to <end>");
                        requireCapacity(size);
                        Task t = new Event(p1[0], p2[0], p2[1]);
                        tasks[size++] = t;
                        boxedAdded(t, size);
                    }

                    default -> {
                        // Reject anything else (e.g., "123", "blah") as invalid
                        throw new YoyoException(
                            "Invalid command: '" + cmd + "'. Try one of:\n" +
                            "  todo <description>\n" +
                            "  deadline <description> /by <when>\n" +
                            "  event <description> /from <start> /to <end>\n" +
                            "  list | mark <n> | unmark <n> | bye"
                        );
                    }
                }

            } catch (YoyoException e) {
                boxed("Oops! " + e.getMessage());
            }
        }
    }
}
 