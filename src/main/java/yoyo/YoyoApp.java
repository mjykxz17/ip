package yoyo;

public class YoyoApp {
    private final Ui ui;
    private final Storage storage;
    private final TaskList tasks;

    public YoyoApp(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        Storage.LoadResult loaded = storage.load();
        this.tasks = new TaskList(loaded.tasks);
        if (!loaded.warnings.isEmpty()) {
            ui.showWarnings(loaded.warnings);
        }
    }

    public void run() {
        ui.showWelcome();
        boolean exit = false;
        while (!exit) {
            String input = ui.readCommand();
            if (input.isEmpty()) {
                continue;
            }
            try {
                Parser.Parsed p = Parser.parse(input);
                switch (p.cmd) {
                    case "list" -> ui.showList(tasks.asList());

                    case "help" -> ui.showHelp();

                    case "todo" -> {
                        if (p.args.isEmpty()) {
                            throw new YoyoException("A todo needs a description.\nHint: todo <description>");
                        }
                        Task t = new Todo(p.args);
                        tasks.add(t);
                        ui.showAdded(t, tasks.size());
                        storage.save(tasks.asList());
                    }

                    case "deadline" -> {
                        if (!p.args.contains("/by")) {
                            throw new YoyoException("Usage: deadline <description> /by <yyyy-MM-dd>");
                        }
                        String[] seg = p.args.split("/by", 2);
                        String desc = seg[0].trim();
                        String by = seg[1].trim();
                        Task t = new Deadline(desc, by);
                        tasks.add(t);
                        ui.showAdded(t, tasks.size());
                        storage.save(tasks.asList());
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
                        tasks.add(t);
                        ui.showAdded(t, tasks.size());
                        storage.save(tasks.asList());
                    }

                    case "mark" -> {
                        int idx = Parser.parseIndex(p.args, tasks.size());
                        tasks.mark(idx);
                        ui.showMark(tasks.get(idx));
                        storage.save(tasks.asList());
                    }

                    case "unmark" -> {
                        int idx = Parser.parseIndex(p.args, tasks.size());
                        tasks.unmark(idx);
                        ui.showUnmark(tasks.get(idx));
                        storage.save(tasks.asList());
                    }

                    case "delete" -> {
                        int idx = Parser.parseIndex(p.args, tasks.size());
                        Task removed = tasks.remove(idx);
                        ui.showRemoved(removed, tasks.size());
                        storage.save(tasks.asList());
                    }

                    case "bye", "exit", "quit" -> {
                        ui.showLine();
                        ui.showError("Bye. Hope to see you again soon!");
                        exit = true;
                    }

                    default -> {
                        throw new IllegalArgumentException("Unknown command: " + p.cmd);
                    }
                }
            } catch (YoyoException e) {
                ui.showError(e.getMessage());
            } catch (IllegalArgumentException e) {
                ui.showError("OOPS!!! I'm sorry, but I don't know what that means :-(");
                ui.showError("Type 'help' to see commands.");
            } catch (Exception e) {
                ui.showError("Unexpected error: " + e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    public static void main(String[] args) {
        new YoyoApp("data/yoyo.txt").run();
    }
}
