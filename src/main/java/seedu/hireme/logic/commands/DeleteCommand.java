package seedu.hireme.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.hireme.logic.Messages.MESSAGE_INDEX_CONSTRAINT;
import static seedu.hireme.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import seedu.hireme.commons.core.index.Index;
import seedu.hireme.commons.util.ToStringBuilder;
import seedu.hireme.logic.Messages;
import seedu.hireme.logic.commands.exceptions.CommandException;
import seedu.hireme.model.Model;
import seedu.hireme.model.internshipapplication.InternshipApplication;

/**
 * Deletes an internship application identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "/d";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the internship application identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (" + MESSAGE_INDEX_CONSTRAINT + ")\n"
            + "Example: " + COMMAND_WORD + " 5" + "    (if total number of applications is <= 5)";

    public static final String MESSAGE_DELETE_INTERNSHIP_APPLICATION_SUCCESS = "Deleted internship application: %1$s";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<InternshipApplication> lastShownList = model.getFilteredList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        InternshipApplication internshipApplicationToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteItem(internshipApplicationToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_INTERNSHIP_APPLICATION_SUCCESS,
                Messages.format(internshipApplicationToDelete)), false,
                false, false, model.getChartData());
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

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
