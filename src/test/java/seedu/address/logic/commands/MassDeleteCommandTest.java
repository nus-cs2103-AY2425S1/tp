package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code MassDeleteCommand}.
 */
public class MassDeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndicesUnfilteredList_success() {
        Person firstPersonToDelete = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        Person secondPersonToDelete = model.getFilteredPersonList().get(INDEX_SECOND.getZeroBased());
        List<Index> indicesToDelete = Arrays.asList(INDEX_FIRST, INDEX_SECOND);
        MassDeleteCommand massDeleteCommand = new MassDeleteCommand(indicesToDelete, Collections.emptyList());

        String expectedMessage = String.format(MassDeleteCommand.MESSAGE_DELETE_PERSONS_SUCCESS,
                Arrays.asList(INDEX_FIRST.getOneBased(), INDEX_SECOND.getOneBased()));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(firstPersonToDelete);
        expectedModel.deletePerson(secondPersonToDelete);

        assertCommandSuccess(massDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndicesFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST);

        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        List<Index> indicesToDelete = Arrays.asList(INDEX_FIRST);
        MassDeleteCommand massDeleteCommand = new MassDeleteCommand(indicesToDelete, Collections.emptyList());

        String expectedMessage = String.format(MassDeleteCommand.MESSAGE_DELETE_PERSONS_SUCCESS,
                Arrays.asList(INDEX_FIRST.getOneBased()));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(massDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noValidIndices_throwsCommandException() {
        MassDeleteCommand massDeleteCommand = new MassDeleteCommand(Collections.emptyList(), Collections.emptyList());

        assertCommandFailure(massDeleteCommand, model, MassDeleteCommand.MESSAGE_NO_VALID_IDS);
    }

    @Test
    public void execute_mixedValidAndInvalidInputs_successWithInvalidInputsReported() {
        Person firstPersonToDelete = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        List<Index> indicesToDelete = Arrays.asList(INDEX_FIRST);
        List<String> invalidInputs = Arrays.asList("a", "b");
        MassDeleteCommand massDeleteCommand = new MassDeleteCommand(indicesToDelete, invalidInputs);

        String expectedMessage = String.format(MassDeleteCommand.MESSAGE_DELETE_PERSONS_SUCCESS,
                Arrays.asList(INDEX_FIRST.getOneBased())) + "\nInvalid inputs: [a, b]";

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(firstPersonToDelete);

        assertCommandSuccess(massDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        MassDeleteCommand massDeleteFirstCommand = new
                MassDeleteCommand(Arrays.asList(INDEX_FIRST), Collections.emptyList());
        MassDeleteCommand massDeleteSecondCommand = new
                MassDeleteCommand(Arrays.asList(INDEX_SECOND), Collections.emptyList());

        // same object -> returns true
        assertTrue(massDeleteFirstCommand.equals(massDeleteFirstCommand));

        // same values -> returns true
        MassDeleteCommand massDeleteFirstCommandCopy = new
                MassDeleteCommand(Arrays.asList(INDEX_FIRST), Collections.emptyList());
        assertTrue(massDeleteFirstCommand.equals(massDeleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(massDeleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(massDeleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(massDeleteFirstCommand.equals(massDeleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        List<Index> targetIndices = Arrays.asList(INDEX_FIRST, INDEX_SECOND);
        MassDeleteCommand massDeleteCommand = new MassDeleteCommand(targetIndices, Collections.emptyList());
        String expected = MassDeleteCommand.class.getCanonicalName()
                + "{targetIndices=" + targetIndices + ", invalidInputs=[]}";
        assertEquals(expected, massDeleteCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);
    }
}
