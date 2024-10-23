package seedu.edulog.logic.commands;


import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.edulog.commons.core.index.Index;
import seedu.edulog.commons.util.ToStringBuilder;
import seedu.edulog.logic.Messages;
import seedu.edulog.logic.commands.exceptions.CommandException;
import seedu.edulog.model.Model;
import seedu.edulog.model.student.Student;

/**
 * Marks a student as unpaid identified using their displayed index from the edulog book.
 */
public class UnmarkIndexCommand extends UnmarkCommand {

    public static final String COMMAND_WORD = "unmark";

    private final Index targetIndex;

    public UnmarkIndexCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Student> lastShownList = model.getFilteredStudentList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        Student studentToUnmark = lastShownList.get(targetIndex.getZeroBased());
        model.unmarkStudent(studentToUnmark);
        return new CommandResult(String.format(MESSAGE_UNMARK_STUDENT_SUCCESS, Messages.format(studentToUnmark)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnmarkIndexCommand)) {
            return false;
        }

        UnmarkIndexCommand otherUnmarkCommand = (UnmarkIndexCommand) other;
        return targetIndex.equals(otherUnmarkCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("targetIndex", targetIndex)
            .toString();
    }
}
