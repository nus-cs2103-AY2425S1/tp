package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORTORDER;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.comparator.PersonComparator;

/**
 * Sorts the current filtered list based on the specified sort type.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the current list based on the specified field "
            + "and order.\n"
            + "To reset the sorted order: " + COMMAND_WORD + " reset\n"
            + "Parameters: " + "FIELD " + PREFIX_SORTORDER + "ORDER\n"
            + "Acceptable value for FIELD: github, name, telegram\n"
            + "Acceptable value for ORDER: asc, desc\n"
            + "Example: " + COMMAND_WORD + " name " + PREFIX_SORTORDER + "asc";

    public static final String MESSAGE_SUCCESS = "List sorted based on %1$s in %2$s order";
    public static final String RESET_MESSAGE_SUCCESS = "List sorting set to default order.";
    public static final String MESSAGE_INVALID_ORDER = "Order should only be asc or desc.";
    public static final String MESSAGE_INVALID_FIELD = "Field should only be github, name, telegram or reset.";
    private final PersonComparator comparator;

    public SortCommand(PersonComparator comparator) {
        this.comparator = comparator;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateSortedPersonList(comparator);

        if (comparator == null) {
            return new CommandResult(RESET_MESSAGE_SUCCESS);
        }
        return new CommandResult(
                String.format(MESSAGE_SUCCESS, comparator.getSortField(), comparator.getSortOrder()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instaceof handles nulls
        if (!(other instanceof SortCommand)) {
            return false;
        }

        SortCommand otherSortCommand = (SortCommand) other;

        if (comparator == null) {
            return otherSortCommand.comparator == null;
        }
        return comparator.equals(otherSortCommand.comparator);
    }

    @Override
    public String toString() {
        if (comparator == null) {
            return SortCommand.class.getCanonicalName() + "{comparator=null}";
        }
        return new ToStringBuilder(this)
                .add("comparator", comparator)
                .toString();
    }
}
