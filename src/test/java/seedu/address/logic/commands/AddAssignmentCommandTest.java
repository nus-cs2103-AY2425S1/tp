package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ASSIGNMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentName;
import seedu.address.model.student.Student;
import seedu.address.testutil.AssignmentBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code AddAssignmentCommand}.
 */
public class AddAssignmentCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullStudentAndAssignment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddAssignmentCommand(null, null));
    }

    @Test
    public void execute_assignmentAcceptedByModel_addSuccessful() throws Exception {
        AddAssignmentCommand.AssignmentDescriptor toAddDescriptor =
                new AddAssignmentCommand.AssignmentDescriptor(AssignmentBuilder.DEFAULT_MAX_SCORE,
                        new AssignmentName(AssignmentBuilder.DEFAULT_ASSIGNMENT_NAME));
        AddAssignmentCommand addAssignmentCommand = new AddAssignmentCommand(INDEX_FIRST_STUDENT, toAddDescriptor);
        Student studentToAddAssignmentTo = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        Assignment expectedAssignment = new AssignmentBuilder().withStudent(studentToAddAssignmentTo).build();
        Student studentWithAssignment = studentToAddAssignmentTo.addAssignment(expectedAssignment);

        String expectedMessage = String.format(AddAssignmentCommand.MESSAGE_SUCCESS,
                Messages.format(expectedAssignment));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setStudent(studentToAddAssignmentTo, studentWithAssignment);

        assertCommandSuccess(addAssignmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateAssignment_throwsCommandException() throws Exception {
        AddAssignmentCommand.AssignmentDescriptor toAddDescriptor =
                new AddAssignmentCommand.AssignmentDescriptor(AssignmentBuilder.DEFAULT_MAX_SCORE,
                        new AssignmentName(AssignmentBuilder.DEFAULT_ASSIGNMENT_NAME));
        AddAssignmentCommand addAssignmentCommand = new AddAssignmentCommand(INDEX_FIRST_STUDENT, toAddDescriptor);
        addAssignmentCommand.execute(model);

        AddAssignmentCommand.AssignmentDescriptor duplicateToAddDescriptor =
                new AddAssignmentCommand.AssignmentDescriptor(AssignmentBuilder.DEFAULT_MAX_SCORE,
                        new AssignmentName(AssignmentBuilder.DEFAULT_ASSIGNMENT_NAME));
        AddAssignmentCommand duplicateAddAssignmentCommand = new AddAssignmentCommand(INDEX_FIRST_STUDENT, duplicateToAddDescriptor);

        assertCommandFailure(duplicateAddAssignmentCommand, model, AddAssignmentCommand.MESSAGE_DUPLICATE_ASSIGNMENT);
    }

    @Test
    public void execute_emojiAssignment_throwsCommandException() throws Exception {
        AddAssignmentCommand.AssignmentDescriptor toAddDescriptor =
                new AddAssignmentCommand.AssignmentDescriptor(AssignmentBuilder.DEFAULT_MAX_SCORE,
                        new AssignmentName(new String(Character.toChars(0x1F349))));
        AddAssignmentCommand addAssignmentCommand = new AddAssignmentCommand(INDEX_FIRST_STUDENT, toAddDescriptor);
        addAssignmentCommand.execute(model);

        AddAssignmentCommand.AssignmentDescriptor duplicateToAddDescriptor =
                new AddAssignmentCommand.AssignmentDescriptor(AssignmentBuilder.DEFAULT_MAX_SCORE,
                        new AssignmentName(AssignmentBuilder.DEFAULT_ASSIGNMENT_NAME));
        AddAssignmentCommand duplicateAddAssignmentCommand = new AddAssignmentCommand(INDEX_FIRST_STUDENT, duplicateToAddDescriptor);

        assertCommandFailure(duplicateAddAssignmentCommand, model, AddAssignmentCommand.MESSAGE_DUPLICATE_ASSIGNMENT);
    }
}
