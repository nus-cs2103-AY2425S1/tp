package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRESENT;
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
 * Marks the attendance of a student for a specific date.
 */
public class MarkAttendanceCommand extends Command {
    public static final String COMMAND_WORD = "markat";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the attendance of a student for a specific date.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_DATE + "DATE "
            + PREFIX_PRESENT + "STATUS : 'p' or 'a' "
            + PREFIX_STUDENT_NUMBER + "STUDENT NUMBER (Optional) \n"
            + "Example: "
            + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_DATE + "2019-10-09 "
            + PREFIX_PRESENT + "p "
            + PREFIX_STUDENT_NUMBER + "A0123456L";

    public static final String MESSAGE_SUCCESS = "Attendance marked: %1$s is %2$s on %3$s";

    public static final String MESSAGE_DUPLICATE_STUDENT = "There is more than 1 student of the same name.\n"
            + "Their student numbers are as follows: %s" + "\n"
            + "Use the following command: " + COMMAND_WORD + " " + PREFIX_NAME + "%s "
            + PREFIX_STUDENT_NUMBER + "STUDENT NUMBER "
            + PREFIX_DATE + "DATE "
            + PREFIX_PRESENT + "STATUS : 'p' or 'a' to mark the attendance.";

    private final Name name;
    private final LocalDate date;
    private final Attendance attendance;
    private final Optional<StudentNumber> studentNumber;
    private Attendance previousAttendance;



    /**
     * Creates a MarkAttendanceCommand to mark the attendance of the specified student.
     *
     * @param name The name of the student.
     * @param date The date for which to mark attendance.
     * @param attendance The attendance status (present/absent).
     */
    public MarkAttendanceCommand(Name name, LocalDate date,
                                 Attendance attendance, Optional<StudentNumber> studentNumber) {
        this.name = name;
        this.date = date;
        this.attendance = attendance;
        this.studentNumber = studentNumber;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        List<Student> studentList = model.getAllStudentsByName(name);

        if (studentList.isEmpty()) {
            throw new CommandException("Student not found: " + name);
        }

        Student student;
        if (studentNumber.isPresent()) {
            List<Student> filteredStudentList = studentList.stream()
                    .filter(s -> s.getStudentNumber().equals(studentNumber.get()))
                    .collect(Collectors.toList());
            if (filteredStudentList.isEmpty()) {
                throw new CommandException("Student not found: " + name + " with Student Number: "
                        + studentNumber.get());
            }
            student = filteredStudentList.get(0);
        } else {
            if (studentList.size() > 1) {
                String duplicateStudentNumbers = studentList.stream()
                        .map(s -> s.getStudentNumber().toString())
                        .collect(Collectors.joining(", "));
                throw new CommandException(String.format(MESSAGE_DUPLICATE_STUDENT, duplicateStudentNumbers, name));
            }
            student = studentList.get(0);
        }


        // Save the previous attendance state
        previousAttendance = student.getAttendanceRecord().stream()
                .filter(record -> record.getDate().equals(date))
                .findFirst()
                .map(record -> record.getAttendance())
                .orElse(null);

        // Mark attendance
        student.markAttendance(date, attendance.value);
        return new CommandResult(String.format(MESSAGE_SUCCESS, name, attendance,
                DateTimeFormatter.ofPattern("MMM d yyyy").format(date)));
    }

    @Override
    public boolean undo(Model model) {
        // Find the student by name
        Student student = model.getStudentByName(name);
        if (student == null) {
            return false;
        }

        // Revert to the previous attendance state
        if (previousAttendance != null) {
            student.markAttendance(date, previousAttendance.value);
        } else {
            student.deleteAttendance(date);
        }
        return true;
    }
}
