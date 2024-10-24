package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_BY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_ORDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUPPLIER;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.SupplierSortComparator;
/**
 * Sorts and lists all suppliers in address book by the field.
 */
public class SortSupplierCommand extends SortCommand {
    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_SUPPLIER
            + " : Sorts all suppliers by name, phone or email and "
            + "displays them as a list with index numbers.\n"
            + "Parameters:\n"
            + PREFIX_SORT_ORDER + "SORT ORDER ('a' for ascending and 'd' for descending)\n"
            + PREFIX_SORT_BY + "SORT BY ('n' for name)\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_SUPPLIER + " "
            + PREFIX_SORT_ORDER + "a "
            + PREFIX_SORT_BY + "n";
    private final SupplierSortComparator comparator;
    public SortSupplierCommand(SupplierSortComparator comparator) {
        this.comparator = comparator;
    }
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateSortedSupplierList(comparator);
        return new CommandResult(
                String.format(Messages.MESSAGE_SUPPLIER_SORTED_OVERVIEW,
                        model.getSortedSupplierList().size(),
                        comparator.toSortByString(),
                        comparator.toSortOrderString()));
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof SortSupplierCommand)) {
            return false;
        }
        SortSupplierCommand otherSortSupplierCommand = (SortSupplierCommand) other;
        return comparator.equals(otherSortSupplierCommand.comparator);
    }

}
