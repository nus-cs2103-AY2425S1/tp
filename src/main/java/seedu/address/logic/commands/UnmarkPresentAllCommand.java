package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_GROUP;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.student.Student;
import seedu.address.model.student.TutorialGroup;

/**
 * Marks the attendance of all students in a specified tutorial group as absent for a specified date.
 */
public class UnmarkPresentAllCommand extends Command {

    public static final String COMMAND_WORD = "unmarkpresentall";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks all students in the specified tutorial group "
            + "as absent for the specified date.\n"
            + "Parameters: " + PREFIX_TUTORIAL_GROUP + "TUTORIAL_GROUP " + PREFIX_DATE + "DATE\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_TUTORIAL_GROUP + "A01 " + PREFIX_DATE + "2019-10-09";

    public static final String MESSAGE_SUCCESS = "Marked all students from tutorial group %1$s as absent on %2$s.";
    public static final String MESSAGE_EMPTY_TG = "The specified tutorial group is empty.";

    private final TutorialGroup tutorialGroup;
    private final LocalDate date;
    private final Map<Student, Attendance> previousAttendances;

    /**
     * Creates a UnmarkPresentAllCommand to mark the attendance of all students in the specified {@code TutorialGroup}
     * as absent on the specified date.
     * @param tutorialGroup Tutorial group for which attendance is being marked.
     * @param date Date that students are marked absent for.
     */
    public UnmarkPresentAllCommand(TutorialGroup tutorialGroup, LocalDate date) {
        requireNonNull(tutorialGroup);
        requireNonNull(date);
        this.tutorialGroup = tutorialGroup;
        this.date = date;
        this.previousAttendances = new HashMap<>();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Student> studentsFromSpecifiedTutorialGroup = model.getStudentsByTutorialGroup(tutorialGroup);

        if (studentsFromSpecifiedTutorialGroup.isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY_TG);
        }

        for (Student student : studentsFromSpecifiedTutorialGroup) {

            previousAttendances.put(student, student.getAttendanceRecord().stream()
                   .filter(record -> record.getDate().equals(date))
                   .findFirst()
                   .map(record -> record.getAttendance())
                    .orElse(null));
            student.markAttendance(date, "a");
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, tutorialGroup, date));
    }

    @Override
    public boolean undo(Model model) {
        requireNonNull(model);
        List<Student> studentsFromSpecifiedTutorialGroup = model.getStudentsByTutorialGroup(tutorialGroup);

        if (studentsFromSpecifiedTutorialGroup.isEmpty()) {
            return false;
        }

        for (Student student: studentsFromSpecifiedTutorialGroup) {
            Attendance previousAttendance = previousAttendances.get(student);
            if (previousAttendance != null) {
                student.markAttendance(date, previousAttendance.value);
            } else {
                student.undoAttendance(date);
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnmarkPresentAllCommand)) {
            return false;
        }

        UnmarkPresentAllCommand otherUnmarkPresentAllCommand = (UnmarkPresentAllCommand) other;
        return tutorialGroup.equals(otherUnmarkPresentAllCommand.tutorialGroup)
                && date.equals(otherUnmarkPresentAllCommand.date);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("tutorialGroup", tutorialGroup)
                .add("date", date)
                .toString();
    }
}
