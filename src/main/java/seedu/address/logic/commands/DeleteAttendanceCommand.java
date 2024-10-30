package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_NUMBER;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.person.Name;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentNumber;


/**
 * Deletes the attendance of a student for a specific date.
 */
public class DeleteAttendanceCommand extends Command {

    public static final String COMMAND_WORD = "deleteat";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the attendance of a student for a specific date.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_DATE + "DATE "
            + PREFIX_STUDENT_NUMBER + "STUDENT NUMBER (Optional)\n"
            + "Example: "
            + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_DATE + "2019-10-09 "
            + PREFIX_STUDENT_NUMBER + "A1234567B";

    public static final String MESSAGE_SUCCESS = "Attendance deleted: %1$s on %2$s";

    public static final String MESSAGE_DUPLICATE_STUDENT = "There is more than 1 student of the same name.\n"
            + "Their student numbers are as follows: %s" + "\n"
            + "Use the following command: " + COMMAND_WORD + " " + PREFIX_NAME + "%s "
            + PREFIX_STUDENT_NUMBER + "STUDENT NUMBER "
            + PREFIX_DATE + "DATE "
            + "to delete the Attendance for the correct Student.";
    private final Name name;
    private final LocalDate date;
    private final Optional<StudentNumber> studentNumber;

    private Optional<Student> student;
    private Attendance previousAttendance;

    /**
     * Creates a DeleteAttendanceCommand to delete the attendance of the specified student.
     * @param name The name of the student.
     * @param date The date for which to delete attendance.
     */
    public DeleteAttendanceCommand(Name name, LocalDate date, Optional<StudentNumber> studentNumber) {
        this.name = name;
        this.date = date;
        this.studentNumber = studentNumber;
        this.student = Optional.empty();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Student> studentList = model.getAllStudentsByName(name);

        if (studentList.isEmpty()) {
            throw new CommandException("Student not found: " + name);
        }

        if (studentNumber.isPresent()) {
            List<Student> filteredStudentList = studentList.stream()
                    .filter(s -> s.getStudentNumber().equals(studentNumber.get()))
                    .collect(Collectors.toList());
            if (filteredStudentList.isEmpty()) {
                throw new CommandException("Student not found: " + name + " with student number: "
                        + studentNumber.get());
            }
            student = Optional.of(filteredStudentList.get(0));
        } else {
            if (studentList.size() > 1) {
                String duplicateStudentNumbers = studentList.stream()
                        .map(s -> s.getStudentNumber().toString())
                        .collect(Collectors.joining(", "));
                throw new CommandException(String.format(MESSAGE_DUPLICATE_STUDENT, duplicateStudentNumbers, name));
            }
            student = Optional.of(studentList.get(0));
        }
        previousAttendance = student.get().getAttendanceRecord().stream()
                .filter(record -> record.getDate().equals(date))
                .findFirst()
                .map(record -> record.getAttendance())
                .orElse(null);

        if (previousAttendance == null) {
            throw new CommandException("No attendance record found for " + name + " on " + date);
        }

        student.get().deleteAttendance(date);
        return new CommandResult(String.format(MESSAGE_SUCCESS,
                name, DateTimeFormatter.ofPattern("dd MMM yyyy").format(date)));

    }

    @Override
    public boolean undo(Model model) {
        if (student.isEmpty()) {
            return false;
        }
        Student student = this.student.get();

        if (previousAttendance != null) {
            student.markAttendance(date, previousAttendance.value);
        }
        return true;
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteAttendanceCommand)) {
            return false;
        }

        DeleteAttendanceCommand otherCommand = (DeleteAttendanceCommand) other;
        return name.equals(otherCommand.name) && date.equals(otherCommand.date);
    }

}
