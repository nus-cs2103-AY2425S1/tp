package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.concert.ConcertContact;

/**
 * Deletes a person identified using its displayed index from the concert identified using its displayed index.
 */
public class DeleteConcertContactCommand extends Command {

    public static final String COMMAND_WORD = "deletecc";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the concertContact identified by the index number used in the displayed concertContact list.\n"
            + "Parameters: " + " CONCERT_CONTACT_INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + "2";

    public static final String MESSAGE_DELETE_CONCERT_CONTACT_SUCCESS = "Deleted Person from Concert: %1$s";

    private final Index targetIndex;

    /**
     * Creates a DeleteConcertContactCommand to delete the specified {@code ConcertContact}
     * using its index in the filtered list.
     */
    public DeleteConcertContactCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<ConcertContact> lastShownConcertContactList = model.getFilteredConcertContactList();
        assert lastShownConcertContactList != null : "ConcertContact list must not be null";

        if (targetIndex.getZeroBased() >= lastShownConcertContactList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CONCERT_CONTACT_DISPLAYED_INDEX);
        }
        ConcertContact concertContactToDelete = lastShownConcertContactList.get(targetIndex
                .getZeroBased());
        model.deleteConcertContact(concertContactToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_CONCERT_CONTACT_SUCCESS,
                 Messages.format(concertContactToDelete)), false, false, true);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteConcertContactCommand)) {
            return false;
        }

        DeleteConcertContactCommand otherDeleteConcertContactCommand = (DeleteConcertContactCommand) other;
        return targetIndex.equals(otherDeleteConcertContactCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
