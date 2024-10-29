package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;

/**
 * Unmarks the attendance of a particular student.
 */
public class UnmarkAttendanceCommand extends Command {

    public static final String COMMAND_WORD = "unmark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unmarks the attendance of a particular student.\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Attendance unmarked successfully.";
    public static final String MESSAGE_NO_STUDENTS = "There is no student to unmark attendance.";

    private final Index targetIndex;

    /**
     * Creates an UnmarkAttendanceCommand to unmark the attendance of the student at the specified {@code Index}.
     */
    public UnmarkAttendanceCommand(Index index) {
        this.targetIndex = index;
    }
    @Override
    public CommandResult executeCommand(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person personToUnmark = lastShownList.get(targetIndex.getZeroBased());
        if (personToUnmark instanceof Student) {
            model.unmarkAttendance(personToUnmark);
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_INDEX);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof UnmarkAttendanceCommand)) {
            return false;
        }

        UnmarkAttendanceCommand otherUnmarkAttendanceCommand = (UnmarkAttendanceCommand) other;
        return targetIndex.equals(otherUnmarkAttendanceCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
