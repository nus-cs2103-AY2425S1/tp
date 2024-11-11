package seedu.eventtory.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eventtory.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.eventtory.logic.Messages;
import seedu.eventtory.logic.commands.exceptions.CommandException;
import seedu.eventtory.model.EventTory;
import seedu.eventtory.model.ReadOnlyEventTory;
import seedu.eventtory.model.event.Event;
import seedu.eventtory.testutil.EventBuilder;
import seedu.eventtory.testutil.ModelStub;
import seedu.eventtory.testutil.TypicalEvents;

public class CreateEventCommandTest {
    @Test
    public void constructor_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CreateEventCommand(null));
    }

    @Test
    public void execute_eventAcceptedByModel_addSuccessful() throws Exception {
        CreateEventCommandTest.ModelStubAcceptingEventAdded modelStub = new
            CreateEventCommandTest.ModelStubAcceptingEventAdded();
        Event validEvent = new EventBuilder().build();

        CommandResult commandResult = new CreateEventCommand(validEvent).execute(modelStub);

        assertEquals(String.format(CreateEventCommand.MESSAGE_SUCCESS, Messages.format(validEvent)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validEvent), modelStub.eventsAdded);
    }

    @Test
    public void execute_duplicateVendor_throwsCommandException() {
        Event validEvent = new EventBuilder().build();
        CreateEventCommand createEventCommand = new CreateEventCommand(validEvent);
        ModelStub modelStub = new CreateEventCommandTest.ModelStubWithEvent(validEvent);

        assertThrows(CommandException.class, CreateEventCommand.MESSAGE_DUPLICATE_EVENT, ()
            -> createEventCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        CreateEventCommand aliceWeddingCommand = new CreateEventCommand(TypicalEvents.ALICE);
        CreateEventCommand bensonBirthdayCommand = new CreateEventCommand(TypicalEvents.BENSON);

        // same object -> returns true
        assertTrue(aliceWeddingCommand.equals(aliceWeddingCommand));

        // same values -> returns true
        CreateEventCommand besonBirthdayCommandCopy = new CreateEventCommand(TypicalEvents.BENSON);
        assertTrue(bensonBirthdayCommand.equals(besonBirthdayCommandCopy));

        // different types -> returns false
        assertFalse(aliceWeddingCommand.equals(1));

        // null -> returns false
        assertFalse(aliceWeddingCommand.equals(null));

        // different vendor -> returns false
        assertFalse(aliceWeddingCommand.equals(bensonBirthdayCommand));
    }

    @Test
    public void toStringMethod() {
        CreateEventCommand createEventCommand = new CreateEventCommand(TypicalEvents.HOON);
        String expected = CreateEventCommand.class.getCanonicalName() + "{toAdd=" + TypicalEvents.HOON + "}";
        assertEquals(expected, createEventCommand.toString());
    }

    /**
     * A Model stub that contains a single event.
     */
    private class ModelStubWithEvent extends ModelStub {
        private final Event event;

        ModelStubWithEvent(Event event) {
            requireNonNull(event);
            this.event = event;
        }

        @Override
        public boolean hasEvent(Event event) {
            requireNonNull(event);
            return this.event.isSameEvent(event);
        }
    }

    /**
     * A Model stub that always accepts the event being added.
     */
    private class ModelStubAcceptingEventAdded extends ModelStub {
        final ArrayList<Event> eventsAdded = new ArrayList<>();

        @Override
        public boolean hasEvent(Event event) {
            requireNonNull(event);
            return eventsAdded.stream().anyMatch(event::isSameEvent);
        }

        @Override
        public void addEvent(Event event) {
            requireNonNull(event);
            eventsAdded.add(event);
        }

        @Override
        public ReadOnlyEventTory getEventTory() {
            return new EventTory();
        }
    }
}
