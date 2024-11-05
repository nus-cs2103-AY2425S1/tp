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

import javax.swing.text.View;


public class ViewEventCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventManager(), new UserPrefs());

    @Test
    public void execute_validIndex_success() {
        Event eventToView = model.getEventManager().getEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        ViewEventCommand viewEventCommand = new ViewEventCommand(INDEX_FIRST_EVENT);

        String expectedMessage = String.format(ViewEventCommand.MESSAGE_SUCCESS,
                eventToView.getName());

        EventManager expectedEventManager = getTypicalEventManager();

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), expectedEventManager, new UserPrefs());
        
        assertCommandSuccess(viewEventCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nullEventManager_throwsNullPointerException() {
        ViewEventCommand viewEventCommand = new ViewEventCommand(INDEX_FIRST_EVENT);
        assertThrows(NullPointerException.class, () -> viewEventCommand.execute(model, null));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        ViewEventCommand viewEventCommand = new ViewEventCommand(Index.fromOneBased(4));
        assertCommandFailure(viewEventCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ViewEventCommand viewFirstCommand = new ViewEventCommand(INDEX_FIRST_PERSON);
        ViewEventCommand viewSecondCommand = new ViewEventCommand(Index.fromOneBased(2));

        // same object -> returns true
        assertTrue(viewFirstCommand.equals(viewFirstCommand));

        // same values -> returns true
        ViewEventCommand viewFirstCommandCopy = new ViewEventCommand(INDEX_FIRST_PERSON);
        assertTrue(viewFirstCommand.equals(viewFirstCommandCopy));

        // different types -> returns false
        assertFalse(viewFirstCommand.equals(1));

        // null -> returns false
        assertFalse(viewFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(viewFirstCommand.equals(viewSecondCommand));
    }

    @Test
    public void toString_returnsCorrectString() {
        Event eventToView = model.getEventManager().getEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        ViewEventCommand viewEventCommand = new ViewEventCommand(INDEX_FIRST_EVENT);

        String expected = ViewEventCommand.class.getCanonicalName() + "{targetIndex=" + INDEX_FIRST_EVENT + "}";
        assertEquals(expected, viewEventCommand.toString());
        System.out.println(eventToView.toString());
    }
}
