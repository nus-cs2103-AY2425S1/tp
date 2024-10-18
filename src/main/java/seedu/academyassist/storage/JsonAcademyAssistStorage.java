package seedu.academyassist.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.academyassist.commons.core.LogsCenter;
import seedu.academyassist.commons.exceptions.DataLoadingException;
import seedu.academyassist.commons.exceptions.IllegalValueException;
import seedu.academyassist.commons.util.FileUtil;
import seedu.academyassist.commons.util.JsonUtil;
import seedu.academyassist.model.ReadOnlyAcademyAssist;

/**
 * A class to access AddressBook data stored as a json file on the hard disk.
 */
public class JsonAcademyAssistStorage implements AcademyAssistStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonAcademyAssistStorage.class);

    private Path filePath;

    public JsonAcademyAssistStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getAcademyAssistFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyAcademyAssist> readAcademyAssist() throws DataLoadingException {
        return readAcademyAssist(filePath);
    }

    /**
     * Similar to {@link AcademyAssistStorage#readAcademyAssist()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyAcademyAssist> readAcademyAssist(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableAcademyAssist> jsonAcademyAssist = JsonUtil.readJsonFile(
                filePath, JsonSerializableAcademyAssist.class);
        if (!jsonAcademyAssist.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonAcademyAssist.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveAcademyAssist(ReadOnlyAcademyAssist addressBook) throws IOException {
        saveAcademyAssist(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveAcademyAssist(ReadOnlyAcademyAssist)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveAcademyAssist(ReadOnlyAcademyAssist addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableAcademyAssist(addressBook), filePath);
    }

}
