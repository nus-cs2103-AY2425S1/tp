package seedu.address.logic.commands.eventcommands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.types.common.DateTime;
import seedu.address.model.types.common.EventInSchedulePredicate;

/**
 * Filters events in schedule based on input
 * If the input is positive integer N, shows all events in next N days
 * If the input is negative integer N, shows all events in past N days
 * If the input is a date YYYY-MM-DD, shows all events on that date
 */
public class ScheduleCommand extends Command {

    public static final String COMMAND_WORD = "schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Input an integer to find all events that happen in "
            + "the next/past N days or input a date to find events on that date\n"
            + "The events are displayed as a list with index numbers.\n"
            + "Parameters: NUM_OF_DAYS or YYYY-MM-DD\n"
            + "Example: " + COMMAND_WORD + " 5 or " + COMMAND_WORD + " 2024-01-01";

    private enum CommandType {
        NUM_OF_DAYS,
        SPECIFIC_DATE,
    }

    private final CommandType commandType;

    private Integer numOfDays;

    private DateTime specificDate;

    /**
     * Constructs an ScheduleCommand with a positive or negative number of days
     * This constructor is used when you want to specify a date for which events before/after that are displayed
     * @param numOfDays the number of days in the future/past.
     */
    public ScheduleCommand(int numOfDays) {
        this.commandType = CommandType.NUM_OF_DAYS;
        this.numOfDays = numOfDays;
    }

    /**
     * Constructs an ScheduleCommand with a specific date
     * This constructor is used when you want to specify a date for which events before/after that are displayed
     * @param specificDate the number of days in the future/past.
     */
    public ScheduleCommand(DateTime specificDate) {
        this.commandType = CommandType.SPECIFIC_DATE;
        this.specificDate = specificDate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (commandType == CommandType.NUM_OF_DAYS) {
            model.updateFilteredEventList(new EventInSchedulePredicate(numOfDays));
        } else {
            model.updateFilteredEventList(new EventInSchedulePredicate(specificDate));
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
        if (!(other instanceof ScheduleCommand)) {
            return false;
        }

        ScheduleCommand otherScheduleCommand = (ScheduleCommand) other;

        if (otherScheduleCommand.commandType != commandType) {
            return false;
        }

        if (commandType == CommandType.NUM_OF_DAYS) {
            return otherScheduleCommand.numOfDays.equals(numOfDays);
        } else {
            return otherScheduleCommand.specificDate.equals(specificDate);
        }

    }

    @Override
    public String toString() {
        if (commandType == CommandType.NUM_OF_DAYS) {
            return new ToStringBuilder(this)
                    .add("Number of Days", numOfDays)
                    .toString();
        } else {
            return new ToStringBuilder(this)
                    .add("Specific Date", specificDate)
                    .toString();
        }
    }
}
