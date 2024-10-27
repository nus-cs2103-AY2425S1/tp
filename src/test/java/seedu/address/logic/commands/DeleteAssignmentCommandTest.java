package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAssignments.ASSIGNMENT1;
import static seedu.address.testutil.TypicalAssignments.ASSIGNMENT2;
import static seedu.address.testutil.TypicalAssignments.getTypicalAssignmentList;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.tut.TutorialList;

/**
 * Contains unit tests for {@code DeleteAssignmentCommand}.
 */
public class DeleteAssignmentCommandTest {
    //TODO: Add a new sample ArrayList
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(),
            getTypicalAssignmentList(), new TutorialList());

    @Test
    public void execute_validTitle_success() {
        Assignment assignment = model.getAssignmentList().getAssignments().get(0);
        DeleteAssignmentCommand deleteAssignmentCommand = new DeleteAssignmentCommand(assignment);
        String expectedMessage = String.format(DeleteAssignmentCommand.MESSAGE_DELETE_ASSIGNMENT_SUCCESS,
                assignment.toStringWithoutStats());
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(),
                getTypicalAssignmentList(), new TutorialList());
        expectedModel.deleteAssignment(assignment);

        assertCommandSuccess(deleteAssignmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTitle_success() {
        Assignment assignment = new Assignment("Invalid", LocalDateTime.of(2000, 10, 10, 20, 20));
        DeleteAssignmentCommand deleteAssignmentCommand = new DeleteAssignmentCommand(assignment);
        assertCommandFailure(deleteAssignmentCommand, model, DeleteAssignmentCommand.MESSAGE_ASSIGNMENT_NOT_FOUND);
    }

    @Test
    public void equals() {
        DeleteAssignmentCommand deleteFirstCommand = new DeleteAssignmentCommand(ASSIGNMENT1);
        DeleteAssignmentCommand deleteSecondCommand = new DeleteAssignmentCommand(ASSIGNMENT2);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteAssignmentCommand deleteFirstCommandCopy = new DeleteAssignmentCommand(ASSIGNMENT1);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different student -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Assignment assignment = ASSIGNMENT1;
        DeleteAssignmentCommand deleteAssignmentCommand = new DeleteAssignmentCommand(assignment);
        String expected = DeleteAssignmentCommand.class.getCanonicalName() + "{assignment=" + assignment + "}";
        assertEquals(expected, deleteAssignmentCommand.toString());
    }


}
