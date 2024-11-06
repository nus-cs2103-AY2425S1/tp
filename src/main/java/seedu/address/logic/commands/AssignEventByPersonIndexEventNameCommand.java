package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.person.Person;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class AssignEventByPersonIndexEventNameCommand extends AssignEventCommand {
    private Index targetPersonIndex;
    private EventName targetEventName;

    public AssignEventByPersonIndexEventNameCommand(Index targetPersonIndex, EventName targetEventName) {
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

        Person personToAssign = lastShownList.get(targetPersonIndex.getZeroBased());
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
        if (!(other instanceof AssignEventByPersonIndexEventNameCommand)) {
            return false;
        }

        AssignEventByPersonIndexEventNameCommand otherAssignEventByPersonIndexEventNameCommand =
                (AssignEventByPersonIndexEventNameCommand) other;

        return targetPersonIndex.equals(otherAssignEventByPersonIndexEventNameCommand.targetPersonIndex)
                && targetEventName.equals(otherAssignEventByPersonIndexEventNameCommand.targetEventName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetPersonIndex", targetPersonIndex)
                .add("targetEventName", targetEventName)
                .toString();
    }
}
