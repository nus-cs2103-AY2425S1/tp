package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUPPLIER;

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


    private final Predicate<Person> predicate;

    public FindSupplierCommand(Predicate<Person> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);

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
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
