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
 * Unlinks a person from an event in the address book.
 */
public class UnlinkPersonCommand extends Command {
    public static final String COMMAND_WORD = "unlink";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unlink identified person from"
            + " an event in the address book.\n"
            + "Parameters: INDEX_OF_PERSON (must be a positive integer) "
            + PREFIX_EVENT + "EVENT_NAME\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_EVENT + "Company Meeting";

    public static final String MESSAGE_HINT = "Use \"unlink\" to unlink identified person from"
            + " an event in the address book.";

    public static final String MESSAGE_LINK_SUCCESS = "Person unlinked from event: %1$s";
    public static final String MESSAGE_NOT_LINKED = "This person is not linked to the event";
    public static final String MESSAGE_EVENT_NOT_FOUND = "This event does not exist in the address book";

    private final Index index;
    private final Name event;

    /**
     * Creates a UnlinkPersonCommand to unlink the specified {@code Person} from the specified {@code Event}
     */
    public UnlinkPersonCommand(Index index, Name event) {
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

        Person personToUnlink = lastShownList.get(index.getZeroBased());
        Event targetEvent = model.getEventByName(event);

        if (targetEvent == null) {
            throw new CommandException(MESSAGE_EVENT_NOT_FOUND);
        }

        if (!model.isPersonLinkedToEvent(personToUnlink, targetEvent)) {
            throw new CommandException(MESSAGE_NOT_LINKED);
        }

        model.unlinkPersonFromEvent(personToUnlink, targetEvent);

        return new CommandResult(String.format(MESSAGE_LINK_SUCCESS, Messages.format(targetEvent)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UnlinkPersonCommand)) {
            return false;
        }

        UnlinkPersonCommand otherCommand = (UnlinkPersonCommand) other;
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
