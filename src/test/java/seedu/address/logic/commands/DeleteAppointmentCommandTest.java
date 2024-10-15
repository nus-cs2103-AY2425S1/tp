package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

public class DeleteAppointmentCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validNameUnfilteredList_success() {
        Person appointmentToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(appointmentToDelete.getName());

        String expectedMessage = String.format(DeleteAppointmentCommand.MESSAGE_DELETE_APPOINTMENT_SUCCESS,
                Messages.format(appointmentToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteAppointment(appointmentToDelete);

        assertCommandSuccess(deleteAppointmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidNameUnfilteredList_throwsCommandException() {
        Name name = new Name("John");
        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(name);

        assertCommandFailure(deleteAppointmentCommand, model, Messages.MESSAGE_INVALID_NAME_DISPLAYED);
    }

    @Test
    public void execute_validNameFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person appointmentToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(appointmentToDelete.getName());

        String expectedMessage = String.format(DeleteAppointmentCommand.MESSAGE_DELETE_APPOINTMENT_SUCCESS,
                Messages.format(appointmentToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);
        expectedModel.deleteAppointment(appointmentToDelete);
    
        assertCommandSuccess(deleteAppointmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        Person personToDeleteFirst = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person personToDeleteSecond = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        DeleteAppointmentCommand deleteFirstCommand = new DeleteAppointmentCommand(personToDeleteFirst.getName());
        DeleteAppointmentCommand deleteSecondCommand = new DeleteAppointmentCommand(personToDeleteSecond.getName());

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteAppointmentCommand deleteFirstCommandCopy = new DeleteAppointmentCommand(personToDeleteFirst.getName());
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Name name = new Name("John");
        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(name);
        String expected = DeleteAppointmentCommand.class.getCanonicalName() + "{toDeleteAppointment=" + name + "}";
        assertEquals(expected, deleteAppointmentCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
