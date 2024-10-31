package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_NUMBER;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.attendance.AttendanceRecord;
import seedu.address.model.person.Name;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentNumber;



/**
 * Retrieves the attendance of a student for a specific date.
 */
public class GetAttendanceCommand extends Command {
    public static final String COMMAND_WORD = "getat";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Retrieves the attendance of a student for a specific date.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_DATE + "DATE "
            + PREFIX_STUDENT_NUMBER + "STUDENT NUMBER (Optional) \n"
            + "Example: "
            + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_DATE + "2019-10-09 "
            + PREFIX_STUDENT_NUMBER + "A0123456L";

    public static final String MESSAGE_SUCCESS = "Attendance for %1$s on %2$s: %3$s";
    public static final String MESSAGE_NO_ATTENDANCE = "No attendance record found for %1$s on %2$s.";
    public static final String MESSAGE_DUPLICATE_STUDENT = "There is more than 1 student of the same name.\n"
            + "Their student numbers are as follows: %s" + "\n"
            + "Use the following command: " + COMMAND_WORD + " " + PREFIX_NAME + "%s "
            + PREFIX_DATE + "DATE "
            + PREFIX_STUDENT_NUMBER + "STUDENT_NUMBER "
            + "to get the Attendance for the correct Student.";

    private final Name name;
    private final LocalDate date;
    private final Optional<StudentNumber> studentNumber;

    /**
     * Creates a GetAttendanceCommand to retrieve the attendance of the specified student.
     *
     * @param name The name of the student.
     * @param date The date for which to retrieve attendance.
     */
    public GetAttendanceCommand(Name name, LocalDate date, Optional<StudentNumber> studentNumber) {
        this.name = name;
        this.date = date;
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
                throw new CommandException("Student not found: " + name + " with student number: "
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
