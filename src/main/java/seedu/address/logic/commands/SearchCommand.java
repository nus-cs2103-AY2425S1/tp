package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BEGIN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE;

import seedu.address.model.Model;

/**
 * Sorts the list of contacts either by name or by schedule in ascending or descending order.
 * If order is not provided, it will be sorted in ascending order by default.
 */
public class SearchCommand extends Command {

    public static final String COMMAND_WORD = "search";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Search for schedules within the provided time period\n"
            + "Either begin time or end time or both has to be provided.\n"
            + "Begin time and end time must be in format YYYY-MM-DD HH:MM.\n"
            + "If begin time is not provided, it will search for all schedule before end time.\n"
            + "If end time is not provided, it will search for all schedule after begin time.\n"
            + "Parameters: "
            + PREFIX_BEGIN + "start time"
            + PREFIX_END + "end time"
            + "Example: " + COMMAND_WORD + PREFIX_BEGIN + "2024-10-10 00:00" + PREFIX_END + "2024-10-12 00:00";

    public static final String MESSAGE_SUCCESS = "Search successful";
    private Date;
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
