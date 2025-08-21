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

    private static String renderTask(boolean done, String desc) {
        return "[" + (done ? "X" : " ") + "] " + desc;
    }

    private static boolean isCommand(String input, String cmd) {
        // exact match or starts with "cmd "
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

        // Storage for up to 100 tasks and their completion state
        String[] tasks = new String[100];
        boolean[] done = new boolean[100];
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
                          .append(done[i] ? "[X]" : "[ ]")
                          .append(" ").append(tasks[i]).append("\n");
                    }
                    boxed(sb.toString().trim().split("\n"));
                }
            } else if (isCommand(input, "mark")) {
                // format: mark N
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
                    done[idx - 1] = true;
                    boxed(
                        "Nice! I've marked this task as done:",
                        "  " + renderTask(true, tasks[idx - 1])
                    );
                } catch (NumberFormatException e) {
                    boxed("Task number must be an integer.");
                }
            } else if (isCommand(input, "unmark")) {
                // format: unmark N
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
                    done[idx - 1] = false;
                    boxed(
                        "OK, I've marked this task as not done yet:",
                        "  " + renderTask(false, tasks[idx - 1])
                    );
                } catch (NumberFormatException e) {
                    boxed("Task number must be an integer.");
                }
            } else if (!input.isEmpty()) {
                if (size < 100) {
                    tasks[size] = input;
                    done[size] = false;
                    size++;
                    boxed("added: " + input);
                } else {
                    boxed("Task list is full (100 items).");
                }
            } // ignore empty lines silently
        }

        sc.close();
    }
}
