package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
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
import seedu.address.testutil.TypicalAssignments;

public class AddAssignmentCommandTest {

    @Test
    public void constructor_nullAssignment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddAssignmentCommand(null));
    }

    @Test
    public void execute_assignmentAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingAssignmentAdded modelStub = new ModelStubAcceptingAssignmentAdded();
        Assignment validAssignment = TypicalAssignments.ASSIGNMENT1;

        CommandResult commandResult = new AddAssignmentCommand(validAssignment).execute(modelStub);

        assertTrue(commandResult.getFeedbackToUser().contains(AddAssignmentCommand.SUCCESS_MESSAGE));
        assertEquals(Arrays.asList(validAssignment), modelStub.assignmentsAdded);
    }

    @Test
    public void execute_duplicateAssignment_throwsCommandException() {
        Assignment validAssignment = TypicalAssignments.ASSIGNMENT1;
        AddAssignmentCommand addAssignmentCommand = new AddAssignmentCommand(validAssignment);
        ModelStub modelStub = new ModelStubWithAssignment(validAssignment);

        assertThrows(CommandException.class,
                AddAssignmentCommand.MESSAGE_DUPLICATE_ASSIGNMENT, () -> addAssignmentCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Assignment assignment1 = TypicalAssignments.ASSIGNMENT1;
        Assignment assignment2 = TypicalAssignments.ASSIGNMENT2;
        AddAssignmentCommand addAssignmentCommand1 = new AddAssignmentCommand(assignment1);
        AddAssignmentCommand addAssignmentCommand2 = new AddAssignmentCommand(assignment2);

        // same object -> returns true
        assertTrue(addAssignmentCommand1.equals(addAssignmentCommand1));

        // same values -> returns true
        AddAssignmentCommand addAssignmentCommand1Copy = new AddAssignmentCommand(assignment1);
        assertTrue(addAssignmentCommand1.equals(addAssignmentCommand1Copy));

        // different types -> returns false
        assertFalse(addAssignmentCommand1.equals(1));

        // null -> returns false
        assertFalse(addAssignmentCommand1.equals(null));

        // different assignment -> returns false
        assertFalse(addAssignmentCommand1.equals(addAssignmentCommand2));
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
        public void addStudent(Student student) {
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
        public boolean hasAssignment(Assignment assignment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public AssignmentList getAssignmentList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteAssignment(Assignment assignment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String checkAssignment(Assignment assignment) throws AssignmentNotFoundException {
            return "";
        }

        @Override
        public void setAssignmentStatus(Assignment assignment, Student student, boolean status)
                throws AssignmentNotFoundException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String listAssignments() {
            return "";
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
        public ObservableList<Student> getFilteredStudentList() {
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
        public void addTutorial(Tutorial toAdd) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public TutorialList getTutorialList() {
            return null;
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
        public void setTutorials(TutorialList tutorials) {
            throw new AssertionError("This method should not be called.");
        }

        public String listTutorials() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that always accepts the assignment being added.
     */
    private class ModelStubAcceptingAssignmentAdded extends ModelStub {
        final ArrayList<Assignment> assignmentsAdded = new ArrayList<>();

        @Override
        public boolean hasAssignment(Assignment assignment) {
            requireNonNull(assignment);
            return assignmentsAdded.stream().anyMatch(assignment::equals);
        }

        @Override
        public void addAssignment(Assignment assignment) {
            requireNonNull(assignment);
            assignmentsAdded.add(assignment);
        }

        @Override
        public AssignmentList getAssignmentList() {
            return new AssignmentList();
        }
    }

    /**
     * A Model stub that contains a single assignment.
     */
    private class ModelStubWithAssignment extends ModelStub {
        private final Assignment assignment;

        ModelStubWithAssignment(Assignment assignment) {
            requireNonNull(assignment);
            this.assignment = assignment;
        }

        @Override
        public boolean hasAssignment(Assignment assignment) {
            requireNonNull(assignment);
            return this.assignment.equals(assignment);
        }
    }
}
