package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentNumber;
import seedu.address.model.student.TutorialGroup;
import seedu.address.testutil.StudentBuilder;

public class DeleteStudentCommandTest {

    private Student validStudent1;
    private Student validStudent2;
    private Student validStudent3;
    private Student validStudent4;
    private ModelStub modelStub;
    @BeforeEach
    public void setUp() {
        validStudent1 = new StudentBuilder().withName("John Ng").withStudentNumber("A1234567X").build();
        validStudent2 = new StudentBuilder().withName("John Ng").withStudentNumber("A0123456Y").build();
        validStudent3 = new StudentBuilder().withName("John Ng").withStudentNumber("A9876543Z").build();
        validStudent4 = new StudentBuilder().withName("John Ng").withStudentNumber("A1111111B").build();
        modelStub = new ModelStubWithStudent(validStudent1, validStudent2,
                validStudent3, validStudent4);
    }


    // Ensure NullPointerException is thrown when no input is provided at all
    @Test
    public void constructor_nullInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteStudentCommand(null));
    }

    // Ensure IllegalArgumentException is thrown when blank values are provided where there should be non-blank input
    @Test
    public void constructor_blankInput_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new DeleteStudentCommand(new Name("")));
        assertThrows(IllegalArgumentException.class, () -> new DeleteStudentCommand(new Name("John Ng"),
                new StudentNumber("")));
    }

    // Ensure CommandException is thrown when student with provided name is not found
    @Test
    public void execute_studentNameNotFound_throwsCommandException() {
        ModelStubWithNoStudent modelStub = new ModelStubWithNoStudent();
        DeleteStudentCommand command = new DeleteStudentCommand(new Name("John Tan"));

        assertThrows(CommandException.class, "This student is not in your student list.", () ->
                command.execute(modelStub));
    }

    // Ensure CommandException is thrown when student with valid provided student number is not found
    @Test
    public void execute_studentNumberNotFound_throwsCommandException() {
        DeleteStudentCommand command = new DeleteStudentCommand(new Name("John Ng"), new StudentNumber("A1234567Y"));

        assertThrows(CommandException.class, "This student is not in your student list.", () ->
                command.execute(modelStub));
    }

    // Ensure CommandException is thrown when duplicate student names exist, and only name is provided
    @Test
    public void execute_duplicateStudentNames_throwsCommandException() {
        DeleteStudentCommand command = new DeleteStudentCommand(new Name("John Ng"));

        assertThrows(CommandException.class,
                String.format(DeleteStudentCommand.MESSAGE_DUPLICATE_STUDENT,
                        "A1234567X A0123456Y A9876543Z A1111111B", "John Ng"), () -> command.execute(modelStub));
    }

    // Ensure deletion by only provided name works when student with correct details exists
    @Test
    public void execute_singleStudentDeleteByNameOnly_success() throws Exception {
        Student validStudent = new StudentBuilder().withName("John Ng").build();
        DeleteStudentCommandTest.ModelStubWithStudent modelStub =
                new DeleteStudentCommandTest.ModelStubWithStudent(validStudent);

        DeleteStudentCommand command = new DeleteStudentCommand(new Name("John Ng"));

        CommandResult result = command.execute(modelStub);

        assertEquals(String.format(DeleteStudentCommand.MESSAGE_DELETE_STUDENT_SUCCESS, validStudent.getName(),
                        validStudent.getStudentNumber()),
                result.getFeedbackToUser());
    }

    // Ensure deletion by provided name and provided student number works when student with correct details exists
    @Test
    public void execute_singleStudentDeleteByNameAndStudentNumber_success() throws Exception {
        Student validStudent = new StudentBuilder().withName("John Ng").withStudentNumber("A1234567X").build();
        DeleteStudentCommandTest.ModelStubWithStudent modelStub =
                new DeleteStudentCommandTest.ModelStubWithStudent(validStudent);

        DeleteStudentCommand command = new DeleteStudentCommand(new Name("John Ng"), new StudentNumber("A1234567X"));

        CommandResult result = command.execute(modelStub);

        assertEquals(String.format(DeleteStudentCommand.MESSAGE_DELETE_STUDENT_SUCCESS, validStudent.getName(),
                        validStudent.getStudentNumber()),
                result.getFeedbackToUser());
    }

    // Ensure deletion by provided name and provided student number works with duplicate student names
    @Test
    public void execute_duplicateStudentDeleteByNameAndStudentNumber_success() throws Exception {

        Name john = new Name("John Ng");

        DeleteStudentCommand command = new DeleteStudentCommand(john, new StudentNumber("A1234567X"));

        CommandResult secondResult = command.execute(modelStub);

        assertEquals(String.format(DeleteStudentCommand.MESSAGE_DELETE_STUDENT_SUCCESS, john,
                        validStudent1.getStudentNumber()),
                secondResult.getFeedbackToUser());

        assertEquals(FXCollections.observableArrayList(validStudent2, validStudent3, validStudent4),
                modelStub.getFilteredStudentList());
    }

    @Test
    public void equals() {
        DeleteStudentCommand deleteFirstCommand = new DeleteStudentCommand(new Name("John Ng"));
        DeleteStudentCommand deleteSecondCommand = new DeleteStudentCommand(new Name("John Tan"));
        DeleteStudentCommand deleteThirdCommand = new DeleteStudentCommand(validStudent1.getName(),
                validStudent1.getStudentNumber());

        // same object -> returns true
        assertEquals(deleteFirstCommand, deleteFirstCommand);

        // same values -> returns true
        DeleteStudentCommand deleteFirstCommandCopy = new DeleteStudentCommand(new Name("John Ng"));
        assertEquals(deleteFirstCommand, deleteFirstCommandCopy);

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(new Object()));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different student -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));

        // non-null student number and null student number -> returns false
        assertFalse(deleteThirdCommand.equals(deleteFirstCommand));
    }

    @Test
    public void undo_validStudent() throws CommandException {
        ModelStubWithStudent model = new ModelStubWithStudent(validStudent1);
        DeleteStudentCommand deleteStudentCommand = new DeleteStudentCommand(validStudent1.getName());
        deleteStudentCommand.execute(model);

        // undo the Delete Student Command
        deleteStudentCommand.undo(model);
        assertEquals(FXCollections.observableArrayList(validStudent1), model.getFilteredStudentList());
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
        public void addStudent(int index, Student student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int deleteStudent(Student target) {
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
        public void addStudent(int index, Student student) {
            requireNonNull(student);
            students.add(index, student);
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
            for (int i = 0; i < students.size(); i++) {
                if (students.get(i).isSamePerson(target)) {
                    students.remove(i);
                    return i;
                }
            }
            return -1;
        }

    }

    /**
     * A Model stub that contains no students.
     */
    private class ModelStubWithNoStudent extends ModelStub {
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
