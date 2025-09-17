package yoyo.util;

/**
 * Constants used throughout the Yoyo application. Centralizes magic strings and
 * numbers for better maintainability.
 */
public final class Constants {

    // Command names
    public static final String CMD_LIST = "list";
    public static final String CMD_HELP = "help";
    public static final String CMD_TODO = "todo";
    public static final String CMD_DEADLINE = "deadline";
    public static final String CMD_EVENT = "event";
    public static final String CMD_MARK = "mark";
    public static final String CMD_UNMARK = "unmark";
    public static final String CMD_DELETE = "delete";
    public static final String CMD_FIND = "find";
    public static final String CMD_SORT = "sort";
    public static final String CMD_BYE = "bye";
    public static final String CMD_EXIT = "exit";
    public static final String CMD_QUIT = "quit";

    // Command arguments
    public static final String ARG_BY = "/by";
    public static final String ARG_FROM = "/from";
    public static final String ARG_TO = "/to";

    // Sort criteria
    public static final String SORT_BY_DATE = "date";
    public static final String SORT_BY_DESCRIPTION = "description";
    public static final String SORT_BY_STATUS = "status";
    public static final String SORT_BY_TYPE = "type";

    // Error messages
    public static final String ERR_TODO_NEEDS_DESC = "A todo needs a description.\nHint: todo <description>";
    public static final String ERR_DEADLINE_USAGE = "Usage: deadline <description> /by <yyyy-MM-dd>";
    public static final String ERR_EVENT_USAGE = "Usage: event <description> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>";
    public static final String ERR_MARK_USAGE = "Usage: mark <taskNumber>";
    public static final String ERR_UNMARK_USAGE = "Usage: unmark <taskNumber>";
    public static final String ERR_DELETE_USAGE = "Usage: delete <taskNumber>";
    public static final String ERR_FIND_NEEDS_KEYWORD = "Please provide a keyword to search.\nHint: find <keyword>";
    public static final String ERR_SORT_USAGE = "Usage: sort <criteria> [asc|desc]\nCriteria: date, description, status, type\nExample: sort date asc";
    public static final String ERR_INVALID_SORT_CRITERIA = "Invalid sort criteria. Use: date, description, status, type";
    public static final String ERR_INVALID_COMMAND = "OOPS!!! I'm sorry, but I don't know what that means :-(";
    public static final String ERR_UNKNOWN_COMMAND = "Unknown command: ";
    public static final String ERR_INVALID_TASK_NUMBER = "Invalid task number: ";
    public static final String ERR_TASK_NUMBER_MUST_BE_INT = "Task number must be an integer.";
    public static final String ERR_UNEXPECTED_ERROR = "Unexpected error: ";
    public static final String ERR_FAILED_TO_READ_FILE = "Failed to read file: ";
    public static final String ERR_FAILED_TO_SAVE = "Failed to save tasks: ";
    public static final String ERR_LINE_SKIPPED = "Line %d skipped: %s";
    public static final String ERR_UNRECOGNIZED_DATE = "Unrecognized date/time: \"%s\". Use yyyy-MM-dd or d/M/yyyy, optionally with time HHmm.";

    // Status symbols
    public static final char STATUS_DONE = 'X';
    public static final char STATUS_NOT_DONE = ' ';

    // Index constants
    public static final int MIN_TASK_INDEX = 1;

    // Date/time format patterns
    public static final String DATETIME_FORMAT_STORAGE = "yyyy-MM-dd HHmm";
    public static final String DATETIME_FORMAT_OUTPUT = "MMM d yyyy, HH:mm";
    public static final String DATE_FORMAT_ISO = "yyyy-MM-dd";
    public static final String DATE_FORMAT_SHORT = "d/M/yyyy";

    // Regex patterns for parsing
    public static final String REGEX_TODO = "^\\[T]\\[( |X)]\\s(.+)$";
    public static final String REGEX_DEADLINE = "^\\[D]\\[( |X)]\\s(.+)\\s\\(by:\\s(.+)\\)$";
    public static final String REGEX_EVENT = "^\\[E]\\[( |X)]\\s(.+)\\s\\(from:\\s(.+)\\s+to:\\s(.+)\\)$";

    // File paths
    public static final String DEFAULT_DATA_FILE = "data/yoyo.txt";

    // Exit message
    public static final String MSG_GOODBYE = "Bye. Hope to see you again soon!";

    // Warning prefix
    public static final String WARNING_PREFIX = "⚠️ ";

    // Invalid command with help
    public static final String ERR_INVALID_COMMAND_WITH_HELP = ERR_INVALID_COMMAND + "\nType 'help' to see commands.";

    // Help text
    public static final String HELP_TEXT = "Commands:\n"
            + "  list\n"
            + "  todo <description>\n"
            + "  deadline <description> /by <deadline text>\n"
            + "  event <description> /from <start> /to <end>\n"
            + "  mark <taskNumber> | unmark <taskNumber> | delete <taskNumber>\n"
            + "  find <keyword>\n"
            + "  bye";

    private Constants() {
        // Utility class should not be instantiated
    }
}
