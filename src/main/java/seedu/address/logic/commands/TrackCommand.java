package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.CategoryContainsKeywordsPredicate;

/**
 * Tracks and lists all persons in the address book who are in the specified category to the user.
 */
public class TrackCommand extends Command {

    public static final String COMMAND_WORD = "track";
    public static final String MESSAGE_ARGUMENTS = "Category: %1$s";

    public static final String MESSAGE_SUCCESS = "Listed all persons";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Tracks and lists all contacts who are in the category of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: CATEGORY (must be one of the predefined categories (student, company))\n"
            + "Example: " + COMMAND_WORD + " student";
    private final CategoryContainsKeywordsPredicate categoryPredicate;

    /**
     * TBC
     * @param categoryPredicate
     */
    public TrackCommand(CategoryContainsKeywordsPredicate categoryPredicate) {
        requireAllNonNull(categoryPredicate);
        this.categoryPredicate = categoryPredicate;
    }
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(categoryPredicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TrackCommand)) {
            return false;
        }

        TrackCommand e = (TrackCommand) other;
        return categoryPredicate.equals(e.categoryPredicate);
    }

}
