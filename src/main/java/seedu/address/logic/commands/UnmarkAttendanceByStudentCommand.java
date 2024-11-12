package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.Messages.MESSAGE_LOGGER_FOR_EXCEPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.participation.Participation;
import seedu.address.model.person.Attendance;
import seedu.address.model.person.Person;
import seedu.address.model.tutorial.Tutorial;

/**
 * Marks the attendance of a student identified using its displayed index from EduVault as absent.
 */
public class UnmarkAttendanceByStudentCommand extends Command {

    public static final String COMMAND_WORD = "umas";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unmarks the attendance of the student identified "
            + "by the index number used in the displayed student list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_ATTENDANCE + "ATTENDANCE "
            + PREFIX_TUTORIAL + "TUTORIAL\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_ATTENDANCE + " 20/10/2024 "
            + PREFIX_TUTORIAL + " Math";

    public static final String MESSAGE_UNMARK_ATTENDANCE_STUDENT_SUCCESS =
            "Unmarked attendance of %1$s student for %2$s tutorial for %3$s";

    public static final String MESSAGE_INVALID_TUTORIAL_FOR_STUDENT =
            "Student %1$s is not enrolled in %2$s tutorial, or %2$s tutorial does not exist";

    public static final String MESSAGE_ATTENDANCE_NOT_MARKED =
            "%1$s's attendance for date %2$s for %3$s tutorial has not been marked before.";

    private final Logger logger = LogsCenter.getLogger(UnmarkAttendanceByStudentCommand.class);
    private final Index targetIndex;
    private final Attendance attendance;
    private final Tutorial tutorial;

    /**
     * @param targetIndex Index of the person in the filtered person list to mark.
     * @param attendance Attendance of the person specified by index.
     * @param tutorial Tutorial the student attended.
     */
    public UnmarkAttendanceByStudentCommand(Index targetIndex, Attendance attendance, Tutorial tutorial) {
        requireAllNonNull(targetIndex, attendance, tutorial);
        this.targetIndex = targetIndex;
        this.attendance = attendance;
        this.tutorial = tutorial;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.info("Running execute(Model model)");
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            logger.warning(String.format(MESSAGE_LOGGER_FOR_EXCEPTION, UnmarkAttendanceByStudentCommand.class
                    + "\n - Invalid index"));
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person studentToUnmarkAttendance = lastShownList.get(targetIndex.getZeroBased());
        Participation currentParticipation = getStudentParticipation(model.getParticipationList(),
                studentToUnmarkAttendance);

        List<Attendance> updatedAttendance = new ArrayList<>(currentParticipation.getAttendanceList());

        if (!containsAttendance(updatedAttendance)) {
            throw new CommandException(String.format(MESSAGE_ATTENDANCE_NOT_MARKED,
                    studentToUnmarkAttendance.getName(), attendance, tutorial.getSubject()));
        }
        updatedAttendance.remove(attendance);

        Participation updatedParticipation = new Participation(currentParticipation.getStudent(),
                currentParticipation.getTutorial(), updatedAttendance);

        model.setParticipation(currentParticipation, updatedParticipation);
        logger.info("Successfully replaced current participation with updated participation for "
                + studentToUnmarkAttendance.getFullName());

        return new CommandResult(String.format(MESSAGE_UNMARK_ATTENDANCE_STUDENT_SUCCESS,
                studentToUnmarkAttendance.getName(), tutorial.getSubject(), attendance));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        //instanceof handles null
        if (!(other instanceof UnmarkAttendanceByStudentCommand)) {
            return false;
        }

        UnmarkAttendanceByStudentCommand otherUnmarkAttendanceCommand = (UnmarkAttendanceByStudentCommand) other;
        return targetIndex.equals(otherUnmarkAttendanceCommand.targetIndex)
                && attendance.equals(otherUnmarkAttendanceCommand.attendance)
                && tutorial.equals(otherUnmarkAttendanceCommand.tutorial);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("attendance", attendance)
                .add("tutorial", tutorial)
                .toString();
    }

    /**
     * Retrieves the participation record for the specified student from the list of participation.
     *
     * @param participationList The list of all participation entries to search through.
     * @param student The student whose attendance is being marked.
     * @return The participation record for the specified student.
     * @throws CommandException if no participation record is found for the specified student.
     */
    private Participation getStudentParticipation(List<Participation> participationList,
                                                  Person student) throws CommandException {
        return participationList.stream()
                .filter(participation -> participation.getStudent().equals(student)
                        && participation.getTutorial().equals(this.tutorial))
                .findFirst()
                .orElseThrow(() -> {
                    logger.warning(String.format(MESSAGE_LOGGER_FOR_EXCEPTION, MarkAttendanceByStudentCommand.class
                            + "\n - No participation found for " + student.getFullName()
                            + " for tutorial: " + tutorial.getSubject()));
                    return new CommandException(String.format(MESSAGE_INVALID_TUTORIAL_FOR_STUDENT,
                                    student.getFullName(), tutorial.getSubject()));
                });
    }

    private boolean containsAttendance(List<Attendance> attendanceList) {
        assert attendanceList != null;
        for (Attendance currentAttendance : attendanceList) {
            if (currentAttendance.equals(attendance)) {
                return true;
            }
        }
        return false;
    }
}
