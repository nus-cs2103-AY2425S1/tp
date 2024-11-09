package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.BuyPropertyContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindBuyCommand extends Command {

    public static final String COMMAND_WORD = "findBuy";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds a particular property that a person "
            + "wants to buy, by any of its fields (housing type, postal code, unit number, buying price, tags).\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " condo 533221 01-21";

    private final BuyPropertyContainsKeywordsPredicate predicate;


    /**
     * Creates an FindBuyCommand to find a name matching the specified {@code BuyPropertyContainsKeywordPredicate}
     *
     * @param predicate the predicate used to filter buy properties based on given keywords
     */
    public FindBuyCommand(BuyPropertyContainsKeywordsPredicate predicate) {
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
        if (!(other instanceof FindBuyCommand otherFindBuyCommand)) {
            return false;
        }

        return predicate.equals(otherFindBuyCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
