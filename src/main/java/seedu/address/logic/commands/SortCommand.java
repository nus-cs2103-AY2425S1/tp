package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE;

import seedu.address.model.Model;

/**
 * Sorts the list of contacts either by name or by schedule in ascending or descending order.
 * If order is not provided, it will be sorted in ascending order by default.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts list in ascending or descending by schedule time or by name in alphabetical order\n"
            + "If order is not provided, it will sort in ascending order by default.\n"
            + "Only accepts one prefix.\n"
            + "Parameters: "
            + PREFIX_NAME + "[ORDER] "
            + PREFIX_SCHEDULE + "[ORDER] "
            + "Example: " + COMMAND_WORD + PREFIX_NAME + " asc/desc/ascending/descending (case insensitive)";

    public static final String MESSAGE_SUCCESS = "Successfully sorted";
    public static final String ASCENDING = "asc";
    public static final String DESCENDING = "desc";
    private String order;
    private Boolean toSortBySchedule;
    /**
     * Creates a SortCommand to sort the list of contacts.
     *
     * @param order the sorting order, either "asc" or "desc".
     * @param toSortBySchedule a flag indicating if the sorting is to be done by schedule.
     *      If false, it will be sorted alphabetically by name.
     */
    public SortCommand(String order, Boolean toSortBySchedule) {
        this.order = order;
        this.toSortBySchedule = toSortBySchedule;
    }
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.sortFilteredPersonList(order, toSortBySchedule);
        return new CommandResult(MESSAGE_SUCCESS);
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SortCommand)) {
            return false;
        }
        SortCommand otherSortCommand = (SortCommand) other;
        return order.equals(otherSortCommand.order) && (toSortBySchedule == otherSortCommand.toSortBySchedule);
    }
}
