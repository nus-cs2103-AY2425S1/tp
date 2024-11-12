package seedu.address.logic.commands;

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

public class CheckAssignmentCommandTest {

    @Test
    public void execute_assignmentExists_success() throws Exception {
        ReadOnlyAddressBook addressBook = TypicalStudents.getTypicalAddressBook();
        AssignmentList assignmentList = TypicalAssignments.getTypicalAssignmentList();
        Model model = new ModelManager(addressBook, new UserPrefs(), assignmentList, new TutorialList());
        CheckAssignmentCommand checkCommand = new CheckAssignmentCommand(ASSIGNMENT1);

        CommandResult result = checkCommand.execute(model);
        String expectedResult = "Below are the assignment completion statistics for: ";
        assertTrue(result.getFeedbackToUser().contains(expectedResult));
    }

    @Test
    public void execute_assignmentDoesNotExist_throwsCommandException() {
        ReadOnlyAddressBook addressBook = TypicalStudents.getTypicalAddressBook();
        AssignmentList assignmentList = new AssignmentList();
        Model model = new ModelManager(addressBook, new UserPrefs(), assignmentList, new TutorialList());
        CheckAssignmentCommand checkCommand = new CheckAssignmentCommand(ASSIGNMENT1);

        assertThrows(CommandException.class, CheckAssignmentCommand.MESSAGE_ASSIGNMENT_NOT_FOUND, () -> {
            checkCommand.execute(model);
        });
    }

    @Test
    public void execute_isCheckingAssignment_returnsTrue() throws Exception {
        ReadOnlyAddressBook addressBook = TypicalStudents.getTypicalAddressBook();
        AssignmentList assignmentList = TypicalAssignments.getTypicalAssignmentList();
        Model model = new ModelManager(addressBook, new UserPrefs(), assignmentList, new TutorialList());
        CheckAssignmentCommand checkCommand = new CheckAssignmentCommand(ASSIGNMENT1);

        CommandResult result = checkCommand.execute(model);
        assertTrue(CheckAssignmentCommand.isCheckingAssignment());
    }

    @Test
    public void equals() {
        CheckAssignmentCommand command1 = new CheckAssignmentCommand(ASSIGNMENT1);
        Assignment assignment2 = new Assignment("Assignment 2", ASSIGNMENT1.getDueDate());
        CheckAssignmentCommand command2 = new CheckAssignmentCommand(assignment2);

        // Same object -> returns true
        assertTrue(command1.equals(command1));

        // Same values -> returns true
        CheckAssignmentCommand command1Copy = new CheckAssignmentCommand(ASSIGNMENT1);
        assertTrue(command1.equals(command1Copy));

        // Different types -> returns false
        assertFalse(command1.equals(1));

        // Null -> returns false
        assertFalse(command1.equals(null));

        // Different assignment -> returns false
        assertFalse(command1.equals(command2));
    }

}
