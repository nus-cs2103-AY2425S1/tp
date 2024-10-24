package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends ConcreteCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";
    public static final String MESSAGE_UNDO_SUCCESS = "Address book has been restored!";

    private AddressBook initialAddressBook;

    @Override
    public CommandResult execute(Model model) {
        requireNotExecuted();
        requireNonNull(model);
        initialAddressBook = new AddressBook(model.getAddressBook());
        model.setAddressBook(new AddressBook());
        isExecuted = true;
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public CommandResult undo(Model model) {
        requireExecuted();
        requireNonNull(model);
        requireNonNull(initialAddressBook);
        model.setAddressBook(initialAddressBook);
        isExecuted = false;
        return new CommandResult(MESSAGE_UNDO_SUCCESS);
    }
}
