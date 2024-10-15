package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.person.Person;
import seedu.address.storage.Storage;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final Storage storage;
    private final FilteredList<Person> filteredPersons;

    /**
     * Initializes a ModelManager with the given address book, user preferences, and storage.
     *
     * @param addressBook The address book data to initialize the model with.
     * @param userPrefs   The user preferences to initialize the model with.
     * @param storage     The storage to be used by the model, for backup and restore operations.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs, Storage storage) {
        requireNonNull(addressBook);
        requireNonNull(userPrefs);

        logger.fine("Initializing with address book: " + addressBook
                + ", user prefs: " + userPrefs
                + ", and storage: " + storage);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.storage = storage; // Storage instance to handle backup and restore.
        this.filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs(), null);
    }

    // ============ User Preferences Methods ============================================================

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

    // ============ Address Book Methods ================================================================

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

    // ============ Filtered Person List Accessors =======================================================

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    // ============ Backup and Restore Methods ===========================================================

    /**
     * Backs up the AddressBook data to the specified file path.
     *
     * @param filePath The file path to save the backup.
     * @throws IOException If there is an error during the backup process.
     */
    @Override
    public void backupData(String filePath) throws IOException {
        requireNonNull(filePath);
        if (storage != null) {
            logger.info("Backing up data to: " + filePath);
            storage.saveAddressBook(this.getAddressBook(), Path.of(filePath));

            // Clean old backups, retaining only the most recent 5 backups
            storage.cleanOldBackups(5);
        } else {
            throw new IOException("Storage is not initialized!");
        }
    }

    /**
     * Restores the AddressBook from the most recent backup, if available.
     *
     * @return True if the restore was successful, false otherwise.
     * @throws IOException If there is an error during the restore process.
     */
    public boolean restoreFromBackup() throws IOException {
        if (storage != null) {
            Optional<Path> restoredPath = storage.restoreBackup();
            if (restoredPath.isPresent()) {
                try {
                    logger.info("Restored AddressBook from backup: " + restoredPath.get());
                    setAddressBook(storage.readAddressBook(restoredPath.get()).orElse(new AddressBook()));
                    return true;
                } catch (DataLoadingException e) {
                    logger.warning("Failed to load data from backup: " + e.getMessage());
                    throw new IOException("Error loading backup data", e);
                }
            } else {
                logger.warning("No backup found to restore.");
                return false;
            }
        } else {
            throw new IOException("Storage is not initialized!");
        }
    }


    /**
     * Cleans up old backups, retaining only the most recent `maxBackups`.
     *
     * @param maxBackups The number of backups to retain.
     * @throws IOException If there is an error during cleanup.
     */
    public void cleanOldBackups(int maxBackups) throws IOException {
        if (storage != null) {
            logger.info("Cleaning old backups, keeping the latest " + maxBackups + " backups.");
            storage.cleanOldBackups(maxBackups);
        } else {
            throw new IOException("Storage is not initialized!");
        }
    }

    // ============ Equality and Storage Access Methods ==================================================

    @Override
    public Storage getStorage() {
        return storage;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons);
    }
}
