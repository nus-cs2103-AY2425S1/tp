package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Unassigns an event from a person using the person's name and the event's name.
 */
public class UnassignEventByPersonNameEventNameCommand extends UnassignEventCommand {

    private Name targetPersonName;
    private EventName targetEventName;

    /**
     * Creates an UnassignEventByPersonNameEventNameCommand to unassign the specified {@code Event} from the specified
     * {@code Person}.
     * @param targetPersonName The name of the Person to unassign the Event from.
     * @param targetEventName The name of the Event to unassign from the Person.
     */
    public UnassignEventByPersonNameEventNameCommand(Name targetPersonName, EventName targetEventName) {
        this.targetPersonName = targetPersonName;
        this.targetEventName = targetEventName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> personList = model.findPersonsWithName(targetPersonName);
        System.out.println(personList);
        List<Event> eventList = model.findEventsWithName(targetEventName);

        if (personList.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_NAME);
        } else if (personList.size() > 1) {
            throw new CommandException(Messages.MESSAGE_MORE_THAN_ONE_PERSON_DISPLAYED_NAME);
        }
        if (eventList.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_NAME);
        } else if (eventList.size() > 1) {
            throw new CommandException(Messages.MESSAGE_MORE_THAN_ONE_EVENT_DISPLAYED_NAME);
        }

        Person personToUnassign = personList.get(0);
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
        if (!(other instanceof UnassignEventByPersonNameEventNameCommand)) {
            return false;
        }

        UnassignEventByPersonNameEventNameCommand otherUnassignEventByPersonNameEventNameCommand =
                (UnassignEventByPersonNameEventNameCommand) other;

        return targetPersonName.equals(otherUnassignEventByPersonNameEventNameCommand.targetPersonName)
                && targetEventName.equals(otherUnassignEventByPersonNameEventNameCommand.targetEventName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetPersonName", targetPersonName)
                .add("targetEventName", targetEventName)
                .toString();
    }

}
