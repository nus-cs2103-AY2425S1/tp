package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAssignments.MATH_ASSIGNMENT_SUBMITTED;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalStudents.HUGH;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.student.Student;
import seedu.address.testutil.StudentBuilder;

public class AddAssignmentCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }
    @Test
    public void execute_newAssignment_success() {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Student hughCopy = new StudentBuilder(HUGH).build();
        hughCopy.addAssignment(MATH_ASSIGNMENT_SUBMITTED);
        expectedModel.addStudent(hughCopy);

        model.addStudent(HUGH);
        assertCommandSuccess(new AddAssignmentCommand(HUGH.getName(), MATH_ASSIGNMENT_SUBMITTED), model,
                String.format(AddAssignmentCommand.MESSAGE_SUCCESS,
                        MATH_ASSIGNMENT_SUBMITTED.getAssignmentName(), HUGH.getName()),
                expectedModel);
        HUGH.deleteLastAssignment(); // This added assignment changed the state of HUGH, so we need to revert it
    }

    @Test
    public void undo_after_execute() {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Student hughCopy = new StudentBuilder(HUGH).build();
        hughCopy.addAssignment(MATH_ASSIGNMENT_SUBMITTED);
        expectedModel.addStudent(hughCopy);

        model.addStudent(HUGH);
        AddAssignmentCommand addAssignmentCommand = new AddAssignmentCommand(HUGH.getName(), MATH_ASSIGNMENT_SUBMITTED);
        assertCommandSuccess(addAssignmentCommand, model,
                String.format(AddAssignmentCommand.MESSAGE_SUCCESS,
                        MATH_ASSIGNMENT_SUBMITTED.getAssignmentName(), HUGH.getName()),
                expectedModel);

        assertTrue(addAssignmentCommand.undo(model));
        assertTrue(HUGH.getAssignments().size() == 0);
    }
}
