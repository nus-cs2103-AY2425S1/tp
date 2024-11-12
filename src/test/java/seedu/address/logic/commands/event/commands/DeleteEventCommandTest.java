package seedu.address.logic.commands.event.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEvents.getTypicalEventManager;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventManager;

public class DeleteEventCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventManager(), new UserPrefs());

    @Test
    public void execute_validIndex_success() {
        Event eventToDelete = model.getEventManager().getEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(INDEX_FIRST_EVENT);

        String expectedMessage = String.format(DeleteEventCommand.MESSAGE_SUCCESS,
                eventToDelete.getName());

        EventManager expectedEventManager = getTypicalEventManager();
        expectedEventManager.removeEvent(eventToDelete);
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), expectedEventManager, new UserPrefs());

        assertCommandSuccess(deleteEventCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nullEventManager_throwsNullPointerException() {
        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(INDEX_FIRST_EVENT);
        assertThrows(NullPointerException.class, () -> deleteEventCommand.execute(model, null));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(Index.fromOneBased(4));
        assertCommandFailure(deleteEventCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void testToString() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(targetIndex);
        String expected = DeleteEventCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteEventCommand.toString());
    }

    @Test
    public void equals() {
        DeleteEventCommand deleteFirstCommand = new DeleteEventCommand(INDEX_FIRST_PERSON);
        DeleteEventCommand deleteSecondCommand = new DeleteEventCommand(Index.fromOneBased(2));

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteEventCommand deleteFirstCommandCopy = new DeleteEventCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }
}
