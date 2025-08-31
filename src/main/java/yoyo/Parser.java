package yoyo;

/**
 * Utility class for parsing user input commands and task indices.
 */
public class Parser {

<<<<<<< HEAD
    /**
     * Represents a parsed command with its type and arguments.
     */
    public static class Parsed {

        /**
         * The command type.
         */
=======
    public static class Parsed {

>>>>>>> branch-A-CodingStandard
        public final String cmd;
        /**
         * The arguments for the command.
         */
        public final String args;

<<<<<<< HEAD
        /**
         * Constructs a Parsed command.
         *
         * @param cmd the command type
         * @param args the command arguments
         */
=======
>>>>>>> branch-A-CodingStandard
        public Parsed(String cmd, String args) {
            this.cmd = cmd;
            this.args = args;
        }
    }

    /**
     * Parses the input string into a command and arguments.
     *
     * @param input the user input string
     * @return the parsed command
     */
    public static Parsed parse(String input) {
        String[] parts = input.trim().split("\\s+", 2);
        String cmd = parts[0].toLowerCase();
        String args = parts.length > 1 ? parts[1] : "";
        return new Parsed(cmd, args);
    }

    /**
     * Parses a task index from a string, validating it against the list size.
     *
     * @param arg the index string
     * @param size the size of the task list
     * @return the parsed 1-based index
     * @throws YoyoException if the index is invalid
     */
    public static int parseIndex(String arg, int size) throws YoyoException {
        if (arg == null || arg.isEmpty()) {
            throw new YoyoException("Usage: mark <taskNumber> | unmark <taskNumber> | delete <taskNumber>.");
        }
        int idx;
        try {
            idx = Integer.parseInt(arg.trim());
        } catch (NumberFormatException e) {
            throw new YoyoException("Task number must be an integer.");
        }
        if (idx < 1 || idx > size) {
            throw new YoyoException("Invalid task number: " + idx);
        }
        return idx;
    }
}
