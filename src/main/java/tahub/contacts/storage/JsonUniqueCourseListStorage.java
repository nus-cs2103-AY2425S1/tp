package tahub.contacts.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import tahub.contacts.commons.core.LogsCenter;
import tahub.contacts.commons.exceptions.DataLoadingException;
import tahub.contacts.commons.exceptions.IllegalValueException;
import tahub.contacts.commons.util.FileUtil;
import tahub.contacts.commons.util.JsonUtil;
import tahub.contacts.model.course.UniqueCourseList;

/**
 * A class to access Course data stored as a json file on the hard disk.
 */
public class JsonUniqueCourseListStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonUniqueCourseListStorage.class);

    private Path filePath;

    public JsonUniqueCourseListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getCourseListFilePath() {
        return filePath;
    }

    public Optional<UniqueCourseList> readCourseList() throws DataLoadingException {
        return readCourseList(filePath);
    }

    /**
     * Similar to {@link #readCourseList()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<UniqueCourseList> readCourseList(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableCourseList> jsonCourseList = JsonUtil.readJsonFile(
                filePath, JsonSerializableCourseList.class);
        if (jsonCourseList.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonCourseList.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    public void saveCourseList(UniqueCourseList addressBook) throws IOException {
        saveCourseList(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveCourseList(UniqueCourseList)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveCourseList(UniqueCourseList addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableCourseList(addressBook), filePath);
    }

}
