package seedu.sellsavvy.logic.commands.generalcommands;

import static java.util.Objects.requireNonNull;

import seedu.sellsavvy.logic.commands.Command;
import seedu.sellsavvy.logic.commands.CommandResult;
import seedu.sellsavvy.model.AddressBook;
import seedu.sellsavvy.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setAddressBook(new AddressBook());
        model.updateSelectedCustomer(null);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
