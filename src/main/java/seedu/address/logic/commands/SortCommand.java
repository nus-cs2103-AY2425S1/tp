package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.SortOrder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Sorts the currently filtered list of Persons in a user-determined order
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_ALIAS = "so";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the list of contacts "
            + "by ascending/descending/original order, based on their names.\n"
            + "Parameters: [asc/desc/og]\n"
            + "Example: " + COMMAND_WORD + " asc";

    public static final String MESSAGE_SUCCESS = "Sorted list in %s order";

    private final SortOrder order;

    /**
     * Creates a SortCommand to sort the list of Persons in the specified {@code order}
     */
    public SortCommand(SortOrder order) {
        requireNonNull(order);
        this.order = order;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (order == SortOrder.ASC) {
            model.updateSortedPersonListComparator((p1, p2) ->
                    String.CASE_INSENSITIVE_ORDER.compare(p1.getName().toString(), p2.getName().toString()));
        } else if (order == SortOrder.DESC) {
            model.updateSortedPersonListComparator((p1, p2) ->
                    String.CASE_INSENSITIVE_ORDER.compare(p2.getName().toString(), p1.getName().toString()));
        } else if (order == SortOrder.OG) {
            model.setSortedListToDefault();
        } else {
            throw new CommandException("Invalid arguments"); // this should not happen ever
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, order));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SortCommand otherSortCommand)) {
            return false;
        }

        return order == otherSortCommand.order;
    }
}
