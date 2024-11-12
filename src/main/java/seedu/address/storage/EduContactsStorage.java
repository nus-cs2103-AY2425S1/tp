package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.EduContacts;
import seedu.address.model.ReadOnlyEduContacts;

/**
 * Represents a storage for {@link EduContacts}.
 */
public interface EduContactsStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getEduContactsFilePath();

    /**
     * Returns EduContacts data as a {@link ReadOnlyEduContacts}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyEduContacts> readEduContacts() throws DataLoadingException;

    /**
     * @see #getEduContactsFilePath()
     */
    Optional<ReadOnlyEduContacts> readEduContacts(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyEduContacts} to the storage.
     * @param eduContacts cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveEduContacts(ReadOnlyEduContacts eduContacts) throws IOException;

    /**
     * @see #saveEduContacts(ReadOnlyEduContacts)
     */
    void saveEduContacts(ReadOnlyEduContacts eduContacts, Path filePath) throws IOException;

}
