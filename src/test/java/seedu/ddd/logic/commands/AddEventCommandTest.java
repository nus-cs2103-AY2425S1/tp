package seedu.ddd.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ddd.logic.commands.AddEventCommand.MESSAGE_DUPLICATE_EVENT;
import static seedu.ddd.testutil.Assert.assertThrows;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_CLIENT_ID;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_VENDOR_ID;
import static seedu.ddd.testutil.event.TypicalEventFields.DEFAULT_EVENT_CLIENT_CONTACT_ID_SET;
import static seedu.ddd.testutil.event.TypicalEventFields.DEFAULT_EVENT_DATE;
import static seedu.ddd.testutil.event.TypicalEventFields.DEFAULT_EVENT_DESCRIPTION;
import static seedu.ddd.testutil.event.TypicalEventFields.DEFAULT_EVENT_ID;
import static seedu.ddd.testutil.event.TypicalEventFields.DEFAULT_EVENT_NAME;
import static seedu.ddd.testutil.event.TypicalEventFields.DEFAULT_EVENT_VENDOR_CONTACT_ID_SET;
import static seedu.ddd.testutil.event.TypicalEventFields.VALID_EVENT_DESCRIPTION_1;
import static seedu.ddd.testutil.event.TypicalEventFields.VALID_EVENT_DESCRIPTION_2;
import static seedu.ddd.testutil.event.TypicalEvents.VALID_EVENT;

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
import seedu.ddd.model.Displayable;
import seedu.ddd.model.Model;
import seedu.ddd.model.ReadOnlyAddressBook;
import seedu.ddd.model.ReadOnlyUserPrefs;
import seedu.ddd.model.common.Id;
import seedu.ddd.model.contact.common.Contact;
import seedu.ddd.model.event.common.Description;
import seedu.ddd.model.event.common.Event;
import seedu.ddd.testutil.contact.ClientBuilder;
import seedu.ddd.testutil.contact.VendorBuilder;
import seedu.ddd.testutil.event.EventBuilder;

public class AddEventCommandTest {

    @Test
    public void constructor_nullName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddEventCommand(
            null,
            DEFAULT_EVENT_DESCRIPTION,
            DEFAULT_EVENT_DATE,
            DEFAULT_EVENT_CLIENT_CONTACT_ID_SET,
            DEFAULT_EVENT_VENDOR_CONTACT_ID_SET,
            DEFAULT_EVENT_ID
        ));
    }

    @Test
    public void constructor_nullDescription_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddEventCommand(
            DEFAULT_EVENT_NAME,
            null,
            DEFAULT_EVENT_DATE,
            DEFAULT_EVENT_CLIENT_CONTACT_ID_SET,
            DEFAULT_EVENT_VENDOR_CONTACT_ID_SET,
            DEFAULT_EVENT_ID
        ));
    }

    @Test
    public void constructor_nullDate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddEventCommand(
            DEFAULT_EVENT_NAME,
            DEFAULT_EVENT_DESCRIPTION,
            null,
            DEFAULT_EVENT_CLIENT_CONTACT_ID_SET,
            DEFAULT_EVENT_VENDOR_CONTACT_ID_SET,
            DEFAULT_EVENT_ID
        ));
    }


    @Test
    public void constructor_nullClientIds_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddEventCommand(
            DEFAULT_EVENT_NAME,
            DEFAULT_EVENT_DESCRIPTION,
            DEFAULT_EVENT_DATE,
            null,
            DEFAULT_EVENT_VENDOR_CONTACT_ID_SET,
            DEFAULT_EVENT_ID
        ));
    }

    @Test
    public void constructor_nullVendorIds_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddEventCommand(
            DEFAULT_EVENT_NAME,
            DEFAULT_EVENT_DESCRIPTION,
            DEFAULT_EVENT_DATE,
            DEFAULT_EVENT_CLIENT_CONTACT_ID_SET,
            null,
            DEFAULT_EVENT_ID
        ));
    }

    @Test
    public void constructor_nullEventId_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddEventCommand(
            DEFAULT_EVENT_NAME,
            DEFAULT_EVENT_DESCRIPTION,
            DEFAULT_EVENT_DATE,
            DEFAULT_EVENT_CLIENT_CONTACT_ID_SET,
            DEFAULT_EVENT_VENDOR_CONTACT_ID_SET,
            null
        ));
    }

    @Test
    public void execute_eventAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingEventAdded modelStub = new ModelStubAcceptingEventAdded();
        CommandResult commandResult = new AddEventCommand(
            DEFAULT_EVENT_NAME,
            DEFAULT_EVENT_DESCRIPTION,
            DEFAULT_EVENT_DATE,
            DEFAULT_EVENT_CLIENT_CONTACT_ID_SET,
            DEFAULT_EVENT_VENDOR_CONTACT_ID_SET,
            DEFAULT_EVENT_ID
        ).execute(modelStub);

        Event validEvent = new EventBuilder().build();
        String expectedMessage = String.format(AddEventCommand.MESSAGE_SUCCESS, Messages.format(validEvent));
        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
        assertEquals(List.of(validEvent), modelStub.eventsAdded);
    }

    @Test
    public void execute_duplicateEvent_throwsCommandException() {
        ModelStub modelStub = new ModelStubWithEvent();
        AddEventCommand addEventCommand = new AddEventCommand(
            VALID_EVENT.getName(),
            DEFAULT_EVENT_DESCRIPTION,
            DEFAULT_EVENT_DATE,
            DEFAULT_EVENT_CLIENT_CONTACT_ID_SET,
            DEFAULT_EVENT_VENDOR_CONTACT_ID_SET,
            DEFAULT_EVENT_ID
        );
        assertThrows(CommandException.class,
                MESSAGE_DUPLICATE_EVENT, () -> addEventCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Description description1 = new Description(VALID_EVENT_DESCRIPTION_1);
        AddEventCommand addEventCommand1 = new AddEventCommand(
            DEFAULT_EVENT_NAME,
            description1,
            DEFAULT_EVENT_DATE,
            DEFAULT_EVENT_CLIENT_CONTACT_ID_SET,
            DEFAULT_EVENT_VENDOR_CONTACT_ID_SET,
            DEFAULT_EVENT_ID
        );
        Description description2 = new Description(VALID_EVENT_DESCRIPTION_2);
        AddEventCommand addEventCommand2 = new AddEventCommand(
            DEFAULT_EVENT_NAME,
            description2,
            DEFAULT_EVENT_DATE,
            DEFAULT_EVENT_CLIENT_CONTACT_ID_SET,
            DEFAULT_EVENT_VENDOR_CONTACT_ID_SET,
            DEFAULT_EVENT_ID
        );

        // same object -> returns true
        assertTrue(addEventCommand1.equals(addEventCommand1));

        // same values -> returns true
        AddEventCommand addEventCommand3 = new AddEventCommand(
            DEFAULT_EVENT_NAME,
            description1,
            DEFAULT_EVENT_DATE,
            DEFAULT_EVENT_CLIENT_CONTACT_ID_SET,
            DEFAULT_EVENT_VENDOR_CONTACT_ID_SET,
            DEFAULT_EVENT_ID
        );
        assertTrue(addEventCommand1.equals(addEventCommand3));

        // different types -> returns false
        assertFalse(addEventCommand1.equals(1));

        // null -> returns false
        assertFalse(addEventCommand1.equals(null));

        // different person -> returns false
        assertFalse(addEventCommand1.equals(addEventCommand2));
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
        public boolean hasClientId(Id contactId) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasVendorId(Id contactId) {
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
        public Contact getContact(Id contactId) {
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
        public ObservableList<Displayable> getDisplayedList() {
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
        public boolean hasClientId(Id contactId) {
            requireNonNull(contactId);
            return contactId.equals(new Id(VALID_CLIENT_ID));
        }

        @Override
        public boolean hasVendorId(Id contactId) {
            requireNonNull(contactId);
            return contactId.equals(new Id(VALID_VENDOR_ID));
        }

        @Override
        public Contact getContact(Id contactId) {
            if (contactId.equals(new Id(VALID_CLIENT_ID))) {
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
        public boolean hasClientId(Id contactId) {
            requireNonNull(contactId);
            return contactId.equals(new Id(VALID_CLIENT_ID));
        }

        @Override
        public boolean hasVendorId(Id contactId) {
            requireNonNull(contactId);
            return contactId.equals(new Id(VALID_VENDOR_ID));
        }

        @Override
        public Contact getContact(Id contactId) {
            if (contactId.equals(new Id(VALID_CLIENT_ID))) {
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
