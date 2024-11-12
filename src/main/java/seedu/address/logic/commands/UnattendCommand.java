package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDANCEDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIALID;

import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.student.StudentId;
import seedu.address.model.student.TutorialId;

/**
 * Marks a student as absent for a particular date and tutorial class.
 */
public class UnattendCommand extends Command {
    public static final String COMMAND_WORD = "deleteAtt";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Delete student attendance for particular date and "
            + "tutorial class. "
            + "Parameters: "
            + PREFIX_STUDENTID + "STUDENT_ID "
            + PREFIX_TUTORIALID + "TUTORIAL_CLASS "
            + "[" + PREFIX_ATTENDANCEDATE + "ATTENDANCE DATE] \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_STUDENTID + "A1234567U "
            + PREFIX_TUTORIALID + "T1001 "
            + PREFIX_ATTENDANCEDATE + "2024-02-21";
    public static final String MESSAGE_SUCCESS = "Student attendance deleted";
    public static final String MESSAGE_FAILURE = "Tutorial Class doesn't exist or Student not in Tutorial Class!";

    private final StudentId studentId;
    private final TutorialId tutorialId;
    private final Date tutDate;

    /**
     * Creates an UnattendCommand to mark the specified student as absent.
     *
     * @param studentId The ID of the student to be marked absent.
     * @param tutorialId The tutorial class the student is enrolled in.
     * @param tutDate The date on which the student is marked absent.
     */
    public UnattendCommand(StudentId studentId, TutorialId tutorialId, Date tutDate) {
        requireNonNull(studentId);
        requireNonNull(tutorialId);
        requireNonNull(tutDate);
        this.studentId = studentId;
        this.tutorialId = tutorialId;
        this.tutDate = tutDate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.setStudentAbsent(studentId, tutorialId, tutDate)) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        return new CommandResult(MESSAGE_SUCCESS + "\n" + this);
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return "Student: " + studentId.toString() + "\n" + "Date: " + sdf.format(tutDate) + "\n"
                + "Tutorial ID: " + tutorialId.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnattendCommand otherAttendCommand)) {
            return false;
        }

        return studentId.equals(otherAttendCommand.studentId)
                && tutorialId.equals(otherAttendCommand.tutorialId)
                && tutDate.equals(otherAttendCommand.tutDate);
    }
}
