package seedu.edulog.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.edulog.logic.commands.MarkCommand.MESSAGE_MARK_STUDENT_SUCCESS;

import java.util.List;

import seedu.edulog.commons.util.ToStringBuilder;
import seedu.edulog.logic.Messages;
import seedu.edulog.logic.commands.exceptions.CommandException;
import seedu.edulog.model.Model;
import seedu.edulog.model.student.Name;
import seedu.edulog.model.student.Student;

/**
 * Marks a student identified using its displayed name from the edulog book.
 */
public class MarkNameCommand extends MarkCommand {

    private final Name targetName;

    public MarkNameCommand(Name targetName) {
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
        Student studentToMark = lastShownList.get(studentId);
        model.markStudent(studentToMark);
        return new CommandResult(String.format(MESSAGE_MARK_STUDENT_SUCCESS, Messages.format(studentToMark)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MarkNameCommand)) {
            return false;
        }

        MarkNameCommand otherMarkCommand = (MarkNameCommand) other;
        return targetName.equals(otherMarkCommand.targetName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("targetName", targetName)
            .toString();
    }
}
