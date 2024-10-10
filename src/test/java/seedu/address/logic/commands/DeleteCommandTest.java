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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Index> firstIndexSet = new HashSet<>();
        firstIndexSet.add(INDEX_FIRST_PERSON);
        DeleteCommand deleteCommand = new DeleteCommand(firstIndexSet);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_multipleValidIndexUnfilteredList_success() {
        ArrayList<Person> personToDelete = new ArrayList<>();
        personToDelete.add(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        personToDelete.add(model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased()));
        Set<Index> indexSet = new HashSet<>();
        indexSet.add(INDEX_FIRST_PERSON);
        indexSet.add(INDEX_SECOND_PERSON);
        DeleteCommand deleteCommand = new DeleteCommand(indexSet);

        String formattedDeletedPeople = personToDelete.stream()
                .map(Messages::format)
                .collect(Collectors.joining("\n"));
        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                formattedDeletedPeople);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        personToDelete.forEach(expectedModel::deletePerson);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Set<Index> outOfBoundIndexSet = new HashSet<>();
        outOfBoundIndexSet.add(outOfBoundIndex);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndexSet);

        assertCommandFailure(deleteCommand, model, String.format(Messages.MESSAGE_INVALID_INDEX_SHOWN,
                String.valueOf(outOfBoundIndex.getOneBased())));
    }

    @Test
    public void execute_multipleInvalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Index outOfBoundIndex2 = Index.fromOneBased(model.getFilteredPersonList().size() + 2);
        Set<Index> outOfBoundIndexSet = new HashSet<>();
        outOfBoundIndexSet.add(outOfBoundIndex);
        outOfBoundIndexSet.add(outOfBoundIndex2);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndexSet);

        String formattedOutOfBoundIndices = outOfBoundIndex.getOneBased() + ", " + outOfBoundIndex2.getOneBased();


        assertCommandFailure(deleteCommand, model, String.format(Messages.MESSAGE_INVALID_INDEX_SHOWN,
                formattedOutOfBoundIndices));
    }

    @Test
    public void execute_oneInvalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Set<Index> outOfBoundIndexSet = new HashSet<>();
        outOfBoundIndexSet.add(outOfBoundIndex);
        outOfBoundIndexSet.add(INDEX_FIRST_PERSON);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndexSet);



        assertCommandFailure(deleteCommand, model, String.format(Messages.MESSAGE_INVALID_INDEX_SHOWN,
                String.valueOf(outOfBoundIndex.getOneBased())));
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Index> firstIndexSet = new HashSet<>();
        firstIndexSet.add(INDEX_FIRST_PERSON);
        DeleteCommand deleteCommand = new DeleteCommand(firstIndexSet);

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
        Set<Index> outOfBoundIndexSet = new HashSet<>();
        outOfBoundIndexSet.add(INDEX_SECOND_PERSON);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndexSet);

        assertCommandFailure(deleteCommand, model, String.format(Messages.MESSAGE_INVALID_INDEX_SHOWN,
                String.valueOf(outOfBoundIndex.getOneBased())));
    }

    @Test
    public void equals() {
        Set<Index> firstIndexSet = new HashSet<>();
        firstIndexSet.add(INDEX_FIRST_PERSON);
        Set<Index> secondIndexSet = new HashSet<>();
        secondIndexSet.add(INDEX_SECOND_PERSON);
        DeleteCommand deleteFirstCommand = new DeleteCommand(firstIndexSet);
        DeleteCommand deleteSecondCommand = new DeleteCommand(secondIndexSet);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        Set<Index> firstIndexSetCopy = new HashSet<>();
        firstIndexSetCopy.add(INDEX_FIRST_PERSON);
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(firstIndexSetCopy);
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
        Set<Index> targetIndexSet = new HashSet<>();
        targetIndexSet.add(INDEX_FIRST_PERSON);
        DeleteCommand deleteCommand = new DeleteCommand(targetIndexSet);
        String expected = DeleteCommand.class.getCanonicalName() + "{targetIndices=" + targetIndexSet + "}";
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
