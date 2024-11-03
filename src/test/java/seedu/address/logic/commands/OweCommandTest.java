package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HOUR_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HOUR_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showStudentAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_OUT_OF_BOUNDS;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_STUDENT;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
        // EP: valid command in an unfiltered list
        Student chosenStudent = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        OweCommand oweCommand = new OweCommand(INDEX_FIRST_STUDENT, Double.parseDouble(VALID_HOUR_AMY));
        Student updatedOwedAmountStudent = createExpectedStudent(chosenStudent, Double.parseDouble(VALID_HOUR_AMY));
        String expectedName = updatedOwedAmountStudent.getName().toString();
        double additionOwedAmount = calculateAdditionOwedAmount(chosenStudent, Double.parseDouble(VALID_HOUR_AMY));

        String expectedMessage = String.format(OweCommand.MESSAGE_UPDATE_OWED_AMOUNT_SUCCESS,
                expectedName, additionOwedAmount);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setStudent(model.getFilteredStudentList().get(0), updatedOwedAmountStudent);

        assertCommandSuccess(oweCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        // EP: valid command in a filtered list
        showStudentAtIndex(model, INDEX_FIRST_STUDENT);

        Student studentInFilteredList = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        OweCommand oweCommand = new OweCommand(INDEX_FIRST_STUDENT, Double.parseDouble(VALID_HOUR_BOB));
        Student updatedOwedAmountStudent = createExpectedStudent(
                studentInFilteredList, Double.parseDouble(VALID_HOUR_BOB));
        String expectedName = updatedOwedAmountStudent.getName().toString();
        double additionOwedAmount = calculateAdditionOwedAmount(
                studentInFilteredList, Double.parseDouble(VALID_HOUR_BOB));

        String expectedMessage = String.format(
                OweCommand.MESSAGE_UPDATE_OWED_AMOUNT_SUCCESS, expectedName, additionOwedAmount);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setStudent(model.getFilteredStudentList().get(0), updatedOwedAmountStudent);

        assertCommandSuccess(oweCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        // EP: invalid index in an unfiltered list
        OweCommand oweCommand = new OweCommand(INDEX_OUT_OF_BOUNDS, Double.parseDouble(VALID_HOUR_BOB));
        assertCommandFailure(oweCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        // EP: invalid index in a filtered list
        showStudentAtIndex(model, INDEX_FIRST_STUDENT);
        OweCommand oweCommand = new OweCommand(Index.fromOneBased(2), Double.parseDouble(VALID_HOUR_BOB));
        assertCommandFailure(oweCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_exceededLimitUnfilteredList_failure() {
        // EP: exceeded limit in an unfiltered list
        Student chosenStudent = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        double currentOwedAmountValue = chosenStudent.getOwedAmountValue();
        double additionalOwedAmountValue = OwedAmount.MAX_VALUE - currentOwedAmountValue + 1;
        double hour = BigDecimal.valueOf(additionalOwedAmountValue / chosenStudent.getRateValue())
                                .setScale(0, RoundingMode.UP)
                                .doubleValue();
        OweCommand oweCommand = new OweCommand(INDEX_FIRST_STUDENT, hour);
        assertCommandFailure(oweCommand, model, Messages.MESSAGE_LIMIT);
    }

    @Test
    public void execute_exceededLimitFilteredList_failure() {
        showStudentAtIndex(model, INDEX_FIRST_STUDENT);
        Student studentInFilteredList = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        double currentOwedAmountValue = studentInFilteredList.getOwedAmountValue();
        double additionalOwedAmountValue = OwedAmount.MAX_VALUE - currentOwedAmountValue;
        double hour = BigDecimal.valueOf(additionalOwedAmountValue / studentInFilteredList.getRateValue())
                                .setScale(0, RoundingMode.UP).doubleValue();
        OweCommand oweCommand = new OweCommand(INDEX_FIRST_STUDENT, hour);

        assertCommandFailure(oweCommand, model, Messages.MESSAGE_LIMIT);
    }

    @Test
    public void equals() {
        final OweCommand standardCommand = new OweCommand(INDEX_FIRST_STUDENT, Double.parseDouble(VALID_HOUR_BOB));

        // same values -> returns true
        OweCommand commandWithSameValues = new OweCommand(INDEX_FIRST_STUDENT, Double.parseDouble(VALID_HOUR_BOB));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different index -> returns false
        assertFalse(standardCommand.equals(new OweCommand(INDEX_SECOND_STUDENT, Double.parseDouble(VALID_HOUR_BOB))));

        // different owedAmount -> returns false
        assertFalse(standardCommand.equals(new OweCommand(INDEX_FIRST_STUDENT, Double.parseDouble(VALID_HOUR_AMY))));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        OweCommand oweCommand = new OweCommand(index, Double.parseDouble(VALID_HOUR_BOB));
        String expected = OweCommand.class.getCanonicalName() + "{index=" + index + ", hourOwed="
                + Double.parseDouble(VALID_HOUR_BOB) + "}";
        assertEquals(expected, oweCommand.toString());
    }

    /**
     * Helper method to create a Student with an addition of number of hours owed.
     */
    private Student createExpectedStudent(Student student, double hourOwed) {
        double additionOwedAmount = calculateAdditionOwedAmount(student, hourOwed);
        double updatedOwedAmount = student.getOwedAmount().value + additionOwedAmount;
        return new StudentBuilder(student).withOwedAmount(Double.toString(updatedOwedAmount)).build();
    }

    /**
     * Calculates addition owedAmount used to generate expected messages and expected students
     */
    private double calculateAdditionOwedAmount(Student student, double hourOwed) {
        return student.getRateValue() * hourOwed;
    }
}
