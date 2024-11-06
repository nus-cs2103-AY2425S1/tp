package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_WEDDINGS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.wedding.NameMatchesWeddingPredicate;
import seedu.address.model.wedding.Wedding;

/**
 * Deletes a wedding identified from the address book, using index or keyword.
 */
public class DeletewCommand extends Command {
    public static final String COMMAND_WORD = "deletew";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the wedding identified by the index number used in the displayed wedding list or keyword.\n"
            + "Parameters: INDEX (must be a positive integer) or KEYWORD (the name of wedding)\n"
            + "Example: " + COMMAND_WORD + " 1 or " + COMMAND_WORD + " alex";

    public static final String MESSAGE_DELETE_EMPTY_LIST_ERROR = "There is nothing to delete.";
    public static final String MESSAGE_DELETE_WEDDING_SUCCESS = "Deleted Wedding: %1$s";
    public static final String MESSAGE_DUPLICATE_HANDLING =
            "Please specify the index of the wedding you want to delete.\n"
                    + "Find the index from the list below and type deletew INDEX\n"
                    + "Example: " + COMMAND_WORD + " 1";
    private final Index targetIndex;
    private final NameMatchesWeddingPredicate predicate;

    /**
     * Creates a DeletewCommand object to delete the wedding at the specified {@code Index} or keyword.
     */
    public DeletewCommand(Index targetIndex, NameMatchesWeddingPredicate predicate) {
        this.targetIndex = targetIndex;
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (this.targetIndex != null) {
            Wedding weddingToDelete = getWeddingByIndex(model);
            model.deleteWedding(weddingToDelete);
            model.updateFilteredWeddingList(PREDICATE_SHOW_ALL_WEDDINGS); // Reset filter
            return new CommandResult(String.format(MESSAGE_DELETE_WEDDING_SUCCESS, Messages.format(weddingToDelete)));
        } else {
            Wedding weddingToDelete = getWeddingByKeyword(model);
            if (weddingToDelete != null) {
                model.deleteWedding(weddingToDelete);
                model.updateFilteredWeddingList(PREDICATE_SHOW_ALL_WEDDINGS); // Reset filter
                return new CommandResult(String.format(MESSAGE_DELETE_WEDDING_SUCCESS,
                        Messages.format(weddingToDelete)));
            } else {
                return new CommandResult(String.format(MESSAGE_DUPLICATE_HANDLING));
            }
        }
    }

    /**
     * Gets the wedding by index without deleting them.
     */
    private Wedding getWeddingByIndex(Model model) throws CommandException {
        List<Wedding> lastShownList = model.getFilteredWeddingList();
        if (lastShownList.isEmpty()) {
            throw new CommandException(MESSAGE_DELETE_EMPTY_LIST_ERROR);
        }

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_WEDDING_DISPLAYED_INDEX,
                    lastShownList.size()));
        }

        return lastShownList.get(targetIndex.getZeroBased());
    }

    /**
     * Gets the wedding by keyword without deleting them.
     */
    private Wedding getWeddingByKeyword(Model model) throws CommandException {
        model.updateFilteredWeddingList(predicate);
        List<Wedding> filteredList = model.getFilteredWeddingList();

        if (filteredList.isEmpty()) {
            throw new CommandException(MESSAGE_DELETE_EMPTY_LIST_ERROR);
        } else if (filteredList.size() == 1) {
            return filteredList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeletewCommand)) {
            return false;
        }

        DeletewCommand otherDeletewCommand = (DeletewCommand) other;

        // Both commands have null fields
        boolean bothHaveNullIndex = targetIndex == null && otherDeletewCommand.targetIndex == null;
        boolean bothHaveNullPredicates = predicate == null && otherDeletewCommand.predicate == null;

        // Both commands have non-null fields
        boolean bothHaveIndex = targetIndex != null && otherDeletewCommand.targetIndex != null;
        boolean bothHavePredicates = predicate != null && otherDeletewCommand.predicate != null;

        // Case 1: Both have null targetIndex and null predicate
        if (bothHaveNullIndex && bothHaveNullPredicates) {
            return true;
        }

        // Case 2: Both have targetIndex but null predicate
        if (bothHaveIndex && bothHaveNullPredicates) {
            return targetIndex.equals(otherDeletewCommand.targetIndex);
        }

        // Case 3: Both have null targetIndex but have predicate
        if (bothHaveNullIndex && bothHavePredicates) {
            return predicate.equals(otherDeletewCommand.predicate);
        }

        // All other cases are false
        return false;
    }

    @Override
    public String toString() {
        if (this.targetIndex != null) {
            return new ToStringBuilder(this)
                    .add("targetIndex", targetIndex)
                    .toString();
        } else {
            return new ToStringBuilder(this)
                    .add("targetKeywords", predicate.toString())
                    .toString();
        }

    }
}
