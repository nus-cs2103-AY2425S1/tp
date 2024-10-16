package seedu.academyassist.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.academyassist.commons.exceptions.DataLoadingException;
import seedu.academyassist.model.ReadOnlyAcademyAssist;
import seedu.academyassist.model.ReadOnlyUserPrefs;
import seedu.academyassist.model.UserPrefs;

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
    Optional<ReadOnlyAcademyAssist> readAddressBook() throws DataLoadingException;

    @Override
    void saveAddressBook(ReadOnlyAcademyAssist addressBook) throws IOException;

}
