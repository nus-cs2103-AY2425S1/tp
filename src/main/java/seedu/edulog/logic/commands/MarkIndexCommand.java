package seedu.edulog.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.edulog.logic.commands.MarkCommand.MESSAGE_MARK_STUDENT_SUCCESS;

import java.util.List;

import seedu.edulog.commons.core.index.Index;
import seedu.edulog.commons.util.ToStringBuilder;
import seedu.edulog.logic.Messages;
import seedu.edulog.logic.commands.exceptions.CommandException;
import seedu.edulog.model.Model;
import seedu.edulog.model.student.Student;

/**
 * Marks a student as has paid, identified using their displayed index from the edulog book.
 */
public class MarkIndexCommand extends MarkCommand {

    private final Index targetIndex;

    public MarkIndexCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Student> lastShownList = model.getFilteredStudentList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        Student studentToMark = lastShownList.get(targetIndex.getZeroBased());
        model.markStudent(studentToMark);
        return new CommandResult(String.format(MESSAGE_MARK_STUDENT_SUCCESS, Messages.format(studentToMark)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MarkIndexCommand)) {
            return false;
        }

        MarkIndexCommand otherMarkCommand = (MarkIndexCommand) other;
        return targetIndex.equals(otherMarkCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("targetIndex", targetIndex)
            .toString();
    }
}
