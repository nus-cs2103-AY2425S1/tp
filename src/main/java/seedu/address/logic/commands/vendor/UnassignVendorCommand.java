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
 * Unassigns an existing contact in the address book to become a vendor.
 * The person must currently be a vendor in the address book.
 */
public class UnassignVendorCommand extends Command {

    public static final String COMMAND_WORD = "unassign-vendor";

    public static final String COMMAND_KEYWORD = "uv";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unassigns the vendor identified by the index number used in the displayed person list.\n"
            + "The person becomes a normal contact in the addressbook and cannot have tasks assigned to it.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private final Index targetIndex;
    private boolean force = false;

    /**
     * Creates an UnassignVendorCommand to assign the specified {@code Person} as a vendor.
     */
    public UnassignVendorCommand(Index targetIndex, boolean force) {
        assert targetIndex != null : "Target index must not be null";
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
        this.force = force;
    }

    /**
     * Creates an UnassignVendorCommand to assign the specified {@code Person} as a vendor, defaulting to a
     * non-forced command.
     */
    public UnassignVendorCommand(Index targetIndex) {
        assert targetIndex != null : "Target index must not be null";
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size() || targetIndex.getZeroBased() < 0) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                    1, model.getFilteredPersonList().size()));
        }

        Person personToUnassign = lastShownList.get(targetIndex.getZeroBased());
        assert personToUnassign != null : "Person to unassign must not be null";

        // need to change to check if model has the vendor already existing
        if (!model.hasVendor(personToUnassign)) {
            throw new CommandException(String.format(Messages.MESSAGE_UNASSIGN_VENDOR_FAILURE_NOT_VENDOR,
                    personToUnassign.getName()));
        }

        if (personToUnassign.hasTasks()) {
            if (this.force) {
                personToUnassign.clearTasks();
            } else {
                throw new CommandException(String.format(Messages.MESSAGE_UNASSIGN_VENDOR_FAILURE_TASK_EXISTS,
                        personToUnassign.getName())
                + ".\n"
                + Messages.MESSAGE_FORCE_UNASSIGN_VENDOR);
            }
        }

        model.unassignVendor(personToUnassign);
        assert !model.hasVendor(personToUnassign) : "Vendor was not unassigned correctly";

        return new CommandResult(String.format(
                Messages.MESSAGE_UNASSIGN_VENDOR_SUCCESS, Messages.format(personToUnassign)
        ));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        UnassignVendorCommand otherUnassignVendorCommand = (UnassignVendorCommand) other;
        return targetIndex.equals(otherUnassignVendorCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toUnassignVendor", targetIndex)
                .toString();
    }

}
