package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;

/**
 * Deletes an event identified using its name from the address book.
 */
public class DeleteEventByNameCommand extends DeleteEventCommand {
    private EventName targetName;

    public DeleteEventByNameCommand(EventName targetName) {
        this.targetName = targetName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Event> eventList = model.findEventsWithName(targetName);
        if (eventList.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_NAME);
        } else if (eventList.size() > 1) {
            throw new CommandException(Messages.MESSAGE_MORE_THAN_ONE_EVENT_DISPLAYED_NAME);
        }
        Event eventToDelete = eventList.get(0);

        model.deleteEvent(eventToDelete);
        return new CommandResult(
                String.format(DeleteEventCommand.MESSAGE_DELETE_EVENT_SUCCESS, Messages.format(eventToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteEventByNameCommand)) {
            return false;
        }

        DeleteEventByNameCommand otherDeleteEventByNameCommand = (DeleteEventByNameCommand) other;

        return targetName.equals(otherDeleteEventByNameCommand.targetName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetName", targetName)
                .toString();
    }
}
