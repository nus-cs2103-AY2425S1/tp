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
 * Assigns an event to a person using the person's displayed index and the event's displayed index.
 */
public class AssignEventByPersonIndexEventIndexCommand extends AssignEventCommand {
    private Index targetPersonIndex;
    private Index targetEventIndex;

    /**
     * Creates an AssignEventByPersonIndexEventIndexCommand to assign the specified {@code Event} to the specified
     * {@code Person}.
     * @param targetPersonIndex The index of the Person to assign the Event to.
     * @param targetEventIndex The index of the Event to assign to the Person.
     */
    public AssignEventByPersonIndexEventIndexCommand(Index targetPersonIndex, Index targetEventIndex) {
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

        Person personToAssign = lastShownList.get(targetPersonIndex.getZeroBased());
        Event eventToAssign = lastShownEventList.get(targetEventIndex.getZeroBased());

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
        if (!(other instanceof AssignEventByPersonIndexEventIndexCommand)) {
            return false;
        }

        AssignEventByPersonIndexEventIndexCommand otherAssignEventByPersonIndexEventIndexCommand =
                (AssignEventByPersonIndexEventIndexCommand) other;

        return targetPersonIndex.equals(otherAssignEventByPersonIndexEventIndexCommand.targetPersonIndex)
                && targetEventIndex.equals(otherAssignEventByPersonIndexEventIndexCommand.targetEventIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetPersonIndex", targetPersonIndex)
                .add("targetEventIndex", targetEventIndex)
                .toString();
    }
}
