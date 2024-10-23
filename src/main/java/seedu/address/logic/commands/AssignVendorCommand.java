package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Assigns an existing contact in the address book to become a vendor.
 */
public class AssignVendorCommand extends Command {

    public static final String COMMAND_WORD = "assign-vendor";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Assigns the person identified by the index number used in the last person listing "
            + "as a vendor.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_SUCCESS = "%1$s has been added as a vendor.";

    public static final String MESSAGE_DUPLICATE_VENDOR = "This person is already a vendor.";

    private final Index targetIndex;


    /**
     * Creates an AssignVendorCommand to assign the specified {@code Person} as a vendor.
     */
    public AssignVendorCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToAssign = lastShownList.get(targetIndex.getZeroBased());

        // need to change to check if model has the vendor already existing
        if (model.hasVendor(personToAssign)) {
            throw new CommandException(MESSAGE_DUPLICATE_VENDOR);
        }

        model.assignVendor(personToAssign);
        return new CommandResult(String.format(MESSAGE_SUCCESS, personToAssign.getName()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AssignVendorCommand)) {
            return false;
        }

        AssignVendorCommand otherAssignVendorCommand = (AssignVendorCommand) other;
        return targetIndex.equals(otherAssignVendorCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAssignVendor", targetIndex)
                .toString();
    }
}
