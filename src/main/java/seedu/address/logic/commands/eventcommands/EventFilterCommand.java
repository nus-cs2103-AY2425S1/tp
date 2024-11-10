package seedu.address.logic.commands.eventcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.AddressBookParser.EVENT_COMMAND_INDICATOR;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Displays volunteers involved in the selected event
 */
public class EventFilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Filters the volunteer display list to only show volunteers who can be assigned to the\n "
            + "event identified by the index number used in the displayed event list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + EVENT_COMMAND_INDICATOR + " " + COMMAND_WORD + " 1";

    private static final String MESSAGE_FILTER_EVENT_SUCCESS = "Showing details of volunteers who are free for %s";

    private final Index targetIndex;

    public EventFilterCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Event> lastShownList = model.getFilteredEventList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event eventToFilter = lastShownList.get(targetIndex.getZeroBased());
        model.filterEvent(eventToFilter);
        return new CommandResult(String.format(MESSAGE_FILTER_EVENT_SUCCESS, eventToFilter.getName().toString()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventFilterCommand)) {
            return false;
        }

        EventFilterCommand otherFilterEventCommand = (EventFilterCommand) other;
        return targetIndex.equals(otherFilterEventCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
