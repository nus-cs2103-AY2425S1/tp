package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_CELEBRITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_CONTACTS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_VENUE;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Time;
import seedu.address.model.event.Venue;
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
            + PREFIX_EVENT_VENUE + "VENUE "
            + PREFIX_EVENT_CELEBRITY + "CELEBRITY "
            + PREFIX_EVENT_CONTACTS + "CONTACTS...\n"
            + "Example: " + COMMAND_WORD + " " + COMMAND_FIELD + " "
            + PREFIX_EVENT_NAME + "Oscars "
            + PREFIX_EVENT_TIME + "Sep 22 2024 1800 to 2200 "
            + PREFIX_EVENT_VENUE + "Hollywood "
            + PREFIX_EVENT_CELEBRITY + "Sydney Sweeney "
            + PREFIX_EVENT_CONTACTS + "Alex Yeoh, Bernice Yu";

    public static final String MESSAGE_SUCCESS = "New Event added: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event  already exists in the address book";
    private final EventName eventName;
    private final Time time;
    private final Venue venue;
    private final String celebrityName;
    private final List<String> contactNames;

    /**
     * Creates an AddEventCommand to add the specified {@code Event}
     */
    public AddEventCommand(EventName eventName, Time time, Venue venue, String celebrityName,
                           List<String> contactNames) {
        requireAllNonNull(eventName, time, venue, celebrityName);
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
        List<Person> contacts;

        try {
            celebrity = model.findPerson(this.celebrityName);
            contacts = contactNames.stream().map(model::findPerson).toList();
        } catch (PersonNotFoundException e) {
            throw new CommandException(String.format(Messages.MESSAGE_MISSING_PERSON, e.personName));
        }

        Event toAdd = new Event(this.eventName, this.time, this.venue, celebrity, contacts);

        if (model.hasEvent(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        model.addEvent(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.eventFormat(toAdd)), false,
                false, CommandTabChange.EVENT, CommandDetailChange.NONE);
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
        return new ToStringBuilder(this)
                .add("eventName", eventName)
                .add("time", time)
                .add("venue", venue)
                .add("Celebrity", celebrityName)
                .add("Contacts", contactNames)
                .toString();
    }
}
