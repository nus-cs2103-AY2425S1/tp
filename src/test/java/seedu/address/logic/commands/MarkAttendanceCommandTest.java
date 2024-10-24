package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.student.Student;
import seedu.address.model.student.TutorialGroup;
import seedu.address.testutil.StudentBuilder;

public class MarkAttendanceCommandTest {

    @Test
    public void execute_studentFound_success() throws Exception {
        Student validStudent = new StudentBuilder().withName("John").build();
        ModelStubWithStudent modelStub = new ModelStubWithStudent(validStudent);
        modelStub.addStudent(validStudent);

        Attendance attendance = new Attendance("p");
        LocalDate date = LocalDate.of(2023, 10, 9);
        MarkAttendanceCommand command = new MarkAttendanceCommand(new Name("John"), date, attendance);

        CommandResult result = command.execute(modelStub);

        assertEquals(String.format(MarkAttendanceCommand.MESSAGE_SUCCESS,
                validStudent.getName(), attendance,
                DateTimeFormatter.ofPattern("MMM d yyyy").format(date)), result.getFeedbackToUser());
        assertEquals(String.format(MarkAttendanceCommand.MESSAGE_SUCCESS, validStudent.getName(), attendance,
                DateTimeFormatter.ofPattern("MMM d yyyy").format(date)), result.getFeedbackToUser());
    }

    @Test
    public void execute_studentNotFound_throwsCommandException() {
        ModelStubWithNoStudent modelStub = new ModelStubWithNoStudent();
        Attendance attendance = new Attendance("p");
        LocalDate date = LocalDate.of(2023, 10, 9);
        MarkAttendanceCommand command = new MarkAttendanceCommand(new Name("John"), date, attendance);

        assertThrows(CommandException.class, "Student not found: John", () -> command.execute(modelStub));
    }

    @Test
    public void execute_invalidAttendanceStatus_throwsCommandException() {
        ModelStubAcceptingStudentAdded modelStub = new ModelStubAcceptingStudentAdded();
        Student validStudent = new StudentBuilder().withName("John Doe").build();
        modelStub.addStudent(validStudent);

        assertThrows(IllegalArgumentException.class, () -> new Attendance("unknown"));
    }






    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedStudent) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public Person getPersonByName(Name name) {
            return null;
        }

        @Override
        public Student getStudentByName(Name name) {
            return null;
        }

        @Override
        public boolean hasStudent(Student student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addStudent(Student student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteStudent(Student target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Student> getFilteredStudentList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredStudentList(Predicate<Student> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setStudent(Student target, Student editedStudent) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Student> getStudentsByTutorialGroup(TutorialGroup tutorialGroup) {
            throw new AssertionError("This method should not be called.");
        }



    }

    /**
     * A Model stub that contains a single student.
     */
    private class ModelStubWithStudent extends ModelStub {
        private final Student student;

        ModelStubWithStudent(Student student) {
            requireNonNull(student);
            this.student = student;
        }

        @Override
        public Student getStudentByName(Name name) {
            requireNonNull(name);
            if (this.student.getName().equals(name)) {
                return this.student;
            }
            return null; // No student found with this name
        }

        @Override
        public boolean hasStudent(Student student) {
            requireNonNull(student);
            return this.student.isSamePerson(student);
        }

        @Override
        public void addStudent(Student student) {
            requireNonNull(student);
        }

    }


    /**
     * A Model stub that always accept the student being added.
     */
    private class ModelStubAcceptingStudentAdded extends ModelStub {
        final ArrayList<Person> studentsAdded = new ArrayList<>();

        @Override
        public boolean hasStudent(Student student) {
            requireNonNull(student);
            // Student student = (Student) person;
            return studentsAdded.stream().map(p -> (Student) p).anyMatch(student::isSameStudent);
        }


        @Override
        public void addStudent(Student student) {
            requireNonNull(student);
            studentsAdded.add(student);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    private class ModelStubWithNoStudent extends ModelStub {
        @Override
        public Student getStudentByName(Name name) {
            return null;
        }

        @Override
        public boolean hasStudent(Student student) {
            return false;
        }
    }
}
