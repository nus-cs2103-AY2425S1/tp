package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.concert.Concert;

/**
 * Deletes a concert identified using its displayed index from the address book.
 */
public class DeleteConcertCommand extends Command {

    public static final String COMMAND_WORD = "deletec";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Deletes the concert identified by the index number used in the displayed concert list.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_DELETE_CONCERT_SUCCESS = "Deleted Concert: %1$s";

    private final Index targetIndex;

    /**
     * Creates a DeleteConcertCommand to delete the specified {@code Concert} at the specified {@code targetIndex}.
     */
    public DeleteConcertCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Concert> lastShownList = model.getFilteredConcertList();
        assert lastShownList != null : "Concert list must not be null";

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CONCERT_DISPLAYED_INDEX);
        }

        Concert concertToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteConcert(concertToDelete);
        model.deleteConcertContact(concertToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_CONCERT_SUCCESS, Messages.format(concertToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteConcertCommand)) {
            return false;
        }

        DeleteConcertCommand otherDeleteCommand = (DeleteConcertCommand) other;
        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
