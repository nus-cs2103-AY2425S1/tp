package bizbook.logic.commands;

import static java.util.Objects.requireNonNull;

import bizbook.model.AddressBook;
import bizbook.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {
    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes all contacts.\n"
            + "Parameters: None\n"
            + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setAddressBook(new AddressBook());
        model.setFocusPerson(null);
        CommandResult commandResult = new CommandResult(MESSAGE_SUCCESS);
        return commandResult;
    }
}
