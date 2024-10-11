package seedu.address.logic.commands;

import java.time.LocalDate;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Attendance;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Marks the attendance of a student for a specific date.
 */
public class MarkAttendanceCommand extends Command {
    public static final String COMMAND_WORD = "markAttendance";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the attendance of a student for a specific date.\n"
            + "Parameters: NAME DATE STATUS (status must be either 'present' or 'absent')\n"
            + "Example: "
            + COMMAND_WORD
            + " John Doe 2023-10-09 present";

    public static final String MESSAGE_SUCCESS = "Attendance marked: %1$s is %2$s on %3$s";

    private final Name name;
    private final LocalDate date;
    private final Attendance attendance;



    /**
     * Creates a MarkAttendanceCommand to mark the attendance of the specified student.
     *
     * @param name The name of the student.
     * @param date The date for which to mark attendance.
     * @param attendance The attendance status (present/absent).
     */
    public MarkAttendanceCommand(Name name, LocalDate date, Attendance attendance) {
        this.name = name;
        this.date = date;
        this.attendance = attendance;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        // Find the student by name
        Person student = model.getPersonByName(name);

        if (student == null) {
            throw new CommandException("Student not found: " + name);
        }

        // Mark attendance
        student.markAttendance(date, attendance.value);
        return new CommandResult(String.format(MESSAGE_SUCCESS, name, attendance.value, date));
    }
}
