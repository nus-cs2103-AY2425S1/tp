package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.ui.DisplayType;

/**
 * Clears the address book of all employees.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book employees have been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setAddressBookEmployee(new AddressBook());
        return new CommandResult(MESSAGE_SUCCESS, DisplayType.EMPLOYEE_LIST);
    }
}
