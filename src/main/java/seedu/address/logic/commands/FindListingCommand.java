package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.listing.ListingContainsKeywordsPredicate;

/**
 * Finds and lists all listings in the system whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindListingCommand extends Command {

    public static final String COMMAND_WORD = "findListings";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all listings whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " Warton Riverdale";

    private final ListingContainsKeywordsPredicate predicate;

    public FindListingCommand(ListingContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredListingList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_LISTINGS_LISTED_OVERVIEW, model.getFilteredListingList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FindListingCommand)) {
            return false;
        }

        FindListingCommand otherFindListingsCommand = (FindListingCommand) other;
        return predicate.equals(otherFindListingsCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
