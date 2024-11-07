package seedu.eventfulnus.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.eventfulnus.logic.Messages;
import seedu.eventfulnus.logic.commands.exceptions.CommandException;
import seedu.eventfulnus.model.Model;
import seedu.eventfulnus.model.event.Event;

/**
 * Adds an event to the address book.
 */
public class AddEventCommand extends Command {

    public static final String COMMAND_WORD = "addevent";
    public static final String MESSAGE_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the address book";
    public static final Object MESSAGE_USAGE = COMMAND_WORD + ": Adds an event to the address book. "
            + "Parameters: "
            + "VALUE";

    private final Event toAdd;

    /**
     * Creates an AddEventCommand to add the specified {@code Event}
     */
    public AddEventCommand(Event event) {
        requireNonNull(event);
        toAdd = event;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasEvent(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        model.addEvent(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.formatEvent(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddEventCommand // instanceof handles nulls
                && toAdd.equals(((AddEventCommand) other).toAdd));
    }
}
