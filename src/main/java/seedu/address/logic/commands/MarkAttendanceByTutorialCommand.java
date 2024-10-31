package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.participation.Participation;
import seedu.address.model.person.Attendance;
import seedu.address.model.tutorial.Tutorial;

/**
 * Marks the attendance of all students in the tutorial.
 */
public class MarkAttendanceByTutorialCommand extends Command {

    public static final String COMMAND_WORD = "mat";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the attendance of the all the students in the specified tutorial.\n"
            + "Parameters: "
            + PREFIX_ATTENDANCE + "ATTENDANCE "
            + PREFIX_TUTORIAL + "TUTORIAL\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_ATTENDANCE + "20/10/2024 "
            + PREFIX_TUTORIAL + "Math";

    public static final String MESSAGE_MARK_TUTORIAL_ATTENDANCE_SUCCESS =
            "Marked attendance of all students in %1$s tutorial for %2$s";
    public static final String MESSAGE_DUPLICATE_WEEKLY_ATTENDANCE_ALL_STUDENTS =
            "All students in %1$s tutorial has attendance marked for the corresponding week of date %2$s";
    public static final String MESSAGE_DUPLICATE_WEEKLY_ATTENDANCE_SOME_STUDENTS = """
            Some students in %1$s tutorial has attendance marked for the corresponding week of date %2$s
            Marked attendance for the following students: %3$s
            Students with duplicate weekly attendance: %4$s
            """;
    public static final String MESSAGE_EMPTY_TUTORIAL =
            "No students are enrolled in tutorial %1$s.";

    private final Tutorial tutorial;
    private final Attendance attendance;

    /**
     * @param tutorial Tutorial to mark the attendance of all students.
     * @param attendance Attendance of the students.
     */
    public MarkAttendanceByTutorialCommand(Tutorial tutorial, Attendance attendance) {
        requireAllNonNull(tutorial, attendance);
        this.tutorial = tutorial;
        this.attendance = attendance;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.getTutorialList().stream().noneMatch(tutorial -> tutorial.equals(this.tutorial))) {
            throw new CommandException(String.format(Messages.MESSAGE_TUTORIAL_NOT_FOUND, tutorial.getSubject()));
        }

        List<Participation> participationList = model.getParticipationList()
                .filtered(participation -> participation.getTutorial().equals(tutorial));

        if (participationList.isEmpty()) {
            throw new CommandException(
                    String.format(MESSAGE_EMPTY_TUTORIAL, tutorial.getSubject()));
        }

        StringBuilder markedStudents = new StringBuilder();
        StringBuilder duplicateAttendanceStudents = new StringBuilder();
        int duplicateStudents = 0;

        for (Participation currentParticipation : participationList) {
            if (containsMarkedAttendance(currentParticipation.getAttendanceList())) {
                duplicateAttendanceStudents.append(currentParticipation.getStudentName() + " ");
                duplicateStudents++;
                continue;
            }
            List<Attendance> updatedAttendance = new ArrayList<>(currentParticipation.getAttendanceList());
            LocalDate attendanceDate = LocalDate.parse(attendance.toString(), Attendance.VALID_DATE_FORMAT);
            updatedAttendance.add(new Attendance(attendanceDate));

            Participation updatedParticipation = new Participation(currentParticipation.getStudent(),
                    currentParticipation.getTutorial(), updatedAttendance);

            model.setParticipation(currentParticipation, updatedParticipation);

            markedStudents.append(currentParticipation.getStudentName());
        }

        if (duplicateStudents == participationList.size()) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_WEEKLY_ATTENDANCE_ALL_STUDENTS,
                    tutorial.getSubject(), attendance));
        }

        if (duplicateStudents != 0) {
            return new CommandResult(String.format(MESSAGE_DUPLICATE_WEEKLY_ATTENDANCE_SOME_STUDENTS,
                    tutorial.getSubject(), attendance, markedStudents, duplicateAttendanceStudents));
        }

        return new CommandResult(String.format(MESSAGE_MARK_TUTORIAL_ATTENDANCE_SUCCESS,
                tutorial.getSubject(), attendance));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MarkAttendanceByTutorialCommand)) {
            return false;
        }

        MarkAttendanceByTutorialCommand otherMarkAttendanceCommand = (MarkAttendanceByTutorialCommand) other;
        return tutorial.equals(otherMarkAttendanceCommand.tutorial)
                && attendance.equals(otherMarkAttendanceCommand.attendance);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("tutorial", tutorial)
                .add("attendance", attendance)
                .toString();
    }

    /**
     * Checks if the list of attendance contains an {@code Attendance} with a date that falls in the same
     * week and year as the specified {@code Attendance} to mark.
     *
     * @param attendanceList List of attendance to compare against.
     * @return true if an attendance within the list exists in the same week and year; false otherwise.
     */
    private boolean containsMarkedAttendance(List<Attendance> attendanceList) {
        for (Attendance attendance : attendanceList) {
            if (attendance.isSameWeek(this.attendance)) {
                return true;
            }
        }
        return false;
    }
}
