package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.EventManager;

/**
 * Represents a general command that contains hidden internal logic and can be executed.
 * Subclasses of this class should provide specific implementations of the execution methods.
 */
public abstract class Command {
    /**
     * Executes the command with the provided {@code EventManager}.
     *
     * This method is intended to be overridden by subclasses that operate on an {@code EventManager}.
     *
     * @param eventManager The {@code EventManager} that the command should interact with.
     * @return The result of executing the command.
     * @throws CommandException If an error occurs during command execution.
     */
    public CommandResult execute(EventManager eventManager) throws CommandException {
        return null;
    };

    /**
     * Executes the command with the provided {@code Model}.
     *
     * This method is intended to be overridden by subclasses that operate on a {@code Model}.
     *
     * @param model The {@code Model} that the command should interact with.
     * @return The result of executing the command.
     * @throws CommandException If an error occurs during command execution.
     */
    public CommandResult execute(Model model) throws CommandException {
        return null;
    };
}
