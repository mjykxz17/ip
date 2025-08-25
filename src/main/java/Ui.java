public class Ui {
    private final java.util.Scanner sc = new java.util.Scanner(System.in);
    private static final String LINE = "____________________________________________________________";

    public void showLine() {
        System.out.println(LINE);
    }

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

    public void showHelp() {
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

    public String readCommand() {
        return sc.hasNextLine() ? sc.nextLine().trim() : "";
    }

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

    public void showAdded(Task t, int size) {
        boxed(
                "Got it. I've added this task:",
                "  " + t.toString(),
                "Now you have " + size + " tasks in the list."
        );
    }

    public void showRemoved(Task t, int newSize) {
        boxed(
                "Noted. I've removed this task:",
                "  " + t.toString(),
                "Now you have " + newSize + " tasks in the list."
        );
    }

    public void showMark(Task t) {
        boxed("Nice! I've marked this task as done:", "  " + t.toString());
    }

    public void showUnmark(Task t) {
        boxed("OK, I've marked this task as not done yet:", "  " + t.toString());
    }

    public void showError(String msg) {
        boxed(msg);
    }

    public void showWarnings(java.util.List<String> warnings) {
        if (warnings == null || warnings.isEmpty()) return;
        boxed("Note: some saved lines were skipped as corrupted:");
        for (String w : warnings) boxed(" - " + w);
    }

    private void boxed(String... lines) {
        System.out.println(LINE);
        for (String line : lines) {
            System.out.println(" " + line);
        }
        System.out.println(LINE);
    }
}
