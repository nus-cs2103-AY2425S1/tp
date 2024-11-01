package seedu.address.logic.commands.event.commands;

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

/**
 * Adds contacts to an event in to the address book.
 */
public class AddPersonToEventCommand extends Command {
    public static final String COMMAND_WORD = "event-add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " ei/EVENT INDEX [a/ or s/ or ve/ or vo/] CONTACT "
            + "INDEX \nAdds contacts to an event in the address book. \nNote: At least one of the following prefixes "
            + "is required—`a/`, `e/`, `ve/`, or `vo/`—each followed by one or more contact index/indices \ne.g. "
            + "event-add ei/1 a/1,2,3";

    public static final String MESSAGE_SUCCESS = "Contacts added to %2$s successfully: \n%1$s";

    private final Index eventIndex;
    private final Set<Index> attendees;
    private final Set<Index> volunteers;
    private final Set<Index> vendors;
    private final Set<Index> sponsors;

    /**
     * Constructs an {@code AddPersonToEventCommand} to add specified persons
     * to an event identified by the given event index.
     *
     * @param eventIndex The index of the event to which persons will be added.
     *                   This cannot be null.
     * @param attendees A set of indices representing the attendees to be added
     *                  to the event. This cannot be null.
     * @param volunteers A set of indices representing the volunteers to be added
     *                   to the event. This cannot be null.
     * @param vendors A set of indices representing the vendors to be added
     *                to the event. This cannot be null.
     * @param sponsors A set of indices representing the sponsors to be added
     *                 to the event. This cannot be null.
     *
     * @throws NullPointerException if any of the parameters are null.
     */
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
            throw new CommandException(e.getMessage());
        }

        sb.delete(sb.length() - 1, sb.length());
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
        if (indices.isEmpty()) {
            return;
        }
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
        //format role
        sb.append(role.substring(0, 1).toUpperCase())
                .append(role.substring(1))
                .append("(s): ");
    }
}
