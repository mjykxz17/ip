package yoyo;

/**
 * User interface class for the Yoyo application. Handles all input and output
 * operations with the user.
 */
public class Ui {

    private final java.util.Scanner sc = new java.util.Scanner(System.in);
    private static final String LINE = "____________________________________________________________";

    /**
     * Displays a horizontal line separator.
     */
    public void showLine() {
        System.out.println(LINE);
    }

    /**
     * Displays the welcome message and application banner.
     */
    public void showWelcome() {
        String banner = """
                 ___                    __   __                
                |_ _|   __ _ _ __ ___   \\ \\ / /__  _   _  ___  
                 | |   / _` | '_ ` _ \\   \\ V / _ \\| | | |/ _ \\ 
                 | |  | (_| | | | | | |   | | (_) | |_| | (_) |
                |___|  \\__,_|_| |_| |_|   |_|\\___/ \\__, |\\___/ 
                                                    |___/       
                                """.stripTrailing();
        java.util.List<String> lines = new java.util.ArrayList<>();
        lines.add("Hello! I'm Yoyo");
        lines.add("Type 'help' to see commands.");
        java.util.Collections.addAll(lines, banner.split("\\R"));
        boxed(lines.toArray(String[]::new));
    }

    /**
     * Displays the help message with available commands.
     */
    public void showHelp() {
        boxed(
                "Commands:",
                "  list",
                "  todo <description>",
                "  deadline <description> /by <deadline text>",
                "  event <description> /from <start> /to <end>",
                "  mark <taskNumber> | unmark <taskNumber> | delete <taskNumber>",
                "  find <keyword>",
                "  bye"
        );
    }

    /**
     * Reads a command from the user input.
     *
     * @return the trimmed command string, or empty if no input
     */
    public String readCommand() {
        return sc.hasNextLine() ? sc.nextLine().trim() : "";
    }

    /**
     * Displays the list of tasks.
     *
     * @param tasks the list of tasks to display
     */
    public void showList(java.util.List<Task> tasks) {
        if (tasks.isEmpty()) {
            boxed("(no tasks yet)");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(" ").append(i + 1).append(".").append(tasks.get(i).toString()).append("\n");
        }
        boxed(sb.toString().trim().split("\\R"));
    }

    /**
     * Displays the list of tasks that match the search keyword.
     *
     * @param tasks the list of matching tasks
     */
    public void showFound(java.util.List<Task> tasks) {
        if (tasks.isEmpty()) {
            boxed("No matching tasks found.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Here are the matching tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(" ").append(i + 1).append(".").append(tasks.get(i).toString()).append("\n");
        }
        boxed(sb.toString().trim().split("\\R"));
    }

    /**
     * Displays a message when a task is added.
     *
     * @param t the added task
     * @param size the new total number of tasks
     */
    public void showAdded(Task t, int size) {
        boxed(
                "Got it. I've added this task:",
                "  " + t.toString(),
                "Now you have " + size + " tasks in the list."
        );
    }

    /**
     * Displays a message when a task is removed.
     *
     * @param t the removed task
     * @param newSize the new total number of tasks
     */
    public void showRemoved(Task t, int newSize) {
        boxed(
                "Noted. I've removed this task:",
                "  " + t.toString(),
                "Now you have " + newSize + " tasks in the list."
        );
    }

    /**
     * Displays a message when a task is marked as done.
     *
     * @param t the marked task
     */
    public void showMark(Task t) {
        boxed("Nice! I've marked this task as done:", "  " + t.toString());
    }

    /**
     * Displays a message when a task is unmarked (marked as not done).
     *
     * @param t the unmarked task
     */
    public void showUnmark(Task t) {
        boxed("OK, I've marked this task as not done yet:", "  " + t.toString());
    }

    /**
     * Displays an error message.
     *
     * @param msg the error message to display
     */
    public void showError(String msg) {
        boxed(msg);
    }

    /**
     * Displays warnings from loading corrupted data.
     *
     * @param warnings the list of warning messages
     */
    public void showWarnings(java.util.List<String> warnings) {
        if (warnings == null || warnings.isEmpty()) {
            return;
        }
        boxed("Note: some saved lines were skipped as corrupted:");
        for (String w : warnings) {
            boxed(" - " + w);
        }
    }

    /**
     * Displays the given lines in a boxed format with horizontal lines.
     *
     * @param lines the lines to display
     */
    private void boxed(String... lines) {
        System.out.println(LINE);
        for (String line : lines) {
            System.out.println(" " + line);
        }
        System.out.println(LINE);
    }
}
