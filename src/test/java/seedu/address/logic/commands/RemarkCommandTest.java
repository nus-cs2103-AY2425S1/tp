package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_MATH;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showStudentAtIndex;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_STUDENT;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.student.Remark;
import seedu.address.model.student.Student;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code RemarkCommand}.
 */
public class RemarkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Student studentToAddRemark = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_STUDENT, new Remark(VALID_REMARK_MATH));

        Student updatedStudent = new Student(studentToAddRemark, new Remark(VALID_REMARK_MATH));

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS,
                Messages.format(updatedStudent));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setStudent(studentToAddRemark, updatedStudent);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_emptyRemark_success() {
        Student studentToAddRemark = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_STUDENT, new Remark(""));

        Student updatedStudent = new Student(studentToAddRemark, new Remark(""));

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS,
                Messages.format(updatedStudent));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setStudent(studentToAddRemark, updatedStudent);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        RemarkCommand remarkCommand = new RemarkCommand(outOfBoundIndex, new Remark(VALID_REMARK_MATH));

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showStudentAtIndex(model, INDEX_FIRST_STUDENT);

        Student studentToAddRemark = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_STUDENT, new Remark(VALID_REMARK_MATH));

        Student updatedStudent = new Student(studentToAddRemark, new Remark(VALID_REMARK_MATH));

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS,
                Messages.format(updatedStudent));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setStudent(studentToAddRemark, updatedStudent);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void constructor_nullRemark_throwsNullPointerException() {
        // null remark should throw a NullPointerException
        assertThrows(AssertionError.class, () -> new RemarkCommand(INDEX_FIRST_STUDENT, null));
    }

    @Test
    public void equals() {
        Remark firstRemark = new Remark("Weak in Math");
        Remark secondRemark = new Remark("Strong in Science");

        RemarkCommand remarkFirstCommand = new RemarkCommand(INDEX_FIRST_STUDENT, firstRemark);
        RemarkCommand remarkSecondCommand = new RemarkCommand(INDEX_SECOND_STUDENT, secondRemark);

        // same object -> returns true
        assertTrue(remarkFirstCommand.equals(remarkFirstCommand));

        // same values -> returns true
        RemarkCommand remarkFirstCommandCopy = new RemarkCommand(INDEX_FIRST_STUDENT, firstRemark);
        assertTrue(remarkFirstCommand.equals(remarkFirstCommandCopy));

        // different types -> returns false
        assertFalse(remarkFirstCommand.equals(1));

        // null -> returns false
        assertFalse(remarkFirstCommand.equals(null));

        // different student -> returns false
        assertFalse(remarkFirstCommand.equals(remarkSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Remark remark = new Remark("Weak at English");
        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_STUDENT, remark);
        String expected = RemarkCommand.class.getCanonicalName() + "{studentIndex=" + INDEX_FIRST_STUDENT
                + ", remark=" + remark + "}";
        assertEquals(expected, remarkCommand.toString());
    }
}
