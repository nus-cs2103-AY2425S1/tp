package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.PersonContainsTagsPredicate;

/**
 * Filters and lists all persons in the address book whose tags contain any of the specified keywords.
 * Tag matching is case-insensitive.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filterContact";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters all persons whose tags contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: "
            + PREFIX_TAG + "TAG " + PREFIX_TAG + "[MORE_TAGS]...\n"
            + "Example: " + COMMAND_WORD + " t/Customer t/Supplier";

    private final PersonContainsTagsPredicate predicate;

    /**
     * Constructs a new FilterCommand with the specified predicate.
     *
     * @param predicate the predicate to filter persons by tags
     */
    public FilterCommand(PersonContainsTagsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.updateFilteredPersonList(predicate);
        int filteredListSize = model.getFilteredPersonList().size();

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, filteredListSize));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FilterCommand)) {
            return false;
        }

        FilterCommand otherFilterCommand = (FilterCommand) other;
        return predicate.equals(otherFilterCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("predicate", predicate).toString();
    }
}