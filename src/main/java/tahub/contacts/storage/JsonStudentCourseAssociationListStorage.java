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
import tahub.contacts.model.ReadOnlyAddressBook;
import tahub.contacts.model.course.UniqueCourseList;
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

    /**
     * Returns StudentCourseAssociationList data as a {@link StudentCourseAssociationList}.
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<StudentCourseAssociationList> readScaList(Path filePath, ReadOnlyAddressBook addressBook,
                                                              UniqueCourseList courseList) throws DataLoadingException {
        requireNonNull(filePath);
        requireNonNull(addressBook);
        requireNonNull(courseList);

        Optional<JsonSerializableStudentCourseAssociationList> jsonScaList = JsonUtil.readJsonFile(
                filePath, JsonSerializableStudentCourseAssociationList.class);
        if (jsonScaList.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonScaList.get().toModelType(addressBook, courseList));
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    public void saveScaList(StudentCourseAssociationList scaList) throws IOException {
        saveScaList(scaList, filePath);
    }

    /**
     * Similar to {@link #saveScaList(StudentCourseAssociationList)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveScaList(StudentCourseAssociationList scaList, Path filePath) throws IOException {
        requireNonNull(scaList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableStudentCourseAssociationList(scaList), filePath);
    }

}
