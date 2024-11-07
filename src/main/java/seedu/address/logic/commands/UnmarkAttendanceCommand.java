package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;

/**
 * Unmarks the attendance of a particular student.
 */
public class UnmarkAttendanceCommand extends Command {

    public static final String COMMAND_WORD = "unmark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unmarks the attendance of the student(s) identified "
            + "by the index numbers used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer, multiple indices should be separated by spaces)\n"
            + "Example: " + COMMAND_WORD + " 1 2 3";

    public static final String MESSAGE_SUCCESS = "Attendance unmarked successfully for:\n%1$s";

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private final Index[] targetIndexArray;

    /**
     * @param targetIndexArray of the student in the filtered person list to unmark attendance
     */
    public UnmarkAttendanceCommand(Index[] targetIndexArray) {
        requireNonNull(targetIndexArray);
        this.targetIndexArray = targetIndexArray;
    }

    /**
     * @param targetIndex of the student in the filtered person list to unmark attendance
     */
    public UnmarkAttendanceCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndexArray = new Index[] { targetIndex };
    }
    @Override
    public CommandResult executeCommand(Model model) throws CommandException {
        requireNonNull(model);
        logger.info("Starting to execute unmark attendance command.");

        List<Person> lastShownList = model.getFilteredPersonList();
        List<Person> studentsToUnmark = new ArrayList<>();

        for (Index index : targetIndexArray) {
            if (index.getZeroBased() >= lastShownList.size()) {
                logger.warning("Invalid index provided: " + index.getOneBased());
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX
                        + ": " + index.getOneBased());
            }

            Person personToUnmark = lastShownList.get(index.getZeroBased());
            assert personToUnmark != null;

            if (!(personToUnmark instanceof Student)) {
                logger.warning("Invalid student index: " + index.getOneBased());
                throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_INDEX);
            }

            if (personToUnmark.getDaysAttendedValue() <= 0) {
                logger.warning("Attendance already at zero for student: " + Messages.format(personToUnmark));
                throw new CommandException(Messages.MESSAGE_INVALID_ATTENDANCE);
            }
            studentsToUnmark.add(personToUnmark);
        }

        StringBuilder unmarkedStudents = new StringBuilder();
        for (Person personToUnmark : studentsToUnmark) {
            model.unmarkAttendance(personToUnmark);
            unmarkedStudents.append(Messages.format(personToUnmark)).append("\n");
        }

        logger.info("Attendance unmarked successfully.");
        return new CommandResult(String.format(MESSAGE_SUCCESS, unmarkedStudents.toString().trim()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UnmarkAttendanceCommand)) {
            return false;
        }

        UnmarkAttendanceCommand otherCommand = (UnmarkAttendanceCommand) other;
        return Arrays.equals(targetIndexArray, otherCommand.targetIndexArray);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndexArray", Arrays.toString(targetIndexArray))
                .toString();
    }
}
