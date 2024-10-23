package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.pet.Pet;

/**
 * Deletes a person identified using it's displayed index from PawPatrol.
 */
public class DeletePetCommand extends DeleteCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the Pet identified by the index number used in the displayed Pet list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PET_SUCCESS = "Deleted Pet: %1$s";

    private final Index targetIndex;

    public DeletePetCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Pet> lastShownList = model.getFilteredPetList(); // must add new method to get Pet

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PET_DISPLAYED_INDEX);
        }

        Pet petToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deletePet(petToDelete);
        model.deleteLinksWithId(petToDelete.getUniqueID());
        return new CommandResult(String.format(MESSAGE_DELETE_PET_SUCCESS, Messages.format(petToDelete)));

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

        DeletePetCommand otherDeleteCommand = (DeletePetCommand) other;
        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
