package seedu.edulog.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.edulog.commons.util.ToStringBuilder;
import seedu.edulog.logic.Messages;
import seedu.edulog.logic.commands.exceptions.CommandException;
import seedu.edulog.model.Model;
import seedu.edulog.model.student.Name;
import seedu.edulog.model.student.Student;

/**
 * Unmarks a student identified using their displayed name from the edulog book.
 */
public class UnmarkNameCommand extends UnmarkCommand {

    private final Name targetName;

    public UnmarkNameCommand(Name targetName) {
        this.targetName = targetName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Student> lastShownList = model.getFilteredStudentList();
        List<Name> studentNames = lastShownList.stream().map(Student::getName).toList();

        if (!studentNames.contains(targetName)) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_NAME);
        }

        /*
         * Since the list of names is mapped from original list
         * the index of the name in the names list should be equal to
         * the index of the student in the original list.
         * Hence, we can directly access the student using studentId.
         */
        int studentId = studentNames.indexOf(targetName);
        Student studentToUnmark = lastShownList.get(studentId);
        model.unmarkStudent(studentToUnmark);
        return new CommandResult(String.format(MESSAGE_UNMARK_STUDENT_SUCCESS, Messages.format(studentToUnmark)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnmarkNameCommand)) {
            return false;
        }

        UnmarkNameCommand otherUnmarkCommand = (UnmarkNameCommand) other;
        return targetName.equals(otherUnmarkCommand.targetName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("targetName", targetName)
            .toString();
    }
}
