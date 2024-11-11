package seedu.address.logic.commands.personcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.types.common.Name;
import seedu.address.model.types.event.Event;
import seedu.address.model.types.person.Person;

/**
 * Links a person to an event in the address book.
 */
public class LinkPersonCommand extends Command {

    public static final String COMMAND_WORD = "link";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Link identified person to"
            + " an event in the address book.\n"
            + "Parameters: INDEX_OF_PERSON (must be a positive integer) "
            + PREFIX_EVENT + "EVENT_NAME\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_EVENT + "Company Meeting";

    public static final String MESSAGE_HINT = "Use \"link\" to link identified person to"
            + " an event in the address book.";

    public static final String MESSAGE_LINK_SUCCESS = "Person linked to event: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person is already linked to the event";
    public static final String MESSAGE_EVENT_NOT_FOUND = "This event does not exist in the address book";

    private final Index index;
    private final Name event;

    /**
     * Creates a LinkPersonCommand to link the specified {@code Person} with the specified {@code Event}
     */
    public LinkPersonCommand(Index index, Name event) {
        requireNonNull(index);
        requireNonNull(event);
        this.index = index;
        this.event = event;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToLink = lastShownList.get(index.getZeroBased());
        Event eventToLink = model.getEventByName(event);

        if (eventToLink == null) {
            throw new CommandException(MESSAGE_EVENT_NOT_FOUND);
        }

        if (model.isPersonLinkedToEvent(personToLink, eventToLink)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        personToLink = copyPerson(personToLink);
        eventToLink = copyEvent(eventToLink);

        model.linkPersonToEvent(personToLink, eventToLink);

        return new CommandResult(String.format(MESSAGE_LINK_SUCCESS, Messages.format(eventToLink)), false, false, true);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code person}
     */
    public Person copyPerson(Person person) {
        return new Person(person.getName(),
                person.getPhone(),
                person.getEmail(),
                person.getAddress(),
                person.getTags());
    }

    /**
     * Creates and returns a {@code Event} with the details of {@code event}
     */
    public Event copyEvent(Event event) {
        return new Event(event.getName(),
                event.getLocation(),
                event.getStartTime(),
                event.getTags());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof LinkPersonCommand)) {
            return false;
        }

        LinkPersonCommand otherCommand = (LinkPersonCommand) other;
        return index.equals(otherCommand.index)
                && event.equals(otherCommand.event);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("event", event)
                .toString();
    }
}
