package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.EventBuilder.DEFAULT_DATE;
import static seedu.address.testutil.EventBuilder.DEFAULT_INDEXES;
import static seedu.address.testutil.EventBuilder.DEFAULT_NAME;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.EventBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyEventBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.testutil.EventBuilder;

public class EventCommandTest {

    @Test
    public void constructor_nullParameters_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EventCommand(null, null, null));

        // test if any field is null
        assertThrows(NullPointerException.class, () -> new EventCommand(null, DEFAULT_DATE, DEFAULT_INDEXES));
        assertThrows(NullPointerException.class, () -> new EventCommand(DEFAULT_NAME, null, DEFAULT_INDEXES));
        assertThrows(NullPointerException.class, () -> new EventCommand(DEFAULT_NAME, DEFAULT_DATE, null));
    }

    @Test
    public void execute_eventAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingEventAdded modelStub = new ModelStubAcceptingEventAdded();
        Event validEvent = new EventBuilder().buildWithNoAttendees();
        CommandResult commandResult =
                new EventCommand(DEFAULT_NAME, DEFAULT_DATE, DEFAULT_INDEXES).execute(modelStub);

        assertEquals(String.format(EventCommand.MESSAGE_SUCCESS, Messages.formatEvent(validEvent)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validEvent), modelStub.eventsAdded);
    }

    @Test
    public void execute_duplicateEvent_throwsCommandException() {
        Event validEvent = new EventBuilder().buildWithNoAttendees();
        EventCommand eventCommand = new EventCommand(DEFAULT_NAME, DEFAULT_DATE, DEFAULT_INDEXES);
        ModelStub modelStub = new ModelStubWithEvent(validEvent);

        assertThrows(CommandException.class,
                EventCommand.MESSAGE_DUPLICATE_EVENT, () -> eventCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        EventCommand eventCommand1 = new EventCommand("event1", DEFAULT_DATE, DEFAULT_INDEXES);
        EventCommand eventCommand2 = new EventCommand("event2", DEFAULT_DATE, DEFAULT_INDEXES);

        // same object -> returns true
        assertTrue(eventCommand1.equals(eventCommand1));

        // same values -> returns true
        EventCommand eventCommandCopy = new EventCommand("event1", DEFAULT_DATE, DEFAULT_INDEXES);
        assertTrue(eventCommand1.equals(eventCommandCopy));

        // different types -> returns false
        assertFalse(eventCommand1.equals(1));

        // null -> returns false
        assertFalse(eventCommand1.equals(null));

        // different person -> returns false
        assertFalse(eventCommand1.equals(eventCommand2));
    }

    @Test
    public void toStringMethod() {
        EventCommand eventCommand = new EventCommand(DEFAULT_NAME, DEFAULT_DATE, DEFAULT_INDEXES);
        String expected = EventCommand.class.getCanonicalName()
                + "{eventName=" + DEFAULT_NAME + ", "
                + "eventDate=" + DEFAULT_DATE + ", "
                + "attendeeIndexes=" + DEFAULT_INDEXES + "}";
        assertEquals(expected, eventCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addEvent(Event event) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasEvent(Event event) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteEvent(Event event) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateEvent(Event newEvent, int oldEventIndex) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Event> getEventList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Event> getFilteredEventList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int getEventListLength() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setEventBook(ReadOnlyEventBook eventBook) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyEventBook getEventBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredEventList(Predicate<Event> predicate) {
            throw new AssertionError("This method should not be called.");
        }


    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithEvent extends ModelStub {
        private final Event event;

        ModelStubWithEvent(Event event) {
            requireNonNull(event);
            this.event = event;
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
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
    private class ModelStubAcceptingEventAdded extends ModelStub {
        final ArrayList<Event> eventsAdded = new ArrayList<>();

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
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
