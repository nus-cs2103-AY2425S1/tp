package seedu.address.logic.commands.listcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EMPLOYEES;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.ui.DisplayType;

/**
 * Lists all employees in the address book to the user.
 */
public class ListEmployeesCommand extends ListCommand {

    // Command word using the 'list' prefix + 'employees'
    public static final String COMMAND_WORD = COMMAND_PREFIX + "employees";

    public static final String MESSAGE_SUCCESS = "%1$s employees listed!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredEmployeeList(PREDICATE_SHOW_ALL_EMPLOYEES);
        int numEmployeesShown = model.getFilteredEmployeeList().size();
        return new CommandResult(String.format(MESSAGE_SUCCESS, numEmployeesShown), DisplayType.EMPLOYEE_LIST);
    }
}
