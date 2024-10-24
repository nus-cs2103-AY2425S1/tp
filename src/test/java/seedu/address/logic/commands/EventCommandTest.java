package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.EventBuilder.DEFAULT_DATE;
import static seedu.address.testutil.EventBuilder.DEFAULT_INDEXES;
import static seedu.address.testutil.EventBuilder.DEFAULT_LOCATION;
import static seedu.address.testutil.EventBuilder.DEFAULT_NAME;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.EventBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyEventBook;
import seedu.address.model.event.Event;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.testutil.EventBuilder;

public class EventCommandTest {

    @Test
    public void constructor_nullParameters_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EventCommand(null, null, null, null));

        // test if any field is null
        assertThrows(NullPointerException.class, () -> new EventCommand(null, DEFAULT_DATE, DEFAULT_LOCATION,
                DEFAULT_INDEXES));
        assertThrows(NullPointerException.class, () -> new EventCommand(DEFAULT_NAME, null, DEFAULT_LOCATION,
                DEFAULT_INDEXES));
        assertThrows(NullPointerException.class, () -> new EventCommand(DEFAULT_NAME, DEFAULT_DATE, null,
                DEFAULT_INDEXES));
        assertThrows(NullPointerException.class, () -> new EventCommand(DEFAULT_NAME, DEFAULT_DATE,
                DEFAULT_LOCATION, null));

    }

    @Test
    public void execute_eventAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingEventAdded modelStub = new ModelStubAcceptingEventAdded();
        Event validEvent = new EventBuilder().build();
        CommandResult commandResult =
                new EventCommand(DEFAULT_NAME, DEFAULT_DATE, DEFAULT_LOCATION, DEFAULT_INDEXES).execute(modelStub);

        assertEquals(String.format(EventCommand.MESSAGE_SUCCESS, Messages.formatEvent(validEvent)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validEvent), modelStub.eventsAdded);
    }

    @Test
    public void execute_eventNoAttendeesAcceptedByModel_addSuccessful() throws Exception {
        // tests when the user does not enter -a prefix
        Set<Index> emptyIndexes = new HashSet<>();
        ModelStubAcceptingEventAdded modelStub = new ModelStubAcceptingEventAdded();
        Event validEvent = new EventBuilder().buildWithNoAttendees();
        CommandResult commandResult =
                new EventCommand(DEFAULT_NAME, DEFAULT_DATE, DEFAULT_LOCATION, emptyIndexes).execute(modelStub);

        assertEquals(String.format(EventCommand.MESSAGE_SUCCESS, Messages.formatEvent(validEvent)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validEvent), modelStub.eventsAdded);
    }

    @Test
    public void execute_duplicateEvent_throwsCommandException() {
        Event validEvent = new EventBuilder().build();
        EventCommand eventCommand = new EventCommand(DEFAULT_NAME, DEFAULT_DATE, DEFAULT_LOCATION, DEFAULT_INDEXES);
        ModelStubWithEvent modelStub = new ModelStubWithEvent(validEvent);

        assertThrows(CommandException.class,
                EventCommand.MESSAGE_DUPLICATE_EVENT, () -> eventCommand.execute(modelStub));
    }

    @Test
    public void execute_outOfBoundsIndex_throwsCommandException() {
        Index index = Index.fromOneBased(SampleDataUtil.ADDRESSBOOK_SIZE + 1);
        Set<Index> indexes = new HashSet<>();
        indexes.add(index);
        EventCommand eventCommand = new EventCommand(DEFAULT_NAME, DEFAULT_DATE, DEFAULT_LOCATION, indexes);

        ModelStubAcceptingEventAdded modelStub = new ModelStubAcceptingEventAdded();
        assertThrows(CommandException.class,
                Messages.MESSAGE_ATTENDEE_NOT_FOUND, () -> eventCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        EventCommand eventCommand1 = new EventCommand("event1", DEFAULT_DATE, DEFAULT_LOCATION, DEFAULT_INDEXES);
        EventCommand eventCommand2 = new EventCommand("event2", DEFAULT_DATE, DEFAULT_LOCATION, DEFAULT_INDEXES);

        // same object -> returns true
        assertTrue(eventCommand1.equals(eventCommand1));

        // same values -> returns true
        EventCommand eventCommandCopy = new EventCommand("event1", DEFAULT_DATE, DEFAULT_LOCATION, DEFAULT_INDEXES);
        assertTrue(eventCommand1.equals(eventCommandCopy));

        // different types -> returns false
        assertFalse(eventCommand1.equals(1));

        // null -> returns false
        assertFalse(eventCommand1.equals(null));

        // different event -> returns false
        assertFalse(eventCommand1.equals(eventCommand2));
    }

    @Test
    public void toStringMethod() {
        EventCommand eventCommand = new EventCommand(DEFAULT_NAME, DEFAULT_DATE, DEFAULT_LOCATION, DEFAULT_INDEXES);
        String expected = EventCommand.class.getCanonicalName()
                + "{eventName=" + DEFAULT_NAME + ", "
                + "eventDate=" + DEFAULT_DATE + ", "
                + "location=" + DEFAULT_LOCATION + ", "
                + "attendeeIndexes=" + DEFAULT_INDEXES + "}";
        assertEquals(expected, eventCommand.toString());
    }



    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithEvent extends CommandTestUtil.ModelStub {
        private final Event event;

        ModelStubWithEvent(Event event) {
            requireNonNull(event);
            this.event = event;
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return SampleDataUtil.getSampleAddressBook();
        }

        @Override
        public boolean hasEvent(Event event) {
            requireNonNull(event);
            return this.event.isSameEvent(event);
        }
    }

    /**
     * A Model stub that always accept the event being added.
     */
    private class ModelStubAcceptingEventAdded extends CommandTestUtil.ModelStub {
        final ArrayList<Event> eventsAdded = new ArrayList<>();

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return SampleDataUtil.getSampleAddressBook();
        }

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
        public ReadOnlyEventBook getEventBook() {
            return new EventBook();
        }
    }

}
