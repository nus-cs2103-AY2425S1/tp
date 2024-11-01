package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.AddressBook;
import seedu.address.model.EventBook;
import seedu.address.model.Model;

/**
 * Clears the contact book or events book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_PERSONS_SUCCESS = "Contact book has been cleared!";
    public static final String MESSAGE_EVENTS_SUCCESS = "Event book has been cleared!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Clears the address book or event book. "
            + "Parameters: -p (for person book) or -e (for event book)\n"
            + "Example: " + COMMAND_WORD + " -p";

    private final boolean isPersonBook;

    public ClearCommand(boolean isPersonBook) {
        this.isPersonBook = isPersonBook;
    }



    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (isPersonBook) {
            model.setAddressBook(new AddressBook());
            return new CommandResult(MESSAGE_PERSONS_SUCCESS);
        } else {
            model.setEventBook(new EventBook());
            return new CommandResult(MESSAGE_EVENTS_SUCCESS);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ClearCommand)) {
            return false;
        }

        ClearCommand otherCommand = (ClearCommand) other;
        return isPersonBook == otherCommand.isPersonBook;
    }

    @Override
    public int hashCode() {
        return Boolean.hashCode(isPersonBook);
    }
}
