package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        //tests deleting person with valid index in an unfiltered list
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        //tests deleting person with invalid index in an unfiltered list
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        //tests deleting person with valid index in filtered list
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        //tests deleting person with invalid index in filtered list
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        //tests equals method of the deletecommand class.
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_PERSON);
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
        DeleteCommand deleteCommand = new DeleteCommand(targetIndex);
        String expected = DeleteCommand.class.getCanonicalName() + "{targetIndex=Optional[" + targetIndex + "]}";
        assertEquals(expected, deleteCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }

    // Test deleting by valid name.

    @Test
    public void execute_validDeleteByName_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased());
        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        String name = "Carl Kurz";
        DeleteCommand deleteCommand = new DeleteCommand(new Name(name));
        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validDeleteByNamePhone_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());

        // Use the same formatting as DeleteCommand
        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        String name = "Benson Meier";
        String phone = "98765432";
        DeleteCommand deleteCommand = new DeleteCommand(new Name(name), new Phone(phone));

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_validDeleteByNameEmail_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased());

        // Use the same formatting as DeleteCommand
        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        String name = "Carl Kurz";
        String email = "heinz@example.com";
        DeleteCommand deleteCommand = new DeleteCommand(new Name(name), new Email(email));

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteByNameNotFound_throwsCommandException() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Name invalidName = new Name("Invalid Person");
        DeleteCommand deleteCommand = new DeleteCommand(invalidName);
        String expectedMessage = "No matching contacts found.";
        assertCommandFailure(deleteCommand, model, expectedMessage);
    }

    @Test
    public void execute_deleteByNameWithDuplicates_throwsCommandException() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person duplicatePerson = new PersonBuilder().withName("Carl Kurz").withPhone("11111111")
                .withEmail("testduplicate@gmail.com").withJobCode("123ABC").build();
        model.addPerson(duplicatePerson);
        DeleteCommand deleteCommand = new DeleteCommand(new Name("Carl Kurz"));
        String expectedMessage = "Multiple contacts with the same full name found. Please specify more using "
                + "this format:\n" + "delete" + " n/NAME e/EMAIL OR "
                + "delete" + " n/NAME p/PHONE";
        assertCommandFailure(deleteCommand, model, expectedMessage);

    }

    @Test
    public void execute_deleteByNameEmailNotFound_throwsCommandException() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person duplicatePerson = new PersonBuilder().withName("Carl Kurz").withPhone("11111111")
                .withEmail("testduplicate@gmail.com").withJobCode("123ABC").build();
        model.addPerson(duplicatePerson);
        Name invalidName = new Name("Invalid Person");
        Email invalidEmail = new Email("invalidEmail@gmail.com");
        DeleteCommand deleteCommand = new DeleteCommand(invalidName, invalidEmail);
        String expectedMessage = "No matching contacts found.";
        assertCommandFailure(deleteCommand, model, expectedMessage);
    }

    @Test
    public void execute_deleteByNamePhoneNotFound_throwsCommandException() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person duplicatePerson = new PersonBuilder().withName("Carl Kurz").withPhone("11111111")
                .withEmail("testduplicate@gmail.com").withJobCode("123ABC").build();
        model.addPerson(duplicatePerson);
        Name invalidName = new Name("Invalid Person");
        Phone invalidPhone = new Phone("99999999");
        DeleteCommand deleteCommand = new DeleteCommand(invalidName, invalidPhone);
        String expectedMessage = "No matching contacts found.";
        assertCommandFailure(deleteCommand, model, expectedMessage);
    }
}
