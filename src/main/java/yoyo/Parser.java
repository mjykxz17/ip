package yoyo;

public class Parser {

    public static class Parsed {

        public final String cmd;
        public final String args;

        public Parsed(String cmd, String args) {
            this.cmd = cmd;
            this.args = args;
        }
    }

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
