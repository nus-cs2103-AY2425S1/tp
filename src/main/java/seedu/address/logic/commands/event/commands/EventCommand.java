package seedu.address.logic.commands.event.commands;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.event.EventManager;

/**
 * Represents an event command with hidden internal logic and the ability to be executed.
 */
public abstract class EventCommand extends Command {
    /**
     * Executes the event-related command and returns the result.
     *
     * @param eventManager The {@code EventManager} which manages the events in the system.
     * @return A {@code CommandResult} representing the result of executing the command.
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(EventManager eventManager) throws CommandException;
}
