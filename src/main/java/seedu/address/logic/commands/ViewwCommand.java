package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.PersonMatchesWeddingPredicate;
import seedu.address.model.wedding.NameMatchesWeddingPredicate;
import seedu.address.model.wedding.Wedding;

/**
 * Views wedding details of a person identified from the address book, using index or keyword.
 */
public class ViewwCommand extends Command {

    public static final String COMMAND_WORD = "vieww";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Views the wedding details of the person identified by the index number used in "
            + "the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer) or KEYWORD (the name of contact)\n"
            + "Example: " + COMMAND_WORD + " 1" + " or " + COMMAND_WORD + " alex";

    public static final String MESSAGE_VIEW_EMPTY_LIST_ERROR = "There are no wedding records to view.";
    public static final String MESSAGE_VIEW_WEDDING_SUCCESS = "Viewing Wedding Details of: %1$s";
    public static final String MESSAGE_DUPLICATE_HANDLING =
            "Please specify the index of the contact whose wedding details you want to view.\n"
                    + "Find the index from the list below and type vieww INDEX\n"
                    + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_NO_WEDDING_DETAILS = "No wedding details found for: %1$s";

    private final Index targetIndex;
    private final NameMatchesWeddingPredicate predicate;

    /**
     * Creates a ViewwCommand to view the wedding details of the specified contact
     */
    public ViewwCommand(Index targetIndex, NameMatchesWeddingPredicate predicate) {
        this.targetIndex = targetIndex;
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (this.targetIndex != null) {
            PersonMatchesWeddingPredicate weddingPredicateToView = viewWithIndex(model);
            model.updateFilteredPersonList(weddingPredicateToView);
            return new CommandResult(String.format(MESSAGE_VIEW_WEDDING_SUCCESS,
                    Messages.format(weddingPredicateToView.getWedding())));

        } else {
            PersonMatchesWeddingPredicate weddingPredicateToView = viewWithKeyword(model);

            if (weddingPredicateToView != null) {
                // Here you would check if the person has wedding details before displaying
                return new CommandResult(String.format(MESSAGE_VIEW_WEDDING_SUCCESS,
                        Messages.format(weddingPredicateToView.getWedding())));
            } else {
                return new CommandResult(String.format(MESSAGE_DUPLICATE_HANDLING));
            }
        }
    }

    /**
     * Performs view wedding command logic when the input is an index.
     *
     * @param model {@code Model} which the command should operate on
     * @return the person whose wedding details are to be viewed
     * @throws CommandException if an invalid index is given
     */
    private PersonMatchesWeddingPredicate viewWithIndex(Model model) throws CommandException {
        List<Wedding> lastShownList = model.getFilteredWeddingList();
        if (lastShownList.isEmpty()) {
            throw new CommandException(MESSAGE_VIEW_EMPTY_LIST_ERROR);
        }

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                    lastShownList.size()));
        }
        Wedding weddingToView = lastShownList.get(targetIndex.getZeroBased());

        return new PersonMatchesWeddingPredicate(weddingToView);
    }

    /**
     * Performs view wedding command logic when the input is a {@code String}.
     *
     * @param model {@code Model} which the command should operate on
     * @return the person whose wedding details are to be viewed
     * @throws CommandException if the filtered list using {@code predicate} is empty
     */
    private PersonMatchesWeddingPredicate viewWithKeyword(Model model) throws CommandException {
        model.updateFilteredWeddingList(predicate);
        List<Wedding> filteredList = model.getFilteredWeddingList();

        if (filteredList.isEmpty()) {
            throw new CommandException(MESSAGE_VIEW_EMPTY_LIST_ERROR);
        } else if (filteredList.size() == 1) {
            Wedding weddingToView = filteredList.get(0);
            return new PersonMatchesWeddingPredicate(weddingToView);
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
        if (!(other instanceof ViewwCommand)) {
            return false;
        }

        ViewwCommand otherViewwCommand = (ViewwCommand) other;

        // Both commands have null fields
        boolean bothHaveNullIndex = targetIndex == null && otherViewwCommand.targetIndex == null;
        boolean bothHaveNullPredicates = predicate == null && otherViewwCommand.predicate == null;

        // Both commands have non-null fields
        boolean bothHaveIndex = targetIndex != null && otherViewwCommand.targetIndex != null;
        boolean bothHavePredicates = predicate != null && otherViewwCommand.predicate != null;

        // Case 1: Both have null targetIndex and null predicate
        if (bothHaveNullIndex && bothHaveNullPredicates) {
            return true;
        }

        // Case 2: Both have targetIndex but null predicate
        if (bothHaveIndex && bothHaveNullPredicates) {
            return targetIndex.equals(otherViewwCommand.targetIndex);
        }

        // Case 3: Both have null targetIndex but have predicate
        if (bothHaveNullIndex && bothHavePredicates) {
            return predicate.equals(otherViewwCommand.predicate);
        }

        // All other cases are false
        return false;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
