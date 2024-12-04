package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.supplier.Supplier;

/**
 * Finds and lists all suppliers in address book whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class ViewSupplierCommand extends Command {

    public static final String COMMAND_WORD = CommandWords.VIEW_SUPPLIER_COMMAND;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all suppliers whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final Predicate<Supplier> predicate;

    /**
     * Creates a ViewSupplierCommand to view suppliers that match the specified {@code Predicate} and display settings.
     */
    public ViewSupplierCommand(Predicate<Supplier> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredSupplierList(predicate);

        CommandResult commandResult = new CommandResult(
                String.format(Messages.MESSAGE_SUPPLIERS_LISTED_OVERVIEW, model.getModifiedSupplierList().size()));
        commandResult.setShowSupplier();
        return commandResult;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewSupplierCommand)) {
            return false;
        }

        ViewSupplierCommand otherViewSupplierCommand = (ViewSupplierCommand) other;
        return predicate.equals(otherViewSupplierCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
