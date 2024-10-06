package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Lists all policies in the address book that are nearing expiry within the next 30 days.
 */
public class ListExpiringPoliciesCommand extends Command {

    /**
     * Command word to trigger this command in the application's UI.
     */
    public static final String COMMAND_WORD = "listExpiringPolicies";

    /**
     * Describes how to use the command and gives an example for users.
     */
    private static final String MESSAGE_NOT_IMPLEMENTED_YET = "ListExpiringPolicies command has not "
            + "been implemented yet";

    @Override
    public CommandResult execute(Model mode) throws CommandException {
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
    }
}

