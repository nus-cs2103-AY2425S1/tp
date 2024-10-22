package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;


/**
 * Sorts the person list based on name or date of last visit.
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_SUCCESS = "Sorted.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the presented list "
            + "in descending or ascending order according to "
            + "name or date of last visit.\n"
            + "Parameters: Parameter prefix/Order\n"
            + "Example: " + COMMAND_WORD + " n/ascending\n"
            + "If order is not specified (ex. n/) ascending order assumed.";

    private final String sortParameter;

    private final boolean isAscendingOrder;

    /**
     * Instantiates SortCommand based on a particular Person parameter
     * in an ascending or descending order.
     *
     * @param sortParameter
     * @param isAscendingOrder
     */
    public SortCommand(String sortParameter, boolean isAscendingOrder) {
        this.sortParameter = sortParameter;
        this.isAscendingOrder = isAscendingOrder;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.sortFilteredPersonList(sortParameter, isAscendingOrder);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
