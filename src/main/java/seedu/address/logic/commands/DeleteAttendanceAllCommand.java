package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_GROUP;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.student.Student;
import seedu.address.model.student.TutorialGroup;

public class DeleteAttendanceAllCommand extends Command{

    public static final String COMMAND_WORD = "deleteatall";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes the attendance of all students" +
            " in the specified " +
            "tutorial group "
            + "for the specified date.\n"
            + "Parameters: " + PREFIX_TUTORIAL_GROUP + "TUTORIAL_GROUP " + PREFIX_DATE + "DATE\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_TUTORIAL_GROUP + "A01 " + PREFIX_DATE + "2019-10-09";

    public static final String MESSAGE_SUCCESS = "Deleted attendance of all students from tutorial group %1$s on %2$s.";

    public static final String MESSAGE_EMPTY_TG = "The specified tutorial group is empty.";

    private final TutorialGroup tutorialGroup;
    private final LocalDate date;
    private final Map<Student, Attendance> previousAttendances;

    /**
     * Creates a DeleteAttendanceAllCommand to delete the attendance of all students in the specified {@code TutorialGroup}
     * on the specified date.
     * @param tutorialGroup Tutorial group for which attendance is being deleted.
     * @param date Date that attendance is being deleted for.
     */
    public DeleteAttendanceAllCommand(TutorialGroup tutorialGroup, LocalDate date) {
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
            student.deleteAttendance(date);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, tutorialGroup,
                DateTimeFormatter.ofPattern("yyyy-MM-dd").format(date)));

    }

    @Override
    public boolean undo(Model model) {
        requireNonNull(model);
        List<Student> studentsFromSpecifiedTutorialGroup = model.getStudentsByTutorialGroup(tutorialGroup);
        if (studentsFromSpecifiedTutorialGroup.isEmpty()) return false;

        for (Student student : studentsFromSpecifiedTutorialGroup) {
            Attendance previousAttendance = previousAttendances.get(student);
            if (previousAttendance != null) {
                student.markAttendance(date, previousAttendance.value);
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteAttendanceAllCommand)) {
            return false;
        }

        DeleteAttendanceAllCommand e = (DeleteAttendanceAllCommand) other;
        return tutorialGroup.equals(e.tutorialGroup) && date.equals(e.date);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("tutorialGroup", tutorialGroup)
                .add("date", date)
                .toString();
    }
}


