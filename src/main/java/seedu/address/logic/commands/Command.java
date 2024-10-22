package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.EventManager;

/**
 * Represents an abstract command that can be executed within a model and event management context.
 */
public abstract class Command {
    /**
     * Executes the command with the given model and event manager.
     *
     * @param model The {@code Model} in which the command operates. This represents the application state.
     * @param eventManager The {@code EventManager} responsible for managing events related to the command.
     * @return A {@code CommandResult} representing the outcome of the command execution.
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model, EventManager eventManager) throws CommandException;
}
