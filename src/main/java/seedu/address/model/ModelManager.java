package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.client.Client;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.property.Property;
import seedu.address.storage.JsonClientBookStorage;
import seedu.address.storage.JsonMeetingBookStorage;
import seedu.address.storage.JsonPropertyBookStorage;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final UserPrefs userPrefs;
    private final PropertyBook propertyBook;
    private final ClientBook clientBook;
    private final MeetingBook meetingBook;
    private final FilteredList<Property> filteredProperties;
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

    /**
     * A wrapper for the display mode of the application.
     * <p>
     * The initial display mode is set to CLIENTS to ensure that the client grid
     * displays both buyers and sellers upon startup, which aligns with the expected
     * behavior of the application.
     */
    private final ReadOnlyObjectWrapper<DisplayMode> displayMode = new ReadOnlyObjectWrapper<>(DisplayMode.CLIENTS);

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyUserPrefs userPrefs,
                        ReadOnlyPropertyBook propertyBook, ReadOnlyClientBook clientBook,
                        ReadOnlyMeetingBook meetingBook) {
        requireAllNonNull(userPrefs, propertyBook, clientBook);

        logger.fine("Initializing with client book: " + clientBook + " and user prefs "
                + userPrefs + " and property book " + propertyBook + " and meeting book " + meetingBook);

        this.propertyBook = new PropertyBook(propertyBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.clientBook = new ClientBook(clientBook);
        this.meetingBook = new MeetingBook(meetingBook);

        this.filteredClients = new FilteredList<>(this.clientBook.getClientList());
        this.filteredProperties = new FilteredList<>(this.propertyBook.getPropertyList());
        this.filteredMeetings = new FilteredList<>(this.meetingBook.getMeetingList());
    }

    public ModelManager() {
        this(new UserPrefs(), new PropertyBook(), new ClientBook(), new MeetingBook());
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
    public Path getClientBookFilePath() {
        return userPrefs.getClientBookFilePath();
    }

    @Override
    public void setClientBookFilePath(Path clientBookFilePath) {
        requireNonNull(clientBookFilePath);
        userPrefs.setClientBookFilePath(clientBookFilePath);
    }

    @Override
    public Path getMeetingBookFilePath() {
        return userPrefs.getMeetingBookFilePath();
    }

    @Override
    public void setMeetingBookFilePath(Path meetingBookFilePath) {
        requireNonNull(meetingBookFilePath);
        userPrefs.setMeetingBookFilePath(meetingBookFilePath);
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
        return clientBook.equals(otherModelManager.clientBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && propertyBook.equals(otherModelManager.propertyBook)
                && meetingBook.equals(otherModelManager.meetingBook);
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
    public ReadOnlyObjectProperty<DisplayMode> getReadOnlyDisplayMode() {
        return displayMode.getReadOnlyProperty();
    }

    // Update display mode using the wrapper, which remains modifiable internally
    @Override
    public void setDisplayClients() {
        displayMode.set(DisplayMode.CLIENTS);
        logger.info("Setting Display Mode to \"CLIENTS\"");
    }

    @Override
    public void setDisplayProperties() {
        displayMode.set(DisplayMode.PROPERTIES);
        logger.info("Setting Display Mode to \"PROPERTIES\"");
    }

    @Override
    public void setDisplayMeetings() {
        displayMode.set(DisplayMode.MEETINGS);
        logger.info("Setting Display Mode to \"MEETINGS\"");
    }
}
