package seedu.eventfulnus.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.eventfulnus.commons.core.index.Index;
import seedu.eventfulnus.commons.util.ToStringBuilder;
import seedu.eventfulnus.logic.Messages;
import seedu.eventfulnus.logic.commands.exceptions.CommandException;
import seedu.eventfulnus.model.Model;
import seedu.eventfulnus.model.event.Event;


/**
 * Deletes an event identified using its displayed index from the database.
 */
public class DeleteEventCommand extends Command {
    public static final String COMMAND_WORD = "deleteevent";
    public static final String MESSAGE_SUCCESS = "Deleted Event: %1$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the event identified by the index number used in the displayed event list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private final Index targetIndex;

    /**
     * Creates a DeleteEventCommand to add the specified {@code Event}
     */
    public DeleteEventCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Event> lastShownList = model.getFilteredEventList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event eventToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteEvent(eventToDelete);
        assert (!lastShownList.contains(eventToDelete));
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.formatEvent(eventToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteEventCommand otherDeleteEventCommand)) {
            return false;
        }

        return targetIndex.equals(otherDeleteEventCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
