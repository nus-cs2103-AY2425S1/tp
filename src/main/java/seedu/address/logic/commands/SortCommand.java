package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT;
import static seedu.address.logic.parser.SortOption.SORT_HOURS;
import static seedu.address.logic.parser.SortOption.SORT_NAME;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.SortOption;
import seedu.address.model.Model;
import seedu.address.model.person.Volunteer;
import seedu.address.model.person.comparators.NameComparator;
import seedu.address.model.person.comparators.VolunteerComparator;

/**
 * Sorts the person list based on the specified option.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the person list.\n"
            + "If no sort option is provided, the list is restored to its original order (i.e., order of addition).\n"
            + "Parameters: " + "[" + PREFIX_SORT + "SORT_OPTION]\n"
            + "Example: " + COMMAND_WORD + " s/name";

    public static final String MESSAGE_DEFAULT_SUCCESS = "Sorted by default order";
    public static final String MESSAGE_SORT_SUCCESS = "Sorted by %s";
    public static final String MESSAGE_UNSUPPORTED_SORT_OPTION = "Unsupported sort option: %s";
    public static final String MESSAGE_SORT_HOURS_WITH_NON_VOLUNTEERS =
            "Volunteers sorted by hours contributed. Non-volunteers are listed after volunteers.";
    public static final String MESSAGE_SORT_HOURS_NO_VOLUNTEERS =
            "No volunteers found. The list remains unsorted.";

    public final SortOption sortOption;

    private static final Logger logger = LogsCenter.getLogger(SortCommand.class);

    /**
     * Constructs a SortCommand with no sort option, which will reset the list to the default order.
     */
    public SortCommand() {
        this.sortOption = null;
    }

    /**
     * Constructs a SortCommand with the specified sort option.
     *
     * @param sortOption The sort option to apply to the list.
     */
    public SortCommand(SortOption sortOption) {
        this.sortOption = sortOption;
    }

    /**
     * Executes the sort command by applying the specified sort option to the person list.
     *
     * @param model The model on which to execute the sorting operation.
     * @return A CommandResult indicating the success or failure of the sort operation.
     * @throws CommandException If an unsupported sort option is provided.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.clearPersonSort();

        if (sortOption == null) {
            return executeDefaultSort(model);
        }

        switch (sortOption.toString()) {
        case SORT_NAME:
            return executeSortByName(model);
        case SORT_HOURS:
            return executeSortByHours(model);
        default:
            // Log that an unexpected sort option has been encountered
            logger.severe("Unexpected sort option encountered: " + sortOption
                    + ". This should not have happened as SortOption should validate inputs.");
            throw new CommandException(String.format(MESSAGE_UNSUPPORTED_SORT_OPTION, sortOption));
        }
    }

    /**
     * Resets the person list to its default order (insertion order).
     *
     * @param model The model on which to clear the sort.
     * @return A CommandResult indicating that the list has been sorted in the default order.
     */
    private static CommandResult executeDefaultSort(Model model) {
        return new CommandResult(MESSAGE_DEFAULT_SUCCESS);
    }

    /**
     * Sorts the person list by name in alphabetical order.
     *
     * @param model The model on which to apply the name sorting.
     * @return A CommandResult indicating that the list has been sorted by name.
     */
    private CommandResult executeSortByName(Model model) {
        model.updatePersonListSort(new NameComparator());
        return new CommandResult(String.format(MESSAGE_SORT_SUCCESS, sortOption));
    }

    /**
     * Sorts the person list by hours contributed for volunteers.
     * Volunteers are listed first, sorted by hours, and non-volunteers are listed after volunteers.
     * If there are no volunteers, the list remains unsorted.
     *
     * @param model The model on which to apply the sorting by hours.
     * @return A CommandResult indicating the outcome of the sorting operation:
     *         - If there are only volunteers, the list is sorted by hours.
     *         - If there are both volunteers and non-volunteers, volunteers are sorted by hours and non-volunteers
     *           are listed at the back.
     *         - If there are no volunteers, the list remains unsorted.
     */
    private CommandResult executeSortByHours(Model model) {
        if (!model.hasPersonsOfType(Volunteer.class)) {
            return new CommandResult(MESSAGE_SORT_HOURS_NO_VOLUNTEERS);
        }

        model.updatePersonListSort(new VolunteerComparator());

        if (model.hasOnlyPersonsOfType(Volunteer.class)) {
            return new CommandResult(String.format(MESSAGE_SORT_SUCCESS, sortOption));
        } else {
            return new CommandResult(MESSAGE_SORT_HOURS_WITH_NON_VOLUNTEERS);
        }
    }

    @Override
    public boolean equals(Object other) {
        // If both commands have the same sortOption, they are considered equal
        if (other == this) {
            return true;
        }

        if (!(other instanceof SortCommand)) {
            return false;
        }

        SortCommand otherCommand = (SortCommand) other;
        return (sortOption == null && otherCommand.sortOption == null)
                || (sortOption != null && sortOption.equals(otherCommand.sortOption));
    }
}
