package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";

    private ReadOnlyAddressBook previousAddressBook;


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        previousAddressBook = new AddressBook(model.getAddressBook());
        model.setAddressBook(new AddressBook());
        model.addCommandToLog(this);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public void undo(Model model) {
        model.setAddressBook(previousAddressBook);
    }
}
