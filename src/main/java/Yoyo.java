
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

    private static boolean isCommand(String input, String cmd) {
        return input.equalsIgnoreCase(cmd) || input.toLowerCase().startsWith(cmd.toLowerCase() + " ");
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

        // Fixed-size storage for up to 100 Task objects
        Task[] tasks = new Task[100];
        int size = 0;

        while (true) {
            String input = sc.nextLine().trim();

            if (input.equalsIgnoreCase("bye")) {
                boxed("Bye. Hope to see you again soon!");
                break;
            } else if (input.equalsIgnoreCase("list")) {
                if (size == 0) {
                    boxed("(no tasks yet)");
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Here are the tasks in your list:\n");
                    for (int i = 0; i < size; i++) {
                        sb.append(" ").append(i + 1).append(".")
                                .append(tasks[i].toString())
                                .append("\n");
                    }
                    boxed(sb.toString().trim().split("\n"));
                }
            } else if (isCommand(input, "mark")) {
                // mark N
                String[] parts = input.split("\\s+");
                if (parts.length < 2) {
                    boxed("Usage: mark <taskNumber>");
                    continue;
                }
                try {
                    int idx = Integer.parseInt(parts[1]); // 1-based
                    if (idx < 1 || idx > size) {
                        boxed("Invalid task number: " + idx);
                        continue;
                    }
                    tasks[idx - 1].markAsDone();
                    boxed(
                            "Nice! I've marked this task as done:",
                            "  " + tasks[idx - 1].toString()
                    );
                } catch (NumberFormatException e) {
                    boxed("Task number must be an integer.");
                }
            } else if (isCommand(input, "unmark")) {
                // unmark N
                String[] parts = input.split("\\s+");
                if (parts.length < 2) {
                    boxed("Usage: unmark <taskNumber>");
                    continue;
                }
                try {
                    int idx = Integer.parseInt(parts[1]); // 1-based
                    if (idx < 1 || idx > size) {
                        boxed("Invalid task number: " + idx);
                        continue;
                    }
                    tasks[idx - 1].markAsUndone();
                    boxed(
                            "OK, I've marked this task as not done yet:",
                            "  " + tasks[idx - 1].toString()
                    );
                } catch (NumberFormatException e) {
                    boxed("Task number must be an integer.");
                }
            } else if (!input.isEmpty()) {
                if (size < 100) {
                    tasks[size++] = new Task(input);
                    boxed("added: " + input);
                } else {
                    boxed("Task list is full (100 items).");
                }
            } // ignore empty lines silently
        }

        sc.close();
    }
}
