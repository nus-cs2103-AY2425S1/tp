package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showStudentAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_STUDENT;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.student.PaidAmount;
import seedu.address.model.student.Student;

/**
 * Contains integration tests (interaction with the Model) and unit tests for PayCommand.
 */
public class PayCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        double hoursPaid = 5.0;
        PayCommand payCommand = new PayCommand(INDEX_FIRST_STUDENT, hoursPaid);

        Student studentToEdit = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        double amountPaid = studentToEdit.getRate().value * hoursPaid;
        Student editedStudent = createEditedStudentWithUpdatedPaidAmount(studentToEdit, amountPaid);

        String expectedMessage = String.format(PayCommand.MESSAGE_EDIT_STUDENT_SUCCESS,
                editedStudent.getName(), amountPaid);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setStudent(studentToEdit, editedStudent);

        assertCommandSuccess(payCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        PayCommand payCommand = new PayCommand(outOfBoundIndex, 3.0);

        assertCommandFailure(payCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_filteredList_success() {
        showStudentAtIndex(model, INDEX_FIRST_STUDENT);

        double hoursPaid = 2.0;
        PayCommand payCommand = new PayCommand(INDEX_FIRST_STUDENT, hoursPaid);

        Student studentInFilteredList = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        double amountPaid = studentInFilteredList.getRate().value * hoursPaid;
        Student editedStudent = createEditedStudentWithUpdatedPaidAmount(studentInFilteredList, amountPaid);

        String expectedMessage = String.format(PayCommand.MESSAGE_EDIT_STUDENT_SUCCESS,
                editedStudent.getName(), amountPaid);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setStudent(studentInFilteredList, editedStudent);

        assertCommandSuccess(payCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidStudentIndexFilteredList_failure() {
        showStudentAtIndex(model, INDEX_FIRST_STUDENT);
        Index outOfBoundIndex = INDEX_SECOND_STUDENT;

        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getStudentList().size());

        PayCommand payCommand = new PayCommand(outOfBoundIndex, 3.0);

        assertCommandFailure(payCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPaidAmountFilteredList_failure() {
        showStudentAtIndex(model, INDEX_FIRST_STUDENT);
        double invalidPaidAmount = 10000000;

        PayCommand payCommand = new PayCommand(INDEX_FIRST_STUDENT, invalidPaidAmount);

        assertCommandFailure(payCommand, model, Messages.MESSAGE_LIMIT);
    }

    @Test
    public void equals() {
        final PayCommand standardCommand = new PayCommand(INDEX_FIRST_STUDENT, 2.0);

        // same values -> returns true
        PayCommand commandWithSameValues = new PayCommand(INDEX_FIRST_STUDENT, 2.0);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new PayCommand(INDEX_SECOND_STUDENT, 2.0)));

        // different hours -> returns false
        assertFalse(standardCommand.equals(new PayCommand(INDEX_FIRST_STUDENT, 3.0)));
    }

    /**
     * Helper method to create a Student with an updated paid amount.
     */
    private Student createEditedStudentWithUpdatedPaidAmount(Student studentToEdit, double amountPaid) {
        double updatedPaidAmount = studentToEdit.getPaidAmount().value + amountPaid;
        return new Student(
                studentToEdit.getName(),
                studentToEdit.getPhone(),
                studentToEdit.getEmail(),
                studentToEdit.getAddress(),
                studentToEdit.getSchedule(),
                studentToEdit.getSubject(),
                studentToEdit.getRate(),
                new PaidAmount(Double.toString(updatedPaidAmount)),
                studentToEdit.getOwedAmount()
        );
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        double hour = 3.5;
        PayCommand payCommand = new PayCommand(index, hour);

        String expected = PayCommand.class.getCanonicalName() + "{index=" + index + ", hour=" + hour + "}";

        assertEquals(expected, payCommand.toString());
    }
}

