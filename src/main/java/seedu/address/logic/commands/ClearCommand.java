package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final CommandType COMMAND_TYPE = CommandType.CLEAR;
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";

    /**
     * Returns Command Type CLEAR
     *
     * @return Command Type CLEAR
     */
    @Override
    public CommandType getCommandType() {
        return COMMAND_TYPE;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setAddressBook(new AddressBook());
        return new CommandResult(MESSAGE_SUCCESS, COMMAND_TYPE);
    }
}
