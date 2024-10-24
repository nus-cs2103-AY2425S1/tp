package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRESENT;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.person.Name;
import seedu.address.model.student.Student;


/**
 * Marks the attendance of a student for a specific date.
 */
public class MarkAttendanceCommand extends Command {
    public static final String COMMAND_WORD = "markat";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the attendance of a student for a specific date.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_DATE + "DATE "
            + PREFIX_PRESENT + "STATUS : 'p' or 'a'\n"
            + "Example: "
            + COMMAND_WORD + " "
            + PREFIX_NAME + " John Doe "
            + PREFIX_DATE + "2019-10-09 "
            + PREFIX_PRESENT + "p";

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
        Student student = model.getStudentByName(name);
        if (student == null) {
            throw new CommandException("Student not found: " + name);
        }


        // Mark attendance
        student.markAttendance(date, attendance.value);
        return new CommandResult(String.format(MESSAGE_SUCCESS, name, attendance, date));
        return new CommandResult(String.format(MESSAGE_SUCCESS, name, tg, attendance,
                DateTimeFormatter.ofPattern("MMM d yyyy").format(date)));
    }
}
