package seedu.eventtory.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.eventtory.logic.parser.CliSyntax.PREFIX_EVENT;

import seedu.eventtory.commons.core.index.Index;
import seedu.eventtory.logic.Messages;
import seedu.eventtory.logic.commands.exceptions.CommandException;
import seedu.eventtory.logic.commands.util.IndexResolverUtil;
import seedu.eventtory.model.Model;
import seedu.eventtory.model.commons.exceptions.AssociationDeleteException;
import seedu.eventtory.model.event.Event;

/**
 * Deletes an event identified using it's displayed index from EventTory.
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

        Event eventToDelete = IndexResolverUtil.resolveEvent(model, targetIndex);

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
