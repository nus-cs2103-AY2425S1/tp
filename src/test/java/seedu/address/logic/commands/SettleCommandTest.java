package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.student.OwedAmount;
import seedu.address.model.student.PaidAmount;
import seedu.address.model.student.SettleAmount;
import seedu.address.model.student.Student;


public class SettleCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        SettleAmount amountToSettle = new SettleAmount("50.00");
        SettleCommand settleCommand = new SettleCommand(INDEX_FIRST_STUDENT, amountToSettle);

        Student studentToEdit = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        Student editedStudent = createEditedStudentWithUpdatedAmounts(studentToEdit, amountToSettle);

        String expectedMessage = String.format(SettleCommand.MESSAGE_SETTLE_SUCCESS,
                amountToSettle.value, editedStudent.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setStudent(studentToEdit, editedStudent);

        assertCommandSuccess(settleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        SettleCommand settleCommand = new SettleCommand(outOfBoundIndex, new SettleAmount("50.00"));

        assertCommandFailure(settleCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_filteredList_success() {
        showStudentAtIndex(model, INDEX_FIRST_STUDENT);

        SettleAmount amountToSettle = new SettleAmount("30.00");
        SettleCommand settleCommand = new SettleCommand(INDEX_FIRST_STUDENT, amountToSettle);

        Student studentInFilteredList = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        Student editedStudent = createEditedStudentWithUpdatedAmounts(studentInFilteredList, amountToSettle);

        String expectedMessage = String.format(SettleCommand.MESSAGE_SETTLE_SUCCESS,
                amountToSettle.value, editedStudent.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setStudent(studentInFilteredList, editedStudent);

        assertCommandSuccess(settleCommand, model, expectedMessage, expectedModel);
    }
    @Test
    public void execute_invalidStudentIndexFilteredList_failure() {
        showStudentAtIndex(model, INDEX_FIRST_STUDENT);
        Index outOfBoundIndex = INDEX_SECOND_STUDENT;

        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getStudentList().size());

        SettleCommand settleCommand = new SettleCommand(outOfBoundIndex, new SettleAmount("50.00"));

        assertCommandFailure(settleCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }
    @Test
    public void execute_invalidAmount_failure() {
        Student student = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        String amountString = Double.toString(student.getOwedAmountValue() + 1.0);
        SettleAmount invalidAmount = new SettleAmount(amountString); // More than what is owed
        SettleCommand settleCommand = new SettleCommand(INDEX_FIRST_STUDENT, invalidAmount);

        assertCommandFailure(settleCommand, model, SettleCommand.MESSAGE_INVALID_AMOUNT);
    }

    @Test
    public void createUpdatedStudent_validAmount_updatesCorrectly() throws Exception {
        SettleAmount amountToSettle = new SettleAmount("20.00");
        SettleCommand settleCommand = new SettleCommand(INDEX_FIRST_STUDENT, amountToSettle);

        Student studentToUpdate = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        Student updatedStudent = settleCommand.createUpdatedStudent(studentToUpdate);

        double expectedOwedAmount = studentToUpdate.getOwedAmount().value - amountToSettle.value;
        double expectedPaidAmount = studentToUpdate.getPaidAmount().value + amountToSettle.value;

        assertEquals(expectedOwedAmount, updatedStudent.getOwedAmount().value, 0.001);
        assertEquals(expectedPaidAmount, updatedStudent.getPaidAmount().value, 0.001);
    }

    // Invalid case where the amount exceeds the student's owed amount
    @Test
    public void createUpdatedStudent_amountExceedsOwed_throwsCommandException() {
        Student studentToUpdate = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        String amountString = Double.toString(studentToUpdate.getOwedAmountValue() + 10.0);
        SettleAmount amountToSettle = new SettleAmount(amountString); // more than owed

        SettleCommand settleCommand = new SettleCommand(INDEX_FIRST_STUDENT, amountToSettle);

        assertThrows(CommandException.class, () -> settleCommand.createUpdatedStudent(studentToUpdate));
    }

    // Case where the amount fully settles the owed amount
    @Test
    public void createUpdatedStudent_fullSettlement_updatesOwedToZero() throws Exception {
        Student studentToUpdate = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        String amountString = Double.toString(studentToUpdate.getOwedAmountValue());

        SettleAmount fullSettlementAmount = new SettleAmount(amountString);

        SettleCommand settleCommand = new SettleCommand(INDEX_FIRST_STUDENT, fullSettlementAmount);
        Student updatedStudent = settleCommand.createUpdatedStudent(studentToUpdate);

        assertEquals(0.0, updatedStudent.getOwedAmountValue(), 0.001);
        double expectedPaidAmount = studentToUpdate.getPaidAmountValue() + fullSettlementAmount.value;
        assertEquals(expectedPaidAmount, updatedStudent.getPaidAmountValue(), 0.001);
    }

    // Case where partial payment is made, ensuring both paid and owed amounts are updated correctly
    @Test
    public void createUpdatedStudent_partialSettlement_updatesCorrectly() throws Exception {
        Student studentToUpdate = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        String amountString = Double.toString(studentToUpdate.getOwedAmountValue() / 2);

        SettleAmount partialPayment = new SettleAmount(amountString);

        SettleCommand settleCommand = new SettleCommand(INDEX_FIRST_STUDENT, partialPayment);
        Student updatedStudent = settleCommand.createUpdatedStudent(studentToUpdate);

        double expectedOwedAmount = studentToUpdate.getOwedAmountValue() - partialPayment.value;
        double expectedPaidAmount = studentToUpdate.getPaidAmountValue() + partialPayment.value;

        assertEquals(expectedOwedAmount, updatedStudent.getOwedAmountValue(), 0.001);
        assertEquals(expectedPaidAmount, updatedStudent.getPaidAmountValue(), 0.001);
    }

    @Test
    public void equals() {
        final SettleCommand standardCommand = new SettleCommand(INDEX_FIRST_STUDENT, new SettleAmount("30.00"));

        // same values -> returns true
        SettleCommand commandWithSameValues = new SettleCommand(INDEX_FIRST_STUDENT, new SettleAmount("30.00"));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new SettleCommand(INDEX_SECOND_STUDENT, new SettleAmount("30.00"))));

        // different amount -> returns false
        assertFalse(standardCommand.equals(new SettleCommand(INDEX_FIRST_STUDENT, new SettleAmount("50.00"))));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        SettleAmount amount = new SettleAmount("50.00");
        SettleCommand settleCommand = new SettleCommand(index, amount);

        String expected = SettleCommand.class.getCanonicalName() + "{index=" + index + ", amount=" + amount + "}";

        assertEquals(expected, settleCommand.toString());
    }

    private Student createEditedStudentWithUpdatedAmounts(Student studentToEdit, SettleAmount amountSettled) {
        double updatedOwedAmount = studentToEdit.getOwedAmount().value - amountSettled.value;
        double updatedPaidAmount = studentToEdit.getPaidAmount().value + amountSettled.value;
        return new Student(
                studentToEdit.getName(),
                studentToEdit.getPhone(),
                studentToEdit.getEmail(),
                studentToEdit.getAddress(),
                studentToEdit.getSchedule(),
                studentToEdit.getSubject(),
                studentToEdit.getRate(),
                new PaidAmount(Double.toString(updatedPaidAmount)),
                new OwedAmount(Double.toString(updatedOwedAmount))
        );
    }
}
