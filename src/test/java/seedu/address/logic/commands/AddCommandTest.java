package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TutUtil.TUTORIAL_ID;
import static seedu.address.testutil.TypicalStudents.ALICE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentList;
import seedu.address.model.assignment.exceptions.AssignmentNotFoundException;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentId;
import seedu.address.model.student.TutorialId;
import seedu.address.model.tut.Tutorial;
import seedu.address.model.tut.TutorialList;
import seedu.address.testutil.StudentBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null, null));
    }

    @Test
    public void execute_studentAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingStudentAdded modelStub = new ModelStubAcceptingStudentAdded();
        Student validStudent = new StudentBuilder().build();

        CommandResult commandResult = new AddCommand(validStudent, TUTORIAL_ID).execute(modelStub);
        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validStudent)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validStudent), modelStub.studentsAdded);
    }

    @Test
    public void execute_duplicateStudent_throwsCommandException() {
        Student validStudent = new StudentBuilder().build();
        AddCommand addCommand = new AddCommand(validStudent, TUTORIAL_ID);
        ModelStub modelStub = new ModelStubWithStudent(validStudent);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_STUDENT, () -> addCommand.execute(modelStub));
    }

    @Test
    public void execute_duplicateStudentId_throwsCommandException() {
        // Setup model with existing student having the same ID
        Student existingStudent = new StudentBuilder().withStudentId("1001").build();
        AddCommand addCommand = new AddCommand(existingStudent, TUTORIAL_ID);

        Student newStudent = new StudentBuilder().withName("Different name").withStudentId("1001").build();
        ModelStub modelStub = new ModelStubWithStudent(newStudent);

        // Assert that the expected exception is thrown
        assertThrows(CommandException.class,
                AddCommand.MESSAGE_DUPLICATE_STUDENTID + "1001", () -> addCommand.execute(modelStub));
    }

    @Test
    public void execute_invalidTutorial_throwsCommandException() {
        TutorialId tutorialId = TutorialId.of("10000");
        Student validStudent = new Student(new Name("ABC"), new StudentId("1999"),
                TutorialId.of("100"), null);
        AddCommand addCommand = new AddCommand(validStudent, tutorialId);
        ModelStub modelStub = new ModelStubWithStudent(ALICE);

        assertThrows(CommandException.class,
                AddCommand.MESSAGE_TUTORIAL_NOT_FOUND + tutorialId, () -> addCommand.execute(modelStub));
    }


    @Test
    public void equals() {
        Student alice = new StudentBuilder().withName("Alice").build();
        Student bob = new StudentBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice, TUTORIAL_ID);
        AddCommand addBobCommand = new AddCommand(bob, TUTORIAL_ID);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice, TUTORIAL_ID);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different student -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        AddCommand addCommand = new AddCommand(ALICE, TUTORIAL_ID);
        String expected = AddCommand.class.getCanonicalName() + "{toAdd=" + ALICE + "}";
        assertEquals(expected, addCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
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
        public void addStudent(Student student) {
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
        public boolean hasStudent(Student student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteStudent(Student target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setStudent(Student target, Student editedStudent) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean setStudentAttendance(StudentId target, TutorialId tut, Date date) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean setStudentAbsent(StudentId target, TutorialId tut, Date date) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addAssignment(Assignment assignment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String checkAssignment(Assignment assignment) throws AssignmentNotFoundException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasAssignment(Assignment assignment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public AssignmentList getAssignmentList() {
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

        public boolean hasTutorial(Tutorial tutorial) {
            return false;
        }

        @Override
        public boolean hasTutorial(TutorialId tutorialClass) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addTutorial(Tutorial toAdd) {
        }

        @Override
        public TutorialList getTutorialList() {
            return null;
        }

        public void deleteAssignment(Assignment assignment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAssignmentStatus(Assignment assignment, Student student, boolean status)
                throws AssignmentNotFoundException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String listAssignments() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAssignments(AssignmentList assignments) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteTutorial(Tutorial tutorial) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasStudentWithId(StudentId studentId) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void assignStudent(Student student, TutorialId tutorialId) {
            throw new AssertionError("This method should not be called.");

        }

        @Override
        public String listTutorials() {
            throw new AssertionError("This method should not be called.");
        }
        public void setTutorials(TutorialList tutorials) {
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
        public boolean hasStudent(Student student) {
            requireNonNull(student);
            return this.student.isSameStudent(student);
        }

        @Override
        public boolean hasStudentWithId(StudentId studentId) {
            requireNonNull(studentId);
            return this.student.isSameStudentId(studentId);
        }

        @Override
        public boolean hasTutorial(TutorialId tutorialClass) {
            requireNonNull(tutorialClass);
            return false;
        }

    }

    /**
     * A Model stub that always accepts the student being added.
     */
    private class ModelStubAcceptingStudentAdded extends ModelStub {
        final ArrayList<Student> studentsAdded = new ArrayList<>();
        final TutorialList tutorialList = new TutorialList();

        @Override
        public boolean hasStudent(Student student) {
            requireNonNull(student);
            return studentsAdded.stream().anyMatch(student::isSameStudent);
        }

        @Override
        public boolean hasStudentWithId(StudentId studentId) {
            return studentsAdded.stream().anyMatch(s -> s.getStudentId().equals(studentId));
        }

        @Override
        public void addStudent(Student student) {
            requireNonNull(student);
            studentsAdded.add(student);
        }

        @Override
        public void assignStudent(Student student, TutorialId tutorialClass) {
            requireNonNull(student);
            requireNonNull(tutorialClass);
        }

        @Override
        public boolean hasTutorial(TutorialId tutorialClass) {
            requireNonNull(tutorialClass);
            return true;
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
