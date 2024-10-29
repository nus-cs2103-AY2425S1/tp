package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ASSIGNMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.student.Student;
import seedu.address.testutil.AssignmentBuilder;
import seedu.address.testutil.StudentBuilder;

class EditAssignmentCommandTest {

    private static final int VALID_SCORE = 85;
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @BeforeEach
    void setUp() {
        model = new ModelManager();
        ArrayList<Assignment> assignments = new ArrayList<Assignment>();
        Assignment assignment = new AssignmentBuilder().build();
        assignments.add(assignment);
        Student validStudent = new StudentBuilder().withAssignments(assignments).build();
        model.addStudent(validStudent);
    }

    @Test
    void execute_validIndices_success() throws CommandException {
        // Retrieve the student and their assignment list
        Student studentToEdit = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        Assignment originalAssignment = studentToEdit.getAssignmentList().get(INDEX_FIRST_ASSIGNMENT.getZeroBased());

        // Initialize and execute the command
        EditAssignmentCommand command = new EditAssignmentCommand(INDEX_FIRST_STUDENT, INDEX_FIRST_ASSIGNMENT, VALID_SCORE);
        CommandResult result = command.execute(model);

        // Verify the score was updated and the success message is correct
        assertEquals(VALID_SCORE, originalAssignment.getScore());
        assertEquals(String.format(EditAssignmentCommand.MESSAGE_EDIT_SUCCESS,
                originalAssignment.getName(), studentToEdit.getName().fullName, VALID_SCORE), result.getFeedbackToUser());
    }

    @Test
    void execute_invalidStudentIndex_throwsCommandException() {
        // Initialize a command with an out-of-bounds student index
        EditAssignmentCommand command = new EditAssignmentCommand(Index.fromZeroBased(1), INDEX_FIRST_ASSIGNMENT, VALID_SCORE);

        // Expect a CommandException due to invalid student index
        CommandException thrown = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX, thrown.getMessage());
    }

    @Test
    void execute_invalidAssignmentIndex_throwsCommandException() {
        // Retrieve the student but leave their assignment list empty
        Student studentToEdit = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        studentToEdit.setAssignmentList(new ArrayList<>()); // clear assignments for this test

        // Initialize a command with an out-of-bounds assignment index
        EditAssignmentCommand command = new EditAssignmentCommand(INDEX_FIRST_STUDENT, Index.fromZeroBased(1), VALID_SCORE);

        // Expect a CommandException due to invalid assignment index
        CommandException thrown = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals(Messages.MESSAGE_INVALID_ASSIGNMENT_INDEX, thrown.getMessage());
    }

    @Test
    void equals() {
        final EditAssignmentCommand standardCommand = new EditAssignmentCommand(INDEX_FIRST_STUDENT, INDEX_FIRST_ASSIGNMENT, VALID_SCORE);

        // Same values should return true
        EditAssignmentCommand commandWithSameValues = new EditAssignmentCommand(INDEX_FIRST_STUDENT, INDEX_FIRST_ASSIGNMENT, VALID_SCORE);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // Same object should return true
        assertTrue(standardCommand.equals(standardCommand));

        // Null should return false
        assertTrue(!standardCommand.equals(null));

        // Different types should return false
        assertTrue(!standardCommand.equals(new Object()));

        // Different student index should return false
        assertTrue(!standardCommand.equals(new EditAssignmentCommand(Index.fromZeroBased(2), INDEX_FIRST_ASSIGNMENT, VALID_SCORE)));

        // Different assignment index should return false
        assertTrue(!standardCommand.equals(new EditAssignmentCommand(INDEX_FIRST_STUDENT, Index.fromZeroBased(2), VALID_SCORE)));

        // Different score should return false
        assertTrue(!standardCommand.equals(new EditAssignmentCommand(INDEX_FIRST_STUDENT, INDEX_FIRST_ASSIGNMENT, 90)));
    }
}
