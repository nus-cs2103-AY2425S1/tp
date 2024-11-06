package seedu.edulog.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edulog.logic.Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW;
import static seedu.edulog.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.edulog.testutil.TypicalEdulog.getTypicalEduLog;

import java.util.Collections;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.edulog.model.Model;
import seedu.edulog.model.ModelManager;
import seedu.edulog.model.UserPrefs;
import seedu.edulog.model.student.StudentHasPaidPredicate;
import seedu.edulog.testutil.TypicalStudents;

/**
 * Contains integration tests (interaction with the Model) for {@code FilterCommand}.
 */
public class FilterCommandTest {
    private Model model = new ModelManager(getTypicalEduLog(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalEduLog(), new UserPrefs());

    /*
     * Each test receives a fresh model for independence.
     */

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalEduLog(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalEduLog(), new UserPrefs());
    }

    @AfterEach
    public void tearDown() {
        model = null;
        expectedModel = null;
    }

    @Test
    public void equals() {
        StudentHasPaidPredicate firstPredicate =
                new StudentHasPaidPredicate(true);
        StudentHasPaidPredicate secondPredicate =
                new StudentHasPaidPredicate(false);

        FilterCommand filterFirstCommand = new FilterCommand(firstPredicate);
        FilterCommand filterSecondCommand = new FilterCommand(secondPredicate);

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same values -> returns true
        FilterCommand filterFirstCommandCopy = new FilterCommand(firstPredicate);
        assertTrue(filterFirstCommand.equals(filterFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        // different status -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }

    // failure

    @Test
    public void execute_paid_noStudentFound() {
        // no student has paid
        expectedModel.unmarkAllStudents();
        String expectedMessage = String.format(MESSAGE_STUDENTS_LISTED_OVERVIEW, 0);
        StudentHasPaidPredicate predicate = preparePredicate(true);
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredStudentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredStudentList());
    }

    @Test
    public void execute_paid_allStudentsFound() {
        String expectedMessage = String.format(MESSAGE_STUDENTS_LISTED_OVERVIEW, 7);
        StudentHasPaidPredicate predicate = preparePredicate(true);
        FilterCommand command = new FilterCommand(predicate);
        model.markAllStudents();
        expectedModel.markAllStudents();
        // everyone has paid now
        expectedModel.updateFilteredStudentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedModel.getFilteredStudentList(), model.getFilteredStudentList());
    }

    @Test
    public void execute_paid_singleStudentFound() {
        String expectedMessage = String.format(MESSAGE_STUDENTS_LISTED_OVERVIEW, 1);
        StudentHasPaidPredicate predicate = preparePredicate(true);
        FilterCommand command = new FilterCommand(predicate);
        model.markStudent(TypicalStudents.CARL);
        expectedModel.markStudent(TypicalStudents.CARL);
        // everyone has paid now
        expectedModel.updateFilteredStudentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedModel.getFilteredStudentList(), model.getFilteredStudentList());
    }

    @Test
    public void execute_unpaid_allStudentsFound() {
        String expectedMessage = String.format(MESSAGE_STUDENTS_LISTED_OVERVIEW, 7);
        StudentHasPaidPredicate predicate = preparePredicate(false);
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredStudentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_unpaid_noStudentFound() {
        String expectedMessage = String.format(MESSAGE_STUDENTS_LISTED_OVERVIEW, 0);
        StudentHasPaidPredicate predicate = preparePredicate(false);
        FilterCommand command = new FilterCommand(predicate);
        model.markAllStudents();
        expectedModel.markAllStudents();
        // everyone has paid now
        expectedModel.updateFilteredStudentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredStudentList());
    }


    @Test
    public void toStringMethodTrue() {
        StudentHasPaidPredicate predicate = new StudentHasPaidPredicate(true);
        FilterCommand findCommand = new FilterCommand(predicate);
        String expected = FilterCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    @Test
    public void toStringMethodFalse() {
        StudentHasPaidPredicate predicate = new StudentHasPaidPredicate(false);
        FilterCommand findCommand = new FilterCommand(predicate);
        String expected = FilterCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code StudentHasPaidPredicate}.
     */
    private StudentHasPaidPredicate preparePredicate(boolean hasPaid) {
        return new StudentHasPaidPredicate(hasPaid);
    }
}

