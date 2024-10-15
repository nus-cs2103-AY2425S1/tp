package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ASSIGNMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_STUDENT;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.student.Student;
import seedu.address.testutil.AssignmentBuilder;
import seedu.address.testutil.StudentBuilder;


public class MarkAssignmentCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validStudentAndAssignmentIndex_markSuccessful() {
        Student student = new StudentBuilder().withAssignments(new ArrayList<>()).build();
        model.addStudent(student);

        Assignment assignment = new AssignmentBuilder()
                .withAssignmentName("Math Homework")
                .withMaxScore(100)
                .build();
        student.getAssignmentList().add(assignment);
        model.updateFilteredStudentList(Model.PREDICATE_SHOW_ALL_STUDENTS);
        MarkAssignmentCommand markAssignmentCommand = new MarkAssignmentCommand(INDEX_FIRST_STUDENT, INDEX_FIRST_ASSIGNMENT);

        String expectedMessage = String.format(MarkAssignmentCommand.MESSAGE_MARK_SUCCESS, "Math Homework",
                student.getName());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredStudentList(Model.PREDICATE_SHOW_ALL_STUDENTS);

        expectedModel.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased())
                .addAssignment(assignment);
        expectedModel.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased()).getAssignmentList()
                        .get(INDEX_FIRST_ASSIGNMENT.getZeroBased()).setHasSubmitted(true);

        assertCommandSuccess(markAssignmentCommand, model, expectedMessage, expectedModel);
        assertTrue(student.getAssignmentList().get(INDEX_FIRST_ASSIGNMENT.getZeroBased()).getHasSubmitted());
    }
}
