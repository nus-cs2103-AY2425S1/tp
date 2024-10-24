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
import tahub.contacts.model.studentcourseassociation.StudentCourseAssociationList;

/**
 * A class to access SCA list data stored as a json file on the hard disk.
 */
public class JsonStudentCourseAssociationListStorage {

    private static final Logger logger =
            LogsCenter.getLogger(JsonStudentCourseAssociationListStorage.class);

    private Path filePath;

    public JsonStudentCourseAssociationListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getScaListFilePath() {
        return filePath;
    }

    public Optional<StudentCourseAssociationList> readScaList() throws DataLoadingException {
        return readScaList(filePath);
    }

    /**
     * Similar to {@link #readScaList()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<StudentCourseAssociationList> readScaList(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableStudentCourseAssociationList> jsonScaList = JsonUtil.readJsonFile(
                filePath, JsonSerializableStudentCourseAssociationList.class);
        if (jsonScaList.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonScaList.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    public void saveScaList(StudentCourseAssociationList addressBook) throws IOException {
        saveScaList(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveScaList(StudentCourseAssociationList)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveScaList(StudentCourseAssociationList addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableStudentCourseAssociationList(addressBook), filePath);
    }

}
