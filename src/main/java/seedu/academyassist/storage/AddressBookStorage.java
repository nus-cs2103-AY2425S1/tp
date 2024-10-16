package seedu.academyassist.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.academyassist.commons.exceptions.DataLoadingException;
import seedu.academyassist.model.AddressBook;
import seedu.academyassist.model.ReadOnlyAcademyAssist;

/**
 * Represents a storage for {@link AddressBook}.
 */
public interface AddressBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getAddressBookFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyAcademyAssist}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyAcademyAssist> readAddressBook() throws DataLoadingException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyAcademyAssist> readAddressBook(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyAcademyAssist} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyAcademyAssist addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyAcademyAssist)
     */
    void saveAddressBook(ReadOnlyAcademyAssist addressBook, Path filePath) throws IOException;

}
