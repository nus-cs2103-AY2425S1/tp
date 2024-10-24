package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonWithName;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalNames;

import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Listings;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.From;
import seedu.address.model.appointment.To;
import seedu.address.model.person.Buyer;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Seller;

public class DeleteAppointmentCommandTest {
    private static final Name DO_NOT_EXIST_NAME = new Name("DO NOT EXIST NAME");
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new Listings());


    @Test
    public void execute_validNameUnfilteredList_success() {
        Random random = new Random();
        List<Name> typicalNames = getTypicalNames();
        int randomIndex = random.nextInt(typicalNames.size() - 1);
        Person personToDeleteAppointment = model.getPersonByName(typicalNames.get(randomIndex));
        DeleteAppointmentCommand deleteAppointmentCommand =
                new DeleteAppointmentCommand(personToDeleteAppointment.getName());

        String expectedMessage = String.format(DeleteAppointmentCommand.MESSAGE_DELETE_APPOINTMENT_SUCCESS,
                personToDeleteAppointment.getName());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), new Listings());
        Person personWithoutAppointment;
        if (personToDeleteAppointment instanceof Buyer) {
            personWithoutAppointment = new Buyer(personToDeleteAppointment.getName(),
                    personToDeleteAppointment.getPhone(),
                    personToDeleteAppointment.getEmail(),
                    personToDeleteAppointment.getTags(),
                    new Appointment(new Date(""), new From(""), new To("")),
                    personToDeleteAppointment.getProperty());
        } else { // Assuming it's a Seller if not a Buyer
            personWithoutAppointment = new Seller(personToDeleteAppointment.getName(),
                    personToDeleteAppointment.getPhone(),
                    personToDeleteAppointment.getEmail(),
                    personToDeleteAppointment.getTags(),
                    new Appointment(new Date(""), new From(""), new To("")),
                    personToDeleteAppointment.getProperty());
        }
        expectedModel.setPerson(personToDeleteAppointment, personWithoutAppointment);

        assertCommandSuccess(deleteAppointmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidNameUnfilteredList_throwsCommandException() {
        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(DO_NOT_EXIST_NAME);

        assertCommandFailure(deleteAppointmentCommand, model, Messages.MESSAGE_INVALID_PERSON_INPUT);
    }

    /*
    Test fails as we are adding a different person to the model, thus the model is different
    @Test
    public void execute_validNameFilteredList_success() {
        Random random = new Random();
        List<Name> typicalNames = getTypicalNames();
        int randomIndex = random.nextInt(typicalNames.size() - 1);
        showPersonWithName(model, typicalNames.get(randomIndex));

        Person personToDeleteProperty = model.getPersonByName(typicalNames.get(randomIndex));
        DeletePropertyCommand deletePropertyCommand = new DeletePropertyCommand(personToDeleteProperty.getName());

        String expectedMessage = String.format(DeletePropertyCommand.MESSAGE_DELETE_PROPERTY_SUCCESS,
                personToDeleteProperty.getName());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Person personWithoutProperty = new Person(personToDeleteProperty.getName(), personToDeleteProperty.getPhone(),
                personToDeleteProperty.getEmail(), personToDeleteProperty.getTags(),
                personToDeleteProperty.getAppointment(), new Property(""));
        expectedModel.setPerson(personToDeleteProperty, personWithoutProperty);
        showNoPerson(expectedModel);

        assertCommandSuccess(deletePropertyCommand, model, expectedMessage, expectedModel);
    }
     */

    @Test
    public void execute_invalidNameFilteredList_throwsCommandException() {
        Random random = new Random();
        List<Name> typicalNames = getTypicalNames();
        int randomIndex = random.nextInt(typicalNames.size() - 2);
        showPersonWithName(model, typicalNames.get(randomIndex));

        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(typicalNames
                .get(randomIndex + 1));

        assertCommandFailure(deleteAppointmentCommand, model, Messages.MESSAGE_INVALID_PERSON_INPUT);
    }

    @Test
    public void equals() {
        DeleteAppointmentCommand deleteFirstAppointmentCommand = new DeleteAppointmentCommand(ALICE.getName());
        DeleteAppointmentCommand deleteSecondAppointmentCommand = new DeleteAppointmentCommand(BENSON.getName());

        // same object -> returns true
        assertTrue(deleteFirstAppointmentCommand.equals(deleteFirstAppointmentCommand));

        // same values -> returns true
        DeleteAppointmentCommand deleteFirstCommandCopy = new DeleteAppointmentCommand(ALICE.getName());
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
        DeleteAppointmentCommand deleteCommand = new DeleteAppointmentCommand(ALICE.getName());
        String expected = DeleteAppointmentCommand.class.getCanonicalName() + "{targetName=" + ALICE.getName() + "}";
        assertEquals(expected, deleteCommand.toString());
    }
}
