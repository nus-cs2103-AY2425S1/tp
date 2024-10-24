package tahub.contacts.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import tahub.contacts.commons.exceptions.DataLoadingException;
import tahub.contacts.model.course.UniqueCourseList;

/**
 * Interface of the CourseListStorage component
 */
public interface CourseListStorage {
    Optional<UniqueCourseList> readCourseList() throws DataLoadingException;

    Optional<UniqueCourseList> readCourseList(Path filePath) throws DataLoadingException;

    void saveCourseList(UniqueCourseList courseList) throws IOException;

    void saveCourseList(UniqueCourseList courseList, Path filePath) throws IOException;
}
