
import java.util.Scanner;

public class Yoyo {

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

            String[] parts = input.split("\\s+", 2);
            String cmd = parts[0].toLowerCase();
            String argsRest = (parts.length > 1) ? parts[1].trim() : "";

            switch (cmd) {
                case "bye": {
                    boxed("Bye. Hope to see you again soon!");
                    sc.close();
                    return;
                }

                case "list": {
                    showList(tasks, size);
                    break;
                }

                case "mark": {
                    if (argsRest.isEmpty()) {
                        boxed("Usage: mark <taskNumber>");
                        break;
                    }
                    try {
                        int idx = Integer.parseInt(argsRest);
                        if (idx < 1 || idx > size) {
                            boxed("Invalid task number: " + idx);
                            break;
                        }
                        tasks[idx - 1].markAsDone();
                        boxed(
                                "Nice! I've marked this task as done:",
                                "  " + tasks[idx - 1].toString()
                        );
                    } catch (NumberFormatException e) {
                        boxed("Task number must be an integer.");
                    }
                    break;
                }

                case "unmark": {
                    if (argsRest.isEmpty()) {
                        boxed("Usage: unmark <taskNumber>");
                        break;
                    }
                    try {
                        int idx = Integer.parseInt(argsRest);
                        if (idx < 1 || idx > size) {
                            boxed("Invalid task number: " + idx);
                            break;
                        }
                        tasks[idx - 1].markAsUndone();
                        boxed(
                                "OK, I've marked this task as not done yet:",
                                "  " + tasks[idx - 1].toString()
                        );
                    } catch (NumberFormatException e) {
                        boxed("Task number must be an integer.");
                    }
                    break;
                }

                case "todo": {
                    if (argsRest.isEmpty()) {
                        boxed("Usage: todo <description>");
                        break;
                    }
                    if (size >= 100) {
                        boxed("Task list is full (100 items).");
                        break;
                    }
                    Task t = new Todo(argsRest);
                    tasks[size++] = t;
                    boxedAdded(t, size);
                    break;
                }

                case "deadline": {
                    if (argsRest.isEmpty()) {
                        boxed("Usage: deadline <description> /by <when>");
                        break;
                    }
                    String[] split = argsRest.split("/by", 2);
                    if (split.length < 2) {
                        boxed("Missing '/by'. Usage: deadline <description> /by <when>");
                        break;
                    }
                    String desc = split[0].trim();
                    String by = split[1].trim();
                    if (desc.isEmpty() || by.isEmpty()) {
                        boxed("Both description and '/by' time are required.");
                        break;
                    }
                    if (size >= 100) {
                        boxed("Task list is full (100 items).");
                        break;
                    }
                    Task t = new Deadline(desc, by);
                    tasks[size++] = t;
                    boxedAdded(t, size);
                    break;
                }

                case "event": {
                    if (argsRest.isEmpty()) {
                        boxed("Usage: event <description> /from <start> /to <end>");
                        break;
                    }
                    String[] p1 = argsRest.split("/from", 2);
                    if (p1.length < 2) {
                        boxed("Missing '/from'. Usage: event <description> /from <start> /to <end>");
                        break;
                    }
                    String desc = p1[0].trim();
                    String[] p2 = p1[1].split("/to", 2);
                    if (p2.length < 2) {
                        boxed("Missing '/to'. Usage: event <description> /from <start> /to <end>");
                        break;
                    }
                    String from = p2[0].trim();
                    String to = p2[1].trim();
                    if (desc.isEmpty() || from.isEmpty() || to.isEmpty()) {
                        boxed("Description, '/from', and '/to' must not be empty.");
                        break;
                    }
                    if (size >= 100) {
                        boxed("Task list is full (100 items).");
                        break;
                    }
                    Task t = new Event(desc, from, to);
                    tasks[size++] = t;
                    boxedAdded(t, size);
                    break;
                }

                default: {
                    // Fallback: treat as a quick todo for convenience
                    if (size >= 100) {
                        boxed("Task list is full (100 items).");
                        break;
                    }
                    Task t = new Todo(input);
                    tasks[size++] = t;
                    boxedAdded(t, size);
                    break;
                }
            }
        }
    }
}
