package yoyo.core;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import yoyo.exception.YoyoException;
import yoyo.parser.Parser;
import yoyo.task.Deadline;
import yoyo.task.Event;
import yoyo.task.Task;
import yoyo.task.Todo;
import yoyo.util.Constants;

public class YoyoAdapter {

    private final YoyoApp core;
    private final ByteArrayOutputStream outputStream;
    private final PrintStream originalOut;

    public YoyoAdapter() {
        // Redirect System.out to capture output
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        // Initialize core
        core = new YoyoApp("data/yoyo.txt");

        // Restore original System.out
        System.setOut(originalOut);
    }

    public String respond(String input) {
        // Capture output
        outputStream.reset();
        PrintStream tempOut = new PrintStream(outputStream);
        System.setOut(tempOut);

        try {
            // Process the input using existing logic
            String response = processCommand(input);
            return response;
        } catch (Exception e) {
            return Constants.WARNING_PREFIX + e.getMessage();
        } finally {
            // Restore original System.out
            System.setOut(originalOut);
        }
    }

    private String processCommand(String input) {
        try {
            Parser.Parsed p = Parser.parse(input);
            switch (p.cmd) {
                case "list" -> {
                    return formatTaskList(core.getTasks().asList());
                }
                case "help" -> {
                    return getHelpText();
                }
                case "todo" -> {
                    if (p.args.isEmpty()) {
                        throw new YoyoException("A todo needs a description.\nHint: todo <description>");
                    }
                    Task t = new Todo(p.args);
                    core.getTasks().add(t);
                    core.getStorage().save(core.getTasks().asList());
                    return "Got it. I've added this task:\n  " + t.toString()
                            + "\nNow you have " + core.getTasks().size() + " tasks in the list.";
                }
                case "deadline" -> {
                    if (!p.args.contains("/by")) {
                        throw new YoyoException("Usage: deadline <description> /by <yyyy-MM-dd>");
                    }
                    String[] seg = p.args.split("/by", 2);
                    String desc = seg[0].trim();
                    String by = seg[1].trim();
                    Task t = new Deadline(desc, by);
                    core.getTasks().add(t);
                    core.getStorage().save(core.getTasks().asList());
                    return "Got it. I've added this task:\n  " + t.toString()
                            + "\nNow you have " + core.getTasks().size() + " tasks in the list.";
                }
                case "event" -> {
                    if (!p.args.contains("/from") || !p.args.contains("/to")) {
                        throw new YoyoException("Usage: event <description> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>");
                    }
                    String[] first = p.args.split("/from", 2);
                    String desc = first[0].trim();
                    String[] second = first[1].split("/to", 2);
                    String from = second[0].trim();
                    String to = second[1].trim();
                    Task t = new Event(desc, from, to);
                    core.getTasks().add(t);
                    core.getStorage().save(core.getTasks().asList());
                    return "Got it. I've added this task:\n  " + t.toString()
                            + "\nNow you have " + core.getTasks().size() + " tasks in the list.";
                }
                case "mark" -> {
                    int idx = Parser.parseIndex(p.args, core.getTasks().size());
                    core.getTasks().mark(idx);
                    core.getStorage().save(core.getTasks().asList());
                    return "Nice! I've marked this task as done:\n  " + core.getTasks().get(idx);
                }
                case "unmark" -> {
                    int idx = Parser.parseIndex(p.args, core.getTasks().size());
                    core.getTasks().unmark(idx);
                    core.getStorage().save(core.getTasks().asList());
                    return "OK, I've marked this task as not done yet:\n  " + core.getTasks().get(idx);
                }
                case "delete" -> {
                    int idx = Parser.parseIndex(p.args, core.getTasks().size());
                    Task removed = core.getTasks().remove(idx);
                    core.getStorage().save(core.getTasks().asList());
                    return "Noted. I've removed this task:\n  " + removed.toString()
                            + "\nNow you have " + core.getTasks().size() + " tasks in the list.";
                }
                case "find" -> {
                    if (p.args.isEmpty()) {
                        throw new YoyoException("Please provide a keyword to search.\nHint: find <keyword>");
                    }
                    java.util.List<Task> found = core.getTasks().find(p.args);
                    return formatFoundTasks(found);
                }
                case "bye", "exit", "quit" -> {
                    return "Bye. Hope to see you again soon!";
                }
                default -> {
                    throw new IllegalArgumentException("Unknown command: " + p.cmd);
                }
            }
        } catch (YoyoException e) {
            return e.getMessage();
        } catch (IllegalArgumentException e) {
            return Constants.ERR_INVALID_COMMAND_WITH_HELP;
        } catch (Exception e) {
            return "Unexpected error: " + e.getMessage();
        }
    }

    private String formatTaskList(java.util.List<Task> tasks) {
        if (tasks.isEmpty()) {
            return "(no tasks yet)";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(" ").append(i + 1).append(".").append(tasks.get(i).toString()).append("\n");
        }
        return sb.toString().trim();
    }

    private String formatFoundTasks(java.util.List<Task> tasks) {
        if (tasks.isEmpty()) {
            return "No matching tasks found.";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Here are the matching tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(" ").append(i + 1).append(".").append(tasks.get(i).toString()).append("\n");
        }
        return sb.toString().trim();
    }

    private String getHelpText() {
        return Constants.HELP_TEXT;
    }

    public boolean shouldExit(String input) {
        String cmd = input.trim().toLowerCase();
        return "bye".equals(cmd) || "exit".equals(cmd) || "quit".equals(cmd);
    }
}
