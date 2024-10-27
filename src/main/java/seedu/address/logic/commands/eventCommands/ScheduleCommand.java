package seedu.address.logic.commands.eventcommands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
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
            + "Example: " + COMMAND_WORD + " 7 or " + COMMAND_WORD + " 2024-01-01";

    private EventInSchedulePredicate predicate;

    public ScheduleCommand(EventInSchedulePredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredEventList(predicate);
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
        return predicate.equals(otherScheduleCommand.predicate);

    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
