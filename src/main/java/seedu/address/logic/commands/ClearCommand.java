package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String SHORT_COMMAND_WORD = ":cl";
    public static final String LONG_COMMAND_WORD = ":clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";
    public static final String COMMAND_SUMMARY_ACTION = "Clear";
    public static final String COMMAND_SUMMARY_FORMAT = LONG_COMMAND_WORD + "\n" + SHORT_COMMAND_WORD;
    public static final String COMMAND_SUMMARY_EXAMPLES = LONG_COMMAND_WORD + "\n" + SHORT_COMMAND_WORD;


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setVersionedAddressBook(new AddressBook());
        model.commitAddressBook(); // Clearing the address book will commit the change
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
