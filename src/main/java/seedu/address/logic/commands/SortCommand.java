package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_SUCCESS = "Sorted.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the presented list "
            + "by the specified person parameter (in descending or ascending order).\n"
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
