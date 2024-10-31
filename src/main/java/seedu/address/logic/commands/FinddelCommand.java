package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.model.delivery.ItemNameContainsKeywordPredicate;
import seedu.address.model.Model;

public class FinddelCommand extends Command{

    public static final String COMMAND_WORD = "finddel";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all deliveries which items contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " monitor keyboard";

    private final ItemNameContainsKeywordPredicate predicate;

    public FinddelCommand(ItemNameContainsKeywordPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        if (AddressBookParser.getInspect()) {
            requireNonNull(model);
            model.updateFilteredDeliveryList(predicate);
            return new CommandResult(
                    String.format(Messages.MESSAGE_DELIVERIES_LISTED_OVERVIEW, model.getFilteredDeliveryList().size())
            );
        } else {
            return new CommandResult(
                    String.format(Messages.MESSAGE_DELIVERIES_LISTED_OVERVIEW, model.getFilteredDeliveryList().size())
            );
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FinddelCommand)) {
            return false;
        }

        FinddelCommand otherFinddelCommand = (FinddelCommand) other;
        return predicate.equals(otherFinddelCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
