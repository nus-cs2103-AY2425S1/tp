package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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


public class DeleteAttendanceCommandTest {

    private final Name validName = new Name("John Doe");
    private final Name invalidName = new Name("Unknown Person");

    private final Optional<StudentNumber> studentNumber = Optional.of(new StudentNumber("A0191222D"));
    private final LocalDate validDate = LocalDate.of(2023, 10, 9);
    private final LocalDate invalidDate = LocalDate.of(2024, 1, 1);

    private final Student studentWithAttendance = new StudentBuilder().withName("John Doe")
            .withStudentNumber("A0191222D").build();



    @Test
    void execute_validStudentAndDate_success() throws CommandException {
        Model model = new ModelManager();
        studentWithAttendance.markAttendance(validDate, "p");
        model.addStudent(studentWithAttendance);

        DeleteAttendanceCommand command = new DeleteAttendanceCommand(validName, validDate, studentNumber);
        CommandResult result = command.execute(model);

        String expectedMessage = String.format(DeleteAttendanceCommand.MESSAGE_SUCCESS, validName,
                DateTimeFormatter.ofPattern("dd MMM yyyy").format(validDate));
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    void execute_validStudentInvalidDate_throwsCommandException() {
        Model model = new ModelManager();
        model.addStudent(studentWithAttendance);

        DeleteAttendanceCommand command = new DeleteAttendanceCommand(validName, invalidDate, studentNumber);;
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    void execute_invalidStudent_throwsCommandException() {
        Model model = new ModelManager();

        DeleteAttendanceCommand command = new DeleteAttendanceCommand(invalidName, validDate, studentNumber);

        assertThrows(CommandException.class, "Student not found: " + invalidName, () ->
                command.execute(model));
    }

    @Test
    void undo_validStudentAndDate_success() throws Exception {
        Student validStudent = new StudentBuilder().withName("John Doe").withStudentNumber("A0191222D").build();
        ModelStubWithStudent modelStub = new ModelStubWithStudent(validStudent);
        validStudent.markAttendance(validDate, "p");
        modelStub.addStudent(validStudent);

        DeleteAttendanceCommand deleteCommand = new DeleteAttendanceCommand(validName, validDate, studentNumber);
        deleteCommand.execute(modelStub);

        CommandStack commandStack = CommandStack.getInstance();
        commandStack.push(deleteCommand);

        UndoCommand undoCommand = new UndoCommand();
        CommandResult result = undoCommand.execute(modelStub);

        assertEquals(UndoCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertTrue(validStudent.getAttendanceRecord().stream()
                .anyMatch(record -> record.getDate().equals(validDate) && record.getAttendance().value.equals("p")));
    }

    @Test
    void equalsMethod() {
        Name name1 = new Name("John");
        Name name2 = new Name("Jane");
        LocalDate date1 = LocalDate.of(2023, 10, 9);
        LocalDate date2 = LocalDate.of(2023, 10, 10);

        DeleteAttendanceCommand command1 = new DeleteAttendanceCommand(name1, date1, studentNumber);
        DeleteAttendanceCommand command2 = new DeleteAttendanceCommand(name1, date1, studentNumber);
        DeleteAttendanceCommand command3 = new DeleteAttendanceCommand(name2, date1, studentNumber);
        DeleteAttendanceCommand command4 = new DeleteAttendanceCommand(name1, date2, studentNumber);

        assertEquals(command1, command1);
        assertEquals(command1, command2);
        assertNotEquals(command1, command3);
        assertNotEquals(command1, command4);
        assertNotEquals(command1, new Object());
        assertNotEquals(command1, null);
    }



    @Test
    void execute_duplicateStudents_throwsCommandException() {
        Student student1 = new StudentBuilder().withName("John Doe").withStudentNumber("A0191222D").build();
        Student student2 = new StudentBuilder().withName("John Doe").withStudentNumber("A0191223E").build();
        ModelStubWithDuplicateStudents modelStub = new ModelStubWithDuplicateStudents(student1, student2);

        DeleteAttendanceCommand command = new DeleteAttendanceCommand(new Name("John Doe"), validDate,
                Optional.empty());

        String expectedMessage = String.format(DeleteAttendanceCommand.MESSAGE_DUPLICATE_STUDENT,
                "A0191222D, A0191223E", "John Doe");

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(modelStub));
    }

    private class ModelStubWithStudent extends ModelStub {
        private final Student student;

        ModelStubWithStudent(Student student) {
            requireNonNull(student);
            this.student = student;
        }

        @Override
        public ObservableList<Student> getAllStudentsByName(Name name) {
            return student.getName().equals(name)
                    ? FXCollections.observableArrayList(List.of(student))
                    : FXCollections.observableArrayList();
        }

        @Override
        public void addStudent(Student student) {
            requireNonNull(student);
            this.student.markAttendance(validDate, "p");
        }
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
