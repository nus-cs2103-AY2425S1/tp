package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.ui.DisplayType;

/**
 * Clears the address book of all persons.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book persons have been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setAddressBookPerson(new AddressBook());
        return new CommandResult(MESSAGE_SUCCESS, DisplayType.PERSON_LIST);
    }
}
