package hallpointer.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import hallpointer.address.commons.core.index.Index;
import hallpointer.address.commons.util.ToStringBuilder;
import hallpointer.address.logic.Messages;
import hallpointer.address.logic.commands.exceptions.CommandException;
import hallpointer.address.model.Model;
import hallpointer.address.model.session.Session;

/**
 * Deletes a session identified using its displayed index from the address book.
 */
public class DeleteSessionCommand extends Command {
    public static final String COMMAND_WORD = "delete_session";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the session identified by the index number used in the displayed session list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_SESSION_SUCCESS = "Deleted Session: %1$s";

    private final Index targetIndex;

    /**
     * Creates a DeleteSessionCommand to delete the specified session.
     *
     * @param targetIndex The index of the session to delete.
     */
    public DeleteSessionCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Session> lastShownList = model.getSessionList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SESSION_DISPLAYED_INDEX);
        }

        Session sessionToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteSession(sessionToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_SESSION_SUCCESS, sessionToDelete.getSessionName()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteSessionCommand)) {
            return false;
        }

        DeleteSessionCommand otherDeleteSessionCommand = (DeleteSessionCommand) other;
        return targetIndex.equals(otherDeleteSessionCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }

}
