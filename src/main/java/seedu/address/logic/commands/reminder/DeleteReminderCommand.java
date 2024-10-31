package seedu.address.logic.commands.reminder;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.reminder.Reminder;

/**
 * Deletes a reminder identified using its displayed index from the reminder list.
 */
public class DeleteReminderCommand extends Command {

    public static final String COMMAND_WORD = "Rdelete";
    public static final String COMMAND_WORD_SHORT = "rd";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes the reminder "
            + "identified by the index number used in the displayed reminder list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_REMINDER_SUCCESS = "Deleted Reminder: %1$s";

    public static final String MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX = "The reminder index provided is invalid";

    private final Index targetIndex;

    /**
     * Constructs a {@code DeleteReminderCommand} with the specified target index.
     *
     * @param targetIndex The index of the reminder to be deleted.
     */
    public DeleteReminderCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    /**
     * Executes the command to delete the reminder from the model.
     *
     * @param model The {@code Model} which the command should operate on.
     * @return A {@code CommandResult} indicating the result of the command execution.
     * @throws CommandException If the specified index is invalid (out of bounds).
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Reminder> lastShownList = model.getDisplayReminders();

        if (targetIndex.getZeroBased() >= lastShownList.size() || targetIndex.getZeroBased() < 0) {
            throw new CommandException(MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);
        }

        Reminder reminderToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteReminder(reminderToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_REMINDER_SUCCESS, Messages.format(reminderToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof DeleteReminderCommand)) {
            return false;
        }

        DeleteReminderCommand otherDeleteCommand = (DeleteReminderCommand) other;
        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}

