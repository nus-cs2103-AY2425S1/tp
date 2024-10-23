package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
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
import seedu.address.model.person.Schedule;


public class DeleteAppointmentCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_invalidNameUnfilteredList_throwsCommandException() {
        Name name = new Name("John");
        Schedule schedule = new Schedule("2024-10-04 1000", "");
        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(name, schedule);

        assertCommandFailure(deleteAppointmentCommand, model, Messages.MESSAGE_INVALID_NAME_DISPLAYED);
    }

    @Test
    public void execute_validNameFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personWithAppointmentToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(
                personWithAppointmentToDelete.getName(),
                personWithAppointmentToDelete.getSchedules().iterator().next());

        String expectedMessage = String.format(DeleteAppointmentCommand.MESSAGE_DELETE_APPOINTMENT_SUCCESS,
                Messages.formatSchedule(personWithAppointmentToDelete,
                        personWithAppointmentToDelete.getSchedules().iterator().next()));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(deleteAppointmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        Person personToDeleteFirst = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person personToDeleteSecond = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        DeleteAppointmentCommand deleteFirstCommand = new DeleteAppointmentCommand(personToDeleteFirst.getName(),
                personToDeleteFirst.getSchedules().iterator().next());
        DeleteAppointmentCommand deleteSecondCommand = new DeleteAppointmentCommand(personToDeleteSecond.getName(),
                personToDeleteSecond.getSchedules().iterator().next());

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteAppointmentCommand deleteFirstCommandCopy = new DeleteAppointmentCommand(personToDeleteFirst.getName(),
                personToDeleteFirst.getSchedules().iterator().next());
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
        Schedule schedule = new Schedule("2024-10-04 1000", "");
        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(name, schedule);
        String expected = DeleteAppointmentCommand.class.getCanonicalName() + "{toDeleteAppointment=" + name + "}";
        assertEquals(expected, deleteAppointmentCommand.toString());
    }
}
