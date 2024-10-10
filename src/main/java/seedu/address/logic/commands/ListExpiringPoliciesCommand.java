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

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all policies nearing expiry within the next 30 days.\n"
            + "Example: " + COMMAND_WORD;

    private static final String MESSAGE_NOT_IMPLEMENTED_YET = "ListExpiringPolicies command has not "
            + "been implemented yet";

    private static final String MESSAGE_SUCCESS = "The following policies are near expiry:";
    private static final String MESSAGE_NO_EXPIRING_POLICY = "No policies expiring within the next 30 days!";
    private static final String MESSAGE_FAILURE = "Failed to retrieve expiring policies. Please try again.";
    private static final String MESSAGE_POLICY_LISTED_DETAILS = "Phone: %1$s, Policy Type: %2$s, Premium Amount: %3$s, "
            + "Coverage Amount: %4$s, Expiry Date: %5$s";


    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ListExpiringPoliciesCommand)) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return COMMAND_WORD;
    }
}

