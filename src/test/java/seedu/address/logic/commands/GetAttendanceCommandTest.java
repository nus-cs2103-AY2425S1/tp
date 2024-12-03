package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentNumber;
import seedu.address.testutil.ModelStub;
import seedu.address.testutil.StudentBuilder;

class GetAttendanceCommandTest {

    private final Name validName = new Name("John Doe");
    private final Name invalidName = new Name("Unknown Person");

    private final Optional<StudentNumber> studentNumber = Optional.of(new StudentNumber("A0191222D"));

    private final LocalDate validDate = LocalDate.of(2023, 10, 9);
    private final LocalDate invalidDate = LocalDate.of(2024, 1, 1);

    private final Student studentWithAttendance = new StudentBuilder().withName("John Doe")
            .withStudentNumber("A0191222D")
            .withAttendanceRecord(validDate, "p").build();

    private final Student studentWithoutAttendance = new StudentBuilder().withName("Jane Doe")
            .withStudentNumber("A0191222D")
            .build();

    @Test
    void execute_validStudentAndDate_success() throws CommandException {
        Model model = new ModelManager();
        studentWithAttendance.markAttendance(validDate, "p");
        model.addStudent(studentWithAttendance);

        GetAttendanceCommand command = new GetAttendanceCommand(validName, validDate, studentNumber);
        CommandResult result = command.execute(model);

        String expectedMessage = String.format(GetAttendanceCommand.MESSAGE_SUCCESS, validName, validDate, "Present");
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    void execute_validStudentInvalidDate_noAttendanceRecord() throws CommandException {
        Model model = new ModelManager();
        model.addStudent(studentWithAttendance);

        GetAttendanceCommand command = new GetAttendanceCommand(validName, invalidDate, studentNumber);
        CommandResult result = command.execute(model);

        String expectedMessage = String.format(GetAttendanceCommand.MESSAGE_NO_ATTENDANCE, validName, invalidDate);
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    void execute_validStudentNoAttendanceRecord_noAttendanceRecord() throws CommandException {
        Model model = new ModelManager();
        model.addStudent(studentWithoutAttendance);

        GetAttendanceCommand command = new GetAttendanceCommand(new Name("Jane Doe"), validDate, studentNumber);
        CommandResult result = command.execute(model);

        String expectedMessage = String.format(GetAttendanceCommand.MESSAGE_NO_ATTENDANCE,
                new Name("Jane Doe"), validDate);
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    void execute_invalidStudent_throwsCommandException() {
        Model model = new ModelManager();

        GetAttendanceCommand command = new GetAttendanceCommand(invalidName, validDate, studentNumber);

        assertThrows(CommandException.class, "Student not found: " + invalidName, () ->
                command.execute(model));
    }

    @Test
    void execute_duplicateStudents_throwsCommandException() {
        Student student1 = new StudentBuilder().withName("John Doe").withStudentNumber("A0191222D").build();
        Student student2 = new StudentBuilder().withName("John Doe").withStudentNumber("A0191223E").build();
        ModelStubWithDuplicateStudents modelStub = new ModelStubWithDuplicateStudents(student1, student2);

        GetAttendanceCommand command = new GetAttendanceCommand(new Name("John Doe"), validDate, Optional.empty());

        String expectedMessage = String.format(GetAttendanceCommand.MESSAGE_DUPLICATE_STUDENT,
                "A0191222D, A0191223E", "John Doe");

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(modelStub));
    }

    @Test
    public void equalsMethod() {
        Name name1 = new Name("John Doe");
        Name name2 = new Name("Jane Doe");
        LocalDate date1 = LocalDate.parse("2023-10-09");
        LocalDate date2 = LocalDate.parse("2023-10-10");

        GetAttendanceCommand command1 = new GetAttendanceCommand(name1, date1, studentNumber);
        GetAttendanceCommand command2 = new GetAttendanceCommand(name1, date1, studentNumber);
        GetAttendanceCommand command3 = new GetAttendanceCommand(name2, date1, studentNumber);
        GetAttendanceCommand command4 = new GetAttendanceCommand(name1, date2, studentNumber);

        // Same object
        assertEquals(command1, command1);

        // Different objects, same values
        assertEquals(command1, command2);

        // Different names
        assertNotEquals(command1, command3);

        // Different dates
        assertNotEquals(command1, command4);

        // Different types
        assertNotEquals(command1, new Object());

        // Null
        assertNotEquals(command1, null);
    }

    @Test
    public void toStringMethod() {
        Name name = new Name("John Doe");
        LocalDate date = LocalDate.parse("2023-10-09");
        GetAttendanceCommand command = new GetAttendanceCommand(name, date, studentNumber);
        String expectedString = GetAttendanceCommand.class.getCanonicalName()
                + "{name=" + name + ", date=" + date + "}";
        assertEquals(expectedString, command.toString());
    }

    private class ModelStubWithDuplicateStudents extends ModelStub {
        private final List<Student> students;

        ModelStubWithDuplicateStudents(Student... students) {
            requireNonNull(students);
            this.students = List.of(students);
        }

        @Override
        public ObservableList<Student> getAllStudentsByName(Name name) {
            return students.stream()
                    .filter(s -> s.getName().equals(name))
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));
        }
    }

}
