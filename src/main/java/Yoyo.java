
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

enum Command {
    LIST, TODO, DEADLINE, EVENT, MARK, UNMARK, DELETE, BYE, HELP;

    static Command parse(String raw) {
        return switch (raw.toLowerCase()) {
            case "list" ->
                LIST;
            case "todo" ->
                TODO;
            case "deadline" ->
                DEADLINE;
            case "event" ->
                EVENT;
            case "mark" ->
                MARK;
            case "unmark" ->
                UNMARK;
            case "delete" ->
                DELETE;
            case "bye", "exit", "quit" ->
                BYE;
            case "help" ->
                HELP;
            default ->
                throw new IllegalArgumentException("Unknown command: " + raw);
        };
    }
}

public class Yoyo {

    private static final String LINE = "____________________________________________________________";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // NEW: persistence
        Storage storage = new Storage(Storage.DEFAULT_PATH);
        Storage.LoadResult loaded = storage.load();
        List<Task> tasks = new ArrayList<>(loaded.tasks);

        greet();
        if (!loaded.warnings.isEmpty()) {
            boxed("Note: some saved lines were skipped as corrupted:");
            for (String w : loaded.warnings) {
                boxed(" - " + w);
            }
        }

        while (true) {
            if (!sc.hasNextLine()) {
                break;
            }
            String input = sc.nextLine().trim();
            if (input.isEmpty()) {
                continue;
            }

            try {
                String[] parts = input.split("\\s+", 2);
                String cmdRaw = parts[0];
                String rest = parts.length > 1 ? parts[1] : "";
                Command cmd = Command.parse(cmdRaw);

                switch (cmd) {
                    case LIST ->
                        showList(tasks);
                    case HELP ->
                        showHelp();

                    case TODO -> {
                        if (rest.isEmpty()) {
                            throw new YoyoException("A todo needs a description.\nHint: todo <description>");
                        }
                        Task t = new Todo(rest);
                        tasks.add(t);
                        boxedAdded(t, tasks.size());
                        storage.save(tasks); // NEW
                    }

                    case DEADLINE -> {
                        if (!rest.contains("/by")) {
                            throw new YoyoException("Usage: deadline <description> /by <deadline text>");
                        }
                        String[] seg = rest.split("/by", 2);
                        String desc = seg[0].trim();
                        String by = seg[1].trim();
                        if (desc.isEmpty() || by.isEmpty()) {
                            throw new YoyoException("Usage: deadline <description> /by <deadline text>");
                        }
                        Task t = new Deadline(desc, by);
                        tasks.add(t);
                        boxedAdded(t, tasks.size());
                        storage.save(tasks); // NEW
                    }

                    case EVENT -> {
                        if (!rest.contains("/from") || !rest.contains("/to")) {
                            throw new YoyoException("Usage: event <description> /from <start> /to <end>");
                        }
                        String[] first = rest.split("/from", 2);
                        String desc = first[0].trim();
                        String[] second = first[1].split("/to", 2);
                        String from = second[0].trim();
                        String to = second[1].trim();
                        if (desc.isEmpty() || from.isEmpty() || to.isEmpty()) {
                            throw new YoyoException("Usage: event <description> /from <start> /to <end>");
                        }
                        Task t = new Event(desc, from, to);
                        tasks.add(t);
                        boxedAdded(t, tasks.size());
                        storage.save(tasks); // NEW
                    }

                    case MARK -> {
                        int idx = parseIndex(rest, tasks.size());
                        Task t = tasks.get(idx - 1);
                        t.markDone();
                        boxed("Nice! I've marked this task as done:", "  " + t.toString());
                        storage.save(tasks); // NEW
                    }

                    case UNMARK -> {
                        int idx = parseIndex(rest, tasks.size());
                        Task t = tasks.get(idx - 1);
                        t.markUndone();
                        boxed("OK, I've marked this task as not done yet:", "  " + t.toString());
                        storage.save(tasks); // NEW
                    }

                    case DELETE -> {
                        int idx = parseIndex(rest, tasks.size());
                        Task removed = tasks.remove(idx - 1);
                        boxed(
                                "Noted. I've removed this task:",
                                "  " + removed.toString(),
                                "Now you have " + tasks.size() + " tasks in the list."
                        );
                        storage.save(tasks); // NEW
                    }

                    case BYE -> {
                        boxed("Bye. Hope to see you again soon!");
                        return;
                    }
                }
            } catch (YoyoException e) {
                boxed(e.getMessage());
            } catch (IllegalArgumentException e) {
                boxed("OOPS!!! I'm sorry, but I don't know what that means :-(",
                        "Type 'help' to see commands.");
            } catch (Exception e) {
                boxed("Unexpected error: " + e.getMessage());
            }
        }
    }

    private static void greet() {
        boxed("Hello! I'm Yoyo", "What can I do for you?");
    }

    private static void showHelp() {
        boxed(
                "Commands:",
                "  list",
                "  todo <description>",
                "  deadline <description> /by <deadline text>",
                "  event <description> /from <start> /to <end>",
                "  mark <taskNumber> | unmark <taskNumber> | delete <taskNumber>",
                "  bye"
        );
    }

    private static void showList(List<Task> tasks) {
        if (tasks.isEmpty()) {
            boxed("(no tasks yet)");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(" ").append(i + 1).append(".")
                    .append(tasks.get(i).toString())
                    .append("\n");
        }
        boxed(sb.toString().trim().split("\\R"));
    }

    private static int parseIndex(String arg, int size) throws YoyoException {
        if (arg == null || arg.isEmpty()) {
            throw new YoyoException("Usage: mark <taskNumber> | unmark <taskNumber> | delete <taskNumber>.");
        }
        int idx;
        try {
            idx = Integer.parseInt(arg.trim());
        } catch (NumberFormatException e) {
            throw new YoyoException("Task number must be an integer.");
        }
        if (idx < 1 || idx > size) {
            throw new YoyoException("Invalid task number: " + idx);
        }
        return idx;
    }

    private static void boxedAdded(Task t, int size) {
        boxed(
                "Got it. I've added this task:",
                "  " + t.toString(),
                "Now you have " + size + " tasks in the list."
        );
    }

    private static void boxed(String... lines) {
        System.out.println(LINE);
        for (String line : lines) {
            System.out.println(" " + line);
        }
        System.out.println(LINE);
    }
}

class YoyoException extends Exception {

    public YoyoException(String msg) {
        super(msg);
    }
}
