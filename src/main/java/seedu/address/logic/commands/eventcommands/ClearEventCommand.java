package seedu.address.logic.commands.eventcommands;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;

/**
 * Clears all events of the address book.
 */
public class ClearEventCommand extends ClearCommand {

    public static final String COMMAND_WORD = "clear";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " e: Clears all events.";

    public static final String MESSAGE_SUCCESS = "All events has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setEventList(new AddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
