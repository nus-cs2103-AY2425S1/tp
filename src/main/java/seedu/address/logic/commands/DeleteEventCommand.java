package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.commons.exceptions.AssociationDeleteException;
import seedu.address.model.event.Event;

/**
 * Deletes an event identified using it's displayed index from the address book.
 */
public class DeleteEventCommand extends DeleteCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the event identified by the index number used in the displayed event list.\n"
            + "Parameters: "
            + PREFIX_EVENT
            + "INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_EVENT + "1";

    public static final String MESSAGE_DELETE_EVENT_SUCCESS = "Deleted Event: %1$s";
    public static final String MESSAGE_DELETE_EVENT_FAILED_DUE_TO_EXISTING_ASSOCIATIONS =
        "Deletion failed as vendors are assigned to Event: %1$s";

    /**
     * Creates a DeleteEventCommand to delete the event at the specified
     * {@code Index}.
     *
     * @param targetIndex Index of the event in the filtered event list to delete.
     */
    public DeleteEventCommand(Index targetIndex) {
        super(targetIndex);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Event> lastShownList = model.getFilteredEventList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event eventToDelete = lastShownList.get(targetIndex.getZeroBased());

        try {
            model.deleteEvent(eventToDelete);
        } catch (AssociationDeleteException ae) {
            throw new CommandException(String.format(MESSAGE_DELETE_EVENT_FAILED_DUE_TO_EXISTING_ASSOCIATIONS,
                Messages.format(eventToDelete)));
        }

        return new CommandResult(String.format(MESSAGE_DELETE_EVENT_SUCCESS, Messages.format(eventToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteEventCommand otherDeleteCommand = (DeleteEventCommand) other;
        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }
}
