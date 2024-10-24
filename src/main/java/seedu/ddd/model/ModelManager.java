package seedu.ddd.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.ddd.commons.core.GuiSettings;
import seedu.ddd.commons.core.LogsCenter;
import seedu.ddd.commons.util.CollectionUtil;
import seedu.ddd.model.contact.common.Contact;
import seedu.ddd.model.contact.common.Id;
import seedu.ddd.model.event.common.Event;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Contact> filteredContacts;
    private final FilteredList<Event> filteredEvents;
    private final ObservableList<Displayable> displayedList = FXCollections.observableArrayList();
    private final ObservableList<Displayable> internalUnmodifiableDisplayedList =
            FXCollections.unmodifiableObservableList(displayedList);

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        CollectionUtil.requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredContacts = new FilteredList<>(this.addressBook.getContactList());
        filteredEvents = new FilteredList<>(this.addressBook.getEventList());

        // listen for changes in the original lists and update the combined list
        filteredContacts.addListener((ListChangeListener<Contact>) change -> {
            displayedList.clear();
            displayedList.addAll(filteredContacts);
        });
        filteredEvents.addListener((ListChangeListener<Event>) change -> {
            displayedList.clear();
            displayedList.addAll(filteredEvents);
        });

        // populate the initial displayed list with contacts only
        displayedList.addAll(filteredContacts);
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }


    @Override
    public boolean hasContact(Contact contact) {
        requireNonNull(contact);
        return addressBook.hasContact(contact);
    }

    @Override
    public boolean hasClientId(Id id) {
        requireNonNull(id);
        return addressBook.hasClientId(id);
    }

    @Override
    public boolean hasVendorId(Id id) {
        requireNonNull(id);
        return addressBook.hasVendorId(id);
    }

    @Override
    public Contact getContact(Id id) {
        requireNonNull(id);
        return addressBook.getContact(id);
    }

    @Override
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return addressBook.hasEvent(event);
    }

    @Override
    public void deleteContact(Contact target) {
        addressBook.removeContact(target);
    }

    @Override
    public void deleteEvent(Event target) {
        addressBook.removeEvent(target);
    }

    @Override
    public void addContact(Contact contact) {
        addressBook.addContact(contact);
        updateFilteredContactList(PREDICATE_SHOW_ALL_CONTACTS);
    }

    @Override
    public void addEvent(Event event) {
        addressBook.addEvent(event);
        updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
    }

    @Override
    public void setContact(Contact target, Contact editedContact) {
        CollectionUtil.requireAllNonNull(target, editedContact);

        addressBook.setContact(target, editedContact);
    }

    @Override
    public void setEvent(Event target, Event editedEvent) {
        CollectionUtil.requireAllNonNull(target, editedEvent);
        addressBook.setEvent(target, editedEvent);
    }

    @Override
    public int getFilteredEventListSize() {
        return filteredEvents.size();
    }

    //=========== Filtered Data List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Contact} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Contact> getFilteredContactList() {
        return filteredContacts;
    }

    @Override
    public void updateFilteredContactList(Predicate<Contact> predicate) {
        requireNonNull(predicate);
        filteredContacts.setPredicate(predicate);
    }

    @Override
    public ObservableList<Event> getFilteredEventList() {
        return filteredEvents;
    }

    @Override
    public void updateFilteredEventList(Predicate<Event> predicate) {
        requireNonNull(predicate);
        filteredEvents.setPredicate(predicate);
    }

    @Override
    public ObservableList<Displayable> getDisplayedList() {
        return internalUnmodifiableDisplayedList;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredContacts.equals(otherModelManager.filteredContacts);
    }

    @Override
    public int getFilteredContactListSize() {
        return getFilteredContactList().size();
    }

}
