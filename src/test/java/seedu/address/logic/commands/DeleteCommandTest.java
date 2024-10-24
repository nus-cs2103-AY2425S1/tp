package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ECNAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ECPHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ECRS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.logic.commands.DeleteCommand.DeleteCommandDescriptor;
import static seedu.address.logic.commands.DeleteCommand.createEditedPerson;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.EmergencyContact;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Relationship;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON, new DeleteCommandDescriptor());

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex, new DeleteCommandDescriptor());

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON, new DeleteCommandDescriptor());

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex, new DeleteCommandDescriptor());

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void createEditedPerson_success() {
        Person personToEdit = model.getFilteredPersonList().get(0);
        Set<EmergencyContact> emergencyContactSet = new LinkedHashSet<>(Arrays.asList(new EmergencyContact(
                new Name(VALID_ECNAME_AMY), new Phone(VALID_ECPHONE_AMY), new Relationship(VALID_ECRS_AMY))));
        Person expectedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), emergencyContactSet, personToEdit.getDoctor(), personToEdit.getTags());
        Person actualPerson = createEditedPerson(personToEdit, emergencyContactSet);

        assertEquals(expectedPerson, actualPerson);
    }

    @Test
    public void execute_deleteEmergencyContact_success() {
        ModelManager actualModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        EmergencyContact emergencyContactToDelete = new EmergencyContact(new Name("Birdie Wee"),
                new Phone("94681732"), new Relationship("Sibling"));

        Set<EmergencyContact> updatedEmergencyContacts = new LinkedHashSet<>(personToDelete.getEmergencyContacts());
        updatedEmergencyContacts.add(emergencyContactToDelete);
        Person expectedPerson = new Person(personToDelete.getName(), personToDelete.getPhone(),
                personToDelete.getEmail(), personToDelete.getAddress(), updatedEmergencyContacts,
                personToDelete.getDoctor(), personToDelete.getTags());
        actualModel.setPerson(personToDelete, expectedPerson);

        DeleteCommandDescriptor deleteCommandDescriptor = new DeleteCommandDescriptor();
        deleteCommandDescriptor.setEmergencyContactIndex(Index.fromOneBased(2));
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON, deleteCommandDescriptor);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_EMERGENCY_CONTACT_SUCCESS,
                Messages.formatEmergencyContact(emergencyContactToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(deleteCommand, actualModel, expectedMessage, expectedModel);
    }
    @Test
    public void execute_deleteEmergencyContact_failure() {
        Index outOfBoundIndex = INDEX_SECOND_PERSON;

        assertTrue(outOfBoundIndex.getOneBased() > model.getAddressBook().getPersonList().get(0)
                .getEmergencyContacts().size());

        DeleteCommandDescriptor deleteCommandDescriptor = new DeleteCommandDescriptor();
        deleteCommandDescriptor.setEmergencyContactIndex(outOfBoundIndex);
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON,
                deleteCommandDescriptor);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_LAST_EMERGENCY_CONTACT_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_PERSON, new DeleteCommandDescriptor());
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_PERSON, new DeleteCommandDescriptor());

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_PERSON,
                new DeleteCommandDescriptor());
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
        Index targetIndex = Index.fromOneBased(1);
        DeleteCommand deleteCommand = new DeleteCommand(targetIndex, new DeleteCommandDescriptor());
        String expected = DeleteCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
