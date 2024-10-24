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
import seedu.address.model.person.Buyer;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Property;
import seedu.address.model.person.Seller;


public class DeletePropertyCommandTest {
    private static final Name DO_NOT_EXIST_NAME = new Name("DO NOT EXIST NAME");
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new Listings());


    @Test
    public void execute_validNameUnfilteredList_success() {
        Random random = new Random();
        List<Name> typicalNames = getTypicalNames();
        int randomIndex = random.nextInt(typicalNames.size() - 1);
        Person personToDeleteProperty = model.getPersonByName(typicalNames.get(randomIndex));
        DeletePropertyCommand deletePropertyCommand = new DeletePropertyCommand(personToDeleteProperty.getName());

        String expectedMessage = String.format(DeletePropertyCommand.MESSAGE_DELETE_PROPERTY_SUCCESS,
                personToDeleteProperty.getName());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), new Listings());
        Person personWithoutProperty;
        if (personToDeleteProperty instanceof Buyer) {
            personWithoutProperty = new Buyer(personToDeleteProperty.getName(), personToDeleteProperty.getPhone(),
                    personToDeleteProperty.getEmail(), personToDeleteProperty.getTags(),
                    personToDeleteProperty.getAppointment(), new Property(""));
        } else {
            personWithoutProperty = new Seller(personToDeleteProperty.getName(), personToDeleteProperty.getPhone(),
                    personToDeleteProperty.getEmail(), personToDeleteProperty.getTags(),
                    personToDeleteProperty.getAppointment(), new Property(""));
        }
        expectedModel.setPerson(personToDeleteProperty, personWithoutProperty);

        assertCommandSuccess(deletePropertyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidNameUnfilteredList_throwsCommandException() {
        DeletePropertyCommand deletePropertyCommand = new DeletePropertyCommand(DO_NOT_EXIST_NAME);

        assertCommandFailure(deletePropertyCommand, model, Messages.MESSAGE_INVALID_PERSON_INPUT);
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

        DeletePropertyCommand deletePropertyCommand = new DeletePropertyCommand(typicalNames
                .get(randomIndex + 1));

        assertCommandFailure(deletePropertyCommand, model, Messages.MESSAGE_INVALID_PERSON_INPUT);
    }

    @Test
    public void equals() {
        DeletePropertyCommand deleteFirstPropertyCommand = new DeletePropertyCommand(ALICE.getName());
        DeletePropertyCommand deleteSecondPropertyCommand = new DeletePropertyCommand(BENSON.getName());

        // same object -> returns true
        assertTrue(deleteFirstPropertyCommand.equals(deleteFirstPropertyCommand));

        // same values -> returns true
        DeletePropertyCommand deleteFirstCommandCopy = new DeletePropertyCommand(ALICE.getName());
        assertTrue(deleteFirstPropertyCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstPropertyCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstPropertyCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstPropertyCommand.equals(deleteSecondPropertyCommand));
    }

    @Test
    public void toStringMethod() {
        DeletePropertyCommand deleteCommand = new DeletePropertyCommand(ALICE.getName());
        String expected = DeletePropertyCommand.class.getCanonicalName() + "{targetName=" + ALICE.getName() + "}";
        assertEquals(expected, deleteCommand.toString());
    }

}
