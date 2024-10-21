package keycontacts.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import keycontacts.commons.exceptions.DataLoadingException;
import keycontacts.model.ReadOnlyStudentDirectory;
import keycontacts.model.StudentDirectory;

/**
 * Represents a storage for {@link StudentDirectory}.
 */
public interface StudentDirectoryStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getStudentDirectoryFilePath();

    /**
     * Returns StudentDirectory data as a {@link ReadOnlyStudentDirectory}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyStudentDirectory> readStudentDirectory() throws DataLoadingException;

    /**
     * @see #getStudentDirectoryFilePath()
     */
    Optional<ReadOnlyStudentDirectory> readStudentDirectory(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyStudentDirectory} to the storage.
     * @param studentDirectory cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveStudentDirectory(ReadOnlyStudentDirectory studentDirectory) throws IOException;

    /**
     * @see #saveStudentDirectory(ReadOnlyStudentDirectory)
     */
    void saveStudentDirectory(ReadOnlyStudentDirectory studentDirectory, Path filePath) throws IOException;

}
