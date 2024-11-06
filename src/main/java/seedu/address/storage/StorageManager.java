package seedu.address.storage;

import static seedu.address.model.profile.Profile.extractProfileNameFromPathOrThrow;
import static seedu.address.model.profile.Profile.isValidProfileFromPath;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.profile.Profile;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private final AddressBookStorage addressBookStorage;
    private final UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code AddressBookStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(AddressBookStorage addressBookStorage, UserPrefsStorage userPrefsStorage) {
        this.addressBookStorage = addressBookStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    @Override
    public void deleteOrphanedProfiles(ReadOnlyUserPrefs userPrefs) throws IOException {
        Path currentProfilePath = userPrefs.getAddressBookFilePath();
        assert isValidProfileFromPath(currentProfilePath)
                : "Profile deletion should not happen when the current profile path is invalid";
        String curProfileName = extractProfileNameFromPathOrThrow(currentProfilePath);

        HashSet<Profile> profiles = userPrefs.getProfiles();
        profiles.add(new Profile(curProfileName));
        Stream<Path> profilePaths = FileUtil.getSiblingsAtPath(currentProfilePath);

        profilePaths
                .flatMap(Profile::extractNameFromPathOrIgnore)
                .map(Profile::new)
                .filter(profileName -> !profiles.contains(profileName))
                .map(Profile::toPath)
                .forEach(profile -> {
                    try {
                        FileUtil.deleteFileAtPath(profile);
                    } catch (IOException e) {
                        logger.severe(e.getMessage());
                    }
                });
    }
    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ AddressBook methods ==============================

    @Override
    public Path getAddressBookFilePath() {
        return addressBookStorage.getAddressBookFilePath();
    }

    @Override
    public void updateAddressBookFilePath(Path filePath) {
        addressBookStorage.updateAddressBookFilePath(filePath);
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataLoadingException {
        return readAddressBook(addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return addressBookStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveAddressBook(addressBook, filePath);
    }

}
