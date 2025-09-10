package yoyo;

/**
 * Main application class for the Yoyo task management system. Handles user
 * interactions, task management, and data persistence.
 */
public class YoyoApp {

    private final Ui ui;
    private final Storage storage;
    private final TaskList tasks;

    /**
     * Constructs a new YoyoApp instance with the specified data file path.
     * Initializes UI, storage, and loads existing tasks.
     *
     * @param filePath the path to the data file for storing tasks
     */
    public YoyoApp(String filePath) {
        assert filePath != null && !filePath.trim().isEmpty() : "File path cannot be null or empty";
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        Storage.LoadResult loaded = storage.load();
        this.tasks = new TaskList(loaded.tasks);
        if (!loaded.warnings.isEmpty()) {
            ui.showWarnings(loaded.warnings);
        }
    }

    /**
     * Runs the main application loop, processing user commands until exit.
     */
    public void run() {
        ui.showWelcome();
        boolean exit = false;
        while (!exit) {
            String input = ui.readCommand();
            if (input.isEmpty()) {
                continue;
            }
            try {
                exit = processCommand(input);
            } catch (YoyoException e) {
                ui.showError(e.getMessage());
            } catch (IllegalArgumentException e) {
                ui.showError(Constants.ERR_INVALID_COMMAND);
                ui.showError("Type 'help' to see commands.");
            } catch (Exception e) {
                ui.showError(Constants.ERR_UNEXPECTED_ERROR + e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    /**
     * Processes a user command and returns true if the application should exit.
     *
     * @param input the user input command
     * @return true if the application should exit, false otherwise
     * @throws YoyoException if there's an application-specific error
     */
    private boolean processCommand(String input) throws YoyoException {
        Parser.Parsed parsed = Parser.parse(input);
        Command command = CommandFactory.createCommand(parsed.cmd, parsed.args, tasks, ui);
        boolean shouldExit = command.execute();

        // Save tasks after commands that modify the task list
        if (shouldSaveAfterCommand(parsed.cmd)) {
            try {
                storage.save(tasks.asList());
            } catch (Exception e) {
                ui.showError(Constants.ERR_FAILED_TO_SAVE + e.getMessage());
            }
        }

        return shouldExit;
    }

    /**
     * Determines if tasks should be saved after executing a command.
     *
     * @param commandName the name of the command that was executed
     * @return true if tasks should be saved, false otherwise
     */
    private boolean shouldSaveAfterCommand(String commandName) {
        return switch (commandName) {
            case Constants.CMD_TODO, Constants.CMD_DEADLINE, Constants.CMD_EVENT,
                 Constants.CMD_MARK, Constants.CMD_UNMARK, Constants.CMD_DELETE -> true;
            default -> false;
        };
    }

    /**
     * Main entry point for the Yoyo application. Creates a new YoyoApp instance
     * and starts the application.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
<<<<<<< HEAD
        new YoyoApp(Constants.DEFAULT_DATA_FILE).run();
=======
        assert args != null : "Command line arguments array cannot be null";
        new YoyoApp("data/yoyo.txt").run();
>>>>>>> master
    }

    // Getter methods for adapter access
    public TaskList getTasks() {
        return tasks;
    }

    public Storage getStorage() {
        return storage;
    }

    public Ui getUi() {
        return ui;
    }
}
