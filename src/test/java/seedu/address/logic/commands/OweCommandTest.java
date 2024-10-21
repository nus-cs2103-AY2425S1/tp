package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HOUR_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HOUR_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OWED_AMOUNT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OWED_AMOUNT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showStudentAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_OUT_OF_BOUNDS;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_STUDENT;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.student.OwedAmount;
import seedu.address.model.student.Student;
import seedu.address.testutil.StudentBuilder;

public class OweCommandTest {
    
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    
    @Test
    public void execute_unfilteredList_success() {
        Student chosenStudent = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        OweCommand oweCommand = new OweCommand(INDEX_FIRST_STUDENT, VALID_HOUR_AMY);
        Student updatedOwedAmountStudent = new StudentBuilder(chosenStudent).withOwedAmount(Double.toString(chosenStudent.getOwedAmount().addOwedAmount(VALID_OWED_AMOUNT_AMY).value)).build();
        
        String expectedMessage = String.format(OweCommand.MESSAGE_UPDATE_OWED_AMOUNT_SUCCESS,
                Messages.format(updatedOwedAmountStudent));
        
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setStudent(model.getFilteredStudentList().get(0), updatedOwedAmountStudent);
        
        assertCommandSuccess(oweCommand, model, expectedMessage, expectedModel);
    }
    
    @Test
    public void execute_filteredList_success() {
        showStudentAtIndex(model, INDEX_FIRST_STUDENT);
        
        Student studentInFilteredList = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        Student updatedOwedAmountStudent = new StudentBuilder(studentInFilteredList).withOwedAmount(studentInFilteredList.getOwedAmount().addOwedAmount(VALID_OWED_AMOUNT_BOB).toString()).build();
        OweCommand oweCommand = new OweCommand(INDEX_FIRST_STUDENT, VALID_HOUR_BOB);
        
        String expectedMessage = String.format(OweCommand.MESSAGE_UPDATE_OWED_AMOUNT_SUCCESS, Messages.format(updatedOwedAmountStudent));
        
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setStudent(model.getFilteredStudentList().get(0), updatedOwedAmountStudent);
        
        assertCommandSuccess(oweCommand, model, expectedMessage, expectedModel);
    }
    
    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        OweCommand oweCommand = new OweCommand(INDEX_OUT_OF_BOUNDS, VALID_HOUR_BOB);
        assertCommandFailure(oweCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }
    
    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showStudentAtIndex(model, INDEX_FIRST_STUDENT);
        OweCommand oweCommand = new OweCommand(Index.fromOneBased(2), VALID_HOUR_BOB);
        assertCommandFailure(oweCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }
    
    @Test
    public void equals() {
        final OweCommand standardCommand = new OweCommand(INDEX_FIRST_STUDENT, VALID_HOUR_BOB);
        
        // same values -> returns true
        OweCommand commandWithSameValues = new OweCommand(INDEX_FIRST_STUDENT, VALID_HOUR_BOB);
        assertTrue(standardCommand.equals(commandWithSameValues));
        
        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));
        
        // null -> returns false
        assertFalse(standardCommand.equals(null));
        
        // different index -> returns false
        assertFalse(standardCommand.equals(new OweCommand(INDEX_SECOND_STUDENT, VALID_HOUR_BOB)));
        
        // different owedAmount -> returns false
        assertFalse(standardCommand.equals(new OweCommand(INDEX_FIRST_STUDENT, VALID_HOUR_AMY)));
    }
    
    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        OweCommand oweCommand = new OweCommand(index, VALID_HOUR_AMY);
        String expected = OweCommand.class.getCanonicalName() + "{index=" + index + ", hourOwed=" + VALID_HOUR_AMY + "}";
        assertEquals(expected, oweCommand.toString());
    }
}
