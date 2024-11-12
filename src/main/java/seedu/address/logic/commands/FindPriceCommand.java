package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.restaurant.PriceContainsKeywordsPredicate;

/**
 * Finds and lists all restaurants in address book that contains any of the price labels specified.
 */
public class FindPriceCommand extends Command {

    public static final String COMMAND_WORD = "price";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all restaurants of a specific price labels "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: PRICE_LABEL [MORE_PRICE_LABELS]...\n"
            + "Example: " + COMMAND_WORD + " $ $$";

    private final PriceContainsKeywordsPredicate predicate;

    public FindPriceCommand(PriceContainsKeywordsPredicate predicate) {
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
        if (!(other instanceof FindPriceCommand)) {
            return false;
        }

        FindPriceCommand otherFindCommand = (FindPriceCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
