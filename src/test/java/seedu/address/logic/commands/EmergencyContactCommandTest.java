package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMERGENCY_CONTACT_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMERGENCY_CONTACT_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMERGENCY_CONTACT_NUMBER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMERGENCY_CONTACT_NUMBER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.model.person.EmergencyContact.NO_NAME;
import static seedu.address.model.person.EmergencyContact.NO_NUMBER;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIFTH_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.EmergencyContact;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.testutil.PersonBuilder;

public class EmergencyContactCommandTest {

    private static final String EMERGENCY_CONTACT_NAME_STUB = "Some name";
    private static final String EMERGENCY_CONTACT_NUMBER_STUB = "123";
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addEmergencyContactUnfilteredList_success() {
        Person thirdPerson = model.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(thirdPerson).withEmergencyContact(EMERGENCY_CONTACT_NAME_STUB,
                EMERGENCY_CONTACT_NUMBER_STUB).build();
        EmergencyContactCommand emergencyContactCommand = new EmergencyContactCommand(INDEX_THIRD_PERSON,
                new EmergencyContact(editedPerson.getEmergencyContact().contactName,
                        editedPerson.getEmergencyContact().contactNumber));
        String expectedMessage = String.format(EmergencyContactCommand.MESSAGE_ADD_EMERGENCY_CONTACT_SUCCESS,
                editedPerson.getName(), EMERGENCY_CONTACT_NAME_STUB, EMERGENCY_CONTACT_NUMBER_STUB);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(thirdPerson, editedPerson);
        assertCommandSuccess(emergencyContactCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_THIRD_PERSON);
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withEmergencyContact(EMERGENCY_CONTACT_NAME_STUB,
                EMERGENCY_CONTACT_NUMBER_STUB).build();
        EmergencyContactCommand emergencyContactCommand = new EmergencyContactCommand(INDEX_FIRST_PERSON,
                new EmergencyContact(editedPerson.getEmergencyContact().contactName,
                        editedPerson.getEmergencyContact().contactNumber));
        String expectedMessage = String.format(EmergencyContactCommand.MESSAGE_ADD_EMERGENCY_CONTACT_SUCCESS,
                editedPerson.getName(), EMERGENCY_CONTACT_NAME_STUB, EMERGENCY_CONTACT_NUMBER_STUB);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);
        assertCommandSuccess(emergencyContactCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_overrideEmergencyContactWithOnlyNameUnfilteredList_success() {
        Person fourthPerson = model.getFilteredPersonList().get(INDEX_FOURTH_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(fourthPerson).withEmergencyContact(EMERGENCY_CONTACT_NAME_STUB,
                EMERGENCY_CONTACT_NUMBER_STUB).build();
        EmergencyContactCommand emergencyContactCommand = new EmergencyContactCommand(INDEX_FOURTH_PERSON,
                new EmergencyContact(editedPerson.getEmergencyContact().contactName,
                        editedPerson.getEmergencyContact().contactNumber));
        String expectedMessage = String.format(EmergencyContactCommand.MESSAGE_ADD_EMERGENCY_CONTACT_SUCCESS,
                editedPerson.getName(), EMERGENCY_CONTACT_NAME_STUB, EMERGENCY_CONTACT_NUMBER_STUB);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(fourthPerson, editedPerson);
        assertCommandSuccess(emergencyContactCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_overrideEmergencyContactWithOnlyNumberUnfilteredList_success() {
        Person fifthPerson = model.getFilteredPersonList().get(INDEX_FIFTH_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(fifthPerson).withEmergencyContact(EMERGENCY_CONTACT_NAME_STUB,
                EMERGENCY_CONTACT_NUMBER_STUB).build();
        EmergencyContactCommand emergencyContactCommand = new EmergencyContactCommand(INDEX_FIFTH_PERSON,
                new EmergencyContact(editedPerson.getEmergencyContact().contactName,
                        editedPerson.getEmergencyContact().contactNumber));
        String expectedMessage = String.format(EmergencyContactCommand.MESSAGE_ADD_EMERGENCY_CONTACT_SUCCESS,
                editedPerson.getName(), EMERGENCY_CONTACT_NAME_STUB, EMERGENCY_CONTACT_NUMBER_STUB);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(fifthPerson, editedPerson);
        assertCommandSuccess(emergencyContactCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addEmergencyContactWithOnlyNameUnfilteredList_failure() {
        Person thirdPerson = model.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(thirdPerson).withEmergencyContact(EMERGENCY_CONTACT_NAME_STUB,
                NO_NUMBER).build();
        EmergencyContactCommand emergencyContactCommand = new EmergencyContactCommand(INDEX_THIRD_PERSON,
                new EmergencyContact(editedPerson.getEmergencyContact().contactName,
                        editedPerson.getEmergencyContact().contactNumber));
        String expectedMessage = EmergencyContactCommand.MESSAGE_INVALID_EMERGENCY_CONTACT_PARAMETERS;
        assertCommandFailure(emergencyContactCommand, model, expectedMessage);
    }

    @Test
    public void execute_addEmergencyContactWithOnlyNumberUnfilteredList_failure() {
        Person thirdPerson = model.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(thirdPerson).withEmergencyContact(NO_NAME,
                EMERGENCY_CONTACT_NUMBER_STUB).build();
        EmergencyContactCommand emergencyContactCommand = new EmergencyContactCommand(INDEX_THIRD_PERSON,
                new EmergencyContact(editedPerson.getEmergencyContact().contactName,
                        editedPerson.getEmergencyContact().contactNumber));
        String expectedMessage = EmergencyContactCommand.MESSAGE_INVALID_EMERGENCY_CONTACT_PARAMETERS;
        assertCommandFailure(emergencyContactCommand, model, expectedMessage);
    }

    @Test
    public void execute_addEmergencyContactWithNoNameAndNumberUnfilteredList_failure() {
        Person thirdPerson = model.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(thirdPerson).withEmergencyContact(NO_NAME,
                NO_NUMBER).build();
        EmergencyContactCommand emergencyContactCommand = new EmergencyContactCommand(INDEX_THIRD_PERSON,
                new EmergencyContact(editedPerson.getEmergencyContact().contactName,
                        editedPerson.getEmergencyContact().contactNumber));
        String expectedMessage = EmergencyContactCommand.MESSAGE_INVALID_EMERGENCY_CONTACT_PARAMETERS;
        assertCommandFailure(emergencyContactCommand, model, expectedMessage);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EmergencyContactCommand emergencyContactCommand = new EmergencyContactCommand(outOfBoundIndex,
                new EmergencyContact(new Name(VALID_EMERGENCY_CONTACT_NAME_BOB),
                        new Phone(VALID_EMERGENCY_CONTACT_NUMBER_BOB)));
        assertCommandFailure(emergencyContactCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_overrideEmergencyContactUnfilteredListErrorMessage_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).build();
        EmergencyContactCommand emergencyContactCommand = new EmergencyContactCommand(INDEX_FIRST_PERSON,
                new EmergencyContact(editedPerson.getEmergencyContact().contactName,
                        editedPerson.getEmergencyContact().contactNumber));
        String expectedMessage = String.format(EmergencyContactCommand.MESSAGE_EMERGENCY_CONTACT_EXISTS,
                editedPerson.getName());
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        assertCommandSuccess(emergencyContactCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());
        EmergencyContactCommand emergencyContactCommand = new EmergencyContactCommand(outOfBoundIndex,
                new EmergencyContact(new Name(VALID_EMERGENCY_CONTACT_NAME_BOB),
                        new Phone(VALID_EMERGENCY_CONTACT_NUMBER_BOB)));
        assertCommandFailure(emergencyContactCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EmergencyContactCommand standardCommand = new EmergencyContactCommand(INDEX_FIRST_PERSON,
                new EmergencyContact(new Name(VALID_EMERGENCY_CONTACT_NAME_AMY),
                        new Phone(VALID_EMERGENCY_CONTACT_NUMBER_AMY)));
        // same values -> returns true
        EmergencyContactCommand commandWithSameValues = new EmergencyContactCommand(INDEX_FIRST_PERSON,
                new EmergencyContact(new Name(VALID_EMERGENCY_CONTACT_NAME_AMY),
                        new Phone(VALID_EMERGENCY_CONTACT_NUMBER_AMY)));
        assertTrue(standardCommand.equals(commandWithSameValues));
        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));
        // null -> returns false
        assertFalse(standardCommand.equals(null));
        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
        // different index -> returns false
        assertFalse(standardCommand.equals(new EmergencyContactCommand(INDEX_SECOND_PERSON,
                new EmergencyContact(new Name(VALID_EMERGENCY_CONTACT_NAME_AMY),
                        new Phone(VALID_EMERGENCY_CONTACT_NUMBER_AMY)))));
        // different remark -> returns false
        assertFalse(standardCommand.equals(new EmergencyContactCommand(INDEX_FIRST_PERSON,
                new EmergencyContact(new Name(VALID_EMERGENCY_CONTACT_NAME_BOB),
                        new Phone(VALID_EMERGENCY_CONTACT_NUMBER_BOB)))));
    }
}
