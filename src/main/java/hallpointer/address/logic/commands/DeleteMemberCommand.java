package hallpointer.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import hallpointer.address.commons.core.index.Index;
import hallpointer.address.commons.util.ToStringBuilder;
import hallpointer.address.logic.commands.exceptions.CommandException;
import hallpointer.address.model.Model;
import hallpointer.address.model.member.Member;

/**
 * Deletes the member identified using its displayed index number from the HallPointer.
 */
public class DeleteMemberCommand extends Command {

    public static final String COMMAND_WORD = "delete_member";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the member in the displayed member list that matches the given index number.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Member %1$s with room %2$s and Telegram username %3$s "
            + "deleted successfully.";
    public static final String MESSAGE_INVALID_INDEX = "Error: Invalid index specified.";

    private final Index targetIndex;

    public DeleteMemberCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Member> lastShownList = model.getFilteredMemberList();

        // Check if the index is valid
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_INDEX);
        }

        // Get the member to delete
        Member memberToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteMember(memberToDelete);

        // Success message with name, room, and telegram details
        return new CommandResult(
            String.format(MESSAGE_SUCCESS,
                memberToDelete.getName().value,
                memberToDelete.getRoom().value,
                memberToDelete.getTelegram().value)
        );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteMemberCommand)) {
            return false;
        }

        DeleteMemberCommand otherDeleteMemberCommand = (DeleteMemberCommand) other;
        return targetIndex.equals(otherDeleteMemberCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}

