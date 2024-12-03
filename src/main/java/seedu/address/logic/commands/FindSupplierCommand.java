package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUPPLIER;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.supplier.Supplier;

/**
 * Finds and lists all suppliers in VendorVault whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindSupplierCommand extends FindCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_SUPPLIER + ": Finds all suppliers "
            + "whose name, company and product contains the specified keyword (case-insensitive) "
            + "and displays them as a list with index numbers.\n"
            + "Parameters:"
            + PREFIX_NAME + "NAME "
            + PREFIX_COMPANY + "COMPANY "
            + PREFIX_PRODUCT + "PRODUCT \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_SUPPLIER + " "
            + PREFIX_NAME + "link "
            + PREFIX_COMPANY + "NUS "
            + PREFIX_PRODUCT + "m";


    private final List<Predicate<Supplier>> listOfPredicates;

    /**
     * Constructs a {@code FindSupplierCommand} with the specified list of predicates.
     *
     * @param listOfPredicates a list of predicates used to filter suppliers.
     */
    public FindSupplierCommand(List<Predicate<Supplier>> listOfPredicates) {
        requireNonNull(listOfPredicates);
        assert !listOfPredicates.isEmpty();
        this.listOfPredicates = listOfPredicates;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        Predicate<Supplier> combinedPredicate = listOfPredicates.stream().reduce(x -> true, Predicate::and);
        model.updateFilteredSupplierList(combinedPredicate);

        return new CommandResult(
                String.format(Messages.MESSAGE_SUPPLIERS_FOUND_OVERVIEW, model.getFilteredSupplierList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindSupplierCommand)) {
            return false;
        }

        FindSupplierCommand otherFindCommand = (FindSupplierCommand) other;
        return listOfPredicates.equals(otherFindCommand.listOfPredicates);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicates", listOfPredicates.toString())
                .toString();
    }
}
