package seedu.address.logic.commands.buyer;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.buyer.BuyerFulfilsPredicate;

/**
 * Lists all buyers in the buyer list.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_SUCCESS = "Viewing all buyers satisfying client type";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all buyers whose buyer types match the keyword"
            + "(buyer/seller) and displays them as a list with index numbers.\n"
            + "If there are no keywords specified, all buyers are listed.\n"
            + "Parameters: [buyer/seller](optional)\n"
            + "Example: " + COMMAND_WORD + " buyer";

    private final BuyerFulfilsPredicate predicate;

    public ViewCommand(BuyerFulfilsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredBuyerList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_BUYERS_LISTED_OVERVIEW, model.getFilteredBuyerList().size())
        );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewCommand)) {
            return false;
        }

        ViewCommand e = (ViewCommand) other;
        return predicate.equals(e.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
