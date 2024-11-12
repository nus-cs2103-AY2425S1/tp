package seedu.address.logic.commands.vendor;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Assigns an existing contact in the address book to become a vendor.
 */
public class AssignVendorCommand extends Command {

    public static final String COMMAND_WORD = "assign-vendor";

    public static final String COMMAND_KEYWORD = "asv";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Assigns the person identified by the index number used in the last person listing "
            + "as a vendor.\n"
            + "Parameters: INDEX (must be a positive integer) \n"
            + "Example: " + COMMAND_WORD + " 1 ";

    private final Index targetIndex;


    /**
     * Creates an AssignVendorCommand to assign the specified {@code Person} as a vendor.
     */
    public AssignVendorCommand(Index targetIndex) {
        assert targetIndex != null : "Target index must not be null";
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size() || targetIndex.getZeroBased() < 0) {
            throw new CommandException(String.format(
                    Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, 1, lastShownList.size()
            ));
        }

        Person personToAssign = lastShownList.get(targetIndex.getZeroBased());
        assert personToAssign != null : "Person to assign must not be null";

        // need to change to check if model already has vendor
        if (model.hasVendor(personToAssign)) {
            throw new CommandException(String.format(Messages.MESSAGE_DUPLICATE_VENDOR, personToAssign.getName()));
        }

        model.assignVendor(personToAssign);
        assert model.hasVendor(personToAssign) : "Vendor was not assigned correctly";
        return new CommandResult(String.format(Messages.MESSAGE_ADD_VENDOR_SUCCESS, personToAssign.getName()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AssignVendorCommand otherAssignVendorCommand)) {
            return false;
        }

        return targetIndex.equals(otherAssignVendorCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAssignVendor", targetIndex)
                .toString();
    }
}
