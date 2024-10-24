package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.student.Student;
import seedu.address.model.student.TutorialGroup;
import seedu.address.testutil.StudentBuilder;

public class DeleteAllStudentsCommandTest {
    @Test
    public void execute_deleteSingleStudent_success() throws Exception {
        Student validStudent = new StudentBuilder().withName("John Ng").withStudentNumber("A0123456L").build();

        ModelStubWithStudent modelStub = new ModelStubWithStudent(validStudent);

        CommandResult commandResult = new DeleteAllStudentsCommand().execute(modelStub);

        assertEquals(DeleteAllStudentsCommand.MESSAGE_DELETE_STUDENT_SUCCESS, commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_deleteMultipleStudents_success() throws Exception {
        Student validStudent1 = new StudentBuilder().withName("John Ng").withStudentNumber("A1234567X").build();
        Student validStudent2 = new StudentBuilder().withName("May Ng Zi Wei").withStudentNumber("A0123456Y").build();
        Student validStudent3 = new StudentBuilder().withName("Lynn Han").withStudentNumber("A9876543Z").build();
        Student validStudent4 = new StudentBuilder().withName("Richard").withStudentNumber("A1111111B").build();

        ModelStubWithStudent modelStub = new ModelStubWithStudent(validStudent1, validStudent2,
                validStudent3, validStudent4);

        CommandResult commandResult = new DeleteAllStudentsCommand().execute(modelStub);

        assertEquals(DeleteAllStudentsCommand.MESSAGE_DELETE_STUDENT_SUCCESS, commandResult.getFeedbackToUser());
    }

    private class ModelStub implements Model {

        private final ObservableList<Student> studentList = FXCollections.observableArrayList();

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
        public void addStudent(int index, Student student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int deleteStudent(Student target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Student> getFilteredStudentList() {
            return studentList;
        }

        public void deleteAllStudents() {
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
    private class ModelStubWithStudent extends DeleteAllStudentsCommandTest.ModelStub {

        private final ObservableList<Student> students = FXCollections.observableArrayList();

        ModelStubWithStudent(Student... students) {
            this.students.addAll(students);
        }

        @Override
        public Student getStudentByName(Name name) {
            requireNonNull(name);
            return students.stream()
                    .filter(student -> student.getName().equals(name))
                    .findFirst()
                    .orElse(null); // Return null if no student is found with this name
        }

        @Override
        public ObservableList<Student> getFilteredStudentList() {
            return students;
        }

        @Override
        public void addStudent(Student student) {
            requireNonNull(student);
            students.add(student);
        }

        @Override
        public boolean hasStudent(Student student) {
            requireNonNull(student);
            return students.stream().anyMatch(student::isSamePerson);
        }

        @Override
        public int deleteStudent(Student target) {
            requireNonNull(target);
            students.remove(target);
            return 1;
        }

    }

    private class ModelStubWithNoStudent extends DeleteAllStudentsCommandTest.ModelStub {
        @Override
        public ObservableList<Student> getFilteredStudentList() {
            return FXCollections.observableArrayList();
        }
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
