package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;

/**
 * Unassigns an event from a person using the person's displayed index and the event's displayed index.
 */
public class UnassignEventByPersonIndexEventIndexCommand extends UnassignEventCommand {

    private Index targetPersonIndex;
    private Index targetEventIndex;

    /**
     * Creates an UnassignEventByPersonIndexEventIndexCommand to unassign the specified {@code Event} from the specified
     * {@code Person}.
     * @param targetPersonIndex The index of the Person to unassign the Event from.
     * @param targetEventIndex The index of the Event to unassign from the Person.
     */
    public UnassignEventByPersonIndexEventIndexCommand(Index targetPersonIndex, Index targetEventIndex) {
        this.targetPersonIndex = targetPersonIndex;
        this.targetEventIndex = targetEventIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();
        List<Event> lastShownEventList = model.getFilteredEventList();

        if (targetPersonIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (targetEventIndex.getZeroBased() >= lastShownEventList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Person personToUnassign = lastShownList.get(targetPersonIndex.getZeroBased());
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
        if (!(other instanceof UnassignEventByPersonIndexEventIndexCommand)) {
            return false;
        }

        UnassignEventByPersonIndexEventIndexCommand otherUnassignEventByPersonIndexEventIndexCommand =
                (UnassignEventByPersonIndexEventIndexCommand) other;

        return targetPersonIndex.equals(otherUnassignEventByPersonIndexEventIndexCommand.targetPersonIndex)
                && targetEventIndex.equals(otherUnassignEventByPersonIndexEventIndexCommand.targetEventIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetPersonIndex", targetPersonIndex)
                .add("targetEventIndex", targetEventIndex)
                .toString();
    }

}
