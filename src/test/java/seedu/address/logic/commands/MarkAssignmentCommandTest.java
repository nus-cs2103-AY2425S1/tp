package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAssignments.ASSIGNMENT1;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
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

public class MarkAssignmentCommandTest {

    @Test
    public void execute_assignmentExists_success() throws Exception {
        ReadOnlyAddressBook addressBook = TypicalStudents.getTypicalAddressBook();
        AssignmentList assignmentList = TypicalAssignments.getTypicalAssignmentList();
        Model model = new ModelManager(addressBook, new UserPrefs(), assignmentList, new TutorialList());
        Assignment assignment = new Assignment("Assignment 1", ASSIGNMENT1.getDueDate());
        MarkAssignmentCommand markCommand = new MarkAssignmentCommand(0, assignment);

        CommandResult result = markCommand.execute(model);
        String expectedResult = "Assignment marked successfully!";
        assertEquals(expectedResult, result.getFeedbackToUser());
    }

    @Test
    public void execute_assignmentDoesNotExist_throwsCommandException() {
        ReadOnlyAddressBook addressBook = TypicalStudents.getTypicalAddressBook();
        AssignmentList assignmentList = new AssignmentList();
        Model model = new ModelManager(addressBook, new UserPrefs(), assignmentList, new TutorialList());
        MarkAssignmentCommand markCommand = new MarkAssignmentCommand(0, ASSIGNMENT1);

        assertThrows(CommandException.class, MarkAssignmentCommand.MESSAGE_ASSIGNMENT_NOT_FOUND, () -> {
            markCommand.execute(model);
        });
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        ReadOnlyAddressBook addressBook = TypicalStudents.getTypicalAddressBook();
        AssignmentList assignmentList = new AssignmentList();
        Model model = new ModelManager(addressBook, new UserPrefs(), assignmentList, new TutorialList());
        MarkAssignmentCommand markCommand1 = new MarkAssignmentCommand(-1, ASSIGNMENT1);
        MarkAssignmentCommand markCommand2 = new MarkAssignmentCommand(10000, ASSIGNMENT1);

        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX, () -> {
            markCommand1.execute(model);
        });
        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX, () -> {
            markCommand2.execute(model);
        });
    }

    @Test
    public void equals() {
        MarkAssignmentCommand command1 = new MarkAssignmentCommand(0, ASSIGNMENT1);
        Assignment assignment2 = new Assignment("Assignment 2", ASSIGNMENT1.getDueDate());
        MarkAssignmentCommand command2 = new MarkAssignmentCommand(0, assignment2);
        MarkAssignmentCommand command3 = new MarkAssignmentCommand(1, ASSIGNMENT1);

        // Same object -> returns true
        assertTrue(command1.equals(command1));

        // Same values -> returns true
        MarkAssignmentCommand command1Copy = new MarkAssignmentCommand(0, ASSIGNMENT1);
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
