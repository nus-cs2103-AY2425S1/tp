package seedu.address.logic.commands;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class AssignEventByPersonNameEventNameCommand extends AssignEventCommand {
    private Name targetPersonName;
    private EventName targetEventName;

    public AssignEventByPersonNameEventNameCommand(Name targetPersonName, EventName targetEventName) {
        this.targetPersonName = targetPersonName;
        this.targetEventName = targetEventName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> personList = model.findPersonsWithName(targetPersonName);
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

        Person personToAssign = personList.get(0);
        Event eventToAssign = eventList.get(0);

        if (personToAssign.checkAssignedToEvent(eventToAssign)) {
            throw new CommandException(String.format(Messages.MESSAGE_PERSON_ALREADY_ASSIGNED_TO_EVENT,
                    personToAssign.getName(), eventToAssign.getEventName()));
        }

        model.assignEventToPerson(personToAssign, eventToAssign);
        return new CommandResult(
                String.format(MESSAGE_SUCCESS, eventToAssign.getEventName(), personToAssign.getName()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AssignEventByPersonNameEventNameCommand)) {
            return false;
        }

        AssignEventByPersonNameEventNameCommand otherAssignEventByPersonNameEventNameCommand =
                (AssignEventByPersonNameEventNameCommand) other;

        return targetPersonName.equals(otherAssignEventByPersonNameEventNameCommand.targetPersonName)
                && targetEventName.equals(otherAssignEventByPersonNameEventNameCommand.targetEventName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetPersonName", targetPersonName)
                .add("targetEventName", targetEventName)
                .toString();
    }
}
