package seedu.address.logic.commands.clientcommands.appointmentcommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Listings;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Buyer;
import seedu.address.model.person.Person;
import seedu.address.model.person.Seller;

public class DeleteAppointmentCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new Listings());

    @Test
    public void execute_validBuyerIndex_success() {
        Person personToDeleteAppointment = DANIEL;
        DeleteAppointmentCommand deleteAppointmentCommand =
                new DeleteAppointmentCommand(INDEX_FOURTH_PERSON);

        String expectedMessage = String.format(DeleteAppointmentCommand.MESSAGE_DELETE_APPOINTMENT_SUCCESS,
                personToDeleteAppointment.getName());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), new Listings());

        Buyer personWithoutAppointment = new Buyer(personToDeleteAppointment.getName(),
                personToDeleteAppointment.getPhone(),
                personToDeleteAppointment.getEmail(),
                personToDeleteAppointment.getTags(),
                Appointment.EMPTY_APPOINTMENT);
        expectedModel.setPerson(personToDeleteAppointment, personWithoutAppointment);

        assertCommandSuccess(deleteAppointmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validSellerIndex_success() {
        Person personToDeleteAppointment = ALICE;
        DeleteAppointmentCommand deleteAppointmentCommand =
                new DeleteAppointmentCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteAppointmentCommand.MESSAGE_DELETE_APPOINTMENT_SUCCESS,
                personToDeleteAppointment.getName());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), new Listings());
        Person personWithoutAppointment = new Seller(personToDeleteAppointment.getName(),
                personToDeleteAppointment.getPhone(),
                personToDeleteAppointment.getEmail(),
                personToDeleteAppointment.getTags(),
                Appointment.EMPTY_APPOINTMENT);

        expectedModel.setPerson(personToDeleteAppointment, personWithoutAppointment);

        assertCommandSuccess(deleteAppointmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        DeleteAppointmentCommand deleteAppointmentCommand =
                new DeleteAppointmentCommand(Index.fromZeroBased(model.getFilteredPersonList().size()));

        assertCommandFailure(deleteAppointmentCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index invalidIndex = INDEX_SECOND_PERSON;

        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(invalidIndex);

        assertCommandFailure(deleteAppointmentCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteAppointmentCommand deleteFirstAppointmentCommand = new DeleteAppointmentCommand(INDEX_FIRST_PERSON);
        DeleteAppointmentCommand deleteSecondAppointmentCommand = new DeleteAppointmentCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstAppointmentCommand.equals(deleteFirstAppointmentCommand));

        // same values -> returns true
        DeleteAppointmentCommand deleteFirstCommandCopy = new DeleteAppointmentCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstAppointmentCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstAppointmentCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstAppointmentCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstAppointmentCommand.equals(deleteSecondAppointmentCommand));
    }

    @Test
    public void toStringMethod() {
        DeleteAppointmentCommand deleteCommand = new DeleteAppointmentCommand(INDEX_FIRST_PERSON);
        String expected = DeleteAppointmentCommand.class.getCanonicalName() + "{targetIndex=" + "1" + "}";
        assertEquals(expected, deleteCommand.toString());
    }
}
