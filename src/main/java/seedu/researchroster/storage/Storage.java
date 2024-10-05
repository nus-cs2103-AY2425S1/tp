package seedu.researchroster.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.researchroster.commons.exceptions.DataLoadingException;
import seedu.researchroster.model.ReadOnlyResearchRoster;
import seedu.researchroster.model.ReadOnlyUserPrefs;
import seedu.researchroster.model.UserPrefs;

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
    Optional<ReadOnlyResearchRoster> readAddressBook() throws DataLoadingException;

    @Override
    void saveAddressBook(ReadOnlyResearchRoster addressBook) throws IOException;

}
