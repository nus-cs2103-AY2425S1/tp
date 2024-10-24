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
import seedu.address.model.person.Person;

/**
 * Finds and lists all suppliers in vendor whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindSupplierCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + PREFIX_SUPPLIER + ": Finds all suppliers whose name, "
            + "company and product contains the specified keyword (case-insensitive) "
            + "and displays them as a list with index numbers.\n"
            + "Parameters:\n"
            + PREFIX_NAME + "KEYWORD "
            + PREFIX_COMPANY + "KEYWORD "
            + PREFIX_PRODUCT + "KEYWORD \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_SUPPLIER + " "
            + PREFIX_NAME + "link "
            + PREFIX_COMPANY + "NUS "
            + PREFIX_PRODUCT + "m";


    private final List<Predicate<Person>> listOfPredicates;

    /**
     * Constructs a {@code FindSupplierCommand} with the specified list of predicates.
     *
     * @param listOfPredicates a list of predicates used to filter suppliers.
     *                         Each predicate corresponds to a specific criterion
     *                         (e.g., name, company, product).
     */
    public FindSupplierCommand(List<Predicate<Person>> listOfPredicates) {
        requireNonNull(listOfPredicates);
        assert !listOfPredicates.isEmpty();
        this.listOfPredicates = listOfPredicates;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        Predicate<Person> combindedPredicate = listOfPredicates.stream().reduce(x -> true, Predicate::and);
        model.updateFilteredPersonList(combindedPredicate);

        return new CommandResult(
                String.format(Messages.MESSAGE_SUPPLIERS_FOUND_OVERVIEW, model.getFilteredPersonList().size()));
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
