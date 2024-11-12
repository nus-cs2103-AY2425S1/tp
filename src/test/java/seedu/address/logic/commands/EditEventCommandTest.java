package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditEventCommand.EditEventDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDescription;
import seedu.address.model.event.EventName;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.testutil.EventBuilder;

public class EditEventCommandTest {

    @Test
    public void constructor_nullDescriptor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EditEventCommand(null, null));
    }

    @Test
    public void execute_allFieldsSpecified_success() throws Exception {
        Event originalEvent = new EventBuilder().build();
        ModelStubWithEvent modelStub = new ModelStubWithEvent(originalEvent);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String startDate = LocalDate.of(2024, 10, 2).format(formatter);
        String endDate = LocalDate.of(2024, 10, 11).format(formatter);

        Event editedEvent = new EventBuilder(originalEvent)
                .withEventName("Updated Event")
                .withEventDescription("Updated Description")
                .withEventDuration(startDate, endDate)
                .build();

        EditEventDescriptor descriptor = new EditEventDescriptor();
        descriptor.setName(new EventName("Updated Event"));
        descriptor.setDescription(new EventDescription("Updated Description"));
        descriptor.setDuration(LocalDate.of(2024, 10, 2), LocalDate.of(2024, 10, 11));

        Index targetIndex = Index.fromZeroBased(0);
        EditEventCommand editEventCommand = new EditEventCommand(targetIndex, descriptor);

        CommandResult commandResult = editEventCommand.execute(modelStub);

        String expectedFormattedEvent = String.format(
                "%s; Description: %s; From: %s; To: %s",
                editedEvent.getEventName(),
                editedEvent.getEventDescription(),
                editedEvent.getEventStartDate(),
                editedEvent.getEventEndDate()
        );

        String expectedMessage = String.format("Event edited: %s", expectedFormattedEvent);

        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
        assertEquals(editedEvent, modelStub.getFilteredEventList().get(targetIndex.getZeroBased()));
    }

    @Test
    public void execute_noFieldSpecified_throwsCommandException() {
        Event originalEvent = new EventBuilder().build();
        ModelStubWithEvent modelStub = new ModelStubWithEvent(originalEvent);

        EditEventDescriptor descriptor = new EditEventDescriptor();
        Index targetIndex = Index.fromZeroBased(0);
        EditEventCommand editEventCommand = new EditEventCommand(targetIndex, descriptor);

        Executable executable = () -> editEventCommand.execute(modelStub);

        assertThrows(CommandException.class, executable, EditEventCommand.MESSAGE_NO_CHANGES);
    }

    @Test
    public void execute_eventNotFound_throwsCommandException() {
        // Create a ModelStub that does not contain the event
        ModelStub modelStub = new ModelStubWithoutEvent();

        EditEventDescriptor descriptor = new EditEventDescriptor();
        descriptor.setName(new EventName("Updated Event"));
        descriptor.setDescription(new EventDescription("Updated Description"));
        descriptor.setDuration(LocalDate.of(2024, 10, 2), LocalDate.of(2024, 10, 11));

        Index targetIndex = Index.fromZeroBased(0);

        // Expect a CommandException to be thrown
        assertThrows(CommandException.class, () -> new EditEventCommand(targetIndex, descriptor).execute(modelStub));
    }

    @Test
    public void equals_differentDescriptors_returnsFalse() {
        EditEventDescriptor descriptor1 = new EditEventDescriptor();
        descriptor1.setName(new EventName("Event 1"));

        EditEventDescriptor descriptor2 = new EditEventDescriptor();
        descriptor2.setName(new EventName("Event 2"));

        assertFalse(descriptor1.equals(descriptor2));
    }
    private class ModelStubWithoutEvent extends ModelStub {
        @Override
        public ObservableList<Event> getFilteredEventList() {
            return javafx.collections.FXCollections.observableArrayList();
        }
    }

    @Test
    public void equals() {
        Event event1 = new EventBuilder().withEventName("Event 1").build();
        Event event2 = new EventBuilder().withEventName("Event 2").build();
        EditEventDescriptor descriptor1 = new EditEventDescriptor();
        descriptor1.setName(new EventName("Event 1"));
        EditEventDescriptor descriptor2 = new EditEventDescriptor();
        descriptor2.setName(new EventName("Event 2"));

        Index index1 = Index.fromZeroBased(0);
        Index index2 = Index.fromZeroBased(1);

        EditEventCommand editEvent1Command = new EditEventCommand(index1, descriptor1);
        EditEventCommand editEvent2Command = new EditEventCommand(index2, descriptor2);

        // same object -> returns true
        assertTrue(editEvent1Command.equals(editEvent1Command));

        // same values -> returns true
        EditEventCommand editEvent1CommandCopy = new EditEventCommand(index1, descriptor1);
        assertTrue(editEvent1Command.equals(editEvent1CommandCopy));

        // different types -> returns false
        assertFalse(editEvent1Command.equals(1));

        // null -> returns false
        assertFalse(editEvent1Command.equals(null));

        // different event -> returns false
        assertFalse(editEvent1Command.equals(editEvent2Command));
    }

    /**
     * A Model stub that contains a single event.
     */
    private class ModelStubWithEvent extends ModelStub {
        private List<Event> events = new ArrayList<>();

        ModelStubWithEvent(Event event) {
            Objects.requireNonNull(event);
            this.events.add(event);
        }

        @Override
        public ObservableList<Event> getFilteredEventList() {
            return javafx.collections.FXCollections.observableArrayList(events);
        }

        @Override
        public void setEvent(Event target, Event editedEvent) {
            Objects.requireNonNull(target);
            Objects.requireNonNull(editedEvent);

            int index = events.indexOf(target);
            if (index != -1) {
                events.set(index, editedEvent);
            }
        }
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
        public List<Person> findPersonsWithName(Name name) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Person> getPersonList() {
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
        public boolean hasEvent(Event event) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteEvent(Event target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addEvent(Event event) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Event> findEventsWithName(EventName eventName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Event> getFilteredEventList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredEventList(Predicate<Event> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void assignEventToPerson(Person person, Event event) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void unassignEventFromPerson(Person person, Event event) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int generateNewPersonId() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int generateNewEventId() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasEventById(int id) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Event getEventById(int id) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setEvent(Event target, Event editedEvent) {
            throw new AssertionError("This method should not be called.");
        }
    }
}
