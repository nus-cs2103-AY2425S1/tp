package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.participation.Participation;
import seedu.address.model.person.Attendance;
import seedu.address.model.person.Person;
import seedu.address.model.tutorial.Tutorial;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL;

/**
 * Marks the attendance of a person identified using it's displayed index from the addres book as absent.
 */
public class UnmarkAttendanceByStudentCommand extends Command {

    public static final String COMMAND_WORD = "umas";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unmarks the attendance of the student identified "
            + "by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_ATTENDANCE + "ATTENDANCE]"
            + "[" + PREFIX_TUTORIAL + "TUTORIAL]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_ATTENDANCE + "20/10/2024"
            + PREFIX_TUTORIAL + "Math";

    public static final String MESSAGE_UNMARK_ATTENDANCE_STUDENT_SUCCESS =
            "Unmarked attendance of %1$s student for %2$s tutorial on %3$s";

    public static final String MESSAGE_INVALID_TUTORIAL_FOR_STUDENT =
            "The student does not take %1$s tutorial";

    private final Index targetIndex;
    private final Attendance attendance;
    private final Tutorial tutorial;

    public UnmarkAttendanceByStudentCommand(Index targetIndex, Attendance attendance, Tutorial tutorial) {
        requireAllNonNull(targetIndex, attendance, tutorial);
        this.targetIndex = targetIndex;
        this.attendance = attendance;
        this.tutorial = tutorial;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person studentToUnmarkAttendance = lastShownList.get(targetIndex.getZeroBased());
        Participation currentParticipation = model.getParticipationList().stream()
                .filter(participation -> participation.getStudent().equals(studentToUnmarkAttendance)
                        && participation.getTutorial().equals(this.tutorial)).findFirst()
                .orElseThrow(() -> new CommandException(String.format(MESSAGE_INVALID_TUTORIAL_FOR_STUDENT, tutorial)));

        List<Attendance> updatedAttendance = new ArrayList<>(currentParticipation.getAttendanceList());
        updatedAttendance.add(attendance);

        Participation updatedParticipation = new Participation(currentParticipation.getStudent(),
                currentParticipation.getTutorial(), updatedAttendance);

        model.setParticipation(currentParticipation, updatedParticipation);

        return new CommandResult(String.format(MESSAGE_UNMARK_ATTENDANCE_STUDENT_SUCCESS,
                studentToUnmarkAttendance.getName(), tutorial.getSubject(), attendance));
    }
}
