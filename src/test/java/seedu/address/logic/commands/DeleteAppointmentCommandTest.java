package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAppointments.getTypicalAppointmentBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_APPOINTMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_APPOINTMENT;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.Appointment;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteAppointmentCommand}.
 */
public class DeleteAppointmentCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalAppointmentBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Appointment appointmentToDelete = model.getFilteredAppointmentList()
                .get(INDEX_FIRST_APPOINTMENT.getZeroBased());
        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(INDEX_FIRST_APPOINTMENT);

        String expectedMessage = String.format(DeleteAppointmentCommand.MESSAGE_DELETE_APPOINTMENT_SUCCESS,
                Messages.formatAppointment(appointmentToDelete));

        ModelManager expectedModel = new ModelManager(
                model.getAddressBook(), getTypicalAppointmentBook(), new UserPrefs());
        expectedModel.deleteAppointment(appointmentToDelete);

        assertCommandSuccess(deleteAppointmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredAppointmentList().size() + 1);
        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(outOfBoundIndex);

        assertCommandFailure(deleteAppointmentCommand, model, Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showAppointmentAtIndex(model, INDEX_FIRST_APPOINTMENT);

        Appointment appointmentToDelete = model.getFilteredAppointmentList()
                .get(INDEX_FIRST_APPOINTMENT.getZeroBased());
        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(INDEX_FIRST_APPOINTMENT);

        String expectedMessage = String.format(DeleteAppointmentCommand.MESSAGE_DELETE_APPOINTMENT_SUCCESS,
                Messages.formatAppointment(appointmentToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), getTypicalAppointmentBook(), new UserPrefs());
        expectedModel.deleteAppointment(appointmentToDelete);
        showNoAppointment(expectedModel);

        assertCommandSuccess(deleteAppointmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showAppointmentAtIndex(model, INDEX_FIRST_APPOINTMENT);

        Index outOfBoundIndex = INDEX_SECOND_APPOINTMENT;
        // ensures that outOfBoundIndex is still in bounds of appointment book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAppointmentBook().getAppointmentList().size());

        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(outOfBoundIndex);

        assertCommandFailure(deleteAppointmentCommand, model, Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteAppointmentCommand deleteFirstCommand = new DeleteAppointmentCommand(INDEX_FIRST_APPOINTMENT);
        DeleteAppointmentCommand deleteSecondCommand = new DeleteAppointmentCommand(INDEX_SECOND_APPOINTMENT);

        // same object -> returns true
        assertEquals(deleteFirstCommand, deleteFirstCommand);

        // same values -> returns true
        DeleteAppointmentCommand deleteFirstCommandCopy = new DeleteAppointmentCommand(INDEX_FIRST_APPOINTMENT);
        assertEquals(deleteFirstCommand, deleteFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, deleteFirstCommand);

        // null -> returns false
        assertNotEquals(null, deleteFirstCommand);

        // different appointment -> returns false
        assertNotEquals(deleteFirstCommand, deleteSecondCommand);
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(targetIndex);
        String expected = DeleteAppointmentCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteAppointmentCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no appointments.
     */
    private void showNoAppointment(Model model) {
        model.updateFilteredAppointmentList(a -> false);

        assertTrue(model.getFilteredAppointmentList().isEmpty());
    }

    /**
     * Updates {@code model}'s filtered list to show only the appointment at the given {@code index}.
     */
    private void showAppointmentAtIndex(Model model, Index index) {
        model.updateFilteredAppointmentList(a -> a.equals(model.getAppointmentBook().getAppointmentList()
                .get(index.getZeroBased())));
    }
}
