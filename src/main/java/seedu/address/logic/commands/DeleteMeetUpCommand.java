package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.meetup.MeetUp;

public class DeleteMeetUpCommand extends Command {

    public static final String COMMAND_WORD = "deletem";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes the details of the meetup in the address book. "
            + "Existing meetup will be deleted from the address book.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_DELETE_MEETUP_SUCCESS = "Deleted Meetup: %1$s";
    public static final String MESSAGE_MEETUP_NOT_DELETED = "Please check for missing fields or invalid format.";

    private final Index targetIndex;

    public DeleteMeetUpCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<MeetUp> lastShownList = model.getFilteredMeetUpList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEETUP_DISPLAYED_INDEX);
        }

        MeetUp meetUpToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteMeetUp(meetUpToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_MEETUP_SUCCESS, Messages.format(meetUpToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteMeetUpCommand)) {
            return false;
        }

        DeleteMeetUpCommand otherDeleteMeetUpCommand = (DeleteMeetUpCommand) other;
        return targetIndex.equals(otherDeleteMeetUpCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
