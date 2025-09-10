package yoyo;

/**
 * Factory class for creating command objects based on user input.
 * Implements the Factory pattern to encapsulate command creation logic.
 */
public class CommandFactory {

    /**
     * Creates a command instance based on the command name and arguments.
     *
     * @param commandName the name of the command (e.g., "list", "todo")
     * @param args the arguments for the command
     * @param tasks the task list to operate on
     * @param ui the UI instance for user interaction
     * @return the appropriate command instance
     * @throws IllegalArgumentException if the command is unknown
     */
    public static Command createCommand(String commandName, String args, TaskList tasks, Ui ui) {
        return switch (commandName) {
            case Constants.CMD_LIST -> new ListCommand(tasks, ui);
            case Constants.CMD_HELP -> new HelpCommand(ui);
            case Constants.CMD_TODO -> new AddTodoCommand(args, tasks, ui);
            case Constants.CMD_DEADLINE -> new AddDeadlineCommand(args, tasks, ui);
            case Constants.CMD_EVENT -> new AddEventCommand(args, tasks, ui);
            case Constants.CMD_MARK -> new MarkCommand(args, tasks, ui);
            case Constants.CMD_UNMARK -> new UnmarkCommand(args, tasks, ui);
            case Constants.CMD_DELETE -> new DeleteCommand(args, tasks, ui);
            case Constants.CMD_FIND -> new FindCommand(args, tasks, ui);
            case Constants.CMD_BYE, Constants.CMD_EXIT, Constants.CMD_QUIT -> new ExitCommand(ui);
            default -> throw new IllegalArgumentException(Constants.ERR_UNKNOWN_COMMAND + commandName);
        };
    }

    // Command implementations

    private static class ListCommand implements Command {
        private final TaskList tasks;
        private final Ui ui;

        ListCommand(TaskList tasks, Ui ui) {
            this.tasks = tasks;
            this.ui = ui;
        }

        @Override
        public boolean execute() {
            ui.showList(tasks.asList());
            return false;
        }
    }

    private static class HelpCommand implements Command {
        private final Ui ui;

        HelpCommand(Ui ui) {
            this.ui = ui;
        }

        @Override
        public boolean execute() {
            ui.showHelp();
            return false;
        }
    }

    private static class AddTodoCommand implements Command {
        private final String args;
        private final TaskList tasks;
        private final Ui ui;

        AddTodoCommand(String args, TaskList tasks, Ui ui) {
            this.args = args;
            this.tasks = tasks;
            this.ui = ui;
        }

        @Override
        public boolean execute() throws YoyoException {
            if (args.isEmpty()) {
                throw new YoyoException(Constants.ERR_TODO_NEEDS_DESC);
            }
            Task task = new Todo(args);
            tasks.add(task);
            ui.showAdded(task, tasks.size());
            return false;
        }
    }

    private static class AddDeadlineCommand implements Command {
        private final String args;
        private final TaskList tasks;
        private final Ui ui;

        AddDeadlineCommand(String args, TaskList tasks, Ui ui) {
            this.args = args;
            this.tasks = tasks;
            this.ui = ui;
        }

        @Override
        public boolean execute() throws YoyoException {
            if (!args.contains(Constants.ARG_BY)) {
                throw new YoyoException(Constants.ERR_DEADLINE_USAGE);
            }
            String[] parts = args.split(Constants.ARG_BY, 2);
            String description = parts[0].trim();
            String by = parts[1].trim();
            Task task = new Deadline(description, by);
            tasks.add(task);
            ui.showAdded(task, tasks.size());
            return false;
        }
    }

    private static class AddEventCommand implements Command {
        private final String args;
        private final TaskList tasks;
        private final Ui ui;

        AddEventCommand(String args, TaskList tasks, Ui ui) {
            this.args = args;
            this.tasks = tasks;
            this.ui = ui;
        }

        @Override
        public boolean execute() throws YoyoException {
            if (!args.contains(Constants.ARG_FROM) || !args.contains(Constants.ARG_TO)) {
                throw new YoyoException(Constants.ERR_EVENT_USAGE);
            }
            String[] firstSplit = args.split(Constants.ARG_FROM, 2);
            String description = firstSplit[0].trim();
            String[] secondSplit = firstSplit[1].split(Constants.ARG_TO, 2);
            String from = secondSplit[0].trim();
            String to = secondSplit[1].trim();
            Task task = new Event(description, from, to);
            tasks.add(task);
            ui.showAdded(task, tasks.size());
            return false;
        }
    }

    private static class MarkCommand implements Command {
        private final String args;
        private final TaskList tasks;
        private final Ui ui;

        MarkCommand(String args, TaskList tasks, Ui ui) {
            this.args = args;
            this.tasks = tasks;
            this.ui = ui;
        }

        @Override
        public boolean execute() throws YoyoException {
            int index = Parser.parseIndex(args, tasks.size());
            tasks.mark(index);
            ui.showMark(tasks.get(index));
            return false;
        }
    }

    private static class UnmarkCommand implements Command {
        private final String args;
        private final TaskList tasks;
        private final Ui ui;

        UnmarkCommand(String args, TaskList tasks, Ui ui) {
            this.args = args;
            this.tasks = tasks;
            this.ui = ui;
        }

        @Override
        public boolean execute() throws YoyoException {
            int index = Parser.parseIndex(args, tasks.size());
            tasks.unmark(index);
            ui.showUnmark(tasks.get(index));
            return false;
        }
    }

    private static class DeleteCommand implements Command {
        private final String args;
        private final TaskList tasks;
        private final Ui ui;

        DeleteCommand(String args, TaskList tasks, Ui ui) {
            this.args = args;
            this.tasks = tasks;
            this.ui = ui;
        }

        @Override
        public boolean execute() throws YoyoException {
            int index = Parser.parseIndex(args, tasks.size());
            Task removed = tasks.remove(index);
            ui.showRemoved(removed, tasks.size());
            return false;
        }
    }

    private static class FindCommand implements Command {
        private final String args;
        private final TaskList tasks;
        private final Ui ui;

        FindCommand(String args, TaskList tasks, Ui ui) {
            this.args = args;
            this.tasks = tasks;
            this.ui = ui;
        }

        @Override
        public boolean execute() throws YoyoException {
            if (args.isEmpty()) {
                throw new YoyoException(Constants.ERR_FIND_NEEDS_KEYWORD);
            }
            java.util.List<Task> found = tasks.find(args);
            ui.showFound(found);
            return false;
        }
    }

    private static class ExitCommand implements Command {
        private final Ui ui;

        ExitCommand(Ui ui) {
            this.ui = ui;
        }

        @Override
        public boolean execute() {
            ui.showLine();
            ui.showError(Constants.MSG_GOODBYE);
            return true;
        }
    }
}
