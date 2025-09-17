package yoyo.command;

import yoyo.exception.YoyoException;

/**
 * Interface for all command implementations in the Yoyo application. Each
 * command encapsulates a specific operation that can be executed.
 */
public interface Command {

    /**
     * Executes the command.
     *
     * @return true if the application should exit after this command, false
     * otherwise
     * @throws YoyoException if there's an application-specific error during
     * execution
     */
    boolean execute() throws YoyoException;
}
