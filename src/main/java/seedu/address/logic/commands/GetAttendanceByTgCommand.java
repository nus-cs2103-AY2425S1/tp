package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_GROUP;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.student.Student;
import seedu.address.model.student.TutorialGroup;
import seedu.address.ui.AttendanceWindow;

/**
 * Retrieves the attendance of all students in a specific tutorial group.
 */
public class GetAttendanceByTgCommand extends Command {
    public static final String COMMAND_WORD = "getattg";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Retrieves the attendance of all students in a specific tutorial group.\n"
            + "Parameters: "
            + PREFIX_TUTORIAL_GROUP + "TUTORIAL_GROUP\n"
            + "Example: "
            + COMMAND_WORD + " "
            + PREFIX_TUTORIAL_GROUP + " A01";

    public static final String MESSAGE_SUCCESS = "Attendance for students in tutorial group %1$s:\n%2$s";
    public static final String MESSAGE_NO_STUDENTS = "No students found in tutorial group %1$s.";

    private final TutorialGroup tutorialGroup;

    /**
     * Creates a GetAttendanceByTGCommand to retrieve attendance records of students in the specified tutorial group.
     *
     * @param tutorialGroup The tutorial group for which to retrieve attendance records.
     */
    public GetAttendanceByTgCommand(TutorialGroup tutorialGroup) {
        this.tutorialGroup = tutorialGroup;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        List<Student> students = model.getStudentsByTutorialGroup(tutorialGroup);

        if (students.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_NO_STUDENTS, tutorialGroup));
        }

        StringBuilder attendanceRecords = new StringBuilder();

        for (Student student : students) {
            attendanceRecords.append(student.getName()).append(": ");
            attendanceRecords.append(student.getAttendanceRecordsString()).append("\n");
        }

        AttendanceWindow window = new AttendanceWindow(tutorialGroup);
        window.show(model);
        return new CommandResult("Attendance window opened for Tutorial Group: " + tutorialGroup.toString());
    }
}
