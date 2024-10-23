package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.time.LocalDate;
import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.attendance.AttendanceRecord;
import seedu.address.model.person.Name;
import seedu.address.model.student.Student;

/**
 * Retrieves the attendance of a student for a specific date.
 */
public class GetAttendanceCommand extends Command {
    public static final String COMMAND_WORD = "getat";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Retrieves the attendance of a student for a specific date.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_DATE + "DATE\n"
            + "Example: "
            + COMMAND_WORD + " "
            + PREFIX_NAME + " John Doe "
            + PREFIX_DATE + "2019-10-09";

    public static final String MESSAGE_SUCCESS = "Attendance for %1$s on %2$s: %3$s";
    public static final String MESSAGE_NO_ATTENDANCE = "No attendance record found for %1$s on %2$s.";

    private final Name name;
    private final LocalDate date;

    /**
     * Creates a GetAttendanceCommand to retrieve the attendance of the specified student.
     *
     * @param name The name of the student.
     * @param date The date for which to retrieve attendance.
     */
    public GetAttendanceCommand(Name name, LocalDate date) {
        this.name = name;
        this.date = date;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        // Find the student by name
        Student student = model.getStudentByName(name);

        if (student == null) {
            throw new CommandException("Student not found: " + name);
        }

        // Retrieve attendance records
        List<AttendanceRecord> records = student.getAttendanceRecord();
        for (AttendanceRecord record : records) {
            if (record.getDate().equals(date)) {
                return new CommandResult(String.format(MESSAGE_SUCCESS, name, date, record.getAttendance()));
            }
        }

        return new CommandResult(String.format(MESSAGE_NO_ATTENDANCE, name, date));
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof GetAttendanceCommand)) {
            return false;
        }

        GetAttendanceCommand otherCommand = (GetAttendanceCommand) other;
        return name.equals(otherCommand.name) && date.equals(otherCommand.date);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("date", date)
                .toString();
    }
}
