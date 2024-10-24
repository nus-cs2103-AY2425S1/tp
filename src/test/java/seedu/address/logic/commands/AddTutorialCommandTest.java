package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TutUtil.TUTORIAL_SAMPLE;
import static seedu.address.testutil.TypicalTutorials.TUTORIAL1;
import static seedu.address.testutil.TypicalTutorials.TUTORIAL2;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentList;
import seedu.address.model.assignment.exceptions.AssignmentNotFoundException;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentId;
import seedu.address.model.student.TutorialId;
import seedu.address.model.tut.Tutorial;
import seedu.address.model.tut.TutorialList;


public class AddTutorialCommandTest {

    @Test
    public void constructor_nullTut_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddTutCommand(null));
    }

    @Test
    public void execute_tutAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingTutAdded modelStub = new ModelStubAcceptingTutAdded();
        Tutorial validTutorial = TUTORIAL_SAMPLE;

        CommandResult commandResult = new AddTutCommand(validTutorial).execute(modelStub);

        assertEquals(String.format(AddTutCommand.MESSAGE_SUCCESS, validTutorial),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validTutorial), modelStub.tutorialsAdded);
    }

    @Test
    public void execute_duplicateTut_throwsCommandException() {
        Tutorial validTutorial = TUTORIAL_SAMPLE;
        AddTutCommand addTutCommand = new AddTutCommand(validTutorial);
        ModelStub modelStub = new ModelStubWithTut(validTutorial);

        assertThrows(CommandException.class,
                AddTutCommand.MESSAGE_DUPLICATE_TUTORIAL, () -> addTutCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Tutorial tutorialSample1 = TUTORIAL1;
        Tutorial tutorialSample2 = TUTORIAL2; // Different tutorial

        AddTutCommand addTutSample1Command = new AddTutCommand(tutorialSample1);
        AddTutCommand addTutSample2Command = new AddTutCommand(tutorialSample2);

        // same object -> returns true
        assertTrue(addTutSample1Command.equals(addTutSample1Command));

        // same values -> returns true
        AddTutCommand addTutSample1CommandCopy = new AddTutCommand(tutorialSample1);
        assertTrue(addTutSample1Command.equals(addTutSample1CommandCopy));

        // different types -> returns false
        assertFalse(addTutSample1Command.equals(1));

        // null -> returns false
        assertFalse(addTutSample1Command.equals(null));

        // different tutorial -> returns false
        assertFalse(addTutSample1Command.equals(addTutSample2Command));
    }

    @Test
    public void toStringMethod() {
        AddTutCommand addTutCommand = new AddTutCommand(TUTORIAL_SAMPLE);
        String expected = AddTutCommand.class.getCanonicalName() + "{toAdd=" + TUTORIAL_SAMPLE + "}";
        assertEquals(expected, addTutCommand.toString());
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
        public void setAddressBook(ReadOnlyAddressBook addressBook) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addStudent(Student student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setStudent(Student target, Student editedStudent) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Student> getFilteredStudentList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean setStudentAttendance(StudentId studentId, TutorialId tutorialId, Date date) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean setStudentAbsent(StudentId target, TutorialId tut, Date date) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredStudentList(Predicate<Student> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTutorial(Tutorial tutorial) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTutorial(TutorialId tutorialId) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addTutorial(Tutorial tutorial) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public TutorialList getTutorialList() {
            return null;
        }

        @Override
        public AssignmentList getAssignmentList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasAssignment(Assignment assignment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addAssignment(Assignment assignment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteAssignment(Assignment assignment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String checkAssignment(Assignment assignment) throws AssignmentNotFoundException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAssignmentStatus(Assignment assignment, Student targetStudent, boolean newStatus)
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
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasStudent(Student student) {
            return false;
        }

        @Override
        public void deleteStudent(Student target) {
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
     * A Model stub that contains a single tutorial.
     */
    private class ModelStubWithTut extends ModelStub {
        private final Tutorial tutorial;

        ModelStubWithTut(Tutorial tutorial) {
            this.tutorial = tutorial;
        }

        @Override
        public boolean hasTutorial(Tutorial tutorial) {
            requireNonNull(tutorial);
            return this.tutorial.equals(tutorial);
        }
    }

    /**
     * A Model stub that always accepts the tutorial being added.
     */
    private class ModelStubAcceptingTutAdded extends ModelStub {
        final ArrayList<Tutorial> tutorialsAdded = new ArrayList<>();

        @Override
        public boolean hasTutorial(Tutorial tutorial) {
            requireNonNull(tutorial);
            return tutorialsAdded.stream().anyMatch(tutorial::equals);
        }

        @Override
        public void addTutorial(Tutorial tutorial) {
            requireNonNull(tutorial);
            tutorialsAdded.add(tutorial);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
