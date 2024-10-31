package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteEventByNameCommand}.
 */
public class DeleteEventByNameCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validName_success() {
        Event eventToDelete = model.getFilteredEventList().get(INDEX_FIRST.getZeroBased());
        EventName nameToDelete = eventToDelete.getName();
        DeleteEventCommand deleteEventCommand = new DeleteEventByNameCommand(nameToDelete);

        String expectedMessage = String.format(DeleteEventCommand.MESSAGE_DELETE_EVENT_SUCCESS,
                Messages.format(eventToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteEvent(eventToDelete);

        assertCommandSuccess(deleteEventCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validLowerCasedName_success() {
        Event eventToDelete = model.getFilteredEventList().get(INDEX_FIRST.getZeroBased());
        EventName nameToDelete = eventToDelete.getName();
        String lowerCasedName = nameToDelete.toString().toLowerCase();
        EventName lowerCasedNameToDelete = new EventName(lowerCasedName);
        DeleteEventCommand deleteEventCommand = new DeleteEventByNameCommand(lowerCasedNameToDelete);

        String expectedMessage = String.format(DeleteEventCommand.MESSAGE_DELETE_EVENT_SUCCESS,
                Messages.format(eventToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteEvent(eventToDelete);

        assertCommandSuccess(deleteEventCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validUpperCasedName_success() {
        Event eventToDelete = model.getFilteredEventList().get(INDEX_FIRST.getZeroBased());
        EventName nameToDelete = eventToDelete.getName();
        String upperCasedName = nameToDelete.toString().toUpperCase();
        EventName upperCasedNameToDelete = new EventName(upperCasedName);
        DeleteEventCommand deleteEventCommand = new DeleteEventByNameCommand(upperCasedNameToDelete);

        String expectedMessage = String.format(DeleteEventCommand.MESSAGE_DELETE_EVENT_SUCCESS,
                Messages.format(eventToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteEvent(eventToDelete);

        assertCommandSuccess(deleteEventCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_partOfName_throwsCommandException() {
        Event eventToDelete = model.getFilteredEventList().get(INDEX_FIRST.getZeroBased());
        EventName nameToDelete = eventToDelete.getName();
        String nameStringToDelete = nameToDelete.toString();
        String partOfNameString = nameStringToDelete.substring(0, nameStringToDelete.length() - 1);
        EventName partOfNameToDelete = new EventName(partOfNameString);

        DeleteEventCommand deleteEventCommand = new DeleteEventByNameCommand(partOfNameToDelete);

        assertCommandFailure(deleteEventCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_NAME);
    }

    @Test
    public void execute_noSpacingName_throwsCommandException() {
        Event eventToDelete = model.getFilteredEventList().get(INDEX_FIRST.getZeroBased());
        EventName nameToDelete = eventToDelete.getName();
        String nameStringToDelete = nameToDelete.toString();
        String noSpacingNameString = nameStringToDelete.replaceAll(" ", "");
        EventName noSpacingNameToDelete = new EventName(noSpacingNameString);

        DeleteEventCommand deleteEventCommand = new DeleteEventByNameCommand(noSpacingNameToDelete);

        assertCommandFailure(deleteEventCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_NAME);
    }

    @Test
    public void execute_duplicateNamesButDifferentCasing_throwsCommandException() {
        Event eventToDelete = model.getFilteredEventList().get(INDEX_FIRST.getZeroBased());
        EventName nameToDelete = eventToDelete.getName();
        String nameStringToDelete = nameToDelete.toString();
        String allLowerCasedNameString = nameStringToDelete.toLowerCase();
        EventName allLowerCasedNameToDelete = new EventName(allLowerCasedNameString);
        Event duplicateEventToDelete = new Event(
                allLowerCasedNameToDelete, eventToDelete.getEventDescription(), eventToDelete.getEventDuration());
        int newEventId = model.generateNewEventId();
        Event duplicateEventToDeleteWithId = duplicateEventToDelete.changeId(newEventId);
        model.addEvent(duplicateEventToDeleteWithId);

        DeleteEventCommand deleteEventCommand = new DeleteEventByNameCommand(nameToDelete);

        assertCommandFailure(deleteEventCommand, model, Messages.MESSAGE_MORE_THAN_ONE_EVENT_DISPLAYED_NAME);
    }

    @Test
    public void equals() {
        EventName firstName = new EventName("Meeting");
        EventName secondName = new EventName("Contest");
        DeleteEventCommand deleteEventFirstCommand = new DeleteEventByNameCommand(firstName);
        DeleteEventCommand deleteEventSecondCommand = new DeleteEventByNameCommand(secondName);

        // same object -> returns true
        assertTrue(deleteEventFirstCommand.equals(deleteEventFirstCommand));

        // same values -> returns true
        DeleteEventCommand deleteEventFirstCommandCopy = new DeleteEventByNameCommand(firstName);
        assertTrue(deleteEventFirstCommand.equals(deleteEventFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteEventFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteEventFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteEventFirstCommand.equals(deleteEventSecondCommand));
    }

    @Test
    public void toStringMethod() {
        EventName targetName = new EventName("Meeting");
        DeleteEventByNameCommand deleteEventCommand = new DeleteEventByNameCommand(targetName);
        String expected = DeleteEventByNameCommand.class.getCanonicalName() + "{targetName=" + targetName + "}";
        assertEquals(expected, deleteEventCommand.toString());
    }
}
