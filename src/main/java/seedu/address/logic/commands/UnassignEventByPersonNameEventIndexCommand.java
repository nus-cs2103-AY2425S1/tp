package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Unassigns an event from a person using the person's name and the event's displayed index.
 */
public class UnassignEventByPersonNameEventIndexCommand extends UnassignEventCommand {

    private Name targetPersonName;
    private Index targetEventIndex;

    /**
     * Creates an UnassignEventByPersonNameEventIndexCommand to unassign the specified {@code Event} from the specified
     * {@code Person}.
     * @param targetPersonName The name of the Person to unassign the Event from.
     * @param targetEventIndex The index of the Event to unassign from the Person.
     */
    public UnassignEventByPersonNameEventIndexCommand(Name targetPersonName, Index targetEventIndex) {
        this.targetPersonName = targetPersonName;
        this.targetEventIndex = targetEventIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> personList = model.findPersonsWithName(targetPersonName);
        List<Event> lastShownEventList = model.getFilteredEventList();

        if (personList.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_NAME);
        } else if (personList.size() > 1) {
            throw new CommandException(Messages.MESSAGE_MORE_THAN_ONE_PERSON_DISPLAYED_NAME);
        }

        if (targetEventIndex.getZeroBased() >= lastShownEventList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Person personToUnassign = personList.get(0);
        Event eventToUnassign = lastShownEventList.get(targetEventIndex.getZeroBased());

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
        if (!(other instanceof UnassignEventByPersonNameEventIndexCommand)) {
            return false;
        }

        UnassignEventByPersonNameEventIndexCommand otherUnassignEventByPersonNameEventIndexCommand =
                (UnassignEventByPersonNameEventIndexCommand) other;

        return targetPersonName.equals(otherUnassignEventByPersonNameEventIndexCommand.targetPersonName)
                && targetEventIndex.equals(otherUnassignEventByPersonNameEventIndexCommand.targetEventIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetPersonName", targetPersonName)
                .add("targetEventIndex", targetEventIndex)
                .toString();
    }

}
