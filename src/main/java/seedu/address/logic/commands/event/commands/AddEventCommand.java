package seedu.address.logic.commands.event.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventManager;

/**
 * Adds an event to the address book.
 */
public class AddEventCommand extends Command {
    public static final String COMMAND_WORD = "new";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " EVENT NAME: Adds an event to the address book. "
            + "e.g. new sumobot festival";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the address book";

    private final Event eventToBeAdded;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddEventCommand(Event event) {
        requireNonNull(event);
        eventToBeAdded = event;
    }

    @Override
    public CommandResult execute(Model model, EventManager eventManager) throws CommandException {
        requireNonNull(eventManager);

        if (eventManager.hasEvent(eventToBeAdded)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        eventManager.addEvent(eventToBeAdded);
        return new CommandResult(String.format(MESSAGE_SUCCESS, eventToBeAdded.getName()));
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
        return eventToBeAdded.equals(otherAddEventCommand.eventToBeAdded);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("eventToBeAdded", eventToBeAdded)
                .toString();
    }
}
