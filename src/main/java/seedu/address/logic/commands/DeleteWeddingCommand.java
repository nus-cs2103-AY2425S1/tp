package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingName;

/**
 * Deletes a wedding identified using its displayed index from the address book.
 */
public class DeleteWeddingCommand extends Command {
    public static final String COMMAND_WORD = "deletew";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the wedding identified by the index number used in the displayed wedding list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_WEDDING_SUCCESS = "Deleted Wedding: %1$s";

    private final Index targetIndex;

    public DeleteWeddingCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Wedding> lastShownList = model.getFilteredWeddingList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_WEDDING_DISPLAYED_INDEX);
        }

        Wedding weddingToDelete = lastShownList.get(targetIndex.getZeroBased());
        WeddingName weddingToDeleteName = weddingToDelete.getWeddingName();
        model.removeWedding(weddingToDelete);

        if (weddingToDeleteName.equals(model.getCurrentWeddingName().getValue())) {
            model.setCurrentWeddingName(null);
            model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        }

        return new CommandResult(String.format(MESSAGE_DELETE_WEDDING_SUCCESS,
                Messages.formatWedding(weddingToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteWeddingCommand)) {
            return false;
        }

        DeleteWeddingCommand otherDeleteWeddingCommand = (DeleteWeddingCommand) other;
        return targetIndex.equals(otherDeleteWeddingCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }

}
