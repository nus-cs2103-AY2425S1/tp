package seedu.eventfulnus.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eventfulnus.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.eventfulnus.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.eventfulnus.logic.commands.CommandTestUtil.showEventAtIndex;
import static seedu.eventfulnus.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.eventfulnus.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.eventfulnus.testutil.TypicalIndexes.INDEX_SECOND_EVENT;

import org.junit.jupiter.api.Test;

import seedu.eventfulnus.commons.core.index.Index;
import seedu.eventfulnus.logic.Messages;
import seedu.eventfulnus.model.Model;
import seedu.eventfulnus.model.ModelManager;
import seedu.eventfulnus.model.UserPrefs;
import seedu.eventfulnus.model.event.Event;


/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteEventCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Event eventToDelete = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(INDEX_FIRST_EVENT);

        String expectedMessage = String.format(DeleteEventCommand.MESSAGE_SUCCESS,
                Messages.formatEvent(eventToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteEvent(eventToDelete);

        assertCommandSuccess(deleteEventCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(outOfBoundIndex);

        assertCommandFailure(deleteEventCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showEventAtIndex(model, INDEX_FIRST_EVENT);

        Event eventToDelete = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(INDEX_FIRST_EVENT);

        String expectedMessage = String.format(DeleteEventCommand.MESSAGE_SUCCESS,
                Messages.formatEvent(eventToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteEvent(eventToDelete);

        assertCommandSuccess(deleteEventCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showEventAtIndex(model, INDEX_FIRST_EVENT);

        Index outOfBoundIndex = INDEX_SECOND_EVENT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getEventList().size());

        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(outOfBoundIndex);

        assertCommandFailure(deleteEventCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteEventCommand deleteFirstEventCommand = new DeleteEventCommand(INDEX_FIRST_EVENT);
        DeleteEventCommand deleteSecondEventCommand = new DeleteEventCommand(INDEX_SECOND_EVENT);

        // same object -> returns true
        assertTrue(deleteFirstEventCommand.equals(deleteFirstEventCommand));

        // same values -> returns true
        DeleteEventCommand deleteFirstEventCommandCopy = new DeleteEventCommand(INDEX_FIRST_EVENT);
        assertTrue(deleteFirstEventCommand.equals(deleteFirstEventCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstEventCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstEventCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstEventCommand.equals(deleteSecondEventCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(targetIndex);
        String expected = DeleteEventCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteEventCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no events.
     */
    private void showNoEvent(Model model) {
        model.updateFilteredEventList(p -> false);
        assertTrue(model.getFilteredEventList().isEmpty());
    }
}
