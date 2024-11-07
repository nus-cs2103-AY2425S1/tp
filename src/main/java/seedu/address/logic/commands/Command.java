package seedu.address.logic.commands;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;



/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
    private final Logger logger = LogsCenter.getLogger(Command.class);

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model) throws CommandException;

    /**
     * Checks if the command can be executed on the contact list.
     * @param model {@code Model} which the command should operate on.
     * @param message Message for the exception thrown
     * @throws CommandException If a command is attempted to be executed on an empty contact list.
     */
    public void executableList(Model model, String message) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        // Checks if there are contacts in the list to execute commands other than the addition of contacts
        if (lastShownList.isEmpty()) {
            logger.warning("Contact list is empty! Unable to execute commands");
            throw new CommandException(message);
        } else {
            logger.info("Executable contact list!");
        }
    }
}
