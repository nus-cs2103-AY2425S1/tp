package seedu.ddd.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ddd.logic.commands.AddEventCommand.MESSAGE_DUPLICATE_EVENT;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_ID_AMY;
import static seedu.ddd.testutil.Assert.assertThrows;
import static seedu.ddd.testutil.EventBuilder.DEFAULT_CLIENT_ID_SET;
import static seedu.ddd.testutil.EventBuilder.DEFAULT_DESCRIPTION;
import static seedu.ddd.testutil.EventBuilder.DEFAULT_EVENT_ID;
import static seedu.ddd.testutil.EventBuilder.DEFAULT_VENDOR_ID_SET;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.ddd.commons.core.GuiSettings;
import seedu.ddd.logic.Messages;
import seedu.ddd.logic.commands.exceptions.CommandException;
import seedu.ddd.model.AddressBook;
import seedu.ddd.model.Model;
import seedu.ddd.model.ReadOnlyAddressBook;
import seedu.ddd.model.ReadOnlyUserPrefs;
import seedu.ddd.model.contact.common.Contact;
import seedu.ddd.model.contact.common.Id;
import seedu.ddd.model.event.common.Description;
import seedu.ddd.model.event.common.Event;
import seedu.ddd.testutil.ClientBuilder;
import seedu.ddd.testutil.EventBuilder;
import seedu.ddd.testutil.VendorBuilder;

public class AddEventCommandTest {
    @Test
    public void constructor_nullClientIds_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddEventCommand(null,
                DEFAULT_VENDOR_ID_SET, DEFAULT_DESCRIPTION, DEFAULT_EVENT_ID));
    }

    @Test
    public void constructor_nullVendorIds_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddEventCommand(DEFAULT_CLIENT_ID_SET,
                null, DEFAULT_DESCRIPTION, DEFAULT_EVENT_ID));
    }

    @Test
    public void constructor_nullDescription_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddEventCommand(DEFAULT_CLIENT_ID_SET,
                DEFAULT_VENDOR_ID_SET, null, DEFAULT_EVENT_ID));
    }

    @Test
    public void constructor_nullEventId_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddEventCommand(DEFAULT_CLIENT_ID_SET,
                DEFAULT_VENDOR_ID_SET, DEFAULT_DESCRIPTION, null));
    }

    @Test
    public void execute_eventAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingEventAdded modelStub = new ModelStubAcceptingEventAdded();
        Event validEvent = new EventBuilder().build();

        CommandResult commandResult = new AddEventCommand(DEFAULT_CLIENT_ID_SET,
                        DEFAULT_VENDOR_ID_SET, DEFAULT_DESCRIPTION, DEFAULT_EVENT_ID).execute(modelStub);

        assertEquals(String.format(AddEventCommand.MESSAGE_SUCCESS, Messages.format(validEvent)),
                commandResult.getFeedbackToUser());

        assertEquals(List.of(validEvent), modelStub.eventsAdded);
    }

    @Test
    public void execute_duplicateEvent_throwsCommandException() {
        AddEventCommand addEventCommand = new AddEventCommand(DEFAULT_CLIENT_ID_SET,
                DEFAULT_VENDOR_ID_SET, DEFAULT_DESCRIPTION, DEFAULT_EVENT_ID);
        ModelStub modelStub = new ModelStubWithEvent();

        assertThrows(CommandException.class,
                MESSAGE_DUPLICATE_EVENT, () -> addEventCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        AddEventCommand addEventOneCommand = new AddEventCommand(DEFAULT_CLIENT_ID_SET, DEFAULT_VENDOR_ID_SET,
                new Description("Description 1"), DEFAULT_EVENT_ID);
        AddEventCommand addEventTwoCommand = new AddEventCommand(DEFAULT_CLIENT_ID_SET, DEFAULT_VENDOR_ID_SET,
                new Description("Description 2"), DEFAULT_EVENT_ID);

        // same object -> returns true
        assertTrue(addEventOneCommand.equals(addEventOneCommand));

        // same values -> returns true
        AddEventCommand addEventOneCommandCopy = new AddEventCommand(DEFAULT_CLIENT_ID_SET, DEFAULT_VENDOR_ID_SET,
                new Description("Description 1"), DEFAULT_EVENT_ID);
        assertTrue(addEventOneCommand.equals(addEventOneCommandCopy));

        // different types -> returns false
        assertFalse(addEventOneCommand.equals(1));

        // null -> returns false
        assertFalse(addEventOneCommand.equals(null));

        // different person -> returns false
        assertFalse(addEventOneCommand.equals(addEventTwoCommand));
    }



    /**
     * A default model stub that have all the methods failing.
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
        public void addContact(Contact person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addEvent(Event event) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasContact(Contact person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasClientId(Id id) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasVendorId(Id id) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasEvent(Event event) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteContact(Contact target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteEvent(Event target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Contact getContact(Id id) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setContact(Contact target, Contact editedContact) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setEvent(Event target, Event editedEvent) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Contact> getFilteredContactList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Event> getFilteredEventList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredContactList(Predicate<Contact> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int getFilteredContactListSize() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int getFilteredEventListSize() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredEventList(Predicate<Event> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub with Amy, Bob and one singleEvent.
     */
    private class ModelStubWithEvent extends ModelStub {
        final ArrayList<Event> eventsAdded = new ArrayList<>(Collections.singletonList(new EventBuilder().build()));

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
        public boolean hasClientId(Id id) {
            requireNonNull(id);
            return id.equals(new Id(ClientBuilder.DEFAULT_ID));
        }

        @Override
        public boolean hasVendorId(Id id) {
            requireNonNull(id);
            return id.equals(new Id(VendorBuilder.DEFAULT_ID));
        }

        @Override
        public Contact getContact(Id id) {
            if (id.equals(new Id(VALID_ID_AMY))) {
                return new ClientBuilder().build();
            }
            return new VendorBuilder().build();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }


    /**
     * A Model stub with Amy and Bob.
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
        public boolean hasClientId(Id id) {
            requireNonNull(id);
            return id.equals(new Id(ClientBuilder.DEFAULT_ID));
        }

        @Override
        public boolean hasVendorId(Id id) {
            requireNonNull(id);
            return id.equals(new Id(VendorBuilder.DEFAULT_ID));
        }

        @Override
        public Contact getContact(Id id) {
            if (id.equals(new Id(VALID_ID_AMY))) {
                return new ClientBuilder().build();
            }
            return new VendorBuilder().build();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
