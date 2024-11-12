package seedu.eventfulnus.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.eventfulnus.commons.exceptions.DataLoadingException;
import seedu.eventfulnus.model.ReadOnlyAddressBook;
import seedu.eventfulnus.model.ReadOnlyUserPrefs;
import seedu.eventfulnus.model.UserPrefs;

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
