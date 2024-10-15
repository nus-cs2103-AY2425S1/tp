package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;
import seedu.address.storage.Storage;


/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss-SSS");
    private static final long MIN_BACKUP_INTERVAL_MS = 1000; // 1 second debounce


    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final Storage storage;
    private final FilteredList<Person> filteredPersons;
    private long lastBackupTime = 0; // Store the last backup timestamp
    private String lastBackupHash = ""; // Store the hash of the last backed-up content

    /**
     * Initializes a ModelManager with the given address book, user preferences, and storage.
     *
     * @param addressBook The address book data to initialize the model with.
     * @param userPrefs   The user preferences to initialize the model with.
     * @param storage     The storage to be used by the model for backup and restore operations.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs, Storage storage) {
        requireNonNull(addressBook);
        requireNonNull(userPrefs);

        logger.fine("Initializing with address book: " + addressBook
                + ", user prefs: " + userPrefs
                + ", and storage: " + storage);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.storage = storage;
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
        triggerBackup(); // Trigger backup after setting new data
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
        triggerBackup(); // Trigger backup after deletion
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        triggerBackup(); // Trigger backup after addition
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);
        addressBook.setPerson(target, editedPerson);
        triggerBackup(); // Trigger backup after editing
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
     * Backs up the AddressBook data to a timestamped backup file.
     */
    private void triggerBackup() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastBackupTime < MIN_BACKUP_INTERVAL_MS) {
            return; // Skip backup if triggered too soon
        }

        String currentHash = computeAddressBookHash();
        if (currentHash.equals(lastBackupHash)) {
            logger.info("No changes detected. Skipping backup.");
            return; // Skip backup if the content hasn't changed
        }

        lastBackupTime = currentTime;
        lastBackupHash = currentHash;

        try {
            backupData(null); // Use default path for backup
        } catch (IOException e) {
            logger.warning("Failed to create backup: " + e.getMessage());
        }
    }

    /**
     * Backs up the AddressBook data to the specified file path or default path.
     *
     * @param filePath The file path to save the backup, or null to use the default path.
     * @throws IOException If there is an error during the backup process.
     */
    @Override
    public void backupData(String filePath) throws IOException {
        if (storage == null) {
            throw new IOException("Storage is not initialized!");
        }

        // Use the default backups directory if no path is provided
        String backupDir = "backups";
        Files.createDirectories(Path.of(backupDir)); // Ensure the directory exists

        // Generate a timestamp-based filename for the backup
        String timestamp = LocalDateTime.now().format(FORMATTER);
        String backupPath = backupDir + "/addressbook-backup-" + timestamp + ".json";

        logger.info("Backing up data to: " + backupPath);
        storage.saveAddressBook(getAddressBook(), Path.of(backupPath));

        // Clean old backups, retaining only the latest 10 backups
        cleanOldBackups(10);
    }

    /**
     * Computes a hash of the current AddressBook data to detect changes.
     */
    private String computeAddressBookHash() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(addressBook.toString().getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            logger.warning("Hash algorithm not found: " + e.getMessage());
            return "";
        }
    }

    /**
     * Cleans up old backups, retaining only the latest `maxBackups`.
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
