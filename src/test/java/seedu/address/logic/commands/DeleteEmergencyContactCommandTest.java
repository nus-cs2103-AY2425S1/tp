package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.model.person.EmergencyContact.NO_NAME;
import static seedu.address.model.person.EmergencyContact.NO_NUMBER;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
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
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class DeleteEmergencyContactCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_deleteEmergencyContactUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withEmergencyContact(NO_NAME, NO_NUMBER).build();
        EmergencyContact firstPersonEmergencyContact = firstPerson.getEmergencyContact();
        DeleteEmergencyContactCommand deleteEmergencyContactCommand =
                new DeleteEmergencyContactCommand(INDEX_FIRST_PERSON);
        String expectedMessage = String.format(DeleteEmergencyContactCommand.MESSAGE_DELETE_EMERGENCY_CONTACT_SUCCESS,
                firstPersonEmergencyContact.getName(), firstPersonEmergencyContact.getNumber(), editedPerson.getName());
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);
        assertCommandSuccess(deleteEmergencyContactCommand, model, expectedMessage, expectedModel);
    }
    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withEmergencyContact(NO_NAME, NO_NUMBER).build();
        EmergencyContact firstPersonEmergencyContact = firstPerson.getEmergencyContact();
        DeleteEmergencyContactCommand deleteEmergencyContactCommand =
                new DeleteEmergencyContactCommand(INDEX_FIRST_PERSON);
        String expectedMessage = String.format(DeleteEmergencyContactCommand.MESSAGE_DELETE_EMERGENCY_CONTACT_SUCCESS,
                firstPersonEmergencyContact.getName(), firstPersonEmergencyContact.getNumber(), editedPerson.getName());
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);
        assertCommandSuccess(deleteEmergencyContactCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noSavedEmergencyContactErrorMessage_success() {
        Person thirdPerson = model.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(thirdPerson).build();
        DeleteEmergencyContactCommand deleteEmergencyContactCommand =
                new DeleteEmergencyContactCommand(INDEX_THIRD_PERSON);
        String expectedMessage = String.format(DeleteEmergencyContactCommand.MESSAGE_NO_EMERGENCY_CONTACT,
                editedPerson.getName());
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        assertCommandSuccess(deleteEmergencyContactCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteEmergencyContactCommand deleteEmergencyContactCommand =
                new DeleteEmergencyContactCommand(outOfBoundIndex);
        assertCommandFailure(deleteEmergencyContactCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());
        DeleteEmergencyContactCommand deleteEmergencyContactCommand =
                new DeleteEmergencyContactCommand(outOfBoundIndex);
        assertCommandFailure(deleteEmergencyContactCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final DeleteEmergencyContactCommand standardCommand = new DeleteEmergencyContactCommand(INDEX_FIRST_PERSON);
        // same values -> returns true
        DeleteEmergencyContactCommand commandWithSameValues = new DeleteEmergencyContactCommand(INDEX_FIRST_PERSON);
        assertTrue(standardCommand.equals(commandWithSameValues));
        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));
        // null -> returns false
        assertFalse(standardCommand.equals(null));
        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
        // different index -> returns false
        assertFalse(standardCommand.equals(new DeleteEmergencyContactCommand(INDEX_SECOND_PERSON)));
    }
}
