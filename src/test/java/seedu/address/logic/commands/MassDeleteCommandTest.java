package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
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
        Person firstPersonToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person secondPersonToDelete = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        List<Index> indicesToDelete = Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);
        MassDeleteCommand massDeleteCommand = new MassDeleteCommand(indicesToDelete);

        String expectedMessage = String.format(MassDeleteCommand.MESSAGE_DELETE_PERSONS_SUCCESS,
                Arrays.asList(INDEX_FIRST_PERSON.getOneBased(), INDEX_SECOND_PERSON.getOneBased()));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(firstPersonToDelete);
        expectedModel.deletePerson(secondPersonToDelete);

        assertCommandSuccess(massDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndicesFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        List<Index> indicesToDelete = Arrays.asList(INDEX_FIRST_PERSON);
        MassDeleteCommand massDeleteCommand = new MassDeleteCommand(indicesToDelete);

        String expectedMessage = String.format(MassDeleteCommand.MESSAGE_DELETE_PERSONS_SUCCESS,
                Arrays.asList(INDEX_FIRST_PERSON.getOneBased()));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(massDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        MassDeleteCommand massDeleteFirstCommand = new MassDeleteCommand(Arrays.asList(INDEX_FIRST_PERSON));
        MassDeleteCommand massDeleteSecondCommand = new MassDeleteCommand(Arrays.asList(INDEX_SECOND_PERSON));

        // same object -> returns true
        assertTrue(massDeleteFirstCommand.equals(massDeleteFirstCommand));

        // same values -> returns true
        MassDeleteCommand massDeleteFirstCommandCopy = new MassDeleteCommand(Arrays.asList(INDEX_FIRST_PERSON));
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
        List<Index> targetIndices = Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);
        MassDeleteCommand massDeleteCommand = new MassDeleteCommand(targetIndices);
        String expected = MassDeleteCommand.class.getCanonicalName() + "{targetIndices=" + targetIndices + "}";
        assertEquals(expected, massDeleteCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
