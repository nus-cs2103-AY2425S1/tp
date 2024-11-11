package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showAppointmentAtIndex;
import static seedu.address.testutil.TypicalAppointments.getTypicalAppointments;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.Appointment;

public class DeleteAppointmentCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalAppointments(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Appointment appointmentToDelete = model.getFilteredAppointmentList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteAppointmentCommand command = new DeleteAppointmentCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteAppointmentCommand.MESSAGE_DELETE_APPOINTMENT_SUCCESS,
                appointmentToDelete.name(), Messages.format(appointmentToDelete));
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, true, false, false);

        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalAppointments(), new UserPrefs());
        expectedModel.deleteAppointment(appointmentToDelete);

        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredAppointmentList().size() + 1);
        DeleteAppointmentCommand command = new DeleteAppointmentCommand(outOfBoundIndex);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showAppointmentAtIndex(model, INDEX_FIRST_PERSON);

        Appointment appointmentToDelete = model.getFilteredAppointmentList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteAppointmentCommand command = new DeleteAppointmentCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteAppointmentCommand.MESSAGE_DELETE_APPOINTMENT_SUCCESS,
                appointmentToDelete.name(), Messages.format(appointmentToDelete));
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, true, false, false);

        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalAppointments(), new UserPrefs());
        expectedModel.deleteAppointment(appointmentToDelete);
        showNoAppointments(expectedModel);

        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showAppointmentAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of appointment list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAppointmentList().size());

        DeleteAppointmentCommand command = new DeleteAppointmentCommand(outOfBoundIndex);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteAppointmentCommand deleteFirstCommand = new DeleteAppointmentCommand(INDEX_FIRST_PERSON);
        DeleteAppointmentCommand deleteSecondCommand = new DeleteAppointmentCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteAppointmentCommand deleteFirstCommandCopy = new DeleteAppointmentCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different command -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteAppointmentCommand command = new DeleteAppointmentCommand(targetIndex);
        String expected = DeleteAppointmentCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, command.toString());
    }

    /**
     * Updates {@code model}'s filtered appointment list to show no appointments.
     */
    private void showNoAppointments(Model model) {
        model.updateFilteredAppointmentList(a -> false);
        assertTrue(model.getFilteredAppointmentList().isEmpty());
    }
}
