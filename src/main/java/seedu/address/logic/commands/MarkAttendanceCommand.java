package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Attendance;
import seedu.address.model.person.AttendanceList;
import seedu.address.model.person.Person;

/**
 * Adds a person to the address book.
 */
public class MarkAttendanceCommand extends Command {

    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Mark the attendance of the person identified by the index number used in "
            + "the displayed person list and a datetime.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_DATE + "DATE_TIME "
            + PREFIX_ATTENDANCE + "ATTENDANCE \n"
            + "Examples: \n"
            + COMMAND_WORD + " 1 " + PREFIX_DATE + "31/01/2024 12:00 " + PREFIX_ATTENDANCE + "Attended \n"
            + COMMAND_WORD + " 1 " + PREFIX_DATE + "31/01/2024 12:00 " + PREFIX_ATTENDANCE + "Absent \n";

    public static final String MESSAGE_MARK_ATTENDANCE_SUCCESS = "Person: %1$s marked as %2$s on %3$s";

    private final Index targetIndex;
    private final LocalDateTime classDate;
    private final Attendance attendance;

    /**
     * Creates an AddCommand to mark the specified {@code Person} as present on
     * {@code Date}
     */
    public MarkAttendanceCommand(Index index, LocalDateTime date, Attendance attendance) {
        requireNonNull(index);
        requireNonNull(date);
        this.targetIndex = index;
        this.classDate = date;
        this.attendance = attendance;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() < 0 || targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person studentToMark = lastShownList.get(targetIndex.getZeroBased());
        Person studentMarked = studentToMark.setAttendance(classDate, attendance);

        model.setPerson(studentToMark, studentMarked);
        String dateString = DateTimeFormatter.ofPattern(AttendanceList.DATE_TIME_FORMAT).format(classDate);
        return new CommandResult(
                String.format(MESSAGE_MARK_ATTENDANCE_SUCCESS, studentMarked.getName(), attendance, dateString));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MarkAttendanceCommand otherMarkAttendanceCommand)) {
            return false;
        }

        return targetIndex.equals(otherMarkAttendanceCommand.targetIndex)
                && classDate.equals(otherMarkAttendanceCommand.classDate)
                && attendance.equals(otherMarkAttendanceCommand.attendance);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("Index", targetIndex)
                .add("Date", DateTimeFormatter.ofPattern(AttendanceList.DATE_TIME_FORMAT).format(classDate))
                .add("Attendance", attendance)
                .toString();
    }
}
