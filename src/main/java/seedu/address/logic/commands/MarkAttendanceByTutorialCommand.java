package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
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
            + "[" + PREFIX_ATTENDANCE + "ATTENDANCE]\n"
            + "[" + PREFIX_TUTORIAL + "TUTORIAL]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_ATTENDANCE + "20/10/2024"
            + PREFIX_TUTORIAL + "Math";

    public static final String MESSAGE_MARK_ATTENDANCE_TUTORIAL_SUCCESS =
            "Marked attendance of all students in %1$s tutorial for %2$s";
    public static final String MESSAGE_INVALID_TUTORIAL =
            "Tutorial %1$s does not exist";

    private final Tutorial tutorial;
    private final Attendance attendance;

    /**
     * @param tutorial Tutorial to mark the attendance of all students
     * @param attendance Attendance of the students
     */
    public MarkAttendanceByTutorialCommand(Tutorial tutorial, Attendance attendance) {
        requireAllNonNull(tutorial, attendance);
        this.tutorial = tutorial;
        this.attendance = attendance;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Participation> participationList = model.getParticipationList()
                .stream()
                .filter(participation -> participation.getTutorial().equals(this.tutorial))
                .toList();

        if (participationList.isEmpty()) {
            throw new CommandException(
                    String.format(MESSAGE_INVALID_TUTORIAL, tutorial.getSubject()));
        }

        for (Participation currentParticipation : participationList) {
            List<Attendance> updatedAttendance = new ArrayList<>(currentParticipation.getAttendanceList());
            updatedAttendance.add(new Attendance(LocalDate.from(attendance.attendanceDate)));

            Participation updatedParticipation = new Participation(currentParticipation.getStudent(),
                    currentParticipation.getTutorial(), updatedAttendance);

            model.setParticipation(currentParticipation, updatedParticipation);
        }

        return new CommandResult(String.format(MESSAGE_MARK_ATTENDANCE_TUTORIAL_SUCCESS,
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
}
