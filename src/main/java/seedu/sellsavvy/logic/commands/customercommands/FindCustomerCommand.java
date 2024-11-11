package seedu.sellsavvy.logic.commands.customercommands;

import static java.util.Objects.requireNonNull;

import seedu.sellsavvy.commons.util.ToStringBuilder;
import seedu.sellsavvy.logic.Messages;
import seedu.sellsavvy.logic.commands.Command;
import seedu.sellsavvy.logic.commands.CommandResult;
import seedu.sellsavvy.model.Model;
import seedu.sellsavvy.model.customer.NameContainsKeywordsPredicate;

/**
 * Finds and lists all customers in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCustomerCommand extends Command {

    public static final String COMMAND_WORD = "findcustomer";
    public static final String COMMAND_ALIAS = "findc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all customer(s) whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final NameContainsKeywordsPredicate predicate;

    public FindCustomerCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredCustomerList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_CUSTOMERS_LISTED_OVERVIEW, model.getFilteredCustomerList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCustomerCommand)) {
            return false;
        }

        FindCustomerCommand otherFindCustomerCommand = (FindCustomerCommand) other;
        return predicate.equals(otherFindCustomerCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
