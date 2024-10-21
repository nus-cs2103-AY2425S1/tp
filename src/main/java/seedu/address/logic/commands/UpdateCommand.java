package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Updates an Event in the address book.
 */
public class UpdateCommand extends Command {
    public static final String COMMAND_WORD = "update";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates an event in the address book. "
            + "Parameters (index is one-based): "
            + PREFIX_INDEX + "EVENT INDEX "
            + PREFIX_NAME + "NEW EVENT NAME "
            + PREFIX_DATE + "NEW DATE (yyyy-mm-dd) \n"
            + "Example: "
            + COMMAND_WORD + " "
            + PREFIX_INDEX + "3"
            + PREFIX_NAME + "New Year's Party "
            + PREFIX_DATE + "2025-01-01";

    public static final String MESSAGE_SUCCESS = "event has been updated: %1$s";

    public static final String MESSAGE_INDEX_OUT_OF_BOUNDS = "There is no event at this index";

    private final Event newEvent;
    private final int oldEventIndex;

    /**
     * Creates an UpdateCommand to update an event to the specified {@code Event}
     */
    public UpdateCommand(Event newEvent, int oldEventIndex) {
        requireNonNull(newEvent);
        this.newEvent = newEvent;
        this.oldEventIndex = oldEventIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (oldEventIndex >= model.getEventListLength() || oldEventIndex < 0) {
            throw new CommandException(MESSAGE_INDEX_OUT_OF_BOUNDS);
        }

        model.updateEvent(newEvent, oldEventIndex);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.formatEvent(newEvent)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UpdateCommand)) {
            return false;
        }

        UpdateCommand otherUpdateCommand = (UpdateCommand) other;
        return newEvent.equals(otherUpdateCommand.newEvent) && (oldEventIndex == otherUpdateCommand.oldEventIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("newEvent", newEvent)
                .add("oldEventIndex", oldEventIndex)
                .toString();
    }
}
