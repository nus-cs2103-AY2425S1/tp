package seedu.address.logic.commands.personcommands;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;

/**
 * Clears all persons of the address book.
 */
public class ClearPersonCommand extends ClearCommand {

    public static final String COMMAND_WORD = "clear";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " p: Clears all persons.";

    public static final String MESSAGE_SUCCESS = "All persons has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setPersonList(new AddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
