package seedu.address.logic.commands.eventcommands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;

/**
 * Clears all events of the address book.
 */
public class ClearEventCommand extends ClearCommand {

    public static final String COMMAND_WORD = "clear";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " e: Clears all events.";

    public static final String MESSAGE_SUCCESS = "All events has been cleared!";

    public static final String MESSAGE_CONFIRMATION = "Are you sure you want to clear all your events?\n"
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
            model.setEventList(new AddressBook());
            return new CommandResult(MESSAGE_SUCCESS);
        }
    }
}
