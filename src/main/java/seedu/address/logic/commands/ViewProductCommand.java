package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.product.Product;

/**
 * Finds and lists all suppliers in address book whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class ViewProductCommand extends Command {

    public static final String COMMAND_WORD = CommandWords.VIEW_PRODUCT_COMMAND;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all products whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " chocolate ice cream";

    private final Predicate<Product> predicate;

    public ViewProductCommand(Predicate<Product> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredProductList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PRODUCTS_LISTED_OVERVIEW, model.getFilteredProductList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewProductCommand)) {
            return false;
        }

        ViewProductCommand otherViewProductCommand = (ViewProductCommand) other;
        return predicate.equals(otherViewProductCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
