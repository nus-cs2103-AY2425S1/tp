package seedu.address.logic.commands.personcommands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Clears all persons of the address book.
 */
public class ClearPersonCommand extends ClearCommand {

    public static final String COMMAND_WORD = "clear";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " p: Clears all persons.";

    public static final String MESSAGE_SUCCESS = "All persons has been cleared!";

    public static final String MESSAGE_CONFIRMATION = "Are you sure you want to clear all your contacts?\n"
            + "Type exactly \"Y\" or \"Yes\" to confirm or any key to abort.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        if (!isPrompted()) {
            setPrompted(true);
            return new CommandResult(MESSAGE_CONFIRMATION);
        } else if (!isConfirmed()) {
            setPrompted(false);
            return new CommandResult(MESSAGE_ABORTED);
        } else {
            setConfirmed(false);
            setPrompted(false);
            model.clearAllPersons();
            return new CommandResult(MESSAGE_SUCCESS);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ClearPersonCommand)) {
            return false;
        }

        // all instances of clear person command are equal
        return true;
    }
}
