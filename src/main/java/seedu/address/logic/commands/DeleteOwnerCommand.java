package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.owner.Owner;

/**
 * Deletes a person identified using it's displayed index from PawPatrol.
 */
public class DeleteOwnerCommand extends DeleteCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the owner identified by the index number used in the displayed owner list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_OWNER_SUCCESS = "Deleted Owner: %1$s";

    private final Index targetIndex;

    public DeleteOwnerCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Owner> lastShownList = model.getFilteredOwnerList(); // must add new method to get owner

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_OWNER_DISPLAYED_INDEX);
        }

        Owner ownerToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteOwner(ownerToDelete);
        model.deleteLinksWithId(ownerToDelete.getUniqueID());
        return new CommandResult(String.format(MESSAGE_DELETE_OWNER_SUCCESS, Messages.format(ownerToDelete)));

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

        DeleteOwnerCommand otherDeleteCommand = (DeleteOwnerCommand) other;
        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}

