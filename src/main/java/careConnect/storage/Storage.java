package careConnect.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import careConnect.commons.exceptions.DataLoadingException;
import careConnect.model.ReadOnlyAddressBook;
import careConnect.model.ReadOnlyUserPrefs;
import careConnect.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getAddressBookFilePath();

    @Override
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataLoadingException;

    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

}
