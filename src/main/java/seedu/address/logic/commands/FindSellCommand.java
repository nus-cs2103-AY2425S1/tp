package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.SellPropertyContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindSellCommand extends Command {

    public static final String COMMAND_WORD = "findSell";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds a particular property that a person "
            + "wants to sell, by any of its fields (housing type, postal code, unit number, buying price, tags).\n "
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " condo 533221 01-21";

    private final SellPropertyContainsKeywordsPredicate predicate;


    /**
     * Creates an FindSellCommand to find a name matching the specified {@code SellPropertyContainsKeywordPredicate}
     *
     * @param predicate the predicate used to filter buy properties based on given keywords
     */
    public FindSellCommand(SellPropertyContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindSellCommand otherFindSellCommand)) {
            return false;
        }

        return predicate.equals(otherFindSellCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
