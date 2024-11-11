package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getPinnedAddressBook;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

public class UnpinCommandTest {

    private Model model = new ModelManager(getPinnedAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        model.sortByPin();
        model.sortByName();
        Person personToUnpin = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        UnpinCommand unpinCommand = new UnpinCommand(List.of(INDEX_FIRST_PERSON));

        String expectedMessage = String.format(UnpinCommand.MESSAGE_UNPIN_PERSON_SUCCESS,
                Messages.format(personToUnpin));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.unpinPerson(personToUnpin);
        expectedModel.sortByPin();
        assertCommandSuccess(unpinCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UnpinCommand unpinCommand = new UnpinCommand(List.of(outOfBoundIndex));

        assertCommandFailure(unpinCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        UnpinCommand unpinCommand = new UnpinCommand(List.of(outOfBoundIndex));

        assertCommandFailure(unpinCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        UnpinCommand unpinFirstCommand = new UnpinCommand(List.of(INDEX_FIRST_PERSON));
        UnpinCommand unpinSecondCommand = new UnpinCommand(List.of(INDEX_SECOND_PERSON));

        // same object -> returns true
        assertTrue(unpinFirstCommand.equals(unpinFirstCommand));

        // same values -> returns true
        UnpinCommand unpinFirstCommandCopy = new UnpinCommand(List.of(INDEX_FIRST_PERSON));
        assertTrue(unpinFirstCommand.equals(unpinFirstCommandCopy));

        // different types -> returns false
        assertFalse(unpinFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unpinFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(unpinFirstCommand.equals(unpinSecondCommand));
    }
}
