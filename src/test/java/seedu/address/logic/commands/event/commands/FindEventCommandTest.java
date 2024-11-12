package seedu.address.logic.commands.event.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.event.commands.FindEventCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEvents.TECH_CONFERENCE;
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
import seedu.address.model.person.PersonInEventPredicate;


public class FindEventCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventManager(), new UserPrefs());

    @Test
    public void execute_nullEventManager_throwsNullPointerException() {
        FindEventCommand findEventCommand = new FindEventCommand(INDEX_FIRST_EVENT);
        assertThrows(NullPointerException.class, () -> findEventCommand.execute(model, null));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        FindEventCommand findEventCommand = new FindEventCommand(Index.fromOneBased(4));
        assertCommandFailure(findEventCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        FindEventCommand viewFirstCommand = new FindEventCommand(INDEX_FIRST_PERSON);
        FindEventCommand viewSecondCommand = new FindEventCommand(Index.fromOneBased(2));

        // same object -> returns true
        assertTrue(viewFirstCommand.equals(viewFirstCommand));

        // same values -> returns true
        FindEventCommand viewFirstCommandCopy = new FindEventCommand(INDEX_FIRST_PERSON);
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
        FindEventCommand findEventCommand = new FindEventCommand(INDEX_FIRST_EVENT);

        String expected = FindEventCommand.class.getCanonicalName() + "{targetIndex=" + INDEX_FIRST_EVENT + "}";
        assertEquals(expected, findEventCommand.toString());
        System.out.println(eventToView.toString());
    }

    @Test
    public void execute_validIndex_success() {
        FindEventCommand findEventCommand = new FindEventCommand(Index.fromOneBased(1));
        String expectedMsg = String.format(MESSAGE_SUCCESS, TECH_CONFERENCE.getName());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalEventManager(), new UserPrefs());
        expectedModel.updateFilteredPersonList(new PersonInEventPredicate(new Event(TECH_CONFERENCE)));
        assertCommandSuccess(findEventCommand, model, expectedMsg, expectedModel);
    }
}
