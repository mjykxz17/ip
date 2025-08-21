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

        // Fixed-size storage for up to 100 tasks
        String[] tasks = new String[100];
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
                    for (int i = 0; i < size; i++) {
                        sb.append(" ").append(i + 1).append(". ").append(tasks[i]).append("\n");
                    }
                    // Split on newlines to keep boxed() simple
                    String[] lines = sb.toString().split("\n");
                    boxed(lines);
                }
            } else if (!input.isEmpty()) {
                if (size < 100) {
                    tasks[size++] = input;
                    boxed("added: " + input);
                } else {
                    boxed("Task list is full (100 items).");
                }
            } // ignore empty lines silently
        }

        sc.close();
    }
}
