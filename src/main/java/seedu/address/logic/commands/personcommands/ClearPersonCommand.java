package seedu.address.logic.commands.personcommands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.AddressBook;
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
            + "Type \"y\" to confirm or \"n\" to abort.";

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
            setPrompted(false);
            setConfirmed(false);
            model.setPersonList(new AddressBook());
            return new CommandResult(MESSAGE_SUCCESS);
        }
    }
}
