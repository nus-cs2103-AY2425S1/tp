package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.ui.DisplayType;

/**
 * Clears the address book of all projects.
 */
public class ClearProjectCommand extends Command {

    public static final String COMMAND_WORD = "clearproject";
    public static final String MESSAGE_SUCCESS = "Address book projects have been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setAddressBookProject(new AddressBook());
        return new CommandResult(MESSAGE_SUCCESS, DisplayType.PROJECT_LIST);
    }
}
