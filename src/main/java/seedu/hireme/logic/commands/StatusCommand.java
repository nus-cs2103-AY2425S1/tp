package seedu.hireme.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.hireme.commons.core.index.Index;
import seedu.hireme.logic.Messages;
import seedu.hireme.logic.commands.exceptions.CommandException;
import seedu.hireme.model.Model;
import seedu.hireme.model.internshipapplication.InternshipApplication;
import seedu.hireme.model.internshipapplication.Status;

/**
 * Changes the status of an internship application identified using its displayed index.
 */
public class StatusCommand extends Command<InternshipApplication> {

    public static final String COMMAND_WORD_ACCEPT = "/accept";
    public static final String COMMAND_WORD_PENDING = "/pending";
    public static final String COMMAND_WORD_REJECT = "/reject";

    public static final String MESSAGE_USAGE = COMMAND_WORD_ACCEPT + " / " + COMMAND_WORD_PENDING + " / " + COMMAND_WORD_REJECT
            + ": Changes the status of the internship application identified by the index number used in the displayed list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD_ACCEPT + " 1";

    public static final String MESSAGE_STATUS_CHANGE_SUCCESS = "Updated status of internship application: %1$s to %2$s";

    private final Index targetIndex;
    private final Status newStatus;

    public StatusCommand(Index targetIndex, Status newStatus) {
        this.targetIndex = targetIndex;
        this.newStatus = newStatus;
    }

    @Override
    public CommandResult execute(Model<InternshipApplication> model) throws CommandException {
        requireNonNull(model);
        List<InternshipApplication> lastShownList = model.getFilteredList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INTERNSHIP_APPLICATION_DISPLAYED_INDEX);
        }

        InternshipApplication internshipApplicationToUpdate = lastShownList.get(targetIndex.getZeroBased());
        internshipApplicationToUpdate.setStatus(newStatus);
        return new CommandResult(String.format(MESSAGE_STATUS_CHANGE_SUCCESS,
                Messages.format(internshipApplicationToUpdate), newStatus.getValue()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof StatusCommand)) {
            return false;
        }

        StatusCommand otherStatusCommand = (StatusCommand) other;
        return targetIndex.equals(otherStatusCommand.targetIndex)
                && newStatus.equals(otherStatusCommand.newStatus);
    }

    @Override
    public String toString() {
        return StatusCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + ", newStatus=" + newStatus.getValue() + "}";
    }
}
