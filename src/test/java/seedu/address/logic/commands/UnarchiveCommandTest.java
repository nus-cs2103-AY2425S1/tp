package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

public class UnarchiveCommandTest {
    private Model model = new ModelManager(new AddressBook(), new UserPrefs(), getTypicalAddressBook());

    @Test
    public void execute_validIndexUnarchive_success() {
        // Set in archived list UI
        model.setArchivedListMode(true);
        Person personToUnarchive = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(UnarchiveCommand.MESSAGE_UNARCHIVE_PERSON_SUCCESS,
                Messages.format(personToUnarchive));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(),
                getTypicalAddressBook());
        expectedModel.addPerson(personToUnarchive);
        expectedModel.deleteArchivedPerson(personToUnarchive);

        assertCommandSuccess(unarchiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexMainList_throwsCommandException() {
        model.setArchivedListMode(false);
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(INDEX_FIRST_PERSON);

        assertCommandFailure(unarchiveCommand, model, UnarchiveCommand.MESSAGE_NOT_IN_ARCHIVED_LIST);
    }

    @Test
    public void execute_invalidIndexArchivedList_throwsCommandException() {
        model.setArchivedListMode(true);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(outOfBoundIndex);

        assertCommandFailure(unarchiveCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        UnarchiveCommand unarchiveFirstCommand = new UnarchiveCommand(INDEX_FIRST_PERSON);
        UnarchiveCommand unarchiveSecondCommand = new UnarchiveCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(unarchiveFirstCommand.equals(unarchiveFirstCommand));

        // same values -> returns true
        UnarchiveCommand unarchiveFirstCommandCopy = new UnarchiveCommand(INDEX_FIRST_PERSON);
        assertTrue(unarchiveFirstCommand.equals(unarchiveFirstCommandCopy));

        // different types -> returns false
        assertFalse(unarchiveFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unarchiveFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(unarchiveFirstCommand.equals(unarchiveSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(targetIndex);
        String expected = UnarchiveCommand.class.getCanonicalName() + "{index=" + targetIndex + "}";
        assertEquals(expected, unarchiveCommand.toString());
    }
}
