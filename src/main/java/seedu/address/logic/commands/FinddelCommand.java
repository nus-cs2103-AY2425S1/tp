package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.model.Model;
import seedu.address.model.delivery.ItemNameContainsKeywordPredicate;

/**
 * Finds and lists all deliveries for the inspected person.
 * Matches item names that contain any of the argument keywords.
 * Keyword matching is case insensitive and matches substrings.
 */
public class FinddelCommand extends Command {

    public static final String COMMAND_WORD = "finddel";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all deliveries which items contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " monitor keyboard";

    public static final String MESSAGE_NOT_IN_INSPECT = "This command can only be used in the inspect window!";

    private final ItemNameContainsKeywordPredicate predicate;

    public FinddelCommand(ItemNameContainsKeywordPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (AddressBookParser.getInspect()) {
            requireNonNull(model);
            model.updateFilteredDeliveryList(predicate);
            return new CommandResult(
                    String.format(Messages.MESSAGE_DELIVERIES_LISTED_OVERVIEW, model.getFilteredDeliveryList().size())
            );
        } else {
            throw new CommandException(MESSAGE_NOT_IN_INSPECT);
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
