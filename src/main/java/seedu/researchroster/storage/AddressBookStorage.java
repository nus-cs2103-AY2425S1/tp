package seedu.researchroster.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.researchroster.commons.exceptions.DataLoadingException;
import seedu.researchroster.model.ReadOnlyResearchRoster;
import seedu.researchroster.model.ResearchRoster;

/**
 * Represents a storage for {@link ResearchRoster}.
 */
public interface AddressBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getAddressBookFilePath();

    /**
     * Returns ResearchRoster data as a {@link ReadOnlyResearchRoster}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyResearchRoster> readAddressBook() throws DataLoadingException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyResearchRoster> readAddressBook(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyResearchRoster} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyResearchRoster addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyResearchRoster)
     */
    void saveAddressBook(ReadOnlyResearchRoster addressBook, Path filePath) throws IOException;

}
