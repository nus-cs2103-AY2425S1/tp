package keycontacts.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import keycontacts.commons.core.LogsCenter;
import keycontacts.commons.exceptions.DataLoadingException;
import keycontacts.commons.exceptions.IllegalValueException;
import keycontacts.commons.util.FileUtil;
import keycontacts.commons.util.JsonUtil;
import keycontacts.model.ReadOnlyStudentDirectory;

/**
 * A class to access StudentDirectory data stored as a json file on the hard disk.
 */
public class JsonStudentDirectoryStorage implements StudentDirectoryStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonStudentDirectoryStorage.class);

    private Path filePath;

    public JsonStudentDirectoryStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getStudentDirectoryFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyStudentDirectory> readStudentDirectory() throws DataLoadingException {
        return readStudentDirectory(filePath);
    }

    /**
     * Similar to {@link #readStudentDirectory()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyStudentDirectory> readStudentDirectory(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableStudentDirectory> jsonStudentDirectory = JsonUtil.readJsonFile(
                filePath, JsonSerializableStudentDirectory.class);
        if (!jsonStudentDirectory.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonStudentDirectory.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveStudentDirectory(ReadOnlyStudentDirectory studentDirectory) throws IOException {
        saveStudentDirectory(studentDirectory, filePath);
    }

    /**
     * Similar to {@link #saveStudentDirectory(ReadOnlyStudentDirectory)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveStudentDirectory(ReadOnlyStudentDirectory studentDirectory, Path filePath) throws IOException {
        requireNonNull(studentDirectory);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableStudentDirectory(studentDirectory), filePath);
    }

}
