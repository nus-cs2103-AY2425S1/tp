package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_GROUP;

import java.util.List;
import java.util.Stack;

import seedu.address.commons.util.ToStringBuilder;
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
    private static AttendanceWindow currentWindow;

    private static Stack<AttendanceWindow> openWindows = new Stack<>();

    private final TutorialGroup tutorialGroup;


    /**
     * Creates a GetAttendanceByTGCommand to retrieve attendance records of students in the specified tutorial group.
     *
     * @param tutorialGroup The tutorial group for which to retrieve attendance records.
     */
    public GetAttendanceByTgCommand(TutorialGroup tutorialGroup) {
        this.tutorialGroup = tutorialGroup;
        AttendanceWindow newWindow = new AttendanceWindow(tutorialGroup);
        openWindows.push(newWindow);
        currentWindow = newWindow;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        List<Student> students = model.getStudentsByTutorialGroup(tutorialGroup);

        if (students.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_NO_STUDENTS, tutorialGroup));
        }
        currentWindow.show(model);
        return new CommandResult("Attendance window opened for Tutorial Group: " + tutorialGroup.toString());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GetAttendanceByTgCommand)) {
            return false;
        }
        GetAttendanceByTgCommand otherCommand = (GetAttendanceByTgCommand) other;
        return tutorialGroup.equals(otherCommand.tutorialGroup);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("tutorialGroup", tutorialGroup).toString();
    }

    public void setAttendanceWindow(AttendanceWindow window) {
        this.currentWindow = window;
    }

    @Override
    public boolean undo(Model model) {
        currentWindow = openWindows.pop();
        if (currentWindow != null) {
            currentWindow.close();
            currentWindow = null;
            return true;
        }
        return false;
    }

    /**
     * Closes all currently opened attendance windows.
     * @return true if windows were closed, false otherwise
     */
    public static boolean closeAllWindows() {
        if (!openWindows.isEmpty()) {
            openWindows.forEach(AttendanceWindow::close);
            openWindows.clear();
            return true;
        }
        return false;
    }


}
