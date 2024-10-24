package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.Messages.MESSAGE_ATTENDEE_NOT_FOUND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDEES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.person.Address;
import seedu.address.model.person.Person;



/**
 * Adds an Event to the address book.
 */
public class EventCommand extends Command {

    public static final String COMMAND_WORD = "event";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an event to the address book.\n"
            + "Parameters: "
            + PREFIX_NAME + "EVENT NAME "
            + PREFIX_DATE + "DATE (yyyy-mm-dd) "
            + PREFIX_LOCATION + "LOCATION \n"
            + PREFIX_ATTENDEES + "ATTENDEES BASED ON ADDRESS BOOK INDEXING \n"
            + "Example: "
            + COMMAND_WORD + " "
            + PREFIX_NAME + "New Year's Party "
            + PREFIX_DATE + "2025-01-01 "
            + PREFIX_LOCATION + "Times Square"
            + PREFIX_ATTENDEES + "1 2 4 5";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the address book";

    private final String eventName;
    private final LocalDate eventDate;
    private final Address location;
    private final Set<Index> attendeeIndexes;

    /**
     * Creates an EventCommand to add the specified {@code Event}
     */
    public EventCommand(String eventName, LocalDate eventDate, Address location, Set<Index> attendeeIndexes) {
        requireAllNonNull(eventName, eventDate, location, attendeeIndexes);
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.location = location;
        this.attendeeIndexes = attendeeIndexes;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Set<Person> attendees = new HashSet<>();
        int size = model.getAddressBook().getPersonList().size();
        for (Index index : this.attendeeIndexes) {
            if (index.getZeroBased() >= size) {
                throw new CommandException(MESSAGE_ATTENDEE_NOT_FOUND);
            }

            Person attendee = model.getAddressBook().getPersonList().get(index.getZeroBased());
            attendees.add(attendee);
        }

        Event toAdd = new Event(eventName, eventDate, location, attendees);

        if (model.hasEvent(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        model.addEvent(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.formatEvent(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventCommand)) {
            return false;
        }

        EventCommand otherEventCommand = (EventCommand) other;
        return eventName.equals(otherEventCommand.eventName)
                && eventDate.equals(otherEventCommand.eventDate)
                && location.equals(otherEventCommand.location)
                && attendeeIndexes.equals(otherEventCommand.attendeeIndexes);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("eventName", eventName)
                .add("eventDate", eventDate)
                .add("location", location)
                .add("attendeeIndexes", attendeeIndexes)
                .toString();
    }
}
