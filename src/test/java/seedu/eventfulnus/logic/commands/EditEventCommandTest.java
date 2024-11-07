package seedu.eventfulnus.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eventfulnus.logic.commands.CommandTestUtil.DESC_HANDBALL_W;
import static seedu.eventfulnus.logic.commands.CommandTestUtil.DESC_LEAGUE;
import static seedu.eventfulnus.logic.commands.CommandTestUtil.VALID_EVENT_SPORT_HANDBALL_W;
import static seedu.eventfulnus.logic.commands.CommandTestUtil.VALID_EVENT_SPORT_LEAGUE;
import static seedu.eventfulnus.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.eventfulnus.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.eventfulnus.logic.commands.CommandTestUtil.showEventAtIndex;
import static seedu.eventfulnus.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.eventfulnus.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.eventfulnus.testutil.TypicalIndexes.INDEX_SECOND_EVENT;

import org.junit.jupiter.api.Test;

import seedu.eventfulnus.commons.core.index.Index;
import seedu.eventfulnus.logic.Messages;
import seedu.eventfulnus.logic.commands.EditEventCommand.EditEventDescriptor;
import seedu.eventfulnus.model.AddressBook;
import seedu.eventfulnus.model.Model;
import seedu.eventfulnus.model.ModelManager;
import seedu.eventfulnus.model.UserPrefs;
import seedu.eventfulnus.model.event.Event;
import seedu.eventfulnus.testutil.EditEventDescriptorBuilder;
import seedu.eventfulnus.testutil.EventBuilder;

/**
 * Contains integration tests (interaction with the {@link Model})
 * and unit tests for {@link EditEventCommand}.
 */
public class EditEventCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Event editedEvent = new EventBuilder().build();
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder(editedEvent).build();
        EditEventCommand editEventCommand = new EditEventCommand(INDEX_FIRST_EVENT, descriptor);

        String expectedMessage = String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS,
                Messages.formatEvent(editedEvent));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setEvent(model.getFilteredEventList().get(0), editedEvent);

        assertCommandSuccess(editEventCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastEvent = Index.fromOneBased(model.getFilteredEventList().size());
        Event lastEvent = model.getFilteredEventList().get(indexLastEvent.getZeroBased());

        EventBuilder eventInList = new EventBuilder(lastEvent);
        Event editedEvent = eventInList
                .withSport(VALID_EVENT_SPORT_LEAGUE)
                .withTeams("COM", "FASS")
                .withParticipants("Alice")
                .build();

        EditEventDescriptor descriptor = new EditEventDescriptorBuilder()
                .withSport(VALID_EVENT_SPORT_LEAGUE)
                .withTeams("COM", "FASS")
                .withParticipants("Alice")
                .build();
        EditEventCommand editEventCommand = new EditEventCommand(indexLastEvent, descriptor);

        String expectedMessage = String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS,
                Messages.formatEvent(editedEvent));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setEvent(lastEvent, editedEvent);

        assertCommandSuccess(editEventCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditEventCommand editEventCommand = new EditEventCommand(INDEX_FIRST_EVENT, new EditEventDescriptor());

        Event editedEvent = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());

        String expectedMessage = String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS,
                Messages.formatEvent(editedEvent));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editEventCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showEventAtIndex(model, INDEX_FIRST_EVENT);

        Event eventInFilteredList = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        Event editedEvent = new EventBuilder(eventInFilteredList).withSport(VALID_EVENT_SPORT_HANDBALL_W).build();
        EditEventCommand editEventCommand = new EditEventCommand(INDEX_FIRST_EVENT,
                new EditEventDescriptorBuilder().withSport(VALID_EVENT_SPORT_HANDBALL_W).build());

        String expectedMessage = String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS,
                Messages.formatEvent(editedEvent));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setEvent(model.getFilteredEventList().get(0), editedEvent);

        assertCommandSuccess(editEventCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateEventUnfilteredList_failure() {
        Event firstEvent = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder(firstEvent).build();
        EditEventCommand editEventCommand = new EditEventCommand(INDEX_SECOND_EVENT, descriptor);

        assertCommandFailure(editEventCommand, model, EditEventCommand.MESSAGE_DUPLICATE_EVENT);
    }

    @Test
    public void execute_duplicateEventFilteredList_failure() {
        showEventAtIndex(model, INDEX_FIRST_EVENT);

        // edit event in filtered list into a duplicate in address book
        Event eventInList = model.getAddressBook().getEventList().get(INDEX_SECOND_EVENT.getZeroBased());
        EditEventCommand editEventCommand = new EditEventCommand(INDEX_FIRST_EVENT,
                new EditEventDescriptorBuilder(eventInList).build());

        assertCommandFailure(editEventCommand, model, EditEventCommand.MESSAGE_DUPLICATE_EVENT);
    }

    @Test
    public void execute_invalidEventIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder()
                .withSport(VALID_EVENT_SPORT_HANDBALL_W).build();
        EditEventCommand editEventCommand = new EditEventCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editEventCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidEventIndexFilteredList_failure() {
        showEventAtIndex(model, INDEX_FIRST_EVENT);
        Index outOfBoundIndex = INDEX_SECOND_EVENT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getEventList().size());

        EditEventCommand editEventCommand = new EditEventCommand(outOfBoundIndex,
                new EditEventDescriptorBuilder().withSport(VALID_EVENT_SPORT_HANDBALL_W).build());

        assertCommandFailure(editEventCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditEventCommand standardCommand = new EditEventCommand(INDEX_FIRST_EVENT, DESC_LEAGUE);

        // same values -> returns true
        EditEventDescriptor copyDescriptor = new EditEventDescriptor(DESC_LEAGUE);
        EditEventCommand commandWithSameValues = new EditEventCommand(INDEX_FIRST_EVENT, copyDescriptor);
        assertEquals(standardCommand, commandWithSameValues);

        // same object -> returns true
        assertEquals(standardCommand, standardCommand);

        // null -> returns false
        assertNotEquals(null, standardCommand);

        // different types -> returns false
        assertNotEquals(standardCommand, new ClearCommand());

        // different index -> returns false
        assertNotEquals(standardCommand, new EditEventCommand(INDEX_SECOND_EVENT, DESC_LEAGUE));

        // different descriptor -> returns false
        assertNotEquals(standardCommand, new EditEventCommand(INDEX_FIRST_EVENT, DESC_HANDBALL_W));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditEventDescriptor descriptor = new EditEventDescriptor();
        EditEventCommand editEventCommand = new EditEventCommand(index, descriptor);
        String expectedString = EditEventCommand.class.getCanonicalName() + "{index=" + index + ", editEventDescriptor="
                + descriptor + "}";
        assertEquals(expectedString, editEventCommand.toString());
    }
}
