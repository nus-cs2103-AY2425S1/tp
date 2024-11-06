package seedu.hireme.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.hireme.logic.Messages.MESSAGE_INDEX_CONSTRAINT;
import static seedu.hireme.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.function.Predicate;

import javafx.collections.transformation.FilteredList;
import seedu.hireme.commons.core.index.Index;
import seedu.hireme.logic.Messages;
import seedu.hireme.logic.commands.exceptions.CommandException;
import seedu.hireme.model.Model;
import seedu.hireme.model.internshipapplication.InternshipApplication;
import seedu.hireme.model.internshipapplication.Status;

/**
 * Changes the status of an internship application identified by its displayed index.
 * The new status can be one of three values: ACCEPTED, PENDING, or REJECTED.
 */
public class StatusCommand extends Command {

    /** Command word for accepting an application. */
    public static final String COMMAND_WORD_ACCEPT = "/accept";

    /** Command word for marking an application as pending. */
    public static final String COMMAND_WORD_PENDING = "/pending";

    /** Command word for rejecting an application. */
    public static final String COMMAND_WORD_REJECT = "/reject";

    /**
     * Usage message providing command usage instructions.
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD_ACCEPT
            + " or " + COMMAND_WORD_PENDING + " or " + COMMAND_WORD_REJECT
            + ": Changes the status of the internship application identified by "
            + "the index number used in the displayed list.\n"
            + "Parameters: INDEX (" + MESSAGE_INDEX_CONSTRAINT + ")\n"
            + "Examples (assuming the total number of applications is 5):\n"
            + COMMAND_WORD_ACCEPT + " 5\n"
            + COMMAND_WORD_PENDING + " 5\n"
            + COMMAND_WORD_REJECT + " 5";

    /**
     * Message to display upon a successful status update.
     */
    public static final String MESSAGE_STATUS_CHANGE_SUCCESS = "Updated status of internship application: %1$s to %2$s";

    private final Index targetIndex;
    private final Status newStatus;

    /**
     * Constructs a {@code StatusCommand} with the specified target index and new status.
     *
     * @param targetIndex The index of the internship application in the displayed list.
     * @param newStatus The new status to assign to the specified internship application.
     */
    public StatusCommand(Index targetIndex, Status newStatus) {
        this.targetIndex = targetIndex;
        this.newStatus = newStatus;
    }

    /**
     * Executes the command to change the status of the specified internship application.
     * Retrieves the application from the filtered list by its index, updates its status,
     * and applies the updated item back to the model.
     *
     * @param model The model containing the list of internship applications.
     * @return A {@code CommandResult} representing the result of the execution.
     * @throws CommandException If the provided index is out of bounds or invalid.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        FilteredList<InternshipApplication> lastShownList =
                (FilteredList<InternshipApplication>) model.getFilteredList();
        @SuppressWarnings("unchecked")
        Predicate<InternshipApplication> prevPredicate = lastShownList.getPredicate() == null
                ? Model.PREDICATE_SHOW_ALL : (Predicate<InternshipApplication>) lastShownList.getPredicate();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        InternshipApplication internshipApplicationToUpdate = lastShownList.get(targetIndex.getZeroBased());
        InternshipApplication updatedInternshipApplication = internshipApplicationToUpdate.deepCopy();
        updatedInternshipApplication.setStatus(newStatus);
        model.setItem(internshipApplicationToUpdate, updatedInternshipApplication);
        model.updateFilteredList(prevPredicate);

        return new CommandResult(String.format(MESSAGE_STATUS_CHANGE_SUCCESS,
                Messages.format(internshipApplicationToUpdate), newStatus.toString()), false,
                false, false, model.getChartData());
    }

    /**
     * Compares this {@code StatusCommand} with another object for equality.
     *
     * @param other The object to compare.
     * @return True if the {@code other} object is also a {@code StatusCommand} with
     *         the same target index and status, false otherwise.
     */
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

    /**
     * Returns a string representation of this {@code StatusCommand}.
     *
     * @return A string describing this command, including the target index and new status.
     */
    @Override
    public String toString() {
        return StatusCommand.class.getCanonicalName()
                + "{targetIndex=" + targetIndex + ", newStatus=" + newStatus.toString() + "}";
    }
}
