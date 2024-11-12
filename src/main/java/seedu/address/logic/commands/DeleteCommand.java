package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.model.Model;
import seedu.address.model.employee.Employee;
import seedu.address.ui.DisplayType;

/**
 * Deletes an employee identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the employee identified by the index number used in the displayed employee list,"
            + " as well as associated assignments.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_EMPLOYEE_SUCCESS = "Deleted Employee: %1$s";

    private static final Logger logger = LogsCenter.getLogger(AddressBookParser.class);

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Employee> lastShownList = model.getFilteredEmployeeList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            logger.warning("Failed to " + COMMAND_WORD + "\n" + targetIndex.getZeroBased()
                    + "\nReason:" + Messages.MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX);

            throw new CommandException(Messages.MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX);
        }

        Employee employeeToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteEmployee(employeeToDelete);

        // Delete associated assignments
        model.deleteAllAssignments(employeeToDelete.getEmployeeId());

        // Deleted employee successfully
        logger.fine(COMMAND_WORD + " employee\n" + employeeToDelete);

        return new CommandResult(String.format(MESSAGE_DELETE_EMPLOYEE_SUCCESS, Messages.format(employeeToDelete)),
                DisplayType.EMPLOYEE_LIST);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
