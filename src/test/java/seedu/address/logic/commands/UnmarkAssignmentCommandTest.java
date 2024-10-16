package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAssignments.ASSIGNMENT1;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentList;
import seedu.address.model.tut.TutorialList;
import seedu.address.testutil.TypicalAssignments;
import seedu.address.testutil.TypicalStudents;

public class UnmarkAssignmentCommandTest {

    @Test
    public void execute_assignmentExists_success() throws Exception {
        ReadOnlyAddressBook addressBook = TypicalStudents.getTypicalAddressBook();
        AssignmentList assignmentList = TypicalAssignments.getTypicalAssignmentList();
        Model model = new ModelManager(addressBook, new UserPrefs(), assignmentList, new TutorialList());
        Assignment assignment = new Assignment("Assignment 1", ASSIGNMENT1.getDueDate());
        UnmarkAssignmentCommand unmarkCommand = new UnmarkAssignmentCommand(0, assignment);

        CommandResult result = unmarkCommand.execute(model);
        String expectedResult = "Assignment unmarked successfully!";
        assertEquals(expectedResult, result.getFeedbackToUser());
    }

    @Test
    public void execute_assignmentDoesNotExist_throwsCommandException() {
        ReadOnlyAddressBook addressBook = TypicalStudents.getTypicalAddressBook();
        AssignmentList assignmentList = new AssignmentList();
        Model model = new ModelManager(addressBook, new UserPrefs(), assignmentList, new TutorialList());
        UnmarkAssignmentCommand unmarkCommand = new UnmarkAssignmentCommand(0, ASSIGNMENT1);

        assertThrows(CommandException.class, UnmarkAssignmentCommand.MESSAGE_ASSIGNMENT_NOT_FOUND, () -> {
            unmarkCommand.execute(model);
        });
    }

    @Test
    public void equals() {
        UnmarkAssignmentCommand command1 = new UnmarkAssignmentCommand(0, ASSIGNMENT1);
        Assignment assignment2 = new Assignment("Assignment 2", ASSIGNMENT1.getDueDate());
        UnmarkAssignmentCommand command2 = new UnmarkAssignmentCommand(0, assignment2);
        UnmarkAssignmentCommand command3 = new UnmarkAssignmentCommand(1, ASSIGNMENT1);

        // Same object -> returns true
        assertTrue(command1.equals(command1));

        // Same values -> returns true
        UnmarkAssignmentCommand command1Copy = new UnmarkAssignmentCommand(0, ASSIGNMENT1);
        assertTrue(command1.equals(command1Copy));

        // Different types -> returns false
        assertFalse(command1.equals(1));

        // Null -> returns false
        assertFalse(command1.equals(null));

        // Different assignment -> returns false
        assertFalse(command1.equals(command2));

        //Different targetIndex -> returns false
        assertFalse(command1.equals(command3));
    }

}
