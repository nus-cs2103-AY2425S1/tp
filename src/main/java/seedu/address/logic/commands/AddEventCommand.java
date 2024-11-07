package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_CELEBRITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_CONTACTS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_VENUE;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Time;
import seedu.address.model.event.Venue;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.ui.CommandDetailChange;
import seedu.address.ui.CommandTabChange;

/**
 * Adds an event to the address book.
 */
public class AddEventCommand extends AddCommand {

    public static final String COMMAND_FIELD = "event";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " "
            + COMMAND_FIELD + ": Adds an event to the address book. "
            + "Parameters: "
            + PREFIX_EVENT_NAME + "NAME "
            + PREFIX_EVENT_TIME + "TIME "
            + "[" + PREFIX_EVENT_VENUE + "VENUE] "
            + PREFIX_EVENT_CELEBRITY + "CELEBRITY "
            + "[" + PREFIX_EVENT_CONTACTS + "CONTACTS]...\n"
            + "Example: " + COMMAND_WORD + " " + COMMAND_FIELD + " "
            + PREFIX_EVENT_NAME + "Oscars "
            + PREFIX_EVENT_TIME + "2024-03-01 12:10 to 2024-03-01 18:30 "
            + PREFIX_EVENT_VENUE + "Hollywood "
            + PREFIX_EVENT_CELEBRITY + "Sydney Sweeney "
            + PREFIX_EVENT_CONTACTS + "Jack Black "
            + PREFIX_EVENT_CONTACTS + "Lebron James";

    public static final String MESSAGE_SUCCESS = "New Event added: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in Talent Hub.";
    public static final String MESSAGE_EVENT_OVERLAP = "%s has another event that clashes with this event.";
    public static final String MESSAGE_CELEBRITY_IN_CONTACT = "Celebrity cannot be a contact in contact list.";
    private final EventName eventName;
    private final Time time;
    private final Venue venue;
    private final Name celebrityName;
    private final Set<Name> contactNames;

    /**
     * Creates an AddEventCommand to add the specified {@code Event}
     */
    public AddEventCommand(EventName eventName, Time time, Venue venue, Name celebrityName,
                           Set<Name> contactNames) {
        requireAllNonNull(eventName, time, celebrityName, contactNames);
        this.eventName = eventName;
        this.time = time;
        this.venue = venue;
        this.celebrityName = celebrityName;
        this.contactNames = contactNames;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Person celebrity;
        Set<Person> contacts;

        try {
            celebrity = model.findPerson(this.celebrityName);
            contacts = new HashSet<>(contactNames.stream().map(model::findPerson).toList());
        } catch (PersonNotFoundException e) {
            throw new CommandException(String.format(Messages.MESSAGE_MISSING_PERSON, e.personName));
        }

        if (contacts.contains(celebrity)) {
            throw new CommandException(MESSAGE_CELEBRITY_IN_CONTACT);
        }

        Event toAdd = Event.createEvent(this.eventName, this.time, this.venue, celebrity, contacts);

        if (model.hasEvent(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        if (model.hasEventOverlap(toAdd)) {
            throw new CommandException(String.format(MESSAGE_EVENT_OVERLAP, celebrity.getName().fullName));
        }

        model.addEvent(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.eventFormat(toAdd)), false,
                false, CommandTabChange.EVENT, CommandDetailChange.SIMPLIFIED);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddEventCommand)) {
            return false;
        }

        AddEventCommand otherAddEventCommand = (AddEventCommand) other;
        return eventName.equals(otherAddEventCommand.eventName)
                && time.equals(otherAddEventCommand.time)
                && venue.equals(otherAddEventCommand.venue)
                && celebrityName.equals(otherAddEventCommand.celebrityName)
                && contactNames.equals(otherAddEventCommand.contactNames);
    }

    @Override
    public String toString() {
        ToStringBuilder result = new ToStringBuilder(this)
                .add("eventName", eventName)
                .add("time", time);
        if (venue != null) {
            result.add("venue", venue);
        } else {
            result.add("venue", "");
        }
        result.add("Celebrity", celebrityName.toString())
                .add("Contacts", contactNames.toString())
                .toString();
        return result.toString();
    }
}
