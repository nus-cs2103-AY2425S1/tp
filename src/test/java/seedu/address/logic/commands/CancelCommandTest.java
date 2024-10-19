package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showEventAtIndex;
import static seedu.address.testutil.TypicalEvents.getTypicalEventBook;
import static seedu.address.testutil.TypicalIndexes.*;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code CancelCommand}.
 */
public class CancelCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Event eventToDelete = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        CancelCommand cancelCommand = new CancelCommand(INDEX_FIRST_EVENT);

        String expectedMessage = String.format(CancelCommand.MESSAGE_CANCEL_EVENT_SUCCESS,
                Messages.formatEvent(eventToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getEventBook(), new UserPrefs());
        expectedModel.deleteEvent(eventToDelete);

        assertCommandSuccess(cancelCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        CancelCommand cancelCommand = new CancelCommand(outOfBoundIndex);

        assertCommandFailure(cancelCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showEventAtIndex(model, INDEX_FIRST_PERSON);

        Event eventToDelete = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        CancelCommand cancelCommand = new CancelCommand(INDEX_FIRST_EVENT);

        String expectedMessage = String.format(CancelCommand.MESSAGE_CANCEL_EVENT_SUCCESS,
                Messages.formatEvent(eventToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getEventBook(), new UserPrefs());
        expectedModel.deleteEvent(eventToDelete);
        showNoEvent(expectedModel);

        assertCommandSuccess(cancelCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showEventAtIndex(model, INDEX_FIRST_EVENT);

        Index outOfBoundIndex = INDEX_SECOND_EVENT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getEventBook().getEventList().size());

        CancelCommand cancelCommand = new CancelCommand(outOfBoundIndex);

        assertCommandFailure(cancelCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        CancelCommand cancelFirstCommand = new CancelCommand(INDEX_FIRST_EVENT);
        CancelCommand cancelSecondCommand = new CancelCommand(INDEX_SECOND_EVENT);

        // same object -> returns true
        assertTrue(cancelFirstCommand.equals(cancelFirstCommand));

        // same values -> returns true
        CancelCommand cancelFirstCommandCopy = new CancelCommand(INDEX_FIRST_PERSON);
        assertTrue(cancelFirstCommand.equals(cancelFirstCommandCopy));

        // different types -> returns false
        assertFalse(cancelFirstCommand.equals(1));

        // null -> returns false
        assertFalse(cancelFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(cancelFirstCommand.equals(cancelSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        CancelCommand cancelCommand = new CancelCommand(targetIndex);
        String expected = CancelCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, cancelCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoEvent(Model model) {
        model.updateFilteredEventList(p -> false);

        assertTrue(model.getFilteredEventList().isEmpty());
    }
}
