package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.restaurant.TagContainsKeywordsPredicate;

/**
 * Finds and lists all restaurants in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindTagCommand extends Command {

    public static final String COMMAND_WORD = "tags";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all restaurants whose tags contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: TAGS [MORE_TAGS]...\n"
            + "Example: " + COMMAND_WORD + " halal western";

    private final TagContainsKeywordsPredicate predicate;

    public FindTagCommand(TagContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredRestaurantList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_RESTAURANTS_LISTED_OVERVIEW, model.getFilteredRestaurantList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindTagCommand)) {
            return false;
        }

        FindTagCommand otherFindCommand = (FindTagCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
