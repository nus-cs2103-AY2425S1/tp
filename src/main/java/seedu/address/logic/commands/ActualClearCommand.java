package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.ActualClearCommandParser.MESSAGE_FAILURE;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;

/**
 * Clears all the address book data.
 */
public class ActualClearCommand extends Command {

    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";
    private final String message;

    public ActualClearCommand() {
        this.message = MESSAGE_SUCCESS;
    }

    public ActualClearCommand(String message) {
        this.message = message;
    }
    @Override
    public CommandResult execute(Model model) {
        if (message.equals(MESSAGE_FAILURE)) {
            return new CommandResult(message);
        }
        requireNonNull(model);
        model.setCurrentWeddingName(null);
        model.setAddressBook(new AddressBook());
        return new CommandResult(message);

    }

}
