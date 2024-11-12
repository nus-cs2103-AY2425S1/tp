package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.person.Person;

/**
 * Unassigns an existing event from a person using the person's displayed index and the event's name.
 */
public class UnassignEventByPersonIndexEventNameCommand extends UnassignEventCommand {

    private Index targetPersonIndex;
    private EventName targetEventName;

    /**
     * Creates an UnassignEventByPersonIndexEventNameCommand to unassign the specified {@code Event} from the specified
     * {@code Person}.
     * @param targetPersonIndex The index of the Person to unassign the Event from.
     * @param targetEventName The name of the Event to unassign from the Person.
     */
    public UnassignEventByPersonIndexEventNameCommand(Index targetPersonIndex, EventName targetEventName) {
        this.targetPersonIndex = targetPersonIndex;
        this.targetEventName = targetEventName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();
        List<Event> eventList = model.findEventsWithName(targetEventName);

        if (targetPersonIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (eventList.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_NAME);
        } else if (eventList.size() > 1) {
            throw new CommandException(Messages.MESSAGE_MORE_THAN_ONE_EVENT_DISPLAYED_NAME);
        }

        Person personToUnassign = lastShownList.get(targetPersonIndex.getZeroBased());
        Event eventToUnassign = eventList.get(0);

        if (!personToUnassign.checkAssignedToEvent(eventToUnassign)) {
            throw new CommandException(String.format(Messages.MESSAGE_PERSON_NOT_ASSIGNED_TO_EVENT,
                    personToUnassign.getName(), eventToUnassign.getEventName()));
        }

        model.unassignEventFromPerson(personToUnassign, eventToUnassign);
        return new CommandResult(
            String.format(MESSAGE_SUCCESS, eventToUnassign.getEventName(), personToUnassign.getName()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnassignEventByPersonIndexEventNameCommand)) {
            return false;
        }

        UnassignEventByPersonIndexEventNameCommand otherUnassignEventByPersonIndexEventNameCommand =
                (UnassignEventByPersonIndexEventNameCommand) other;

        return targetPersonIndex.equals(otherUnassignEventByPersonIndexEventNameCommand.targetPersonIndex)
                && targetEventName.equals(otherUnassignEventByPersonIndexEventNameCommand.targetEventName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetPersonIndex", targetPersonIndex)
                .add("targetEventName", targetEventName)
                .toString();
    }

}
