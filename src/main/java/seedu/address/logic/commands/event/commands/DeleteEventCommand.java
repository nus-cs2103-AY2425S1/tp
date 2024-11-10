package seedu.address.logic.commands.event.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventManager;

/**
 * Deletes an event from the address book.
 */
public class DeleteEventCommand extends Command {
    public static final String COMMAND_WORD = "erase";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the event identified by the index number used in the displayed event list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Deleted Event: %1$s";

    private final Index targetIndex;

    /**
     * Creates a DeleteEventCommand to delete the event at the specified {@code targetIndex}.
     *
     * @param targetIndex the index of the event to be deleted
     */
    public DeleteEventCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, EventManager eventManager) throws CommandException {
        requireNonNull(eventManager);
        List<Event> events = eventManager.getEventList();

        if (targetIndex.getZeroBased() >= events.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event eventToDelete = events.get(targetIndex.getZeroBased());
        eventManager.removeEvent(eventToDelete);

        return new CommandResult(String.format(MESSAGE_SUCCESS, eventToDelete.getName()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteEventCommand)) {
            return false;
        }

        DeleteEventCommand otherDeleteEventCommand = (DeleteEventCommand) other;
        return targetIndex.equals(otherDeleteEventCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
