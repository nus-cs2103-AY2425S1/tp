package seedu.address.logic.commands.eventcommands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.types.common.DateTime;
import seedu.address.model.types.common.EventUpcomingPredicate;

/**
 * todo write
 */
public class UpcomingEventCommand extends Command {

    public static final String COMMAND_WORD = "upcoming";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all events that happen in the next N days (when "
            + "N is positive), or has happened in the past N days (when N is negative), and displays them as a list "
            + "with index numbers.\n"
            + "Parameters: NUM_OF_DAYS (must be integer)\n"
            + "Example: " + COMMAND_WORD + " 5";

    private enum CommandType {
        NUM_OF_DAYS,
        SPECIFIC_DAY,
    }

    private final CommandType commandType;

    private Integer numOfDays;

    private DateTime specificDay;

    /**
     * Constructs an UpcomingEventCommand with a positive or negative number of days
     * This constructor is used when you want to specify a date for which events before/after that are displayed
     * @param numOfDays the number of days in the future/past.
     */
    public UpcomingEventCommand(int numOfDays) {
        this.commandType = CommandType.NUM_OF_DAYS;
        this.numOfDays = numOfDays;
    }

    /**
     * Constructs an UpcomingEventCommand with a specific date
     * This constructor is used when you want to specify a date for which events before/after that are displayed
     * @param specificDay the number of days in the future/past.
     */
    public UpcomingEventCommand(DateTime specificDay) {
        this.commandType = CommandType.SPECIFIC_DAY;
        this.specificDay = specificDay;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (commandType == CommandType.NUM_OF_DAYS) {
            model.updateFilteredEventList(new EventUpcomingPredicate(numOfDays));
        } else {
            model.updateFilteredEventList(new EventUpcomingPredicate(specificDay));
        }

        return new CommandResult(
                String.format(Messages.MESSAGE_EVENTS_LISTED_OVERVIEW, model.getFilteredEventList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UpcomingEventCommand)) {
            return false;
        }

        UpcomingEventCommand otherUpcomingEventCommand = (UpcomingEventCommand) other;

        if (otherUpcomingEventCommand.commandType != commandType) {
            return false;
        }

        if (commandType == CommandType.NUM_OF_DAYS) {
            return otherUpcomingEventCommand.numOfDays.equals(numOfDays);
        } else {
            return otherUpcomingEventCommand.specificDay.equals(specificDay);
        }

    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("Number of Days", numOfDays)
                .toString();
    }
}
