package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class AssignEventByPersonNameEventIndexCommand extends AssignEventCommand {
    private Name targetPersonName;
    private Index targetEventIndex;

    public AssignEventByPersonNameEventIndexCommand(Name targetPersonName, Index targetEventIndex) {
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

        Person personToAssign = personList.get(0);
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
        if (!(other instanceof AssignEventByPersonNameEventIndexCommand)) {
            return false;
        }

        AssignEventByPersonNameEventIndexCommand otherAssignEventByPersonNameEventIndexCommand =
                (AssignEventByPersonNameEventIndexCommand) other;

        return targetPersonName.equals(otherAssignEventByPersonNameEventIndexCommand.targetPersonName)
                && targetEventIndex.equals(otherAssignEventByPersonNameEventIndexCommand.targetEventIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetPersonName", targetPersonName)
                .add("targetEventIndex", targetEventIndex)
                .toString();
    }
}
