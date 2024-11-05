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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.ArchivedAddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new ArchivedAddressBook());

    @BeforeEach
    public void setUp() {
        model.setArchivedListMode(false);
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(),
                new ArchivedAddressBook());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), new ArchivedAddressBook());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteByNameSingleMatch_success() {
        Name targetName = new Name("George Best");
        DeleteCommand deleteCommand = new DeleteCommand(targetName);

        Person personToDelete = model.getFilteredPersonList().stream()
                .filter(person -> person.getName().equals(targetName))
                .findFirst()
                .orElseThrow(); // fails if person with that name not found

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), new ArchivedAddressBook());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteByNameMultipleMatches_displaysMultipleMatchesMessage() {
        Name duplicateName = new Name("Fiona Kunz");
        DeleteCommand deleteCommand = new DeleteCommand(duplicateName);

        String expectedMessage = DeleteCommand.MESSAGE_MULTIPLE_MATCHES + " '" + duplicateName + "'. "
                + DeleteCommand.MESSAGE_DELETE_BY_ID + '\n';

        assertCommandSuccess(deleteCommand, model, expectedMessage, model);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_deleteByNameNameNotFound_throwsCommandException() {
        Name nonExistentName = new Name("Non Existent Name");
        DeleteCommand deleteCommand = new DeleteCommand(nonExistentName);

        assertCommandFailure(deleteCommand, model, DeleteCommand.MESSAGE_NAME_NOT_FOUND);
    }

    @Test
    public void execute_deleteByIndexArchivedList_throwsCommandException() {
        model.setArchivedListMode(true);
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_NOT_IN_MAIN_LIST);
    }

    @Test
    public void equals() {
        DeleteCommand deleteByIndexCommand1 = new DeleteCommand(INDEX_FIRST_PERSON);
        DeleteCommand deleteByIndexCommand2 = new DeleteCommand(INDEX_SECOND_PERSON);
        DeleteCommand deleteByNameCommand1 = new DeleteCommand(new Name("Alice"));
        DeleteCommand deleteByNameCommand2 = new DeleteCommand(new Name("Bob"));

        // same object -> returns true
        assertTrue(deleteByIndexCommand1.equals(deleteByIndexCommand1));
        assertTrue(deleteByNameCommand1.equals(deleteByNameCommand1));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteByIndexCommand1.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteByIndexCommand1.equals(1));
        assertFalse(deleteByNameCommand1.equals("string"));
        assertFalse(deleteByIndexCommand1.equals(deleteByNameCommand1));

        // null -> returns false
        assertFalse(deleteByIndexCommand1.equals(null));
        assertFalse(deleteByNameCommand1.equals(null));

        // different person -> returns false
        assertFalse(deleteByIndexCommand1.equals(deleteByIndexCommand2));
        assertFalse(deleteByNameCommand1.equals(deleteByNameCommand2));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteCommand deleteCommand = new DeleteCommand(targetIndex);
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
