package seedu.sellsavvy.logic.commands.customercommands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.sellsavvy.commons.core.index.Index;
import seedu.sellsavvy.commons.util.ToStringBuilder;
import seedu.sellsavvy.logic.Messages;
import seedu.sellsavvy.logic.commands.Command;
import seedu.sellsavvy.logic.commands.CommandResult;
import seedu.sellsavvy.logic.commands.exceptions.CommandException;
import seedu.sellsavvy.model.Model;
import seedu.sellsavvy.model.customer.Customer;

/**
 * Deletes a customer identified using it's displayed index from the address book.
 */
public class DeleteCustomerCommand extends Command {

    public static final String COMMAND_WORD = "deletecustomer";
    public static final String COMMAND_ALIAS = "deletec";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the customer identified by the index number used in the displayed customer list.\n"
            + "Parameters: CUSTOMER_INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Customer: %1$s";

    private final Index targetIndex;

    /**
     * Creates an DeleteCustomerCommand to delete the specified {@param targetIndex}
     */
    public DeleteCustomerCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Customer> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Customer customerToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deletePerson(customerToDelete);

        if (model.isSelectedPerson(customerToDelete)) {
            model.updateSelectedPerson(null);
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(customerToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCustomerCommand)) {
            return false;
        }

        DeleteCustomerCommand otherDeleteCustomerCommand = (DeleteCustomerCommand) other;
        return targetIndex.equals(otherDeleteCustomerCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
