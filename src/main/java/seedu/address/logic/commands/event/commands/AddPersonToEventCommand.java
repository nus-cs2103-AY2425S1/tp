package seedu.address.logic.commands.event.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventManager;
import seedu.address.model.person.Person;

public class AddPersonToEventCommand extends Command {
    public static final String COMMAND_WORD = "event-add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " ei/ [Event Index] [a/e/ve/vo]/ [Contact Index]: " +
            "Adds contacts to an event in the address book. e.g. event-add ei/1 a/1,2,3";

    public static final String MESSAGE_SUCCESS = "Contacts added to %2$s successfully: \n%1$s";

    private final Index eventIndex;
    private final Set<Index> attendees;
    private final Set<Index> volunteers;
    private final Set<Index> vendors;
    private final Set<Index> sponsors;

    public AddPersonToEventCommand(Index eventIndex, Set<Index> attendees, Set<Index> volunteers, Set<Index> vendors,
                                   Set<Index> sponsors) {
        requireAllNonNull(eventIndex, attendees, volunteers, vendors, sponsors);
        this.eventIndex = eventIndex;
        this.attendees = attendees;
        this.volunteers = volunteers;
        this.vendors = vendors;
        this.sponsors = sponsors;
    }

    @Override
    public CommandResult execute(Model model, EventManager eventManager) throws CommandException {
        requireAllNonNull(model, eventManager);
        List<Person> lastShownList = model.getFilteredPersonList();
        List<Event> events = eventManager.getEventList();
        int size = lastShownList.size();

        checkValidIndex(attendees, size);
        checkValidIndex(volunteers, size);
        checkValidIndex(vendors, size);
        checkValidIndex(sponsors, size);

        if (eventIndex.getZeroBased() >= events.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event event = events.get(eventIndex.getZeroBased());
        StringBuilder sb = new StringBuilder();

        try {
            addContactsToEvent(attendees, lastShownList, event, "attendee", sb);
            addContactsToEvent(volunteers, lastShownList, event, "volunteer", sb);
            addContactsToEvent(vendors, lastShownList, event, "vendor", sb);
            addContactsToEvent(sponsors, lastShownList, event, "sponsor", sb);
        } catch (IllegalValueException e) {
            throw new CommandException("One or more person(s) to be added does not have the role that you are " +
                    "adding him or her to. For example, to add a person as an attendee of Event X, make sure he or she " +
                    "has the attendee role.");
        }

        sb.delete(sb.length() - 2, sb.length());
        return new CommandResult(String.format(MESSAGE_SUCCESS, sb, event.getName()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddPersonToEventCommand)) {
            return false;
        }

        AddPersonToEventCommand otherAddPersonToEventCommand = (AddPersonToEventCommand) other;
        return eventIndex.equals(otherAddPersonToEventCommand.eventIndex)
                && attendees.equals(otherAddPersonToEventCommand.attendees)
                && volunteers.equals(otherAddPersonToEventCommand.volunteers)
                && vendors.equals(otherAddPersonToEventCommand.vendors)
                && sponsors.equals(otherAddPersonToEventCommand.sponsors);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("eventIndex", eventIndex)
                .add("attendees", attendees)
                .add("volunteers", volunteers)
                .add("vendors", vendors)
                .add("sponsors", sponsors)
                .toString();
    }

    private void checkValidIndex(Set<Index> indices, int size) throws CommandException {
        for (Index index : indices) {
            if (index.getZeroBased() >= size) {
                throw new CommandException(Messages.MESSAGE_MULTIPLE_INVALID_CONTACT_DISPLAYED_INDEX);
            }
        }
    }

    private void addContactsToEvent(Set<Index> indices, List<Person> persons, Event event, String role,
                                    StringBuilder sb)
            throws IllegalValueException {
        assert role != null;
        assert !role.isEmpty();
        addRoleToMsg(indices, role, sb);
        for (Index index : indices) {
            Person person = persons.get(index.getZeroBased());
            event.addPerson(person, role);
            sb.append(person.getName()).append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append("\n");
    }

    private static void addRoleToMsg(Set<Index> indices, String role, StringBuilder sb) {
        if (!indices.isEmpty()) {
            //format role
            sb.append(role.substring(0, 1).toUpperCase())
                    .append(role.substring(1))
                    .append("(s): ");
        }
    }
}
