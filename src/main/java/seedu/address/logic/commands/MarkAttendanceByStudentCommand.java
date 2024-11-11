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
 * Marks the attendance of a student identified using it's displayed index.
 */
public class MarkAttendanceByStudentCommand extends Command {

    public static final String COMMAND_WORD = "mas";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the attendance of the student identified "
            + "by the index number used in the displayed student list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_ATTENDANCE + "ATTENDANCE "
            + PREFIX_TUTORIAL + "TUTORIAL\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_ATTENDANCE + "20/10/2024 "
            + PREFIX_TUTORIAL + "Math";

    public static final String MESSAGE_MARK_ATTENDANCE_STUDENT_SUCCESS =
            "Marked attendance of %1$s student for %2$s tutorial for %3$s";
    public static final String MESSAGE_INVALID_TUTORIAL_FOR_STUDENT =
            "Student %1$s is not enrolled in %2$s tutorial, or %2$s tutorial does not exist";
    public static final String MESSAGE_DUPLICATE_WEEKLY_ATTENDANCE =
            "Student %1$s has attendance marked for the corresponding week of date %2$s for %3$s tutorial";

    private final Logger logger = LogsCenter.getLogger(MarkAttendanceByStudentCommand.class);

    private final Index targetIndex;
    private final Attendance attendance;
    private final Tutorial tutorial;

    /**
     * @param targetIndex Index of the student in the filtered student list to mark.
     * @param attendance Attendance of the student specified by index.
     * @param tutorial Tutorial the student attended.
     */
    public MarkAttendanceByStudentCommand(Index targetIndex, Attendance attendance, Tutorial tutorial) {
        requireAllNonNull(targetIndex, attendance, tutorial);
        this.targetIndex = targetIndex;
        this.attendance = attendance;
        this.tutorial = tutorial;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        assert tutorial != null;
        assert attendance != null;
        assert targetIndex != null;
        logger.info("Running execute(Model model)");

        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            logger.warning(String.format(MESSAGE_LOGGER_FOR_EXCEPTION, MarkAttendanceByStudentCommand.class
                    + "\n - Invalid index"));
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person studentToMarkAttendance = lastShownList.get(targetIndex.getZeroBased());

        Participation currentParticipation = getStudentParticipation(
                model.getParticipationList(), studentToMarkAttendance);

        if (containsDuplicateWeeklyAttendance(currentParticipation.getAttendanceList())) {
            logger.warning(String.format(MESSAGE_LOGGER_FOR_EXCEPTION, MarkAttendanceByStudentCommand.class
                    + "\n - Duplicate weekly attendance found for " + studentToMarkAttendance.getFullName()));
            throw new CommandException(String.format(MESSAGE_DUPLICATE_WEEKLY_ATTENDANCE,
                    studentToMarkAttendance.getName(), attendance, tutorial.getSubject()));
        }

        List<Attendance> updatedAttendance = new ArrayList<>(currentParticipation.getAttendanceList());
        updatedAttendance.add(attendance);

        Participation updatedParticipation = new Participation(currentParticipation.getStudent(),
                currentParticipation.getTutorial(), updatedAttendance);

        model.setParticipation(currentParticipation, updatedParticipation);
        logger.info("Successfully replaced current participation with updated participation for "
                + studentToMarkAttendance.getFullName());

        return new CommandResult(String.format(MESSAGE_MARK_ATTENDANCE_STUDENT_SUCCESS,
                studentToMarkAttendance.getName(), tutorial.getSubject(), attendance));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MarkAttendanceByStudentCommand)) {
            return false;
        }

        MarkAttendanceByStudentCommand otherMarkAttendanceCommand = (MarkAttendanceByStudentCommand) other;
        return targetIndex.equals(otherMarkAttendanceCommand.targetIndex)
                && attendance.equals(otherMarkAttendanceCommand.attendance)
                && tutorial.equals(otherMarkAttendanceCommand.tutorial);
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
        assert participationList != null;
        assert student != null;

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

    /**
     * Checks if the list of attendance contains an {@code Attendance} with a date that falls in the same
     * week and year as the specified {@code Attendance} to mark.
     *
     * @param attendanceList List of attendance to compare against.
     * @return true if an attendance within the list exists in the same week and year; false otherwise.
     */
    private boolean containsDuplicateWeeklyAttendance(List<Attendance> attendanceList) {
        assert attendanceList != null;
        for (Attendance currentAttendance : attendanceList) {
            if (currentAttendance.isSameWeek(attendance)) {
                return true;
            }
        }
        return false;
    }

}
