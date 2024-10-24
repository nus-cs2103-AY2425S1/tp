package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";
    public static final String MESSAGE_PROMPT = "This will delete ALL data. Confirm command? (y/n)";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(MESSAGE_PROMPT, () -> confirmDelete(model));
    }

    private CommandResult confirmDelete(Model model) {
        model.setAddressBook(new AddressBook());
        model.setLastViewedClient(null);
        model.updateVisibleRentalInformationList(List.of());

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
