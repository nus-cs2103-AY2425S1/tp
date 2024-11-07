package seedu.address.logic.commands.eventcommands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEvents.TYPICAL_EVENT_A;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.exceptions.VolunteerDeleteMissingDateException;
import seedu.address.model.exceptions.VolunteerDuplicateDateException;
import seedu.address.model.volunteer.Volunteer;
import seedu.address.testutil.EventBuilder;

public class EventNewCommandTest {

    @Test
    public void constructor_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EventNewCommand(null));
    }

    @Test
    public void execute_eventAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingEventAdded modelStub = new ModelStubAcceptingEventAdded();
        Event validEvent = new EventBuilder().build();

        CommandResult commandResult = new EventNewCommand(validEvent).execute(modelStub);

        assertEquals(String.format(EventNewCommand.MESSAGE_SUCCESS, Messages.format(validEvent)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validEvent), modelStub.eventsAdded);
    }

    @Test
    public void execute_duplicateEvent_throwsCommandException() {
        Event validEvent = new EventBuilder().build();
        EventNewCommand eventNewCommand = new EventNewCommand(validEvent);
        ModelStub modelStub = new ModelStubWithEvent(validEvent);

        assertThrows(CommandException.class, EventNewCommand.MESSAGE_DUPLICATE_EVENT, () ->
                eventNewCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Event eventFoodCollection = new EventBuilder().withEventName("Food Collection").build();
        Event eventFoodDrive = new EventBuilder().withEventName("Food Drive").build();
        EventNewCommand eventNewCommandFoodCollection = new EventNewCommand(eventFoodCollection);
        EventNewCommand eventNewCommandFoodDrive = new EventNewCommand(eventFoodDrive);

        // same object -> returns true
        assertTrue(eventNewCommandFoodCollection.equals(eventNewCommandFoodCollection));

        // same values -> returns true
        EventNewCommand eventNewCommandFoodCollectionCopy = new EventNewCommand(eventFoodCollection);
        assertTrue(eventNewCommandFoodCollection.equals(eventNewCommandFoodCollectionCopy));

        // different types -> returns false
        assertFalse(eventNewCommandFoodCollection.equals(1));

        // null -> returns false
        assertFalse(eventNewCommandFoodCollection.equals(null));

        // different event -> returns false
        assertFalse(eventNewCommandFoodCollection.equals(eventNewCommandFoodDrive));
    }

    @Test
    public void toStringMethod() {
        EventNewCommand eventNewCommand = new EventNewCommand(TYPICAL_EVENT_A);
        String expected = "EventNewCommand[toAdd=" + TYPICAL_EVENT_A + "]";
        assertEquals(expected, eventNewCommand.toString());
    }

    /**
     * A default model stub that has all of the methods failing.
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
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasVolunteer(Volunteer volunteer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasEvent(Event event) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteVolunteer(Volunteer target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteEvent(Event event) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetDisplayLists() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addVolunteer(Volunteer volunteer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addEvent(Event event) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setVolunteer(Volunteer volunteer, Volunteer editedVolunteer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Volunteer> getFilteredVolunteerList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Event> getFilteredEventList() {
            return FXCollections.observableArrayList();
        }

        @Override
        public void updateFilteredVolunteerList(Predicate<Volunteer> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredEventList(Predicate<Event> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Volunteer getVolunteer(int volunteerId) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Event getEvent(int eventId) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean filterEventsByName(Predicate<Event> predicate) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void viewEvent(Event eventToView) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void viewVolunteer(Volunteer volunteerToView) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void assignVolunteerToEvent(Volunteer volunteer, Event event) throws CommandException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void unassignVolunteerFromEvent(Volunteer volunteer, Event event) throws CommandException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addDatesToVolunteer(Volunteer volunteerToAddDate, String dateList) throws
                VolunteerDuplicateDateException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeDatesFromVolunteer(Volunteer volunteerToRemoveDate, String dateList) throws
                VolunteerDeleteMissingDateException {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public boolean filterVolunteersByName(Predicate<Volunteer> predicate) {
            throw new AssertionError("This method should not be called.");
        }
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
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
