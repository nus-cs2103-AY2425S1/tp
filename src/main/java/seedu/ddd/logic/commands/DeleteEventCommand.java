package seedu.ddd.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.ddd.logic.parser.CliFlags.FLAG_EVENT;

import java.util.List;

import seedu.ddd.commons.core.index.Index;
import seedu.ddd.commons.util.ToStringBuilder;
import seedu.ddd.logic.Messages;
import seedu.ddd.logic.commands.exceptions.CommandException;
import seedu.ddd.model.Model;
import seedu.ddd.model.event.common.Event;

/**
 * Deletes an event identified using it's displayed index from the address book.
 */
public class DeleteEventCommand extends Command {
    public static final String COMMAND_WORD = "delete";

    public static final String COMMAND_DESCRIPTION = COMMAND_WORD + ": deletes an event";
    public static final String COMMAND_USAGE = "usage: " + COMMAND_WORD + " " + FLAG_EVENT + " INDEX";
    public static final String EXAMPLE_USAGE = "example: " + COMMAND_WORD + " " + FLAG_EVENT + " 1";
    public static final String MESSAGE_USAGE = COMMAND_DESCRIPTION + "\n"
            + COMMAND_USAGE + "\n"
            + EXAMPLE_USAGE;
    public static final String MESSAGE_DELETE_EVENT_SUCCESS = "Deleted Event: %1$s";
    private final Index targetIndex;

    public DeleteEventCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Event> lastShownList = model.getFilteredEventList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event eventToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteEvent(eventToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_EVENT_SUCCESS, Messages.format(eventToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteEventCommand)) {
            return false;
        }

        DeleteEventCommand otherDeleteEventCommand = (DeleteEventCommand) other;
        return targetIndex.equals(otherDeleteEventCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
