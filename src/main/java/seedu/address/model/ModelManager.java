package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.client.Client;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.person.Person;
import seedu.address.model.property.Property;
import seedu.address.storage.JsonClientBookStorage;
import seedu.address.storage.JsonMeetingBookStorage;
import seedu.address.storage.JsonPropertyBookStorage;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final PropertyBook propertyBook;
    private final ClientBook clientBook;
    private final MeetingBook meetingBook;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Property> filteredProperties;
    // note that filteredClients may be removed if we decide not to keep the filtering feature
    private final FilteredList<Client> filteredClients;
    private final FilteredList<Meeting> filteredMeetings;

    private Path clientBookFilePath = Paths.get("data" , "clientbook.json");
    private Path propertyBookFilePath = Paths.get("data" , "propertybook.json");
    private Path meetingBookFilePath = Paths.get("data" , "meetingbook.json");

    /* To determine type of cards to display */
    /**
     * Enum representing the different types of records that can be displayed
     * in the application's UI. This enum is used to determine which set of
     * cards (clients, meetings, or properties) should be shown to the user.
     *
     * <p>Each value corresponds to a specific type of data:
     * <ul>
     *     <li>{@link #CLIENTS} - Displays the list of clients.</li>
     *     <li>{@link #MEETINGS} - Displays the list of scheduled meetings.</li>
     *     <li>{@link #PROPERTIES} - Displays the list of properties.</li>
     * </ul>
     *
     * The {@code DisplayMode} enum helps in controlling the UI state and
     * ensures that only one type of data is shown at any given time.
     */
    public enum DisplayMode {
        /**
         * Represents the mode for displaying the list of clients.
         */
        CLIENTS,

        /**
         * Represents the mode for displaying the list of meetings.
         */
        MEETINGS,

        /**
         * Represents the mode for displaying the list of properties.
         */
        PROPERTIES
    }

    private final ObjectProperty<DisplayMode> displayMode = new SimpleObjectProperty<>(DisplayMode.CLIENTS);

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs,
                        ReadOnlyPropertyBook propertyBook, ReadOnlyClientBook clientBook,
                        ReadOnlyMeetingBook meetingBook) {
        requireAllNonNull(addressBook, userPrefs, propertyBook, clientBook);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs "
                + userPrefs + " and property prefs " + propertyBook + " and client book " + clientBook);

        this.addressBook = new AddressBook(addressBook);
        this.propertyBook = new PropertyBook(propertyBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.clientBook = new ClientBook(clientBook);
        this.meetingBook = new MeetingBook(meetingBook);
        this.filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        this.filteredClients = new FilteredList<>(this.clientBook.getClientList());
        this.filteredProperties = new FilteredList<>(this.propertyBook.getPropertyList());
        this.filteredMeetings = new FilteredList<>(this.meetingBook.getMeetingList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs(), new PropertyBook(), new ClientBook(), new MeetingBook());
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

    @Override
    public Path getClientBookFilePath() {
        return userPrefs.getClientBookFilePath();
    }

    @Override
    public void setClientBookFilePath(Path clientBookFilePath) {
        requireNonNull(clientBookFilePath);
        userPrefs.setAddressBookFilePath(clientBookFilePath);
    }

    @Override
    public Path getMeetingBookFilePath() {
        return userPrefs.getMeetingBookFilePath();
    }

    @Override
    public void setMeetingBookFilePath(Path meetingBookFilePath) {
        requireNonNull(meetingBookFilePath);
        userPrefs.setAddressBookFilePath(meetingBookFilePath);
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
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }
    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }
    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }
    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);
        addressBook.setPerson(target, editedPerson);
    }
    //=========== Filtered Person List Accessors =============================================================
    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== ClientBook ================================================================================

    @Override
    public void setClientBook(ReadOnlyClientBook clientBook) {
        this.clientBook.resetData(clientBook);
    }

    @Override
    public ReadOnlyClientBook getClientBook() {
        return clientBook;
    }

    @Override
    public boolean hasClient(Client client) {
        requireNonNull(client);
        return clientBook.hasClient(client);
    }

    @Override
    public void deleteClient(Client target) {
        clientBook.removeClient(target);
        try {
            JsonClientBookStorage jsonClientBookStorage = new JsonClientBookStorage(clientBookFilePath);
            jsonClientBookStorage.saveClientBook(clientBook);
        } catch (IOException e) {
            System.out.println("Error while saving ClientBook: " + e.getMessage());
        }
    }

    @Override
    public void addClient(Client client) {
        clientBook.addClient(client);
        updateFilteredClientList(PREDICATE_SHOW_ALL_CLIENTS);
    }

    @Override
    public void setClient(Client target, Client editedClient) {
        requireAllNonNull(target, editedClient);

        clientBook.setClient(target, editedClient);
    }

    //=========== Filtered Client List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Client} backed by the internal list of
     * {@code versionedClientBook}
     */
    @Override
    public ObservableList<Client> getFilteredClientList() {
        return filteredClients;
    }

    @Override
    public void updateFilteredClientList(Predicate<Client> predicate) {
        requireNonNull(predicate);
        filteredClients.setPredicate(predicate);
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
                && filteredPersons.equals(otherModelManager.filteredPersons);
    }

    //=========== Property =============================================================
    @Override
    public Path getPropertyBookFilePath() {
        return userPrefs.getPropertyBookFilePath();
    }

    @Override
    public void setPropertyBookFilePath(Path propertyBookFilePath) {
        requireNonNull(propertyBookFilePath);
        userPrefs.setPropertyBookFilePath(propertyBookFilePath);
    }

    @Override
    public void deleteProperty(Property target) {
        propertyBook.removeProperty(target);
        try {
            JsonPropertyBookStorage jsonPropertyBookStorage = new JsonPropertyBookStorage(propertyBookFilePath);
            jsonPropertyBookStorage.savePropertyBook(propertyBook);
        } catch (IOException e) {
            System.out.println("Error while saving PropertyBook: " + e.getMessage());
        }
    }

    @Override
    public void addProperty(Property property) {
        propertyBook.addProperty(property);
    }

    @Override
    public boolean hasProperty(Property property) {
        requireNonNull(property);
        return propertyBook.hasProperty(property);
    }

    @Override
    public ReadOnlyPropertyBook getPropertyBook() {
        return propertyBook;
    }

    //=========== Filtered Property List Accessors =============================================================
    /**
     * Returns an unmodifiable view of the list of {@code Property} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Property> getFilteredPropertyList() {
        return filteredProperties;
    }

    @Override
    public void updateFilteredPropertyList(Predicate<Property> predicate) {
        requireNonNull(predicate);
        filteredProperties.setPredicate(predicate);
    }

    //=========== MeetingBook ================================================================================
    @Override
    public void setMeetingBook(ReadOnlyMeetingBook meetingBook) {
        this.meetingBook.resetData(meetingBook);
    }
    @Override
    public ReadOnlyMeetingBook getMeetingBook() {
        return meetingBook;
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        meetingBook.removeMeeting(meeting);
        try {
            JsonMeetingBookStorage jsonMeetingBookStorage = new JsonMeetingBookStorage(meetingBookFilePath);
            jsonMeetingBookStorage.saveMeetingBook(meetingBook);
        } catch (IOException e) {
            System.out.println("Error while saving MeetingBook: " + e.getMessage());
        }
    }

    @Override
    public boolean hasMeeting(Meeting meeting) {
        requireNonNull(meeting);
        return meetingBook.hasMeeting(meeting);
    }

    @Override
    public void addMeeting(Meeting meeting) {
        meetingBook.addMeeting(meeting);
        updateFilteredMeetingList(PREDICATE_SHOW_ALL_MEETINGS);
    }
    //=========== Filtered Meeting List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Meeting} backed by the internal list of
     * {@code versionedMeetingBook}
     */
    @Override
    public ObservableList<Meeting> getFilteredMeetingList() {
        return filteredMeetings;
    }

    @Override
    public void updateFilteredMeetingList(Predicate<Meeting> predicate) {
        requireNonNull(predicate);
        filteredMeetings.setPredicate(predicate);
    }

    //=========== Managing UI  ==================================================================================
    @Override
    public ObjectProperty<DisplayMode> getDisplayMode() {
        return this.displayMode;
    }

    @Override
    public void setDisplayClients() {
        this.displayMode.set(DisplayMode.CLIENTS);
    }

    @Override
    public void setDisplayProperties() {
        this.displayMode.set(DisplayMode.PROPERTIES);
    }

    @Override
    public void setDisplayMeetings() {
        this.displayMode.set(DisplayMode.MEETINGS);
    }
}
